/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.Question;
import cn.strong.leke.question.dao.mybatis.QuestionDao;
import cn.strong.leke.question.duplication.model.DupQuestionQuery;
import cn.strong.leke.question.duplication.model.UpdatedQuestionQuery;
import cn.strong.leke.question.duplication.service.DuplicationQuestionService;

/**
 *
 *
 * @author  liulongbiao
 * @created 2015年1月18日 下午1:46:33
 * @since   v3.2.2
 */
@Service
public class DuplicationQuestionServiceImpl implements
		DuplicationQuestionService {

	@Autowired
	private QuestionDao questionDao;

	@Override
	public List<Long> queryUpdatedPlatformQuestionIds(
			UpdatedQuestionQuery query) {
		return questionDao.queryUpdatedPlatformQuestionIds(query);
	}

	@Override
	public List<Long> queryRemovedPlatformQuestionIds(
			UpdatedQuestionQuery query) {
		return questionDao.queryRemovedPlatformQuestionIds(query);
	}

	@Override
	public void updateMayDup(Long questionId, boolean mayDup) {
		if (questionId == null) {
			throw new LekeRuntimeException("que.question.info.incomplete");
		}
		Question que = new Question();
		que.setQuestionId(questionId);
		que.setMayDup(mayDup);
		questionDao.updateMayDup(que);
	}

	@Override
	public List<Long> queryDupQuestionIds(DupQuestionQuery query, Page page) {
		return questionDao.queryDupQuestionIds(query, page);
	}

}
