package cn.strong.leke.question.controller.question;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.question.core.question.query.IFavoriteQuestionQueryService;

@Controller
@RequestMapping("/auth/common/question/pad")
public class CommonPadQuestionController {

	@Resource
	private IFavoriteQuestionQueryService favoriteQuestionQueryService;

	@RequestMapping("view")
	public void view(Long questionId, Model model) {
		model.addAttribute("questionId", questionId);
		model.addAttribute("isFavorite",
				favoriteQuestionQueryService.countFavorite(questionId, UserContext.user.getUserId()));
	}

	@RequestMapping("question")
	public void question(Long questionId, Model model) {
		model.addAttribute("questionId", questionId);
	}

	@RequestMapping("detail")
	public void detail(Long questionId, Model model) {
		model.addAttribute("questionId", questionId);
	}

	@RequestMapping("detailPart")
	public void detailPart(Long questionId, Model model) {
		model.addAttribute("questionId", questionId);
	}

}
