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
import cn.strong.leke.monitor.mongo.model.JobExRecord;
import cn.strong.leke.monitor.mongo.model.query.JobExRecordQuery;
import cn.strong.leke.monitor.mongo.model.query.JobExStat;
import cn.strong.leke.monitor.mongo.service.IJobExRecordService;

/**
 * 定时任务异常控制器
 * 
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/auth/operationStaff/jobex")
public class JobExController {

	@Autowired
	private IJobExRecordService recordService;

	@RequestMapping("stat")
	public void stat() {

	}

	@RequestMapping("statDatas")
	@ResponseBody
	public JsonResult dailyDatas(JobExRecordQuery query) {
		JsonResult result = new JsonResult();
		List<JobExStat> stats = recordService.findJobExStats(query);
		result.addDatas("stats", stats);
		return result;
	}

	@RequestMapping("record")
	public void record(JobExRecordQuery query, Model model) {
		model.addAttribute("query", JsonUtils.toJSON(query));
	}

	@RequestMapping("recordDatas")
	@ResponseBody
	public JsonResult recordDatas(JobExRecordQuery query, Page page) {
		JsonResult result = new JsonResult();
		List<JobExRecord> records = recordService.findJobExRecords(query, page);
		result.addDatas("records", records);
		result.addDatas("page", page);
		return result;
	}

}
