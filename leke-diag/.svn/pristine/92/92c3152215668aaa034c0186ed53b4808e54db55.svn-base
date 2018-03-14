package cn.strong.leke.diag.chart.build;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.core.business.chart.BarChartRes;
import cn.strong.leke.core.business.chart.ChartBuilder;
import cn.strong.leke.core.business.chart.ChartRes;
import cn.strong.leke.core.business.chart.Data;
import cn.strong.leke.diag.chart.model.AchievementStatChartReq;
import cn.strong.leke.diag.manage.SchoolStatsService;
import cn.strong.leke.diag.model.SubjStatsDto;
import cn.strong.leke.diag.service.HomeworkDtlService;
import cn.strong.leke.diag.service.HomeworkService;

/**
 *
 * 描述:学生 学科优劣势分析
 *
 * @author  DuanYanming
 * @created 2014年7月31日 上午9:49:11
 * @since   v1.0.0
 */
@Component("chart_subjectScore")
public class SubjectScoreBuilder implements ChartBuilder<AchievementStatChartReq> {

	@Resource
	private HomeworkService homeworkService;

	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private SchoolStatsService schoolStatsService;

	@Override
	public ChartRes build(AchievementStatChartReq chartDto) {

		Data xData = new Data();
		Data stuData = new Data();
		Data maxData = new Data();
		Data avgData = new Data();
		BarChartRes chartRes = new BarChartRes();
		List<SubjStatsDto> dataList = schoolStatsService.findStudentSubjScore(chartDto.getStudentId());
		if(dataList == null || dataList.size() == 0){
			chartRes.setErrorMessage(NO_DATA_USED_DISPLAY);
			return chartRes;
		}
		for (SubjStatsDto item : dataList) {
			xData.addItem(item.getSubjectName());
			maxData.addItem(item.getMaxScore() == null ? 0d : new DecimalFormat("#0.##").format(item.getMaxScore()));
			stuData.addItem(item.getStuScore() == null ? 0d : new DecimalFormat("#0.##").format(item.getStuScore()));
			avgData.addItem(item.getAvgScore() == null ? 0d : new DecimalFormat("#0.##").format(item.getAvgScore()));
		}
		chartRes.addxAxis(xData);
		chartRes.addSeries(maxData);
		chartRes.addSeries(avgData);
		chartRes.addSeries(stuData);

		return chartRes;
	}
}
