/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.remote;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.model.question.QuestionType;
import cn.strong.leke.question.service.QuestionTypeService;
import cn.strong.leke.remote.service.question.IQuestionTypeRemoteService;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年7月2日 上午9:35:00
 * @since   v1.0.0
 */
@Service
public class QuestionTypeRemoteService implements IQuestionTypeRemoteService {

	@Resource
	private QuestionTypeService questionTypeService;

	@Override
	public Map<String, List<QuestionType>> findAllSubjectQuestionTypeMap() {
		return questionTypeService.findAllSubjectQuestionTypeMap();
	}

	@Override
	public List<QuestionType> findQuestionTypesBySubjectId(Long subjectId) {
		return questionTypeService.findQuestionTypesBySubjectId(subjectId);
	}

	@Override
	public List<QuestionType> findAllQuestionTypes() {
		return questionTypeService.findAllQuestionTypes();
	}

}
