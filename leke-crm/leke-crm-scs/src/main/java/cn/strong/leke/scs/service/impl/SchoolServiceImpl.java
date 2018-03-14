/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.scs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.scs.dao.slave.TutorUserDao;
import cn.strong.leke.scs.model.TutorUser;
import cn.strong.leke.scs.model.query.TutorUserQuery;
import cn.strong.leke.scs.service.SchoolService;

/**
 *
 * 描述:
 *
 * @author  yuanyi
 * @created 2015年9月1日 下午3:55:00
 * @since   v1.0.0
 */
@Service
public class SchoolServiceImpl implements SchoolService {

	@Resource
	private TutorUserDao tutorUserDao;
	
	@Override
	public List<TutorUser> findSchoolUserList(TutorUserQuery query, Page page) {
		return tutorUserDao.queryTutorUserList(query, page);
	}

	@Override
	public List<Long> querySchoolIdsBySeller(Long sellerId) {
		if (sellerId == null) {
			return null;
		}
		return tutorUserDao.querySchoolIdsBySeller(sellerId);
	}
	
	

}
