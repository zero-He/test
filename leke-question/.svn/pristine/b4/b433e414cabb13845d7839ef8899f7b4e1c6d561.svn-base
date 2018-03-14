package cn.strong.leke.question.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.context.question.QuestionTypeContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.question.dao.mybatis.IQuestionTypeSubDao;
import cn.strong.leke.question.model.QuestionTypeSub;
import cn.strong.leke.question.service.IQuestionTypeSubService;

@Service
public class QuestionTypeSubService implements IQuestionTypeSubService {

	@Autowired
	private IQuestionTypeSubDao questionTypeSubDao;

	@Override
	public void saveQueTypeSubs(QuestionTypeSub queTypeSub) {
		if (queTypeSub == null || queTypeSub.getQuestionTypeId() == null) {
			throw new ValidateException("que.questionTypeSub.info.incomplete");
		}
		Long questionTypeId = queTypeSub.getQuestionTypeId();
		questionTypeSubDao.deleteQueTypeSubsByQueTypeId(questionTypeId);
		if (queTypeSub.getSubs().size() > 0) {
			questionTypeSubDao.insertQueTypeSubs(queTypeSub.getSubs());
		}
		QuestionTypeContext.clearCache();
	}
}
