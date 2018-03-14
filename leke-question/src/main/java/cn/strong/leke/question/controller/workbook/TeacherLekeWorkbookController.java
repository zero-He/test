package cn.strong.leke.question.controller.workbook;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.WorkbookRepositoryRecord;
import cn.strong.leke.question.core.workbook.query.ILekeWorkbookQueryService;

/**
 * 教师乐课习题册
 * 
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/auth/teacher/leke/workbook")
public class TeacherLekeWorkbookController {

	@Resource
	private ILekeWorkbookQueryService lekeWorkbookQueryService;

	@RequestMapping("list")
	public void list() {
		// nothing to do
	}
	
	@RequestMapping("details")
	@ResponseBody
	public JsonResult details(RepositoryWorkbookQuery query, Page page) {
		JsonResult result = new JsonResult();
		page.setPageSize(12);
		List<WorkbookRepositoryRecord> records = lekeWorkbookQueryService.queryLekeWorkbooks(query, page);
		result.addDatas("records", records);
		result.addDatas("page", page);
		return result;
	}
}
