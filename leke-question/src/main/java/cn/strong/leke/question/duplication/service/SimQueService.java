/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication.service;

import com.mongodb.client.FindIterable;

import cn.strong.leke.question.duplication.model.SimQue;

/**
 *
 *
 * @author  liulongbiao
 * @created 2015年1月19日 上午9:55:48
 * @since   v3.2.2
 */
public interface SimQueService {

	/**
	 * 根据习题ID获取记录
	 * 
	 * @author liulongbiao
	 * @created 2015年1月19日 下午1:11:02
	 * @since v3.2.2
	 * @param questionId
	 * @return
	 */
	SimQue getById(Long questionId);

	/**
	 * 根据习题ID移除记录
	 * 
	 * @author liulongbiao
	 * @created 2015年1月19日 上午11:12:35
	 * @since v3.2.2
	 * @param questionId
	 */
	void removeById(Long questionId);

	/**
	 * 查找包含指定习题ID 的记录
	 * 
	 * @author liulongbiao
	 * @created 2015年1月19日 上午11:12:47
	 * @since v3.2.2
	 * @param questionId
	 * @return
	 */
	FindIterable<SimQue> findContains(Long questionId);

	/**
	 * 添加关联
	 * 
	 * @author liulongbiao
	 * @created 2015年1月19日 下午1:36:17
	 * @since v3.2.2
	 * @param questionId
	 * @param simId
	 */
	void addRef(Long questionId, Long simId);

	/**
	 * 移除关联
	 * 
	 * @author liulongbiao
	 * @created 2015年1月19日 下午1:36:20
	 * @since v3.2.2
	 * @param questionId
	 * @param simId
	 */
	void removeRef(Long questionId, Long simId);
}
