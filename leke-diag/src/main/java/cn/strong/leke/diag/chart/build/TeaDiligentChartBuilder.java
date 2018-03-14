package cn.strong.leke.diag.chart.build;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.business.chart.BarChartRes;
import cn.strong.leke.core.business.chart.ChartBuilder;
import cn.strong.leke.core.business.chart.ChartRes;
import cn.strong.leke.core.business.chart.Data;
import cn.strong.leke.diag.chart.model.TeaDiligentChartReq;
import cn.strong.leke.diag.model.StuDiligentDto;
import cn.strong.leke.diag.service.HomeworkDtlService;
import cn.strong.leke.framework.spring.MessageSource;

/**
 * 作业勤奋报告(教师、班主任)
 * @author  andy
 * @created 2014年8月6日 下午2:00:33
 * @since   v1.0.0
 */
@Component("chart_teaDiligent")
public class TeaDiligentChartBuilder implements ChartBuilder<TeaDiligentChartReq> {

	@Resource
	private HomeworkDtlService homeworkDtlService;

	@Override
	public ChartRes build(TeaDiligentChartReq chartDto) {
		BarChartRes chartRes = new BarChartRes();

		Long teacherId = null;
		if (RoleCst.TEACHER.equals(UserContext.user.getCurrentRoleId())) {
			// 如果当前用户为老师，只能查看自已的作业
			teacherId = UserContext.user.getUserId();
		}
		Date startTime = null, endTime = null;
		if (chartDto.getStartTime() != null)
			startTime = DateUtils.toDayStartTime(chartDto.getStartTime());
		if (chartDto.getEndTime() != null)
			endTime = DateUtils.toDayEndTime(chartDto.getEndTime());
		List<StuDiligentDto> stuDiligentDtoList = this.homeworkDtlService.findStuDiligentDtoList(
				chartDto.getClassType(), chartDto.getClassId(), chartDto.getSubjectId(), teacherId, startTime, endTime);
		if (stuDiligentDtoList.isEmpty()) {
			chartRes.setErrorMessage(MessageSource.getMessage("diag.java.subAnalysis.nodata"));
			return chartRes;
		}

		Data xdata = new Data();
		Data normalData = new Data();
		Data delayData = new Data();
		Data unsubmitData = new Data();

		for (StuDiligentDto stuDiligentDto : stuDiligentDtoList) {
			xdata.addItem(stuDiligentDto.getStudentName());
			normalData.addItem(stuDiligentDto.getNormalRate());
			delayData.addItem(stuDiligentDto.getDelayRate());
			unsubmitData.addItem(stuDiligentDto.getUnsubmitRate());
		}

		chartRes.addxAxis(xdata);
		chartRes.addSeries(normalData);
		chartRes.addSeries(delayData);
		chartRes.addSeries(unsubmitData);
		chartRes.addRawData("dataList", stuDiligentDtoList);

		return chartRes;
	}
}
