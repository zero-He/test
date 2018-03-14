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

import cn.strong.leke.model.question.QuestionOfficialTag;
import cn.strong.leke.question.dao.mybatis.QuestionOfficialTagDao;
import cn.strong.leke.question.service.QuestionOfficialTagService;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年5月9日 下午3:41:15
 * @since   v1.0.0
 */
@Service
public class QuestionOfficialTagServiceImpl implements QuestionOfficialTagService {

	@Autowired
	private QuestionOfficialTagDao questionOfficialTagDao;

	@Override
	public void addQuestionOfficialTag(QuestionOfficialTag assoc) {
		questionOfficialTagDao.insertQuestionOfficialTag(assoc);
	}

	@Override
	public void deleteQuestionOfficialTag(QuestionOfficialTag assoc) {
		questionOfficialTagDao.deleteQuestionOfficialTag(assoc);
	}

	@Override
	public void deleteQuestionOfficialTagByQuestionId(Long questionId) {
		questionOfficialTagDao
				.deleteQuestionOfficialTagByQuestionId(questionId);
	}

	@Override
	public List<QuestionOfficialTag> findQuestionOfficialTagByQuestionId(
			Long questionId) {
		return questionOfficialTagDao
				.findQuestionOfficialTagByQuestionId(questionId);
	}

	@Override
	public QuestionOfficialTag getQuestionOfficialTag(Long quesOfficialTagId) {
		return questionOfficialTagDao.getQuestionOfficialTag(quesOfficialTagId);
	}

}
