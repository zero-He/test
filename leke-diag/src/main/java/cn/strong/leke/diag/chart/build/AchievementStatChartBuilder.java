package cn.strong.leke.diag.chart.build;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.core.business.chart.ChartBuilder;
import cn.strong.leke.core.business.chart.ChartRes;
import cn.strong.leke.core.business.chart.Data;
import cn.strong.leke.core.business.chart.LineChartRes;
import cn.strong.leke.diag.chart.model.AchievementStatChartReq;
import cn.strong.leke.diag.service.HomeworkService;
import cn.strong.leke.framework.spring.MessageSource;

/**
 * 学生成绩统计（教师、班主任、教务）
 * @author  andy
 * @created 2014年7月24日 下午3:04:20
 * @since   v1.0.0
 */
@Component("chart_achievementStat")
public class AchievementStatChartBuilder implements ChartBuilder<AchievementStatChartReq> {

	@Resource
	private HomeworkService homeworkService;

	@Override
	public ChartRes build(AchievementStatChartReq chartDto) {
		LineChartRes chartRes = new LineChartRes();
		Long studentId = chartDto.getStudentId();
		List<Map<String, Object>> list = this.homeworkService.findStudentScoreStat(chartDto.getSubjectId(),
				chartDto.getClassType(), chartDto.getClassId(), studentId, chartDto.getStartTime(),
				chartDto.getEndTime());
		chartRes.addRawData("dataList", list);
		if (list.isEmpty()) {
			chartRes.setErrorMessage(MessageSource.getMessage("diag.java.subAnalysis.nodata"));
			return chartRes;
		}

		Data xdata = new Data();
		Data maxdata = new Data();
		Data mindata = new Data();
		Data avgdata = new Data();
		Data scoreData = new Data();

		for (Map<String, Object> item : list) {
			xdata.addItem(item.get("homeworkName"));
			maxdata.addItem(toFixed(item.get("maxScore")));
			mindata.addItem(toFixed(item.get("minScore")));
			avgdata.addItem(toFixed(item.get("avgScore")));
			scoreData.addItem(toFixed(item.get("score")));
		}

		chartRes.addxAxis(xdata);
		chartRes.addSeries(maxdata);
		chartRes.addSeries(mindata);
		chartRes.addSeries(avgdata);
		chartRes.addSeries(scoreData);
		return chartRes;
	}
	
	private Double toFixed(Object obj){
		if (obj == null) {
			return 0d;
		} else {
			return new Double(new DecimalFormat("#.##").format(obj));
		}
	}
}
