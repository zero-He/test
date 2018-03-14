/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.monitor.mongo.service;

import cn.strong.leke.monitor.mongo.model.SqlHash;

/**
 * SQL hash 记录服务
 * 
 * @author liulongbiao
 */
public interface ISqlHashService {

	/**
	 * 保存 SqlHash
	 * 
	 * @author liulongbiao
	 * @param sqlHash
	 */
	void save(SqlHash sqlHash);

	/**
	 * 获取 SqlHash
	 * 
	 * @param hash
	 * @return
	 */
	SqlHash getSqlHash(String hash);
}
