package cn.strong.leke.diag.chart.build;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.core.business.chart.ChartBuilder;
import cn.strong.leke.core.business.chart.ChartRes;
import cn.strong.leke.core.business.chart.Data;
import cn.strong.leke.core.business.chart.LineChartRes;
import cn.strong.leke.diag.chart.model.AchievementStatTeacherChartReq;
import cn.strong.leke.diag.model.CourseSubjectHomeworkDetailsDto;
import cn.strong.leke.diag.model.CourseSubjectHomeworkStatisticsStudentsQueryDto;
import cn.strong.leke.diag.service.HomeworkService;
import cn.strong.leke.framework.spring.MessageSource;

@Component("chart_achievementStatTeacher")
public class AchievementStatTeacherChartBuilder implements ChartBuilder<AchievementStatTeacherChartReq> {

	@Resource
	private HomeworkService homeworkService;

	@Override
	public ChartRes build(AchievementStatTeacherChartReq chartDto) {
		LineChartRes chartRes = new LineChartRes();
		CourseSubjectHomeworkStatisticsStudentsQueryDto queryDto = new CourseSubjectHomeworkStatisticsStudentsQueryDto();
		queryDto.setClassId(chartDto.getCourseId());
		queryDto.setStartDate(chartDto.getStartTime());
		queryDto.setSubjectId(chartDto.getSubjectId());
		List<CourseSubjectHomeworkDetailsDto> dataList = homeworkService.findCourseSubjectDetails(queryDto);

		if (dataList.size() == 0) {
			chartRes.setErrorMessage(MessageSource.getMessage("diag.java.subAnalysis.nodata"));
			return chartRes;
		}
		//x 数据
		Data xdata = new Data();
		Data maxData = new Data();
		Data minData = new Data();
		Data avgData = new Data();
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		//组装数据
		for (CourseSubjectHomeworkDetailsDto item : dataList) {
			xdata.addItem(item.getHomeworkName());
			maxData.addItem(item.getMaxScore() == null ? 0f : new Double(decimalFormat.format(item
					.getMaxScore() * 100)));
			minData.addItem(item.getMinScore() == null ? 0f : new Double(decimalFormat.format(item
					.getMinScore() * 100)));
			avgData.addItem(item.getAvgScore() == null ? 0f : new Double(decimalFormat.format(item
					.getAvgScore() * 100)));
		}
		chartRes.addxAxis(xdata);
		chartRes.addSeries(maxData);
		chartRes.addSeries(minData);
		chartRes.addSeries(avgData);
		return chartRes;
	}
}
