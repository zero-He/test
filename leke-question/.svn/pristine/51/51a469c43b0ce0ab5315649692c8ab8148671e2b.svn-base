package cn.strong.leke.question.core.question.cmd.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.constant.RepoLevelCst;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.base.SchoolCst;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.cmd.store.IQuestionLevelHandler;
import cn.strong.leke.question.core.question.cmd.store.IQuestionOperationHandler;
import cn.strong.leke.question.dao.mybatis.QuestionDao;
import cn.strong.leke.question.util.QuestionIndexContext;

@Service
public class QuestionLevelHandler implements IQuestionLevelHandler {

	@Resource
	private QuestionDao questionDao;
	@Resource
	private IQuestionOperationHandler questionOperationHandler;

	@Override
	public void setQuestionPrime(Long questionId, User user) {
		verify(questionId);
		Long userId = user.getId();
		questionDao.setQuestionLevel(questionId, RepoLevelCst.LEVEL_PRIME, userId);
		questionOperationHandler.add(questionId, RepoLevelCst.LEVEL_PRIME, user);
		QuestionContext.deleteCache(questionId);
		QuestionIndexContext.sendUpdateIndex(questionId);
	}

	@Override
	public void setQuestionGeneral(Long questionId, User user) {
		verify(questionId);
		Long userId = user.getId();
		questionDao.setQuestionLevel(questionId, RepoLevelCst.LEVEL_GENERAL, userId);
		questionOperationHandler.add(questionId, RepoLevelCst.LEVEL_GENERAL, user);
		QuestionContext.deleteCache(questionId);
		QuestionIndexContext.sendUpdateIndex(questionId);
	}

	private void verify(Long questionId) {
		QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
		if (question == null) {
			throw new ValidateException("que.question.not.exist");
		}
		if (SchoolCst.LEKE != question.getSchoolId()) {
			throw new ValidateException("习题不属于精品库！");
		}
	}

}
