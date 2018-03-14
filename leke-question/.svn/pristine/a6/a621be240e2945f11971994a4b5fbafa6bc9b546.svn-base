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
import cn.strong.leke.question.core.workbook.query.IWorkbookQueryService;

@Controller
@RequestMapping("/auth/common/control/workbook")
public class ControlUserWorkbookController {
	
	@Resource
	private IWorkbookQueryService workbookQueryService;

	@ResponseBody
	@RequestMapping("details")
	public JsonResult details(RepositoryWorkbookQuery query, Page page) {
		JsonResult result = new JsonResult();
		query.setUserId(UserContext.user.getUserId());
		List<RepositoryRecord> records = workbookQueryService.queryByScope(query, page, UserContext.user.get());
		page.setDataList(records);
		result.addDatas("page", page);
		return result;
	}

}
