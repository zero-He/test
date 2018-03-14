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

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.common.utils.function.Function;
import cn.strong.leke.model.question.QuestionType;
import cn.strong.leke.question.dao.mybatis.QuestionTypeDao;
import cn.strong.leke.question.model.SimilarQueTypeQuery;
import cn.strong.leke.question.model.SubjectQueType;
import cn.strong.leke.question.service.QuestionTypeService;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年6月25日 下午2:04:39
 * @since   v1.0.0
 */
@Service
public class QuestionTypeServiceImpl implements QuestionTypeService {

	@Autowired
	private QuestionTypeDao questionTypeDao;

	@Override
	public Map<String, List<QuestionType>> findAllSubjectQuestionTypeMap() {
		List<SubjectQueType> subjectTypes = questionTypeDao
				.findAllSubjectQueTypes();
		doSetSubTypes(subjectTypes);
		return ListUtils.group(subjectTypes, new FnSubjectId(), new FnType());
	}

	@Override
	public List<QuestionType> findQuestionTypesBySubjectId(Long subjectId) {
		List<QuestionType> result = questionTypeDao
				.findQuestionTypesBySubjectId(subjectId);
		doSetSubTypes(result);
		return result;
	}

	private void doSetSubTypes(List<? extends QuestionType> types) {
		if (CollectionUtils.isEmpty(types)) {
			return;
		}
		for (QuestionType type : types) {
			if (type.getHasSub() != null && type.getHasSub().equals(true)) {
				Long typeId = type.getQuestionTypeId();
				List<QuestionType> subTypes = questionTypeDao
						.findSubTypesByQuestionTypeId(typeId);
				type.setSubTypes(subTypes);
			}
		}
	}

	private static class FnSubjectId implements
			Function<SubjectQueType, String> {

		@Override
		public String apply(SubjectQueType t) {
			return String.valueOf(t.getSubjectId());
		}

	}

	private static class FnType implements
			Function<SubjectQueType, QuestionType> {

		@Override
		public QuestionType apply(SubjectQueType t) {
			QuestionType result = new QuestionType();
			result.setQuestionTypeId(t.getQuestionTypeId());
			result.setQuestionTypeName(t.getQuestionTypeName());
			result.setTemplate(t.getTemplate());
			result.setSubjective(t.getSubjective());
			result.setHasSub(t.getHasSub());
			result.setOrd(t.getOrd());
			result.setSubTypes(t.getSubTypes());
			return result;
		}

	}

	@Override
	public List<QuestionType> findAllQuestionTypes() {
		List<QuestionType> result = questionTypeDao.findAllQuestionTypes();
		doSetSubTypes(result);
		return result;
	}

	@Override
	public List<QuestionType> findSimilarQuestionTypes(SimilarQueTypeQuery query) {
		return questionTypeDao.findSimilarQuestionTypes(query);
	}

	@Override
	public List<QuestionType> findQuestionTypesByHasSub(Boolean isHasSub) {
		return questionTypeDao.findQuestionTypesByHasSub(isHasSub);
	}

	@Override
	public List<QuestionType> findSubTypesByQuestionTypeId(Long questionTypeId) {
		return questionTypeDao.findSubTypesByQuestionTypeId(questionTypeId);
	}
}
