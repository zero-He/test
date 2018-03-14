package cn.strong.leke.question.core.question.cmd.store.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.cmd.store.IQuestionUpdateHandler;
import cn.strong.leke.question.service.AnalysisService;
import cn.strong.leke.question.service.AnswerService;
import cn.strong.leke.question.service.QuestionService;
import cn.strong.leke.question.service.QuestionStemService;
import cn.strong.leke.question.util.QueContentDiff;

@Service
public class QuestionUpdateHandler implements IQuestionUpdateHandler {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionStemService questionStemService;
	@Autowired
	private AnalysisService analysisService;
	@Autowired
	private AnswerService answerService;

	@Override
	public void updateQuestionContent(QuestionDTO question, User user) {
		if (question == null || question.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Long questionId = question.getQuestionId();
		QuestionDTO backend = QuestionContext.getQuestionDTO(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}
		QueContentDiff.diff2(backend, question);
		questionService.updateQuestion(question);
	}


}
