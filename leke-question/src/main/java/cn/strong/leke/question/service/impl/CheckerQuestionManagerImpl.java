/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service.impl;

import static cn.strong.leke.model.question.Question.QUE_STATUS_IMPORT_CHECKED;
import static cn.strong.leke.model.question.Question.QUE_STATUS_IMPORT_REJECTED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionKnowledge;
import cn.strong.leke.model.question.QuestionLog;
import cn.strong.leke.model.question.QuestionOfficialTag;
import cn.strong.leke.question.model.QuestionTaskItem;
import cn.strong.leke.question.service.CheckerQuestionManager;
import cn.strong.leke.question.service.IQuestionLogService;
import cn.strong.leke.question.service.IQuestionTaskService;
import cn.strong.leke.question.service.QuestionKnowledgeService;
import cn.strong.leke.question.service.QuestionManager;
import cn.strong.leke.question.service.QuestionOfficialTagService;
import cn.strong.leke.question.service.QuestionService;

/**
 *
 * 描述:
 *
 * @author  lavender
 * @created 2014年12月30日 下午2:53:21
 * @since   v1.0.0
 */
@Service
public class CheckerQuestionManagerImpl implements CheckerQuestionManager {
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private IQuestionLogService questionLogService;
	@Autowired
	private QuestionKnowledgeService questionKnowledgeService;
	@Autowired
	private QuestionOfficialTagService questionOfficialTagService;
	@Autowired
	private IQuestionTaskService questionTaskService;
	@Override
	public void updateImportReject(QuestionDTO dto, String rejectReason) {
		if (dto == null || dto.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Long questionId = dto.getQuestionId();
		QuestionDTO backend = questionService.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}

		questionManager.updateStatus(questionId, QUE_STATUS_IMPORT_REJECTED);

		QuestionLog log = new QuestionLog();
		log.setQuestionId(dto.getQuestionId());
		log.setCreatedBy(dto.getModifiedBy());
		log.setUserName(dto.getOperatorName());
		log.setReason(rejectReason);
		log.setType(QUE_STATUS_IMPORT_REJECTED);
		questionLogService.addQuestionLog(log);

		QuestionContext.deleteCache(questionId);

	}

	@Override
	public void updateImportCheck(QuestionDTO dto) {
		if (dto == null || dto.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Long questionId = dto.getQuestionId();
		QuestionDTO backend = questionService.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}
		List<QuestionKnowledge> knowledges = questionKnowledgeService.findQuestionKnowledgeByQuestionId(questionId);
		if (CollectionUtils.isEmpty(knowledges)) {
			throw new ValidateException("que.question.no.knowledges");
		}
		List<QuestionOfficialTag> tags = questionOfficialTagService.findQuestionOfficialTagByQuestionId(questionId);
		if (CollectionUtils.isEmpty(tags)) {
			throw new ValidateException("que.question.no.tags");
		}

		questionManager.updateStatus(questionId, QUE_STATUS_IMPORT_CHECKED);

		QuestionTaskItem taskItem = new QuestionTaskItem();
		taskItem.setQuestionId(questionId);
		taskItem.setModifiedBy(dto.getModifiedBy());
		questionTaskService.deleteQuestionTaskItemByQuestionId(taskItem);

		questionManager.addLog(dto, QUE_STATUS_IMPORT_CHECKED);

		QuestionContext.deleteCache(questionId);
	}

}
