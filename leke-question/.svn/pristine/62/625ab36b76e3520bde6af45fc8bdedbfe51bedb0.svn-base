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

import cn.strong.leke.model.question.QuestionSection;
import cn.strong.leke.question.dao.mybatis.question.IQuestionSectionDao;
import cn.strong.leke.question.service.QuestionSectionService;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年5月8日 下午2:39:25
 * @since   v1.0.0
 */
@Service
public class QuestionSectionServiceImpl implements QuestionSectionService {

	@Autowired
	private IQuestionSectionDao questionSectionDao;


	@Override
	public void addQuestionSection(QuestionSection assoc) {
		questionSectionDao.insertQuestionSection(assoc);
	}

	@Override
	public void deleteQuestionSectionByQuestionId(Long questionId, Long modifiedBy) {
		questionSectionDao.deleteQuestionSections(questionId, modifiedBy);
	}

	@Override
	public void deleteQuestionSectionByQuesSectionId(Long quesSectionId, Long modifiedBy) {
		questionSectionDao.deleteQuestionSection(quesSectionId, modifiedBy);
	}

	@Override
	public QuestionSection getQuestionSection(Long quesSectionId) {
		return questionSectionDao.getQuestionSection(quesSectionId);
	}

	@Override
	public List<QuestionSection> findQuestionSectionsByQuestionId(
			Long questionId) {
		return questionSectionDao.findByQuestionId(questionId);
	}

}
