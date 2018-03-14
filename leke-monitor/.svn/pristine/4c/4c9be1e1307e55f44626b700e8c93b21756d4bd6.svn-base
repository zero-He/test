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
import cn.strong.leke.monitor.mongo.model.MleRecord;
import cn.strong.leke.monitor.mongo.model.query.MleRecordQuery;
import cn.strong.leke.monitor.mongo.service.IMleRecordService;

/**
 * 消息监听异常控制器
 * 
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/auth/operationStaff/mle")
public class MleController {

	@Autowired
	private IMleRecordService recordService;

	@RequestMapping("record")
	public void record(MleRecordQuery query, Model model) {
		model.addAttribute("query", JsonUtils.toJSON(query));
	}

	@RequestMapping("recordDatas")
	@ResponseBody
	public JsonResult recordDatas(MleRecordQuery query, Page page) {
		JsonResult result = new JsonResult();
		List<MleRecord> records = recordService.findMleRecords(query, page);
		result.addDatas("records", records);
		result.addDatas("page", page);
		return result;
	}
}
