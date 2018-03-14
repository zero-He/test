/**
 * 
 */
package cn.strong.leke.question.core.question.cmd.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.question.core.question.cmd.store.IQuestionGetHandler;
import cn.strong.leke.question.core.question.query.IQuestionQueryService;

/**
 * @author liulongbiao
 *
 */
@Service
public class QuestionGetHandler implements IQuestionGetHandler {
	@Resource
	private IQuestionQueryService questionQueryService;

	@Override
	public QuestionDTO getById(Long questionId) {
		return questionQueryService.getById(questionId);
	}

}
