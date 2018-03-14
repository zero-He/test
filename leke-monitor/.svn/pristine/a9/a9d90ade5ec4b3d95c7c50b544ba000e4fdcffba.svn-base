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
import cn.strong.leke.monitor.mongo.model.MleRecord;
import cn.strong.leke.monitor.mongo.model.query.MleRecordQuery;

/**
 * 消息监听异常记录服务
 * 
 * @author liulongbiao
 */
public interface IMleRecordService {

	/**
	 * 添加消息监听异常记录
	 * 
	 * @author liulongbiao
	 * @param record
	 */
	void add(MleRecord record);

	/**
	 * 查询消息监听异常记录
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<MleRecord> findMleRecords(MleRecordQuery query, Page page);
}
