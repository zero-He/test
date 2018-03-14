/**
 * 
 */
package cn.strong.leke.question.controller.question;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.querys.RepositoryQuestionQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.cmd.store.IFamousTeacherQuestionHandler;
import cn.strong.leke.question.core.question.query.IFamousTeacherQuestionQueryService;
import cn.strong.leke.question.model.question.query.AgencyFmsTchQuestionQuery;

/**
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/auth/researcher/question/fmstch")
public class ResearcherFmsTchQuestionController {
	@Resource
	private IFamousTeacherQuestionQueryService famousTeacherQuestionQueryService;
	@Resource
	private IFamousTeacherQuestionHandler famousTeacherQuestionHandler;

	@RequestMapping("list")
	public void list() {
		// nothing to do
	}

	@RequestMapping("details")
	public void details(RepositoryQuestionQuery query, Page page, Model model) {
		query.setShareScope(ShareScopes.FAMOUS_TEACHER);
		List<RepositoryRecord> records = famousTeacherQuestionQueryService
				.queryFmsTchQuestions(query, page);
		model.addAttribute("records", records);
		model.addAttribute("page", page);
	}

	@ResponseBody
	@RequestMapping("removeFmTch")
	public JsonResult removeFmTch(Long questionId, Long teacherId) {
		User user = UserContext.user.get();
		famousTeacherQuestionHandler.remove(questionId, teacherId, user);
		return new JsonResult();
	}

	@RequestMapping("myAgencyList")
	public void myAgencyList() {
		// nothing to do
	}

	@RequestMapping("myAgencyDetails")
	public void myAgencyDetails(AgencyFmsTchQuestionQuery query, Page page, Model model) {
		query.setUserId(UserContext.user.getUserId());
		List<RepositoryRecord> records = famousTeacherQuestionQueryService
				.queryMyAgencyFmsTchQuestions(query, page);
		model.addAttribute("records", records);
		model.addAttribute("page", page);
	}
}
