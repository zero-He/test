package cn.strong.leke.diag.report.unit;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.diag.model.WorkDetail;
import cn.strong.leke.diag.model.WorkDetail.GroupScore;
import cn.strong.leke.diag.model.report.HwRptView.GroupModel;
import cn.strong.leke.model.paper.PaperDTO;
import cn.strong.leke.model.paper.QuestionGroup;
import cn.strong.leke.model.paper.ScoredQuestion;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionKnowledge;

/**
 * 单份作业分析：知识点得分分析
 * @author  andy
 * @since   v1.0.0
 */
public class HwKnoGroupScoreLogicalUnit implements LogicalUnit<List<GroupModel>> {

	@Override
	public List<GroupModel> execute(LogicalContext context) {
		WorkDetail workDetail = context.getValue("detail");
		List<WorkDetail> details = context.getValue("details");
		PaperDTO paperDTO = context.getValue("paperDTO");
		List<GroupModel> groupModelList = this.parseGroupModel(paperDTO);
		for (GroupModel groupModel : groupModelList) {
			List<GroupScore> list = new ArrayList<>();
			for (WorkDetail detail : details) {
				if (CollectionUtils.isNotEmpty(detail.getKnoScores())) {
					Optional<GroupScore> optional = detail.getKnoScores().stream()
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
		if (workDetail == null || CollectionUtils.isEmpty(workDetail.getKnoScores())) {
			return groupModelList;
		}
		List<GroupScore> list = workDetail.getKnoScores();
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
		Map<Long, GroupModel> groupModelMap = new HashMap<>();
		for (QuestionGroup group : paperDTO.getDetail().getGroups()) {
			for (ScoredQuestion sq : group.getQuestions()) {
				QuestionDTO questionDTO = QuestionContext.getQuestionDTO(sq.getQuestionId());
				if (CollectionUtils.isNotEmpty(questionDTO.getKnowledges())) {
					for (QuestionKnowledge knowledge : questionDTO.getKnowledges()) {
						GroupModel groupModel = groupModelMap.get(knowledge.getKnowledgeId());
						if (groupModel == null) {
							String name = knowledge.getPath();
							name = name.substring(name.lastIndexOf('-') + 1);
							groupModel = new GroupModel();
							groupModel.setId(knowledge.getKnowledgeId());
							groupModel.setName(name);
							groupModel.setQids(new ArrayList<>());
							groupModel.setTotalScore(new BigDecimal(0));
							groupModelMap.put(knowledge.getKnowledgeId(), groupModel);
						}
						groupModel.getQids().add(sq.getQuestionId());
						groupModel.setTotalScore(groupModel.getTotalScore().add(sq.getScore()));
					}
				}
			}
		}
		return groupModelMap.values().stream().sorted((a, b) -> Long.compare(a.getId(), b.getId())).collect(toList());
	}
}
