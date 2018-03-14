/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication.service;

import org.bson.types.ObjectId;

import cn.strong.leke.question.duplication.model.DupJob;

/**
 *
 *
 * @author  liulongbiao
 * @created 2015年1月18日 上午10:14:23
 * @since   v3.2.2
 */
public interface DupJobService {

	void save(ObjectId id, int status);

	DupJob getLastSuccess();
}
