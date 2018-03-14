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

import cn.strong.leke.model.question.Answer;
import cn.strong.leke.question.dao.mybatis.AnswerDao;
import cn.strong.leke.question.service.AnswerService;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年5月4日 下午4:59:18
 * @since   v1.0.0
 */
@Service
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private AnswerDao answerDao;

	@Override
	public void addAnswer(Answer answer) {
		answerDao.insertAnswer(answer);
	}

	@Override
	public void deleteAnswerByQuestionId(Long questionId) {
		answerDao.deleteAnswerByQuestionId(questionId);
	}

	@Override
	public List<Answer> findAnswersByQuestionId(Long questionId) {
		return answerDao.findAnswersByQuestionId(questionId);
	}

	@Override
	public int updateAnswer(Answer answer) {
		return answerDao.updateAnswer(answer);
	}
}
