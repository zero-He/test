package cn.strong.leke.question.core.question.cmd.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.cmd.store.IQuestionOperationHandler;
import cn.strong.leke.question.dao.mybatis.question.IQuestionOperationDao;
import cn.strong.leke.question.model.question.QuestionOperation;

@Service
public class QuestionOperationHandler implements IQuestionOperationHandler {

	@Resource
	private IQuestionOperationDao questionOperationDao;

	@Override
	public void add(Long questionId, int operationType, User user) {
		Validation.notNull(questionId,"习题ID不能为空！");
		Long userId = user.getId();
		QuestionOperation assoc=new QuestionOperation();
		assoc.setQuestionId(questionId);
		assoc.setCreatedBy(userId);
		assoc.setOperationType(operationType);
		questionOperationDao.add(assoc);
	}

}
