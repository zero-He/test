package cn.strong.leke.diag.report.unit;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import cn.strong.leke.diag.model.StuQueStat;
import cn.strong.leke.diag.model.StuSubjScore;
import cn.strong.leke.diag.model.StuSubjWork;
import cn.strong.leke.diag.model.report.StuScoreRptView;

/**
 * 学生成绩分析：概要信息
 * @author  andy
 * @since   v1.0.0
 */
public class StuScoreSummaryLogicalUnit implements LogicalUnit<StuScoreRptView.Summary> {

	@Override
	public StuScoreRptView.Summary execute(LogicalContext context) {
		List<StuSubjWork> works = context.getValue("works");
		List<StuSubjScore> scores = context.getValue("scores");
		StuQueStat stuQueStat = context.getValue("stuQueStat");
		Long studentId = context.getValue("studentId");
		Long subjectId = context.getValue("subjectId");

		StuScoreRptView.Summary summary = new StuScoreRptView.Summary();
		// 完成情况
		summary.setTotalNum(works.stream().mapToInt(StuSubjWork::getTotalNum).sum());
		summary.setSubmitNum(works.stream().mapToInt(StuSubjWork::getSubmitNum).sum());
		summary.setDelayNum(works.stream().mapToInt(StuSubjWork::getDelayNum).sum());
		summary.setCorrectNum(works.stream().mapToInt(StuSubjWork::getCorrectNum).sum());
		// 成绩占比情况
		Stream<StuSubjScore> stream = scores.stream().filter(v -> v.getUserId().equals(studentId));
		if (subjectId > 0) {
			stream = stream.filter(v -> v.getSubjectId().equals(subjectId));
		}
		Map<String, Long> map = stream.collect(groupingBy(v -> MAPPER_SCORE_TO_LEVEL.apply(v.getScore()), counting()));
		summary.setLevelA(map.getOrDefault("A", 0L).intValue());
		summary.setLevelB(map.getOrDefault("B", 0L).intValue());
		summary.setLevelC(map.getOrDefault("C", 0L).intValue());
		summary.setLevelD(map.getOrDefault("D", 0L).intValue());
		summary.setLevelE(map.getOrDefault("E", 0L).intValue());
		// 答题情况
		if (stuQueStat != null) {
			summary.setQueNum(stuQueStat.getQueNum());
			summary.setErrNum(stuQueStat.getErrNum());
		} else {
			summary.setQueNum(0);
			summary.setErrNum(0D);
		}
		return summary;
	}
}
