package cn.strong.leke.diag.chart.build;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.core.business.chart.BarChartRes;
import cn.strong.leke.core.business.chart.ChartBuilder;
import cn.strong.leke.core.business.chart.ChartRes;
import cn.strong.leke.core.business.chart.Data;
import cn.strong.leke.diag.chart.model.stuAnalysisBarChartReq;
import cn.strong.leke.diag.manage.SchoolStatsService;
import cn.strong.leke.diag.model.SubjStatsDto;
import cn.strong.leke.diag.service.HomeworkDtlService;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;

@Component("chart_stuAnalysisBar")
public class stuAnalysisBarBuilder implements ChartBuilder<stuAnalysisBarChartReq> {

	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private SchoolStatsService schoolStatsService;

	@Override
	public ChartRes build(stuAnalysisBarChartReq chartDto) {
		BarChartRes chartRes = new BarChartRes();
		// 从mongodb 读取学生课程成绩分析 
		List<SubjStatsDto> dataList = schoolStatsService.findStudentSubjScore(chartDto.getStudentId());
		if (dataList == null || dataList.size() == 0) {
			chartRes.setErrorMessage(NO_DATA_USED_DISPLAY);
			return chartRes;
		}

		Data xdData = new Data();
		Data maxData = new Data();
		Data avgData = new Data();
		Data myData = new Data();
		
		DecimalFormat format = new DecimalFormat("#0.##");

		for (SubjStatsDto item : dataList) {
			xdData.addItem(item.getSubjectName());
			//数值格式化
			maxData.addItem(item.getMaxScore() == null ? 0d : format.format(item.getMaxScore()));
			avgData.addItem(item.getAvgScore() == null ? 0d : format.format(item.getAvgScore()));
			myData.addItem(item.getStuScore() == null ? 0d : format.format(item.getStuScore()));
		}
		chartRes.addxAxis(xdData);
		chartRes.addSeries(maxData);
		chartRes.addSeries(avgData);
		chartRes.addSeries(myData);

		return chartRes;
	}
}
