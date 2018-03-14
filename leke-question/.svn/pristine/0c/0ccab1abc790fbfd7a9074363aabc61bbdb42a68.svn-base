/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service.impl;

import static cn.strong.leke.model.BaseCache.PREFIX_PRESS;
import static cn.strong.leke.model.BaseCache.PREFIX_STGSBJ_PRESS;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.core.nosql.CacheUtils;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.question.Press;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.dao.mybatis.IPressDao;
import cn.strong.leke.question.dao.mybatis.base.IStageSubjectPressDao;
import cn.strong.leke.question.service.IPressService;

/**
 *
 * 描述:
 *
 * @author  C.C
 * @created 2014-4-29 下午4:47:28
 * @since   v1.0.0
 */
@Service
public class PressService implements IPressService {
	
	@Resource
	private IPressDao pressDao;
	@Resource
	private IStageSubjectPressDao stageSubjectPressDao;

	@Override
	public Long addPress(Press press) {
		String pressName = press.getPressName();
		Validation.isTrue(pressDao.countByName(pressName) == 0, "已存在名为'" + pressName + "'的教材版本");
		pressDao.addPress(press);
		CacheUtils.delete(PREFIX_PRESS);
		CacheUtils.delete(PREFIX_STGSBJ_PRESS);
		return press.getId();
	}

	@Override
	public boolean updatePress(Press press) {
		Long pressId = press.getPressId();
		String pressName = press.getPressName();
		Press old = pressDao.getById(pressId);
		Validation.notNull(old, "不存在ID为" + pressId + "的教材版本");
		if (!old.getPressName().equals(pressName)) {
			// 修改了名称需要判断和数据库中其它教材版本有没有重名
			Validation.isTrue(pressDao.countByName(pressName) == 0, "已存在名为'" + pressName + "'的教材版本");
		}
		int update = pressDao.updatePress(press);
		CacheUtils.delete(PREFIX_PRESS);
		CacheUtils.delete(PREFIX_STGSBJ_PRESS);
		return update > 0;
	}

	@Override
	public boolean deletePress(Long pressId, User user) {
		int deleted = pressDao.deletePress(pressId, user.getId());
		CacheUtils.delete(PREFIX_PRESS);
		CacheUtils.delete(PREFIX_STGSBJ_PRESS);
		return deleted > 0;
	}

	@Override
	public List<Press> findPresses() {
		return pressDao.findAll();
	}

	@Override
	public List<Press> findPresses(Long schoolStageId, Long subjectId) {
		return stageSubjectPressDao.findPresses(schoolStageId, subjectId);
	}

}
