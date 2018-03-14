package cn.strong.leke.diag.chart.build;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.core.business.chart.BarChartRes;
import cn.strong.leke.core.business.chart.ChartBuilder;
import cn.strong.leke.core.business.chart.ChartRes;
import cn.strong.leke.core.business.chart.Data;
import cn.strong.leke.diag.chart.model.StuGradeStatesChartReq;
import cn.strong.leke.diag.manage.SchoolStatsService;
import cn.strong.leke.diag.model.ClassStatsDto;
import cn.strong.leke.diag.service.HomeworkDtlService;

@Component("chart_stuGradeStates")
public class StuGradeStatesBuilder implements ChartBuilder<StuGradeStatesChartReq> {

	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private SchoolStatsService schoolStatsService;

	@Override
	public ChartRes build(StuGradeStatesChartReq chartDto) {
		//从 mongodb 中读取数据
		BarChartRes chartRes = new BarChartRes();
		//params
		Long schoolId = chartDto.getSchoolId();
		Long gradeId = chartDto.getGradeId();
		Long subjectId = chartDto.getSubjectId();
		//x轴 data
		Data xData = new Data();
		List<ClassStatsDto> dataList = schoolStatsService.findGradeClassScore(schoolId, gradeId, subjectId);
		if (dataList.size() == 0) {
			chartRes.setErrorMessage(NO_DATA_USED_DISPLAY);
			return chartRes;
		}
		//seriesData
		Data seriesDataSubmit = new Data();
		for (ClassStatsDto item : dataList) {
			xData.addItem(item.getClassName());
			seriesDataSubmit.addItem(item.getAvgScore() == null ? 0d : new DecimalFormat("#0.##").format(item.getAvgScore()));
		}
		chartRes.addxAxis(xData);
		chartRes.addSeries(seriesDataSubmit);
		return chartRes;
	}
}
