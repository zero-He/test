package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.common.utils.BeanUtils;
import cn.strong.leke.common.utils.function.Function;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.framework.spring.SpringContextHolder;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionFeedbackDTO;
import cn.strong.leke.model.question.UserQuestionDTO;

public enum GetUserQuestionFeedbackFn implements Function<Long, UserQuestionDTO> {
	INSTANCE;
	public static IQuestionFeedbackService questionFeedbackService = SpringContextHolder
			.getBean(IQuestionFeedbackService.class);

	@Override
	public UserQuestionDTO apply(Long questionId) {
		if (null != questionId) {
			QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
			UserQuestionDTO result = new UserQuestionDTO();
			if (null != question) {
				BeanUtils.copyProperties(result, question);
				List<QuestionFeedbackDTO> feedbacks = questionFeedbackService.findQuestionFeedback(questionId);
				for (QuestionFeedbackDTO feedback : feedbacks) {
					Long questionFeedbackId = feedback.getQuestionFeedbackId();
					List<Integer> feedbackType = questionFeedbackService.findQuestionFeedbackType(questionFeedbackId);
					feedback.setQuestionFeedbackType(feedbackType);
				}
				result.setFeedback(feedbacks);
			}
			return result;
		}
		return null;
	}

}