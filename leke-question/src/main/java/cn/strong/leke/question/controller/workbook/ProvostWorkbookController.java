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

@Controller
@RequestMapping("/auth/provost/workbook")
public class ProvostWorkbookController {

	@Resource
	private ISchoolWorkbookQueryService schoolWorkbookQueryService;

	@RequestMapping("school/list")
	public void list() {

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

}
