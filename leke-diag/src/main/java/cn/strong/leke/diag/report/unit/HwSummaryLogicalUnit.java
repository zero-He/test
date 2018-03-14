package cn.strong.leke.diag.report.unit;

import static java.util.stream.Collectors.averagingInt;

import java.util.List;

import cn.strong.leke.diag.model.Homework;
import cn.strong.leke.diag.model.HomeworkDtl;
import cn.strong.leke.diag.model.report.HwRptView;

/**
 * 单份作业分析：基本信息分析
 * @author  andy
 * @since   v1.0.0
 */
public class HwSummaryLogicalUnit implements LogicalUnit<HwRptView.Summary> {

	@Override
	public HwRptView.Summary execute(LogicalContext context) {
		Homework homework = context.getValue("homework");
		HomeworkDtl homeworkDtl = context.getValue("homeworkDtl");
		List<HomeworkDtl> homeworkDtls = context.getValue("homeworkDtls");

		int avgUsedTime = homeworkDtls.stream().filter(v -> v.getCorrectTime() != null && v.getUsedTime() != null)
				.collect(averagingInt(HomeworkDtl::getUsedTime)).intValue();

		HwRptView.Summary summary = new HwRptView.Summary();
		summary.setHomeworkId(homework.getHomeworkId());
		summary.setHomeworkName(homework.getHomeworkName());
		// 提交&批改
		summary.setTotalNum(homework.getTotalNum());
		summary.setSubmitNum(homework.getFinishNum());
		summary.setDelayNum(homework.getDelayNum());
		summary.setCorrectNum(homework.getCorrectNum());
		summary.setAvgUsedTime(avgUsedTime);
		// 分数
		summary.setAvgScore(homework.getAvgScore());
		summary.setMaxScore(homework.getMaxScore());
		summary.setMinScore(homework.getMinScore());
		// 学生
		if (homeworkDtl != null) {
			summary.setSelfScore(homeworkDtl.getScore());
			if (homeworkDtl.getUsedTime() != null) {
				summary.setSelfUsedTime(homeworkDtl.getUsedTime());
			} else {
				summary.setSelfUsedTime(0);
			}
			int submitRank = (int) homeworkDtls.stream().filter(v -> v.getSubmitTime() != null)
					.filter(v -> v.getSubmitTime().before(homeworkDtl.getSubmitTime())).count() + 1;
			int scoreRank = (int) homeworkDtls.stream().filter(v -> v.getCorrectTime() != null)
					.filter(v -> v.getScore().compareTo(homeworkDtl.getScore()) > 0).count() + 1;
			summary.setSubmitRank(submitRank);
			summary.setScoreRank(scoreRank);
		}
		return summary;
	}
}
