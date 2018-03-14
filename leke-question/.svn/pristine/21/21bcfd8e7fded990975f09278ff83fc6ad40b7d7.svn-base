/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service;

import cn.strong.leke.model.question.Analysis;

/**
 * 
 * 描述:
 * 
 * @author liulb
 * @created 2014年5月4日 下午4:30:30
 * @since v1.0.0
 */
public interface AnalysisService {
	/**
	 * 
	 * 描述: 添加题目解析
	 * 
	 * @author liulb
	 * @created 2014年5月4日 下午4:24:17
	 * @since v1.0.0
	 * @param analysis
	 * @return void
	 */
	void addAnalysis(Analysis analysis);

	/**
	 * 
	 * 描述: 根据题目ID删除题目解析
	 * 
	 * @author liulb
	 * @created 2014年5月4日 下午4:24:56
	 * @since v1.0.0
	 * @param questionId
	 * @return void
	 */
	void deleteAnalysisByQuestionId(Long questionId);

	/**
	 * 
	 * 描述: 根据题目ID获取题目解析
	 * 
	 * @author liulb
	 * @created 2014年5月4日 下午4:23:23
	 * @since v1.0.0
	 * @param questionId
	 * @return Analysis
	 */
	Analysis getAnalysisByQuestionId(Long questionId);

	/**
	 *	
	 * 描述:更新题干内容
	 *
	 * @author  lavender
	 * @created 2014年10月24日 下午4:37:11
	 * @since   v1.0.0 
	 * @param analysis
	 * @return
	 * @return  int
	 */
	int updateAnalysis(Analysis analysis);
}
