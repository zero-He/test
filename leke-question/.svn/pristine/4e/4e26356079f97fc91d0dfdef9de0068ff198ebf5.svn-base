/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service;

import cn.strong.leke.common.utils.BeanUtils;
import cn.strong.leke.common.utils.function.Function;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.UserQuestionDTO;

/**
 *
 * 描述:
 *
 * @author  lavender
 * @created 2015年1月28日 下午3:38:04
 * @since   v1.0.0
 */
public enum GetUserQuestionDTOFn implements Function<Long, UserQuestionDTO> {
	INSTANCE;

	@Override
	public UserQuestionDTO apply(Long questionId) {
		if (null != questionId) {
			QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
			UserQuestionDTO result = new UserQuestionDTO();
			if (null != question) {
				BeanUtils.copyProperties(result, question);
			}
			return result;
		}
		return null;
	}

}
