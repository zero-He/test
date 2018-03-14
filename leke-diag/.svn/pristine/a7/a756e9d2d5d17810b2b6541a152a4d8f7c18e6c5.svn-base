package cn.strong.leke.diag.report.unit;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import cn.strong.leke.diag.model.StuSubjScore;
import cn.strong.leke.diag.model.report.ReportCycle;
import cn.strong.leke.diag.model.report.TrendModel;
import cn.strong.leke.diag.util.ScoreUtils;

/**
 * 成绩成长走势
 * @author  andy
 * @since   v1.0.0
 */
public class ScoreTrendLogicalUnit implements LogicalUnit<TrendModel> {

	private static Boolean inCycle(Date date, ReportCycle cycle) {
		return date.getTime() >= cycle.getStart().getTime() && date.getTime() <= cycle.getEnd().getTime();
	}

	@Override
	public TrendModel execute(LogicalContext context) {
		List<ReportCycle> cycles = context.getValue("cycles");
		List<StuSubjScore> scores = context.getValue("scores");
		Long studentId = context.getValue("studentId");
		String label = context.getValue("label");
		Boolean isUnit = context.getValue("isUnit");

		List<String> names = Lists.newArrayList();
		List<Double> klass = !isUnit ? Lists.newArrayList() : null;
		List<Double> selfs = studentId != null ? Lists.newArrayList() : null;
		
		for (ReportCycle cycle : cycles) {
			List<StuSubjScore> clss = scores.stream().filter(v -> inCycle(v.getDate(), cycle)).collect(toList());
			names.add(cycle.getLabel());
			if (!isUnit) {
				klass.add(this.calcClassAvgScore(clss));
			}
			if (studentId != null) {
				List<StuSubjScore> stus = clss.stream().filter(v -> v.getUserId().equals(studentId)).collect(toList());
				selfs.add(this.calcSelfAvgScore(stus));
			}
		}

		TrendModel trendModel = new TrendModel();
		trendModel.setLabel(label);
		trendModel.setNames(names);
		trendModel.setKlass(klass);
		trendModel.setSelfs(selfs);
		
		return trendModel;
	}

	/**
	 * 计算班级平均成绩。
	 * 如果传为的数据为多学科，结果为综合成绩。
	 * @param scores
	 * @return
	 */
	private Double calcClassAvgScore(List<StuSubjScore> scores) {
		if (scores.isEmpty()) {
			return null;
		}
		// 按人分组数据
		Collection<List<StuSubjScore>> stus = scores.stream().collect(groupingBy(StuSubjScore::getUserId)).values();
		Double value = stus.stream().map(list -> calcSelfAvgScore(list)).collect(averagingDouble(v -> v));
		return ScoreUtils.toFixed(value, 1);
	}

	/**
	 * 计算学生平均成绩。
	 * 如果传为的数据为多学科，结果为综合成绩。
	 * @param scores
	 * @return
	 */
	private Double calcSelfAvgScore(List<StuSubjScore> scores) {
		if (scores.isEmpty()) {
			return null;
		}
		Map<Long, Double> subjScores = scores.stream()
				.collect(groupingBy(StuSubjScore::getSubjectId, averagingDouble(StuSubjScore::getScore)));
		Double value = subjScores.values().stream().collect(averagingDouble(v -> v));
		return ScoreUtils.toFixed(value, 1);
	}
}
