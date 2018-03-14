package cn.strong.leke.diag.chart.build;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.core.business.chart.ChartBuilder;
import cn.strong.leke.core.business.chart.ChartRes;
import cn.strong.leke.core.business.chart.Data;
import cn.strong.leke.core.business.chart.LineChartRes;
import cn.strong.leke.diag.chart.model.AchievementStatChartReq;
import cn.strong.leke.diag.service.HomeworkService;

/**
 * 学生成绩 个人成长曲线
 * @author  andy
 * @created 2014年7月24日 下午3:04:20
 * @since   v1.0.0
 */
@Component("chart_selfStatistical")
public class SelfStatisticalChartBuilder implements ChartBuilder<AchievementStatChartReq> {

	@Resource
	private HomeworkService homeworkService;

	@Override
	public ChartRes build(AchievementStatChartReq chartDto) {
		LineChartRes chartRes = new LineChartRes();

		Date startTime = DateUtils.toDayStartTime(chartDto.getStartTime());
		Date endTime = DateUtils.toDayEndTime(chartDto.getEndTime());
		List<Map<String, Object>> list = this.homeworkService.findStudentScoreSelf(chartDto.getStudentId(),chartDto.getSubjectId(), startTime, endTime);
		if (list.isEmpty()) {
			chartRes.setErrorMessage(NO_DATA_USED_DISPLAY);
			return chartRes;
		}
		Data xdata = new Data();
		Data maxdata = new Data();
		Data selfdata = new Data();
		Data avgdata = new Data();

		for (Map<String, Object> item : list) {
			xdata.addItem(item.get("homeworkName"));
			maxdata.addItem(convertToDecimal(item.get("maxScore")));
			selfdata.addItem(convertToDecimal(item.get("score")));
			avgdata.addItem(convertToDecimal(item.get("avgScore")));
		}
		
		
		chartRes.addxAxis(xdata);
		chartRes.addSeries(maxdata);
		chartRes.addSeries(avgdata);
		chartRes.addSeries(selfdata);
		chartRes.addRawData("dataList", list);
		
		return chartRes;
	}
	
	private BigDecimal convertToDecimal(Object obj) {
		BigDecimal v = new BigDecimal("0");
		if(obj != null){
			v = new BigDecimal(obj.toString());
		}
		return v;
	}
}
