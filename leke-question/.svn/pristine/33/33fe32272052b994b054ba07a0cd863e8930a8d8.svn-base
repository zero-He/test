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

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.model.question.Press;

/**
 *
 * 教材版本 DAO
 *
 * @author C.C
 * @created 2014-4-29 下午4:41:35
 * @since v1.0.0
 */
public interface IPressDao {
	
	/**
	 * 添加版本
	 * 
	 * @param press
	 * @return
	 */
	int addPress(Press press);
	
	/**
	 * 查找所有教材版本
	 * 
	 * @return
	 */
	List<Press> findAll();
	
	/**
	 * 更新教材版本
	 * 
	 * @param press
	 * @return
	 */
	int updatePress(Press press);
	
	/**
	 * 删除教材版本
	 * 
	 * @param press
	 * @return
	 */
	int deletePress(@Param("pressId") Long pressId, @Param("modifiedBy") Long modifiedBy);

	/**
	 * 获取指定教材版本
	 * 
	 * @param pressId
	 * @return
	 */
	Press getById(Long pressId);

	/**
	 * 获取指定名称出版社数量
	 * 
	 * @param pressName
	 * @return
	 */
	int countByName(String pressName);
}
