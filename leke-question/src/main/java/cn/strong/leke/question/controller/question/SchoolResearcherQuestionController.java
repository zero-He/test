package cn.strong.leke.question.controller.question;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.SchoolQuestionOutlineNode;
import cn.strong.leke.model.question.querys.RepositoryQuestionQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.cmd.store.ISchoolQuestionHandler;
import cn.strong.leke.question.core.question.query.ISchoolQuestionQueryService;
import cn.strong.leke.question.model.question.query.AgencySchoolQuestionQuery;
import cn.strong.leke.question.model.question.query.SchoolQuestionCheckQuery;
import cn.strong.leke.question.service.ISchQueOutlineNodeService;
import cn.strong.leke.repository.common.cmd.ISchoolRepoCheckHandler;
import cn.strong.leke.repository.common.model.BatchCheckRejectEvent;

/**
 *
 * 描述: 学校教研员
 *
 * @author raolei
 */
@Controller
@RequestMapping("/auth/schoolResearcher/question")
public class SchoolResearcherQuestionController {

	@Resource
	private ISchoolQuestionQueryService schoolQuestionQueryService;
	@Resource(name = "schoolQuestionCheckHandler")
	private ISchoolRepoCheckHandler schoolQuestionCheckHandler;
	@Resource
	private ISchQueOutlineNodeService schQueOutlineNodeService;
	@Resource
	private ISchoolQuestionHandler schoolQuestionHandler;

	@RequestMapping("personal/list")
	public void list() {
	}

	@RequestMapping("school/list")
	public void schoolList() {
		// nothing to do
	}

	@RequestMapping("school/details")
	public void details(RepositoryQuestionQuery query, Page page, Model model) {
		query.setShareScope(ShareScopes.SCHOOL);
		query.setSchoolId(UserContext.user.getCurrentSchoolId());
		if (query.getSourceType() != null && query.getSourceType() == 1) {
			query.setAgencyUserId(UserContext.user.getUserId());
		}
		List<RepositoryRecord> records = schoolQuestionQueryService.querySchoolQuestions(query, page);
		model.addAttribute("records", records);
		model.addAttribute("page", page);
	}

	/**
	 * 学校习题审核
	 */
	@RequestMapping("school/checkList")
	public void checkList() {

	}

	@RequestMapping("school/checkDetails")
	public void checkDetails(SchoolQuestionCheckQuery query, Page page, Model model) {
		query.setSchoolId(UserContext.user.getCurrentSchoolId());
		List<RepositoryRecord> records = schoolQuestionQueryService
				.querySchoolQuestionForCheck(query, page);
		model.addAttribute("query", query);
		model.addAttribute("records", records);
		model.addAttribute("page", page);
	}

	@RequestMapping("school/doRemove")
	@ResponseBody
	public JsonResult doRemove(String dataJson) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		Long schoolId = UserContext.user.getCurrentSchoolId();
		List<Long> questionIds = JsonUtils.readList(dataJson, Long.class);
		schoolQuestionCheckHandler.remove(questionIds, schoolId, user);
		return json;
	}

	@RequestMapping("school/doCheckPass")
	@ResponseBody
	public JsonResult doCheckPass(String dataJson) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		Long schoolId = UserContext.user.getCurrentSchoolId();
		List<Long> questionIds = JsonUtils.readList(dataJson, Long.class);
		schoolQuestionCheckHandler.checkPass(questionIds, schoolId, user);
		return json;
	}

	@RequestMapping("school/doCheckReject")
	@ResponseBody
	public JsonResult doCheckReject(String dataJson) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		Long schoolId = UserContext.user.getCurrentSchoolId();
		BatchCheckRejectEvent event = JsonUtils.fromJSON(dataJson, BatchCheckRejectEvent.class);
		Validation.notNull(event, "信息不完整");
		schoolQuestionCheckHandler.checkReject(event.getResIds(), schoolId, event.getCheckNote(),
				user);
		return json;
	}

	@RequestMapping("school/checkView")
	public void checkView(Long questionId, Model model) {
		SchoolQuestionOutlineNode olnode = schQueOutlineNodeService
				.findOutlineNodeByQuestionId(questionId);
		model.addAttribute("questionId", questionId);
		String olnodeStr = JsonUtils.toJSON(olnode);
		if (StringUtils.isEmpty(olnodeStr)) {
			olnodeStr = "{}";
		}
		model.addAttribute("olnodeJson", olnodeStr);
	}

	@RequestMapping("school/updateOlnode")
	@ResponseBody
	public JsonResult updateOlnode(String dataJson) {
		SchoolQuestionOutlineNode olnode = JsonUtils.fromJSON(dataJson,
				SchoolQuestionOutlineNode.class);
		schoolQuestionHandler.updateOutlineNode(olnode, UserContext.user.get());
		return new JsonResult();
	}

	/**
	 * 我的代录
	 */
	@RequestMapping("school/myAgencyList")
	public void myAgencyList() {
		// nothing to do
	}

	@RequestMapping("school/myAgencyDetails")
	public void myAgencyDetails(AgencySchoolQuestionQuery query, Page page, Model model) {
		query.setUserId(UserContext.user.getUserId());
		query.setSchoolId(UserContext.user.getCurrentSchoolId());
		List<RepositoryRecord> records = schoolQuestionQueryService.queryMyAgencySchoolQuestions(
				query, page);
		model.addAttribute("records", records);
		model.addAttribute("page", page);
	}
}
