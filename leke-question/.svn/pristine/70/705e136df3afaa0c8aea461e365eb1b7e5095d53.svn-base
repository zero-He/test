package cn.strong.leke.question.controller.question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.querys.RepositoryQuestionQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.question.service.QuestionService;

@Controller
@RequestMapping("/auth/provost/question/league")
public class ProvostLeagueQuestionController {
	@Autowired
	private QuestionService questionService;

	@RequestMapping("list")
	public void list(Long leagueId, Model model) {
		model.addAttribute("leagueId", leagueId);
	}

	@RequestMapping("details")
	public void details(RepositoryQuestionQuery query, Page page, Model model) {
		query.setShareScope(ShareScopes.LEAGUE);
		query.setSchoolId(UserContext.user.getCurrentSchoolId());
		List<RepositoryRecord> records = questionService.queryLeagueQuestions(query, page);
		model.addAttribute("records", records);
		model.addAttribute("page", page);
	}
}
