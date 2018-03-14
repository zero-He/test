package cn.strong.leke.diag.report.unit;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.diag.model.StuSubjScore;
import cn.strong.leke.diag.model.report.ScoreRank;
import cn.strong.leke.diag.util.ScoreUtils;
import cn.strong.leke.model.user.UserBase;

/**
 * 班级学科成绩排行榜。
 * @author  andy
 * @since   v1.0.0
 */
public class SubjScoreRankLogicalUnit implements LogicalUnit<List<ScoreRank>> {

	@Override
	public List<ScoreRank> execute(LogicalContext context) {
		List<StuSubjScore> scores = context.getValue("scores");
		List<UserBase> userBaseList = context.getValue("userBaseList");
		Collection<List<StuSubjScore>> stuScores = scores.parallelStream().collect(groupingBy(StuSubjScore::getUserId))
				.values();
		List<ScoreRank> scoreRanks = stuScores.parallelStream().map(list -> {
			StuSubjScore score = list.get(0);
			ScoreRank scoreRank = new ScoreRank();
			scoreRank.setUserId(score.getUserId());
			scoreRank.setUserName(score.getUserName());
			scoreRank.setScore(ScoreUtils.toFixed(list.stream().collect(averagingDouble(StuSubjScore::getScore)), 1));
			return scoreRank;
		}).sorted(Collections.reverseOrder(Comparator.comparing(v -> v.getScore()))).collect(toList());

		if (userBaseList != null) {
			Set<Long> scoreUserIds = scoreRanks.stream().map(ScoreRank::getUserId).collect(toSet());
			userBaseList.stream().filter(v -> !scoreUserIds.contains(v.getUserId())).forEach(v -> {
				ScoreRank scoreRank = new ScoreRank();
				scoreRank.setUserId(v.getUserId());
				scoreRank.setUserName(v.getUserName());
				scoreRank.setScore(null);
				scoreRanks.add(scoreRank);
			});
		}
		return scoreRanks;
	}

	public static void main(String[] args) {
		List<StuSubjScore> scores = new ArrayList<>();
		scores.add(createStuSubjScore(1L, "Andy", 1L, 20D));
		scores.add(createStuSubjScore(1L, "Andy", 1L, 45D));

		scores.add(createStuSubjScore(2L, "Jack", 1L, 33D));
		scores.add(createStuSubjScore(2L, "Jack", 1L, 49D));

		scores.add(createStuSubjScore(3L, "Sam", 1L, 29D));

		SubjScoreRankLogicalUnit logicalUnit = new SubjScoreRankLogicalUnit();
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
