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

import cn.strong.leke.model.question.QuestionSection;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年5月8日 下午2:39:16
 * @since   v1.0.0
 */
public interface QuestionSectionService {
	/**
	 * 
	 * 描述: 新增题目章节节点关联
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午10:36:04
	 * @since v1.0.0
	 * @param assoc
	 * @return int
	 */
	void addQuestionSection(QuestionSection assoc);

	/**
	 * 
	 * 描述: 删除题目章节节点关联
	 * 
	 * @param questionId
	 * @param modifiedBy
	 * @return
	 */
	void deleteQuestionSectionByQuestionId(Long questionId, Long modifiedBy);

	/**
	 *
	 * 描述: 根据主键删除
	 *
	 * @author raolei
	 * @created 2016年8月3日 下午4:18:22
	 * @since v1.0.0
	 * @param quesSectionId
	 * @param modifiedBy
	 * @return void
	 */
	void deleteQuestionSectionByQuesSectionId(Long quesSectionId, Long modifiedBy);

	QuestionSection getQuestionSection(Long quesSectionId);

	/**
	 * 描述: 根据题目ID获取关联
	 * 
	 * @author liulongbiao
	 * @created 2015年4月30日 上午9:29:03
	 * @since v3.2.2
	 * @param questionId
	 * @return
	 */
	List<QuestionSection> findQuestionSectionsByQuestionId(Long questionId);
}
