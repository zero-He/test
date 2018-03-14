/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service.impl;
import static cn.strong.leke.model.question.Question.QUE_STATUS_CHECKED;
import static cn.strong.leke.model.question.Question.QUE_STATUS_IMPORT_CHECKED;
import static cn.strong.leke.model.question.Question.QUE_STATUS_IMPORT_VERIFIED;
import static cn.strong.leke.model.question.Question.QUE_STATUS_TEACHER_CHECKED;
import static cn.strong.leke.model.question.Question.QUE_STATUS_TEACHER_INPUT;
import static cn.strong.leke.model.question.Question.QUE_STATUS_TEACHER_REJECT;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.common.utils.function.Function;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.Question;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionLog;
import cn.strong.leke.model.question.QuestionRejection;
import cn.strong.leke.model.user.Researcher;
import cn.strong.leke.question.dao.mybatis.QuestionDao;
import cn.strong.leke.question.dao.mybatis.question.IQuestionCheckPendingDao;
import cn.strong.leke.question.model.ImportedQuestionQuery;
import cn.strong.leke.question.service.IQuestionLogService;
import cn.strong.leke.question.service.IQuestionRejectionService;
import cn.strong.leke.question.service.QuestionManager;
import cn.strong.leke.question.service.ResearcherQuestionManager;
/**
 *
 *
 * @author  liulongbiao
 * @created 2014年12月26日 下午4:26:42
 * @since   v3.2.2
 */
@Service
public class ResearcherQuestionManagerImpl implements ResearcherQuestionManager {

	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private IQuestionLogService questionLogService;
	@Autowired
	private IQuestionRejectionService questionRejectionService;
	@Resource
	@Qualifier("userDynamicSender")
	private MessageSender userDynamicSender;
	@Resource
	private IQuestionCheckPendingDao questionCheckPendingDao;

	@Override
	public List<QuestionDTO> queryImportedQuestions(
			ImportedQuestionQuery query, Page page) {
		List<Long> questionIds = questionDao.queryImportedQuestionIds(query,
				page);
		List<QuestionDTO> result = ListUtils.map(questionIds,
				new Function<Long, QuestionDTO>() {

					@Override
					public QuestionDTO apply(Long questionId) {
						QuestionDTO result = QuestionContext
								.getQuestionDTO(questionId);
						if (result != null
								&& result.getQuestionStatus().equals(
										Question.QUE_STATUS_IMPORT_REJECTED)) {
							List<QuestionLog> logs = questionLogService
									.findQuestionLogByQuestionId(questionId);
							result.setLogs(logs);
						}
						return result;
					}

				});
		return result;
	}
	
	@Override
	public void updateChecked(Long questionId, Researcher researcher) {
		questionManager.validateQueIntegrity(questionId);

		QuestionDTO dto = questionDao.getQuestion(questionId);
		int oldStatus = dto.getQuestionStatus();
		int logType = QUE_STATUS_CHECKED;
		switch (oldStatus) {
		case QUE_STATUS_IMPORT_CHECKED:
			logType = QUE_STATUS_IMPORT_VERIFIED;
			break;
		case QUE_STATUS_TEACHER_INPUT:
		case QUE_STATUS_TEACHER_REJECT:
			logType = QUE_STATUS_TEACHER_CHECKED;
			break;
		default:
			logType = QUE_STATUS_CHECKED;
			break;
		}
		questionManager.addLog(questionId, logType, researcher);
		questionManager.updateStatus(questionId, QUE_STATUS_CHECKED);
		questionCheckPendingDao.del(questionId);
		QuestionContext.deleteCache(questionId);
	}

	@Override
	public void updateCheckedTeacherShare(Long questionId, Researcher researcher) {
		updateChecked(questionId, researcher);
	}

	@Override
	public void updateRejectedTeacherShare(QuestionRejection rejection,
			Researcher researcher) {
		if (rejection == null || rejection.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Long questionId = rejection.getQuestionId();
		QuestionDTO backend = questionDao.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}
		questionManager.updateStatus(questionId, QUE_STATUS_TEACHER_REJECT);
		questionRejectionService.addQuestionRejection(rejection);
		QuestionContext.deleteCache(questionId);
	}

}
