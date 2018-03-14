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

import cn.strong.leke.model.question.Answer;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年5月4日 下午4:59:07
 * @since   v1.0.0
 */
public interface AnswerService {
	/**
	 * 
	 * 描述: 添加题目答案
	 * 
	 * @author liulb
	 * @created 2014年5月4日 下午4:24:17
	 * @since v1.0.0
	 * @param answer
	 * @return void
	 */
	void addAnswer(Answer answer);

	/**
	 * 
	 * 描述: 根据题目ID删除题目答案
	 * 
	 * @author liulb
	 * @created 2014年5月4日 下午4:24:56
	 * @since v1.0.0
	 * @param questionId
	 * @return void
	 */
	void deleteAnswerByQuestionId(Long questionId);

	/**
	 * 
	 * 描述: 根据题目ID获取题目答案
	 * 
	 * @author liulb
	 * @created 2014年5月4日 下午4:23:23
	 * @since v1.0.0
	 * @param questionId
	 * @return Answer
	 */
	List<Answer> findAnswersByQuestionId(Long questionId);

	/**
	 *	
	 * 描述:更新题干内容
	 *
	 * @author  lavender
	 * @created 2014年10月24日 下午4:39:52
	 * @since   v1.0.0 
	 * @param answer
	 * @return
	 * @return  int
	 */
	int updateAnswer(Answer answer);
}
