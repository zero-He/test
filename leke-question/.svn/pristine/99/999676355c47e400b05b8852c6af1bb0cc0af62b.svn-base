/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.question.dao.mybatis.QuestionReviewDao;
import cn.strong.leke.question.model.QuestionReview;
import cn.strong.leke.question.service.QuestionReviewService;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年7月30日 下午5:24:48
 * @since   v1.1.0
 */
@Service
public class QuestionReviewServiceImpl implements QuestionReviewService {

	@Autowired
	private QuestionReviewDao questionReviewDao;

	@Override
	public void addQuestionReview(QuestionReview review) {
		if (review == null || review.getQuestionId() == null) {
			throw new ValidateException("que.review.info.incomplete");
		}
		int count = questionReviewDao.countQuestionReview(review);
		if (count < 1) {
			questionReviewDao.insertQuestionReview(review);
		}
	}
}
