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
import cn.strong.leke.monitor.mongo.model.SqlDaily;
import cn.strong.leke.monitor.mongo.model.SqlHash;
import cn.strong.leke.monitor.mongo.model.SqlRecord;
import cn.strong.leke.monitor.mongo.model.query.SqlDailyQuery;
import cn.strong.leke.monitor.mongo.model.query.SqlRecordQuery;
import cn.strong.leke.monitor.mongo.model.query.SqlStat;
import cn.strong.leke.monitor.mongo.service.ISqlDailyService;
import cn.strong.leke.monitor.mongo.service.ISqlHashService;
import cn.strong.leke.monitor.mongo.service.ISqlRecordService;

/**
 *
 * 描述:
 *
 * @author  C.C
 * @created 2014-8-19 下午3:33:40
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/operationStaff/sql")
public class SqlController {
	@Autowired
	private ISqlDailyService dailyService;
	@Autowired
	private ISqlRecordService recordService;
	@Autowired
	private ISqlHashService hashService;
	
	@RequestMapping("daily")
	public void daily() {

	}

	@RequestMapping("dailyDatas")
	@ResponseBody
	public JsonResult dailyDatas(SqlDailyQuery query) {
		JsonResult result = new JsonResult();
		List<SqlDaily> stats = dailyService.findSqlDailys(query);
		result.addDatas("stats", stats);
		return result;
	}

	@RequestMapping("record")
	public void record(SqlRecordQuery query, Model model) {
		fixSpaceToPlus(query);
		model.addAttribute("query", JsonUtils.toJSON(query));
	}

	@RequestMapping("recordDatas")
	@ResponseBody
	public JsonResult recordDatas(SqlRecordQuery query, Page page) {
		fixSpaceToPlus(query);
		JsonResult result = new JsonResult();
		List<SqlRecord> records = recordService.findSqlRecords(query, page);
		result.addDatas("records", records);
		result.addDatas("page", page);
		return result;
	}

	@RequestMapping("recordStat")
	@ResponseBody
	public JsonResult recordStat(SqlRecordQuery query) {
		fixSpaceToPlus(query);
		JsonResult result = new JsonResult();
		SqlStat stat = recordService.getSqlStat(query);
		SqlHash sqlHash = hashService.getSqlHash(query.getHash());
		result.addDatas("stat", stat);
		result.addDatas("info", sqlHash);
		return result;
	}

	@RequestMapping("info")
	public void info(String hash, Model model) {
		SqlHash sqlHash = hashService.getSqlHash(fixSpaceToPlus(hash));
		if (sqlHash != null) {
			sqlHash.setSql(replaceBlankLines(sqlHash.getSql()));
		}
		model.addAttribute("info", sqlHash);
	}

	private static String replaceBlankLines(String sql) {
		if (sql == null) {
			return sql;
		}
		return sql.replaceAll("\\n\\s*\\n", "\n");
	}

	private static String fixSpaceToPlus(String hash) {
		if (hash == null) {
			return hash;
		}
		return hash.replace('\u0020', '+'); // 修正 URL 解码 + 号被替换为空格
	}

	private static void fixSpaceToPlus(SqlRecordQuery query) {
		if (query == null || query.getHash() == null) {
			return;
		}
		query.setHash(fixSpaceToPlus(query.getHash()));
	}
}
