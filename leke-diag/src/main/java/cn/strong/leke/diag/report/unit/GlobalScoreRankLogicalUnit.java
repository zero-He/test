package cn.strong.leke.diag.report.unit;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.diag.model.StuSubjScore;
import cn.strong.leke.diag.model.report.ScoreRank;
import cn.strong.leke.diag.util.ScoreUtils;

/**
 * 班级综合成绩排行榜
 * @author  andy
 * @since   v1.0.0
 */
public class GlobalScoreRankLogicalUnit implements LogicalUnit<List<ScoreRank>> {

	@Override
	public List<ScoreRank> execute(LogicalContext context) {
		List<StuSubjScore> scores = context.getValue("scores");
		Long subjectId = context.getValue("subjectId");
		Collection<List<StuSubjScore>> stuScores = scores.parallelStream().collect(groupingBy(StuSubjScore::getUserId))
				.values();
		List<ScoreRank> scoreRanks = stuScores.parallelStream().map(list -> {
			StuSubjScore stuSubjScore = list.get(0);
			Map<Long, Double> subjs = list.stream()
					.collect(groupingBy(StuSubjScore::getSubjectId, averagingDouble(StuSubjScore::getScore)));
			Double score = subjs.values().stream().collect(averagingDouble(v -> v));

			ScoreRank scoreRank = new ScoreRank();
			scoreRank.setUserId(stuSubjScore.getUserId());
			scoreRank.setUserName(stuSubjScore.getUserName());
			scoreRank.setScore(ScoreUtils.toFixed(score, 1));
			subjs.forEach((subjId, value) -> scoreRank.setSubjScore(subjId, ScoreUtils.toFixed(value, 1)));
			return scoreRank;
		}).collect(toList());

		scoreRanks = scoreRanks.stream().filter(v -> v.getSubjScore(subjectId) != null)
				.sorted(Collections.reverseOrder(Comparator.comparing(v -> v.getSubjScore(subjectId))))
				.collect(toList());
		Double prev = 1000D;
		int index = 0;
		for (int i = 0; i < scoreRanks.size(); i++) {
			ScoreRank scoreRank = scoreRanks.get(i);
			Double score = scoreRank.getSubjScore(subjectId);
			if (score < prev) {
				index = i + 1;
				prev = score;
			}
			scoreRank.setIndex(index);
		}
		return scoreRanks;
	}

	public static void main(String[] args) {
		List<StuSubjScore> scores = new ArrayList<>();
		scores.add(createStuSubjScore(1L, "Andy", 1L, 0.20D));
		scores.add(createStuSubjScore(1L, "Andy", 1L, 0.45D));
		scores.add(createStuSubjScore(1L, "Andy", 2L, 0.25D));
		scores.add(createStuSubjScore(1L, "Andy", 3L, 0.67D));

		scores.add(createStuSubjScore(2L, "Jack", 1L, 0.33D));
		scores.add(createStuSubjScore(2L, "Jack", 1L, 0.49D));
		scores.add(createStuSubjScore(2L, "Jack", 3L, 0.90D));

		scores.add(createStuSubjScore(3L, "Sam", 1L, 0.29D));
		scores.add(createStuSubjScore(3L, "Sam", 2L, 0.38D));

		GlobalScoreRankLogicalUnit logicalUnit = new GlobalScoreRankLogicalUnit();
		LogicalContext context = new LogicalContext();
		context.put("scores", scores);
		Object result = logicalUnit.execute(context);

		System.out.println(JsonUtils.toJSON(result));
	}

	private static StuSubjScore createStuSubjScore(Long userId, String userName, Long subjectId, Double score) {
		StuSubjScore stuSubjScore = new StuSubjScore();
		stuSubjScore.setUserId(userId);
		stuSubjScore.setUserName(userName);
		stuSubjScore.setSubjectId(subjectId);
		stuSubjScore.setScore(score);
		return stuSubjScore;
	}
}
