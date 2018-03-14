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

import cn.strong.leke.model.question.QuestionStem;

/**
 * 
 * 描述: 题干DAO
 * 
 * @author liulb
 * @created 2014年5月4日 上午11:23:28
 * @since v1.0.0
 */
public interface QuestionStemDao {

	/**
	 * 
	 * 描述: 新增题干信息
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午10:36:04
	 * @since v1.0.0
	 * @param questionStem
	 * @return int
	 */
	int insertQuestionStem(QuestionStem questionStem);

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
	int deleteQuestionStemByQuestionId(Long questionId);

	/**
	 * 
	 * 描述: 根据题目ID获取题干内容
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午11:29:43
	 * @since v1.0.0
	 * @param questionId
	 * @return
	 * @return List<QuestionStem>
	 */
	List<QuestionStem> findQuestionStemByQuestionId(Long questionId);

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
