package cn.strong.leke.question.controller.workbook;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.question.core.workbook.cmd.store.IWorkbooLeveHandler;

@Controller
@RequestMapping("/auth/common/workbook")
public class CommonWorkbookController {

	@Resource
	private IWorkbooLeveHandler workbooLeveHandler;

	@ResponseBody
	@RequestMapping("setWorkbookPrime")
	public JsonResult setWorkbookPrime(Long workbookId) {
		workbooLeveHandler.setWorkbookPrime(workbookId, UserContext.user.get());
		return new JsonResult();
	}

	@ResponseBody
	@RequestMapping("setWorkbookGeneral")
	public JsonResult setWorkbookGeneral(Long workbookId) {
		workbooLeveHandler.setWorkbookGeneral(workbookId, UserContext.user.get());
		return new JsonResult();
	}
}
