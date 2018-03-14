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
import java.util.Map;

import cn.strong.leke.model.question.QuestionKnowledge;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年5月9日 下午2:47:53
 * @since   v1.0.0
 */
public interface QuestionKnowledgeService {
	/**
	 * 
	 * 描述: 新增题目知识点节点关联
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午10:36:04
	 * @since v1.0.0
	 * @param assoc
	 * @return
	 */
	void addQuestionKnowledge(QuestionKnowledge assoc);

	/**
	 * 
	 * 描述: 删除题目知识点节点关联
	 * 
	 * @author liulb
	 * @created 2014年5月18日 下午2:42:27
	 * @since v1.0.0
	 * @param assoc
	 * @return
	 */
	void deleteQuestionKnowledge(QuestionKnowledge assoc);

	/**
	 * 
	 * 描述: 删除题目知识点节点关联
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午10:36:04
	 * @since v1.0.0
	 * @param questionId
	 * @return
	 */
	void deleteQuestionKnowledgeByQuestionId(Long questionId);

	/**
	 * 
	 * 描述: 根据题目ID获取关联
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午11:29:43
	 * @since v1.0.0
	 * @param questionId
	 * @return List<QuestionKnowledge>
	 */
	List<QuestionKnowledge> findQuestionKnowledgeByQuestionId(Long questionId);

	/**
	 * 
	 * 描述: 根据主键ID获取关联
	 * 
	 * @author liulb
	 * @created 2014年8月8日 下午4:21:33
	 * @since v1.0.0
	 * @param quesKnowledgeId
	 * @return QuestionKnowledge
	 */
	QuestionKnowledge getQuestionKnowledge(Long quesKnowledgeId);
	
	/**
	 * 统计 题目 的相关知识点，知识点题目数量
	 * @param questionIds
	 * @return
	 */
	List<Map<Long, Long>> findKnowledgeQuestionTotal(List<Long> questionIds);

}
