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
import cn.strong.leke.question.core.question.cmd.store.IFamousSchoolQuestionHandler;
import cn.strong.leke.question.core.question.query.IFamousSchoolQuestionQueryService;
import cn.strong.leke.question.model.question.query.AgencyFmsSchQuestionQuery;

/**
 * 教研名校习题控制器
 * 
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/auth/researcher/question/fmssch")
public class ResearcherFmsSchQuestionController {
	@Resource
	private IFamousSchoolQuestionQueryService famousSchoolQuestionQueryService;
	@Resource
	private IFamousSchoolQuestionHandler famousSchoolQuestionHandler;

	@RequestMapping("list")
	public void list() {
		// nothing to do
	}

	@RequestMapping("details")
	public void details(RepositoryQuestionQuery query, Page page, Model model) {
		query.setShareScope(ShareScopes.FAMOUS_SCHOOL);
		List<RepositoryRecord> records = famousSchoolQuestionQueryService
				.queryFmsSchQuestions(query, page);
		model.addAttribute("records", records);
		model.addAttribute("page", page);
	}

	@ResponseBody
	@RequestMapping("removeFmSch")
	public JsonResult removeFmSch(Long questionId, Long schoolId) {
		User user = UserContext.user.get();
		famousSchoolQuestionHandler.remove(questionId, schoolId, user);
		return new JsonResult();
	}

	@RequestMapping("myAgencyList")
	public void myAgencyList() {
		// nothing to do
	}
	
	@RequestMapping("myAgencyDetails")
	public void myAgencyDetails(AgencyFmsSchQuestionQuery query, Page page, Model model) {
		query.setUserId(UserContext.user.getUserId());
		List<RepositoryRecord> records = famousSchoolQuestionQueryService
				.queryMyAgencyFmsSchQuestions(query, page);
		model.addAttribute("records", records);
		model.addAttribute("page", page);
	}
}
