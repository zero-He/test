package cn.strong.leke.question.controller.workbook;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.question.service.IWorkbookService;

@Controller
@RequestMapping("/auth/researcher/leke/workbook")
public class ResearcherLekeWorkbookController {
	@Resource
	private IWorkbookService workbookService;

	@RequestMapping("list")
	public void list() {
		// nothing to do
	}

	@RequestMapping("details")
	@ResponseBody
	public JsonResult details(RepositoryWorkbookQuery query, Page page) {
		JsonResult result = new JsonResult();
		query.setShareScope(ShareScopes.LEKE_BOUTIQUE);
		List<RepositoryRecord> records = workbookService.queryLekeBoutiqueWorkbooks(query, page);
		result.addDatas("records", records);
		result.addDatas("page", page);
		return result;
	}
}
