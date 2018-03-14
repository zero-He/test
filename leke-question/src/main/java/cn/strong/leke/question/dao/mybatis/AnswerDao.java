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

import cn.strong.leke.model.question.Answer;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年5月4日 下午4:54:17
 * @since   v1.0.0
 */
public interface AnswerDao {

	/**
	 * 
	 * 描述: 新增题干信息
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午10:36:04
	 * @since v1.0.0
	 * @param answer
	 * @return int
	 */
	int insertAnswer(Answer answer);

	/**
	 * 
	 * 描述: 删除题干信息
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午10:36:04
	 * @since v1.0.0
	 * @param questionId
	 * @return int
	 */
	int deleteAnswerByQuestionId(Long questionId);

	/**
	 *
	 * 描述:
	 *
	 * @author raolei
	 * @created 2017年6月30日 下午9:03:39
	 */
	int deleteAnswer(Long answerId);

	/**
	 * 
	 * 描述: 根据题目ID获取题干内容
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午11:29:43
	 * @since v1.0.0
	 * @param questionId
	 * @return
	 * @return List<Answer>
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
