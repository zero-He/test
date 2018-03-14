/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.dao.mybatis;

import java.util.List;

import cn.strong.leke.model.question.HandwriteType;

/**
 *
 *
 * @author  liulb
 * @created 2014年11月17日 上午9:46:07
 * @since   v1.1.0
 */
public interface HandwriteTypeDao {

	/**
	 * 获取所有的手写题类型
	 * 
	 * @author liulb
	 * @created 2014年11月17日 上午9:44:46
	 * @since v3.2.1
	 * @return
	 */
	List<HandwriteType> findAllHandwriteTypes();
}
