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
import cn.strong.leke.question.core.workbook.query.ISchoolWorkbookQueryService;

/**
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/auth/teacher/workbook/school")
public class TeacherSchoolWorkbookController {

	@Resource
	private ISchoolWorkbookQueryService schoolWorkbookQueryService;

	@RequestMapping("list")
	public void list() {

	}

	@RequestMapping("details")
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
}
