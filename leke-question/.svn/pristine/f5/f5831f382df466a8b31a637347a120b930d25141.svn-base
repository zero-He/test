/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.model.question.HandwriteType;
import cn.strong.leke.question.dao.mybatis.HandwriteTypeDao;
import cn.strong.leke.question.service.HandwriteTypeService;

/**
 *
 *
 * @author  liulb
 * @created 2014年11月17日 上午9:44:03
 * @since   v1.1.0
 */
@Service
public class HandwriteTypeServiceImpl implements HandwriteTypeService {

	@Autowired
	private HandwriteTypeDao handwriteTypeDao;

	@Override
	public List<HandwriteType> findAllHandwriteTypes() {
		return handwriteTypeDao.findAllHandwriteTypes();
	}

}
