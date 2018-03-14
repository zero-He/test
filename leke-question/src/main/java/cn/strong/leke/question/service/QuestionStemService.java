/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service;

import cn.strong.leke.model.question.QuestionStem;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年5月4日 上午11:21:01
 * @since   v1.0.0
 */
public interface QuestionStemService {

	/**
	 * 
	 * 描述: 添加题干信息
	 * 
	 * @author liulb
	 * @created 2014年5月4日 下午4:24:17
	 * @since v1.0.0
	 * @param questionStem
	 * @return void
	 */
	void addQuestionStem(QuestionStem questionStem);

	/**
	 * 
	 * 描述: 根据题目ID删除题干
	 * 
	 * @author liulb
	 * @created 2014年5月4日 下午4:24:56
	 * @since v1.0.0
	 * @param questionId
	 * @return void
	 */
	void deleteQuestionStemByQuestionId(Long questionId);

	/**
	 * 
	 * 描述: 根据题目ID获取题干信息
	 * 
	 * @author liulb
	 * @created 2014年5月4日 下午4:23:23
	 * @since v1.0.0
	 * @param questionId
	 * @return QuestionStem
	 */
	QuestionStem getQuestionStemByQuestionId(Long questionId);

	/**
	 * 
	 * 描述:更新题干信息
	 *
	 * @author lavender
	 * @created 2014年10月24日 下午3:09:52
	 * @since v1.0.0
	 * @param questionStem
	 * @return
	 * @return int
	 */
	int updateQuestionStem(QuestionStem questionStem);
}
