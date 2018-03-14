package cn.strong.leke.question.controller.paper;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.paper.PaperContext;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.base.SchoolCst;
import cn.strong.leke.model.paper.PaperDTO;
import cn.strong.leke.model.paper.PaperDetail;
import cn.strong.leke.model.paper.QuestionGroup;
import cn.strong.leke.model.paper.ScoredQuestion;
import cn.strong.leke.model.question.QuestionDTO;

@Controller
@RequestMapping("/auth/researcher/paper/")
public class ResearcherPaperController {

	@RequestMapping("check/view")
	public void view(Long paperId, Model model) {
		PaperDTO paper = PaperContext.getPaperDTO(paperId);
		Validation.notNull(paper, "pap.paper.not.exist");
		// Validation.isTrue(paper.getPaperStatus().equals(PaperConst.PAP_STATUS_INPUT),
		// "试卷已审核！");
		Validation.isTrue(paper.getSchoolId().equals(SchoolCst.LEKE), "该资源不属于乐课精品库");
		model.addAttribute("paper", paper);
		model.addAttribute("initJson", JsonUtils.toJSON(paper));
	}

	@RequestMapping("check/details")
	public void details(Long paperId, Integer questionStatus, Model model) {
		PaperDTO paper = PaperContext.getPaperDTO(paperId);
		model.addAttribute("paperStatus", paper.getPaperStatus());
		model.addAttribute("questions", getQuestions(paperId, questionStatus));
		model.addAttribute("questionStatus", questionStatus);
	}

	private List<QuestionDTO> getQuestions(Long paperId,Integer questionStatus) {
		PaperDTO paper = PaperContext.getPaperDTO(paperId);
		PaperDetail detail = paper.getDetail();
		List<QuestionGroup> qGroups = detail.getGroups();
		List<QuestionDTO> questions = ListUtils.newArrayList();
		for (QuestionGroup qg : qGroups) {
			for (ScoredQuestion sq : qg.getQuestions()) {
				QuestionDTO question = QuestionContext.getQuestionDTO(sq.getQuestionId());
				if (Objects.equals(questionStatus, question.getQuestionStatus())) {
					questions.add(question);
				}
			}
		}
		return questions;
	}

}
