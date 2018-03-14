/**
 * 
 */
package cn.strong.leke.monitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.monitor.mongo.model.ExceptionRecord;
import cn.strong.leke.monitor.mongo.model.query.ExceptionRecordQuery;
import cn.strong.leke.monitor.mongo.model.query.ExceptionStat;
import cn.strong.leke.monitor.mongo.service.IExceptionRecordService;

/**
 * 系统异常控制器
 * 
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/auth/operationStaff/exception")
public class ExceptionController {

	@Autowired
	private IExceptionRecordService recordService;

	@RequestMapping("stat")
	public void stat() {

	}

	@RequestMapping("statDatas")
	@ResponseBody
	public JsonResult dailyDatas(ExceptionRecordQuery query) {
		JsonResult result = new JsonResult();
		List<ExceptionStat> stats = recordService.findExceptionStats(query);
		result.addDatas("stats", stats);
		return result;
	}

	@RequestMapping("record")
	public void record(ExceptionRecordQuery query, Model model) {
		model.addAttribute("query", JsonUtils.toJSON(query));
	}

	@RequestMapping("recordDatas")
	@ResponseBody
	public JsonResult recordDatas(ExceptionRecordQuery query, Page page) {
		JsonResult result = new JsonResult();
		List<ExceptionRecord> records = recordService.findExceptionRecords(query, page);
		result.addDatas("records", records);
		result.addDatas("page", page);
		return result;
	}

}
