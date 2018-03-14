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
import cn.strong.leke.monitor.mongo.model.ExceptionRecord;
import cn.strong.leke.monitor.mongo.model.query.ExceptionRecordQuery;
import cn.strong.leke.monitor.mongo.model.query.ExceptionStat;

/**
 *
 *
 * @author  liulongbiao
 */
public interface IExceptionRecordService {

	/**
	 * 添加异常记录
	 * 
	 * @author liulongbiao
	 * @param record
	 */
	void add(ExceptionRecord record);

	/**
	 * 查找异常记录
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<ExceptionRecord> findExceptionRecords(ExceptionRecordQuery query, Page page);

	/**
	 * 获取统计
	 * 
	 * @param query
	 * @return
	 */
	List<ExceptionStat> findExceptionStats(ExceptionRecordQuery query);
}
