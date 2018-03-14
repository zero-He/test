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

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.model.question.QuestionStem;
import cn.strong.leke.question.dao.mybatis.QuestionStemDao;
import cn.strong.leke.question.service.QuestionStemService;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年5月4日 上午11:21:48
 * @since   v1.0.0
 */
@Service
public class QuestionStemServiceImpl implements QuestionStemService {

	@Autowired
	private QuestionStemDao questionStemDao;

	@Override
	public void addQuestionStem(QuestionStem questionStem) {
		questionStemDao.insertQuestionStem(questionStem);
	}

	@Override
	public void deleteQuestionStemByQuestionId(Long questionId) {
		questionStemDao.deleteQuestionStemByQuestionId(questionId);
	}

	@Override
	public QuestionStem getQuestionStemByQuestionId(Long questionId) {
		List<QuestionStem> stems = questionStemDao
				.findQuestionStemByQuestionId(questionId);
		return CollectionUtils.getFirst(stems);
	}

	@Override
	public int updateQuestionStem(QuestionStem questionStem) {
		return questionStemDao.updateQuestionStem(questionStem);
	}

}
