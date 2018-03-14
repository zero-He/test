package cn.strong.leke.diag.report.unit;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

import cn.strong.leke.diag.model.HomeworkDtl;
import cn.strong.leke.diag.model.report.HwRptView;
import cn.strong.leke.diag.model.report.HwRptView.ScoreModel;
import cn.strong.leke.diag.util.ScoreLevelUtils;

/**
 * 单份作业分析：学生成绩分析
 * @author  andy
 * @since   v1.0.0
 */
public class HwStuScoreRankLogicalUnit implements LogicalUnit<List<HwRptView.ScoreModel>> {

	@Override
	public List<HwRptView.ScoreModel> execute(LogicalContext context) {
		List<HomeworkDtl> list = context.getValue("homeworkDtls");
		Stream<ScoreModel> stream = list.stream().map(homeworkDtl -> {
			HwRptView.ScoreModel scoreModel = new HwRptView.ScoreModel();
			scoreModel.setUserId(homeworkDtl.getStudentId());
			scoreModel.setUserName(homeworkDtl.getStudentName());
			if (homeworkDtl.getCorrectTime() != null) {
				scoreModel.setLevel(ScoreLevelUtils.toLevel(homeworkDtl.getScoreRate().doubleValue() * 100));
			} else if (homeworkDtl.getSubmitTime() != null) {
				scoreModel.setLevel(6);
			} else {
				scoreModel.setLevel(7);
			}
			scoreModel.setScore(homeworkDtl.getScore());
			scoreModel.setScoreRate(homeworkDtl.getScoreRate());
			return scoreModel;
		}).sorted((a, b) -> {
			// 规则：已经批改的学生按分数高低排序，紧跟着是已经提交未批改的，最后是未提交的
			int result = Integer.compare(a.getLevel(), b.getLevel());
			if (result == 0 && a.getLevel() < 7) {
				result = -a.getScoreRate().compareTo(b.getScoreRate());
			}
			return result;
		});
		return stream.collect(toList());
	}
}
