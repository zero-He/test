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

import cn.strong.leke.model.question.QuestionType;
import cn.strong.leke.question.model.SimilarQueTypeQuery;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年6月25日 下午2:00:54
 * @since   v1.0.0
 */
public interface QuestionTypeService {

	/**
	 * 
	 * 描述: 获取所有学科对应题型的MAP
	 * 
	 * @author liulb
	 * @created 2014年6月25日 下午2:02:12
	 * @since v1.0.0
	 * @return Map<Long,QuestionType>
	 */
	Map<String, List<QuestionType>> findAllSubjectQuestionTypeMap();

	/**
	 * 
	 * 描述: 根据学科ID获取题目类型
	 * 
	 * @author liulb
	 * @created 2014年6月25日 下午2:03:25
	 * @since v1.0.0
	 * @param subjectId
	 * @return List<QuestionType>
	 */
	List<QuestionType> findQuestionTypesBySubjectId(Long subjectId);

	/**
	 * 
	 * 描述: 查询所有题型
	 * 
	 * @author liulb
	 * @created 2014年7月2日 上午9:32:15
	 * @since v1.0.0
	 * @return
	 * @return List<QuestionType>
	 */
	List<QuestionType> findAllQuestionTypes();

	/**
	 * 
	 * 描述: 查询相似题型
	 * 
	 * @author liulb
	 * @created 2014年7月2日 下午4:30:13
	 * @since v1.0.0
	 * @param query
	 * @return List<QuestionType>
	 */
	List<QuestionType> findSimilarQuestionTypes(SimilarQueTypeQuery query);

	/**
	 *
	 * 描述:查询是否有子题的题型
	 *
	 * @author raolei
	 * @created 2016年1月25日 下午4:47:32
	 * @since v1.0.0
	 * @param isHasSub
	 * @return
	 * @return List<QuestionType>
	 */
	List<QuestionType> findQuestionTypesByHasSub(Boolean isHasSub);
	
	/**
	 * 
	 * 描述: 根据题型ID获取所有子题型
	 * 
	 * @author liulb
	 * @created 2014年6月25日 上午11:11:52
	 * @since v1.0.0
	 * @param questionTypeId
	 * @return List<QuestionType>
	 */
	List<QuestionType> findSubTypesByQuestionTypeId(Long questionTypeId);
}
