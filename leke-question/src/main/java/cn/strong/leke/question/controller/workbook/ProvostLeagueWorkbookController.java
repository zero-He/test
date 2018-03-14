package cn.strong.leke.question.controller.workbook;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.question.service.IWorkbookService;

@Controller
@RequestMapping("/auth/provost/workbook/league")
public class ProvostLeagueWorkbookController {

	@Resource
	private IWorkbookService workbookService;

	@RequestMapping("list")
	public void list(Long leagueId, Model model) {
		model.addAttribute("leagueId", leagueId);
	}

	@RequestMapping("details")
	@ResponseBody
	public JsonResult details(RepositoryWorkbookQuery query, Page page) {
		JsonResult result = new JsonResult();
		query.setShareScope(ShareScopes.LEAGUE);
		query.setSchoolId(UserContext.user.getCurrentSchoolId());
		List<RepositoryRecord> records = workbookService.queryLeagueWorkbooks(query, page);
		result.addDatas("records", records);
		result.addDatas("page", page);
		return result;
	}
}
