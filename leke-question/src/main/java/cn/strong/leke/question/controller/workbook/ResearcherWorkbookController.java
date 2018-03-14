/**
 * 
 */
package cn.strong.leke.question.controller.workbook;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.workbook.cmd.store.IFamousSchoolWorkbookHandler;
import cn.strong.leke.question.core.workbook.cmd.store.IFamousTeacherWorkbookHandler;
import cn.strong.leke.question.core.workbook.query.IFamousSchoolWorkbookQueryService;
import cn.strong.leke.question.core.workbook.query.IFamousTeacherWorkbookQueryService;
import cn.strong.leke.question.model.workbook.query.AgencyFmsSchWorkbookQuery;
import cn.strong.leke.question.model.workbook.query.AgencyFmsTchWorkbookQuery;

/**
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/auth/researcher/workbook")
public class ResearcherWorkbookController {
	@Resource
	private IFamousTeacherWorkbookQueryService famousTeacherWorkbookQueryService;
	@Resource
	private IFamousSchoolWorkbookQueryService famousSchoolWorkbookQueryService;
	@Resource
	private IFamousTeacherWorkbookHandler famousTeacherWorkbookHandler;
	@Resource
	private IFamousSchoolWorkbookHandler famousSchoolWorkbookHandler;

	@RequestMapping("workbookList")
	public String workbookList() {
		return "auth/common/workbook/workbookList";
	}

	@RequestMapping("personal/list")
	public void list() {

	}

	@RequestMapping("fmssch/list")
	public void fmsschList() {

	}

	@RequestMapping("fmssch/details")
	@ResponseBody
	public JsonResult fmsschDetails(RepositoryWorkbookQuery query, Page page) {
		query.setShareScope(ShareScopes.FAMOUS_SCHOOL);
		JsonResult result = new JsonResult();
		List<RepositoryRecord> records = famousSchoolWorkbookQueryService.queryFmsSchWorkbooks(
				query, page);
		result.addDatas("records", records);
		result.addDatas("page", page);
		return result;
	}

	@RequestMapping("fmssch/myAgencyList")
	public void fmsschAgencyList() {

	}

	@RequestMapping("fmssch/myAgencyDetails")
	@ResponseBody
	public JsonResult fmsschAgencyDetails(AgencyFmsSchWorkbookQuery query, Page page) {
		query.setUserId(UserContext.user.getUserId());
		List<RepositoryRecord> records = famousSchoolWorkbookQueryService
				.queryMyAgencyFmsSchWorkbooks(query, page);
		JsonResult result = new JsonResult();
		result.addDatas("records", records);
		result.addDatas("page", page);
		return result;
	}

	@RequestMapping("fmstch/list")
	public void fmstchList() {

	}

	@RequestMapping("fmstch/details")
	@ResponseBody
	public JsonResult fmstchDetails(RepositoryWorkbookQuery query, Page page) {
		query.setShareScope(ShareScopes.FAMOUS_TEACHER);
		JsonResult result = new JsonResult();
		List<RepositoryRecord> records = famousTeacherWorkbookQueryService.queryFmsTchWorkbooks(
				query, page);
		result.addDatas("records", records);
		result.addDatas("page", page);
		return result;
	}

	@RequestMapping("fmstch/myAgencyList")
	public void fmstchAgencyList() {

	}

	@RequestMapping("fmstch/myAgencyDetails")
	@ResponseBody
	public JsonResult fmstchAgencyDetails(AgencyFmsTchWorkbookQuery query, Page page) {
		query.setUserId(UserContext.user.getUserId());
		List<RepositoryRecord> records = famousTeacherWorkbookQueryService
				.queryMyAgencyFmsTchWorkbooks(query, page);
		JsonResult result = new JsonResult();
		result.addDatas("records", records);
		result.addDatas("page", page);
		return result;
	}

	@ResponseBody
	@RequestMapping("fmssch/removeFmSch")
	public JsonResult removeFmSch(Long workbookId, Long schoolId) {
		User user = UserContext.user.get();
		famousSchoolWorkbookHandler.remove(workbookId, schoolId, user);
		return new JsonResult();
	}

	@ResponseBody
	@RequestMapping("fmstch/removeFmTch")
	public JsonResult removeFmTch(Long workbookId, Long teacherId) {
		User user = UserContext.user.get();
		famousTeacherWorkbookHandler.remove(workbookId, teacherId, user);
		return new JsonResult();
	}
}
