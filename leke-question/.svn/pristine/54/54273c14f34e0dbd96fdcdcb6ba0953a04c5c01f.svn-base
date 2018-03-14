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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.model.question.QuestionKnowledge;
import cn.strong.leke.question.dao.mybatis.QuestionKnowledgeDao;
import cn.strong.leke.question.service.QuestionKnowledgeService;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年5月9日 下午2:48:04
 * @since   v1.0.0
 */
@Service
public class QuestionKnowledgeServiceImpl implements QuestionKnowledgeService {

	@Autowired
	private QuestionKnowledgeDao questionKnowledgeDao;

	@Override
	public void addQuestionKnowledge(QuestionKnowledge assoc) {
		questionKnowledgeDao.insertQuestionKnowledge(assoc);
	}

	@Override
	public void deleteQuestionKnowledge(QuestionKnowledge assoc) {
		questionKnowledgeDao.deleteQuestionKnowledge(assoc);
	}

	@Override
	public void deleteQuestionKnowledgeByQuestionId(Long questionId) {
		questionKnowledgeDao.deleteQuestionKnowledgeByQuestionId(questionId);
	}

	@Override
	public List<QuestionKnowledge> findQuestionKnowledgeByQuestionId(
			Long questionId) {
		return questionKnowledgeDao
				.findQuestionKnowledgeByQuestionId(questionId);
	}

	@Override
	public QuestionKnowledge getQuestionKnowledge(Long quesKnowledgeId) {
		return questionKnowledgeDao.getQuestionKnowledge(quesKnowledgeId);
	}
	
	@Override
	public List<Map<Long, Long>> findKnowledgeQuestionTotal(List<Long> questionIds) {
		return questionKnowledgeDao.findKnowledgeQuestionTotal(questionIds);
	}

}
