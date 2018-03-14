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

import cn.strong.leke.model.question.QuestionOfficialTag;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年5月9日 下午3:41:03
 * @since   v1.0.0
 */
public interface QuestionOfficialTagService {
	/**
	 * 
	 * 描述: 新增题目标签关联
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午10:36:04
	 * @since v1.0.0
	 * @param assoc
	 * @return
	 */
	void addQuestionOfficialTag(QuestionOfficialTag assoc);

	/**
	 * 
	 * 描述: 删除题目标签关联
	 * 
	 * @author liulb
	 * @created 2014年5月18日 下午2:45:25
	 * @since v1.0.0
	 * @param assoc
	 * @return
	 */
	void deleteQuestionOfficialTag(QuestionOfficialTag assoc);

	/**
	 * 
	 * 描述: 删除题目标签关联
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午10:36:04
	 * @since v1.0.0
	 * @param questionId
	 * @return int
	 */
	void deleteQuestionOfficialTagByQuestionId(Long questionId);

	/**
	 * 
	 * 描述: 根据题目ID获取关联
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午11:29:43
	 * @since v1.0.0
	 * @param questionId
	 * @return List<QuestionOfficialTag>
	 */
	List<QuestionOfficialTag> findQuestionOfficialTagByQuestionId(
			Long questionId);

	/**
	 * 
	 * 描述: 根据ID获取关联
	 * 
	 * @author liulb
	 * @created 2014年8月8日 下午4:30:40
	 * @since v1.0.0
	 * @param quesOfficialTagId
	 * @return QuestionOfficialTag
	 */
	QuestionOfficialTag getQuestionOfficialTag(Long quesOfficialTagId);
}
