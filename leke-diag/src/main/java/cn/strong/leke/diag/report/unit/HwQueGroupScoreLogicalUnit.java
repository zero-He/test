package cn.strong.leke.diag.report.unit;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.diag.model.WorkDetail;
import cn.strong.leke.diag.model.WorkDetail.GroupScore;
import cn.strong.leke.diag.model.report.HwRptView.GroupModel;
import cn.strong.leke.model.paper.PaperDTO;
import cn.strong.leke.model.paper.QuestionGroup;
import cn.strong.leke.model.paper.ScoredQuestion;

/**
 * 单份作业分析：大题得分分析
 * @author  andy
 * @since   v1.0.0
 */
public class HwQueGroupScoreLogicalUnit implements LogicalUnit<List<GroupModel>> {

	@Override
	public List<GroupModel> execute(LogicalContext context) {
		WorkDetail workDetail = context.getValue("detail");
		List<WorkDetail> details = context.getValue("details");
		PaperDTO paperDTO = context.getValue("paperDTO");
		List<GroupModel> groupModelList = this.parseGroupModel(paperDTO);
		for (GroupModel groupModel : groupModelList) {
			List<GroupScore> list = new ArrayList<>();
			for (WorkDetail detail : details) {
				if (CollectionUtils.isNotEmpty(detail.getQueScores())) {
					Optional<GroupScore> optional = detail.getQueScores().stream()
							.filter(v -> v.getId().equals(groupModel.getId())).findFirst();
					if (optional.isPresent()) {
						list.add(optional.get());
					}
				}
			}
			if (list.isEmpty()) {
				groupModel.setClassScore(new BigDecimal(0));
			} else {
				BigDecimal score = new BigDecimal(0);
				for (GroupScore groupScore : list) {
					score = score.add(groupScore.getSelfScore());
				}
				BigDecimal classScore = score.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
				groupModel.setClassScore(classScore);
			}
		}
		if (workDetail == null || CollectionUtils.isEmpty(workDetail.getQueScores())) {
			return groupModelList;
		}
		List<GroupScore> list = workDetail.getQueScores();
		for (GroupModel groupModel : groupModelList) {
			Optional<GroupScore> opt = list.stream().filter(v -> v.getId().equals(groupModel.getId())).findFirst();
			if (opt.isPresent()) {
				GroupScore groupScore = opt.get();
				groupModel.setSelfScore(groupScore.getSelfScore());
				groupModel.setRightNum(groupScore.getRightNum());
			}
		}

		return groupModelList;
	}

	private List<GroupModel> parseGroupModel(PaperDTO paperDTO) {
		List<GroupModel> groupModelList = new ArrayList<>();
		for (int i = 0; i < paperDTO.getDetail().getGroups().size(); i++) {
			QuestionGroup group = paperDTO.getDetail().getGroups().get(i);
			List<Long> qids = group.getQuestions().stream().map(ScoredQuestion::getQuestionId).collect(toList());
			BigDecimal totalScore = group.getQuestions().stream().map(ScoredQuestion::getScore)
					.reduce(new BigDecimal(0), (a, b) -> a.add(b));
			GroupModel groupModel = new GroupModel();
			groupModel.setId(i + 1L);
			groupModel.setName(group.getGroupTitle());
			groupModel.setQids(qids);
			groupModel.setTotalScore(totalScore);
			groupModelList.add(groupModel);
		}
		return groupModelList;
	}
}
