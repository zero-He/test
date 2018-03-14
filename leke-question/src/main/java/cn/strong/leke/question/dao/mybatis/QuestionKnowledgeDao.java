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
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.model.question.QuestionKnowledge;

/**
 * 
 * 描述: 题目知识点节点关联DAO
 * 
 * @author liulb
 * @created 2014年5月7日 下午3:44:07
 * @since v1.0.0
 */
public interface QuestionKnowledgeDao {

	/**
	 * 
	 * 描述: 新增题目知识点节点关联
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午10:36:04
	 * @since v1.0.0
	 * @param assoc
	 * @return int
	 */
	int insertQuestionKnowledge(QuestionKnowledge assoc);

	/**
	 * 
	 * 描述: 删除题目知识点节点关联
	 * 
	 * @author liulb
	 * @created 2014年5月18日 下午2:42:27
	 * @since v1.0.0
	 * @param assoc
	 * @return int
	 */
	int deleteQuestionKnowledge(QuestionKnowledge assoc);

	/**
	 * 
	 * 描述: 删除题目知识点节点关联
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午10:36:04
	 * @since v1.0.0
	 * @param questionId
	 * @return int
	 */
	int deleteQuestionKnowledgeByQuestionId(Long questionId);

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
	 * @created 2014年8月8日 下午4:22:52
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
	List<Map<Long, Long>> findKnowledgeQuestionTotal(@Param("questionIds") List<Long> questionIds);

}