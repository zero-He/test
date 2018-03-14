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
import cn.strong.leke.monitor.mongo.model.JobExRecord;
import cn.strong.leke.monitor.mongo.model.query.JobExRecordQuery;
import cn.strong.leke.monitor.mongo.model.query.JobExStat;

/**
 * 定时任务异常记录服务
 *
 * @author liulongbiao
 */
public interface IJobExRecordService {

	/**
	 * 添加定时任务异常记录
	 * 
	 * @author liulongbiao
	 * @param record
	 */
	void add(JobExRecord record);

	/**
	 * 查找定时任务异常记录
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<JobExRecord> findJobExRecords(JobExRecordQuery query, Page page);

	/**
	 * 获取统计
	 * 
	 * @param query
	 * @return
	 */
	List<JobExStat> findJobExStats(JobExRecordQuery query);
}
