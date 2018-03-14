/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.que.template;

import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionType;
import cn.strong.leke.question.word.ParseCsts.QuestionTypeNames;
import cn.strong.leke.question.word.model.QuestionPart;
import cn.strong.leke.question.word.que.BaseQuestionPartParser;

/**
 * 
 *
 * @author  liulongbiao
 * @created 2014年12月11日 上午9:01:47
 * @since   v3.2
 */
public class DelayedChoiceConverter extends ChoiceConverter {
	@Override
	public void postProcess(QuestionDTO result, QuestionPart part,
			BaseQuestionPartParser parser) {
		super.postProcess(result, part, parser);
		postSetType(result, parser);
	}

	private void postSetType(QuestionDTO result, BaseQuestionPartParser parser) {
		int correctCount = getCorrectOptionCount(result.getAnswers());
		String typeName = correctCount == 1 ? QuestionTypeNames.SINGLE_CHOICE
				: QuestionTypeNames.MULTI_CHOICE;
		QuestionType qt = parser.getQuestionType(typeName);
		if (qt != null) {
			result.setQuestionTypeId(qt.getQuestionTypeId());
			result.setHasSub(qt.getHasSub());
			result.setSubjective(qt.getSubjective());
			result.setTemplate(qt.getTemplate());
			result.setHandwritten(qt.isHandwritten());
		}
	}
}
