package cn.strong.leke.diag.report.unit;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;

import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.diag.model.StuSubjScore;
import cn.strong.leke.diag.model.report.ScoreRank;
import cn.strong.leke.diag.model.report.StuScoreRptView.SubjScore;
import cn.strong.leke.diag.util.ScoreUtils;
import cn.strong.leke.remote.model.tukor.SubjectRemote;

/**
 * 综合分析：学科成绩对比
 * @author  andy
 * @since   v1.0.0
 */
public class StuScoreCompareLogicalUnit implements LogicalUnit<List<SubjScore>> {

	@Override
	public List<SubjScore> execute(LogicalContext context) {
		ScoreRank scoreRank = context.getValue("scoreRank");
		List<ScoreRank> scoreRanks = context.getValue("scoreRanks");
		List<StuSubjScore> prevScores = context.getValue("prevScores");

		Set<Long> subjectIds = scoreRanks.stream().flatMap(
				rank -> rank.keySet().stream().filter(v -> v instanceof Long).map(v -> (Long) v).filter(v -> v > 0))
				.distinct().collect(toSet());

		Map<Long, Double> prevScoreMap = this.calcSubjAvgScore(prevScores);

		List<SubjectRemote> subjects = SubjectContext.findSubjects(new ArrayList<>(subjectIds));
		return subjects.stream().map(subj -> {
			SubjScore subjScore = new SubjScore();
			subjScore.setLabel(subj.getSubjectName());
			Double prev = prevScoreMap.get(subj.getSubjectId()), self = scoreRank.getSubjScore(subj.getSubjectId());
			if (prev != null) {
				subjScore.setPrev(ScoreUtils.toFixed(prev, 1));
			}
			if (self != null) {
				subjScore.setSelf(ScoreUtils.toFixed(self, 1));
			}
			Double klass = scoreRanks.stream().map(v -> v.getSubjScore(subj.getSubjectId())).filter(v -> v != null)
					.collect(averagingDouble(v -> v));
			subjScore.setKlass(ScoreUtils.toFixed(klass, 1));
			return subjScore;
		}).collect(toList());
	}

	/**
	 * 计算学科平均成绩。
	 * 如果传为的数据为多学科，结果为综合成绩。
	 * @param scores
	 * @return
	 */
	private Map<Long, Double> calcSubjAvgScore(List<StuSubjScore> scores) {
		if (scores == null) {
			return Maps.newHashMap();
		}
		return scores.stream().collect(groupingBy(StuSubjScore::getSubjectId, averagingDouble(StuSubjScore::getScore)));
	}
}
