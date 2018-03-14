package cn.strong.leke.diag.report.unit;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

import cn.strong.leke.diag.model.report.ClsSubjRptView;
import cn.strong.leke.diag.model.report.ClsSubjRptView.Summary;
import cn.strong.leke.diag.model.report.ScoreRank;
import cn.strong.leke.diag.util.ScoreUtils;

/**
 * 班级成绩分析：概要信息
 * @author  andy
 * @since   v1.0.0
 */
public class ClassSubjSummaryLogicalUnit implements LogicalUnit<ClsSubjRptView.Summary> {

	@Override
	public Summary execute(LogicalContext context) {
		List<ScoreRank> scoreRanks = context.getValue("scoreRanks");
		ClsSubjRptView.Summary summary = new ClsSubjRptView.Summary();
		summary.setTotalNum(scoreRanks.size());
		// 成绩平均情况
		DoubleSummaryStatistics dss = scoreRanks.stream().filter(v -> v.getScore() != null)
				.mapToDouble(v -> v.getScore()).summaryStatistics();
		summary.setAvgScore(ScoreUtils.toFixed(dss.getAverage(), 1));
		summary.setMaxScore(ScoreUtils.toFixed(dss.getMax(), 1));
		summary.setMinScore(ScoreUtils.toFixed(dss.getMin(), 1));
		// 成绩占比情况
		Map<String, Long> map = scoreRanks.stream()
				.collect(groupingBy(v -> MAPPER_SCORE_TO_LEVEL.apply(v.getScore()), counting()));
		summary.setLevelA(map.getOrDefault("A", 0L).intValue());
		summary.setLevelB(map.getOrDefault("B", 0L).intValue());
		summary.setLevelC(map.getOrDefault("C", 0L).intValue());
		summary.setLevelD(map.getOrDefault("D", 0L).intValue());
		summary.setLevelE(map.getOrDefault("E", 0L).intValue());
		summary.setOtherNum(map.getOrDefault("O", 0L).intValue());
		return summary;
	}
}
