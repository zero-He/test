/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.model.question.Analysis;
import cn.strong.leke.question.dao.mybatis.AnalysisDao;
import cn.strong.leke.question.service.AnalysisService;

/**
 * 
 * 描述:
 * 
 * @author liulb
 * @created 2014年5月4日 下午4:30:42
 * @since v1.0.0
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {

	@Autowired
	private AnalysisDao analysisDao;

	@Override
	public void addAnalysis(Analysis analysis) {
		analysisDao.insertAnalysis(analysis);
	}

	@Override
	public void deleteAnalysisByQuestionId(Long questionId) {
		analysisDao.deleteAnalysisByQuestionId(questionId);
	}

	@Override
	public Analysis getAnalysisByQuestionId(Long questionId) {
		List<Analysis> analysises = analysisDao
				.findAnalysisByQuestionId(questionId);
		return CollectionUtils.getFirst(analysises);
	}

	@Override
	public int updateAnalysis(Analysis analysis) {
		return analysisDao.updateAnalysis(analysis);
	}

}
