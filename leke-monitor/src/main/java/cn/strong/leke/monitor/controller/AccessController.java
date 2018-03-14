/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
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
import cn.strong.leke.monitor.core.model.AccessDailyDto;
import cn.strong.leke.monitor.core.model.AccessRangeStatDto;
import cn.strong.leke.monitor.core.service.IAccessService;
import cn.strong.leke.monitor.mongo.model.AccessRecord;
import cn.strong.leke.monitor.mongo.model.AccessUrl;
import cn.strong.leke.monitor.mongo.model.query.AccessDailyQuery;
import cn.strong.leke.monitor.mongo.model.query.AccessRangeQuery;
import cn.strong.leke.monitor.mongo.model.query.AccessRecordQuery;
import cn.strong.leke.monitor.mongo.model.query.AccessStat;
import cn.strong.leke.monitor.mongo.service.IAccessRecordService;
import cn.strong.leke.monitor.mongo.service.IAccessUrlService;

/**
 *
 * 描述:
 *
 * @author  C.C
 * @created 2014-8-14 下午5:34:04
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/operationStaff/access")
public class AccessController {
	@Autowired
	private IAccessService accessService;
	@Autowired
	private IAccessUrlService urlService;
	@Autowired
	private IAccessRecordService recordService;
	
	@RequestMapping("daily")
	public void daily() {

	}

	@RequestMapping("dailyDatas")
	@ResponseBody
	public JsonResult dailyDatas(AccessDailyQuery query) {
		JsonResult result = new JsonResult();
		List<AccessDailyDto> stats = accessService
				.findAccessDailyStats(query);
		result.addDatas("stats", stats);
		return result;
	}

	@RequestMapping("saveUrlName")
	@ResponseBody
	public JsonResult saveUrlName(AccessUrl url) {
		urlService.save(url);
		return new JsonResult();
	}

	@RequestMapping("record")
	public void record(AccessRecordQuery query, Model model) {
		model.addAttribute("query", JsonUtils.toJSON(query));
	}

	@RequestMapping("recordDatas")
	@ResponseBody
	public JsonResult recordDatas(AccessRecordQuery query, Page page) {
		JsonResult result = new JsonResult();
		List<AccessRecord> records = recordService.findAccessRecords(query, page);
		result.addDatas("records", records);
		result.addDatas("page", page);
		return result;
	}

	@RequestMapping("recordStat")
	@ResponseBody
	public JsonResult recordStat(AccessRecordQuery query) {
		JsonResult result = new JsonResult();
		AccessStat stat = recordService.getAccessRecordStat(query);
		result.addDatas("stat", stat);
		return result;
	}

	@RequestMapping("range")
	public void range() {

	}

	@RequestMapping("rangeDatas")
	@ResponseBody
	public JsonResult rangeDatas(AccessRangeQuery query) {
		JsonResult result = new JsonResult();
		List<AccessRangeStatDto> stats = accessService.findAccessRangeStats(query);
		result.addDatas("stats", stats);
		return result;
	}

}
