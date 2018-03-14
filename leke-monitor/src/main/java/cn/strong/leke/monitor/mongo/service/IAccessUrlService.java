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

import cn.strong.leke.monitor.mongo.model.AccessUrl;

/**
 *
 *
 * @author  liulongbiao
 */
public interface IAccessUrlService {

	/**
	 * 保存访问 URL 信息
	 * 
	 * @author liulongbiao
	 * @param url
	 */
	void save(AccessUrl url);

	/**
	 * 获取所有 URL 信息
	 * 
	 * @return
	 */
	List<AccessUrl> findAll();
}
