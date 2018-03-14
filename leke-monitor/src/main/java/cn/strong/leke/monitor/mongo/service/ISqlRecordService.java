/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.monitor.mongo.service;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.mongo.model.SqlRecord;
import cn.strong.leke.monitor.mongo.model.query.SqlRecordQuery;
import cn.strong.leke.monitor.mongo.model.query.SqlStat;

/**
 * SQL 记录服务
 * 
 * @author liulongbiao
 */
public interface ISqlRecordService {

	/**
	 * 添加 SQL 记录
	 * 
	 * @author liulongbiao
	 * @param record
	 */
	void add(SqlRecord record);

	/**
	 * 查找 SQL 记录
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<SqlRecord> findSqlRecords(SqlRecordQuery query, Page page);

	/**
	 * 获取统计
	 * 
	 * @param query
	 * @return
	 */
	SqlStat getSqlStat(SqlRecordQuery query);
}
