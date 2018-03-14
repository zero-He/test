package cn.strong.leke.diag.report.unit;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.diag.model.report.ScoreRank;
import cn.strong.leke.diag.model.report.StuScoreRptView;
import cn.strong.leke.diag.model.report.StuScoreRptView.SubjScore;

/**
 * 学生成绩分析：学科优劣分析。
 * @author  andy
 * @since   v1.0.0
 */
public class StuScoreOverallLogicalUnit implements LogicalUnit<StuScoreRptView.Overall> {

	@Override
	public StuScoreRptView.Overall execute(LogicalContext context) {
		List<SubjScore> subjScores = context.getValue("subjScores");
		List<Long> userIds = context.getValue("userIds");
		ScoreRank scoreRank = context.getValue("scoreRank");

		subjScores = subjScores.stream().filter(subj -> subj.getSelf() != null)
				.sorted(Comparator.comparingDouble(SubjScore::getSelf)).collect(toList());

		Double score = scoreRank.getScore();

		List<SubjScore> maxes1 = subjScores.stream().filter(v -> v.getSelf() > score + 5).collect(toList());
		List<SubjScore> mines1 = subjScores.stream().filter(v -> v.getSelf() < score - 5).collect(toList());
		if (maxes1.isEmpty()) {
			SubjScore subjScore = CollectionUtils.getLast(subjScores);
			if (subjScore.getSelf() > score) {
				maxes1.add(subjScore);
			}
		}
		if (mines1.isEmpty()) {
			SubjScore subjScore = CollectionUtils.getFirst(subjScores);
			if (subjScore.getSelf() < score) {
				mines1.add(subjScore);
			}
		}
		List<SubjScore> maxes2 = subjScores.stream().filter(v -> v.getSelf() > v.getKlass()).collect(toList());
		List<SubjScore> mines2 = subjScores.stream().filter(v -> v.getSelf() < v.getKlass()).collect(toList());

		StuScoreRptView.Overall overall = new StuScoreRptView.Overall();
		overall.setScore(scoreRank.getScore());
		overall.setRank(scoreRank.getIndex());
		overall.setTotal(userIds.size());
		overall.setMaxes1(maxes1.isEmpty() ? "暂无" : maxes1.stream().map(SubjScore::getLabel).collect(joining("、")));
		overall.setMines1(mines1.isEmpty() ? "暂无" : mines1.stream().map(SubjScore::getLabel).collect(joining("、")));
		overall.setMaxes2(maxes2.isEmpty() ? "暂无" : maxes2.stream().map(SubjScore::getLabel).collect(joining("、")));
		overall.setMines2(mines2.isEmpty() ? "暂无" : mines2.stream().map(SubjScore::getLabel).collect(joining("、")));

		return overall;
	}
}
