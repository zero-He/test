/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.model.question.Press;
import cn.strong.leke.model.user.User;

/**
 *
 * 描述:
 *
 * @author  C.C
 * @created 2014-4-29 下午4:46:54
 * @since   v1.0.0
 */
public interface IPressService {
	
	/**
	 * 添加教材版本
	 * 
	 * @param press
	 * @return
	 */
	Long addPress(Press press);
	
	/**
	 * 根据条件查找教材版本
	 * 
	 * @param press
	 * @return
	 */
	List<Press> findPresses();
	
	/**
	 * 更新版本信息
	 * 
	 * @param press
	 * @return
	 */
	boolean updatePress(Press press);
	
	/**
	 * 删除教材版本
	 * 
	 * @param pressId
	 * @param user
	 * @return
	 */
	boolean deletePress(Long pressId, User user);

	/**
	 * 根据学段学科查找教材版本
	 * 
	 * @param schoolStageId
	 * @param subjectId
	 * @return
	 */
	List<Press> findPresses(Long schoolStageId, Long subjectId);
}
