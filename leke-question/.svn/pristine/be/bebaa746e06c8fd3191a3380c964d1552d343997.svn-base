package cn.strong.leke.question.controller.workbook;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import cn.strong.leke.question.core.workbook.query.ISchoolWorkbookQueryService;
import cn.strong.leke.repository.common.cmd.store.ISchoolRepoHandler;

/**
 *
 * 描述: 学校教研员
 *
 * @author raolei
 */
@Controller
@RequestMapping("/auth/schoolResearcher/workbook")
public class SchoolResearcherWbController {

	@Resource
	private ISchoolWorkbookQueryService schoolWorkbookQueryService;
	@Autowired
	@Qualifier("schoolWorkbookHandler")
	private ISchoolRepoHandler schoolWorkbookHandler;

	@RequestMapping("personal/list")
	public void list() {

	}


	@RequestMapping("school/list")
	public void schoolList() {

	}

	@RequestMapping("school/details")
	@ResponseBody
	public JsonResult details(RepositoryWorkbookQuery query, Page page) {
		JsonResult result = new JsonResult();
		query.setShareScope(ShareScopes.SCHOOL);
		query.setSchoolId(UserContext.user.getCurrentSchoolId());
		List<RepositoryRecord> records = schoolWorkbookQueryService.querySchoolWorkbooks(query,
				page);
		result.addDatas("records", records);
		result.addDatas("page", page);
		return result;
	}

	@ResponseBody
	@RequestMapping("school/remove")
	public JsonResult removeSchool(Long workbookId, Long schoolId) {
		User user= UserContext.user.get();
		schoolWorkbookHandler.remove(workbookId, schoolId, user);
		return new JsonResult();
	}
}
