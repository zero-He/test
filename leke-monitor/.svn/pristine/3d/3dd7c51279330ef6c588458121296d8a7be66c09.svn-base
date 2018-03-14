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
import cn.strong.leke.monitor.mongo.model.AccessRecord;
import cn.strong.leke.monitor.mongo.model.query.AccessRecordQuery;
import cn.strong.leke.monitor.mongo.model.query.AccessStat;

/**
 *
 *
 * @author  liulongbiao
 */
public interface IAccessRecordService {

	/**
	 * 添加访问记录
	 * 
	 * @author liulongbiao
	 * @param access
	 */
	void add(AccessRecord access);

	/**
	 * 查找访问日志
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<AccessRecord> findAccessRecords(AccessRecordQuery query, Page page);

	/**
	 * 获取统计
	 * 
	 * @param query
	 * @return
	 */
	AccessStat getAccessRecordStat(AccessRecordQuery query);
}
