package cn.strong.leke.diag.chart.build;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.core.business.chart.ChartBuilder;
import cn.strong.leke.core.business.chart.ChartRes;
import cn.strong.leke.core.business.chart.Data;
import cn.strong.leke.core.business.chart.PieChartRes;
import cn.strong.leke.diag.chart.model.CorrectStatChartReq;
import cn.strong.leke.diag.model.CorrectInfoDto;
import cn.strong.leke.diag.service.HomeworkService;
import cn.strong.leke.framework.spring.MessageSource;

/**
 * 教师作业批改统计。
 * @author  andy
 * @created 2014年8月6日 下午1:59:12
 * @since   v1.0.0
 */
@Component("chart_correctStat")
public class CorrectStatChartBuilder implements ChartBuilder<CorrectStatChartReq> {

	@Resource
	private HomeworkService homeworkService;

	@Override
	public ChartRes build(CorrectStatChartReq chartDto) {
		PieChartRes chartRes = new PieChartRes();

		Date startTime = DateUtils.toDayStartTime(chartDto.getStartTime());
		Date endTime = DateUtils.toDayEndTime(chartDto.getEndTime());
		List<CorrectInfoDto> correctInfoDtoList = homeworkService.findCorrectInfoDtoList(chartDto.getSubjectId(),
				chartDto.getTeacherId(),chartDto.getSchoolId(), startTime, endTime);
		chartRes.addRawData("dataList", correctInfoDtoList);
		
		if (correctInfoDtoList.isEmpty()) {
			chartRes.setErrorMessage(MessageSource.getMessage("diag.java.subAnalysis.nodata"));
			return chartRes;
		}

		int A = 0, B = 0, C = 0;
		for (CorrectInfoDto correctInfoDto : correctInfoDtoList) {
			if (correctInfoDto.getCorrectNum() >= correctInfoDto.getFinishNum()) {
				A++;
			} else if (correctInfoDto.getCorrectNum() > 0) {
				B++;
			} else {
				C++;
			}
		}

		Data data = new Data();

		Map<String, Object> item = new HashMap<String, Object>();
		item.put("name", MessageSource.getMessage("diag.java.subAnalysis.allcorrections"));
		item.put("value", A);
		data.addItem(item);

		item = new HashMap<String, Object>();
		item.put("name", MessageSource.getMessage("diag.java.subAnalysis.partcorrection"));
		item.put("value", B);
		data.addItem(item);

		item = new HashMap<String, Object>();
		item.put("name", MessageSource.getMessage("diag.java.subAnalysis.nocorrection"));
		item.put("value", C);
		data.addItem(item);

		chartRes.addSeries(data);

		return chartRes;
	}
}
