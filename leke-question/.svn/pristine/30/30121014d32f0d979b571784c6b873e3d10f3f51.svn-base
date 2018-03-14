package cn.strong.leke.question.core.question.cmd.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.cmd.store.IQuestionShareLogHandler;
import cn.strong.leke.question.core.question.cmd.store.ISchoolQuestionHandler;
import cn.strong.leke.question.dao.mybatis.QuestionDao;
import cn.strong.leke.question.dao.mybatis.question.IQuestionShareLogDao;
import cn.strong.leke.question.model.question.QuestionShareLog;
@Service
public class QuestionShareLogHandler implements IQuestionShareLogHandler {

	@Resource
	private IQuestionShareLogDao questionShareLogDao;
	@Resource
	private ISchoolQuestionHandler schoolQuestionHandler;
	@Resource
	private QuestionDao questionDao;

	@Override
	public void remove(Long shareLogId, User user) {
		QuestionShareLog shareLog = questionShareLogDao.getQuestionShareLog(shareLogId);
		Long userId = user.getId();
		if (shareLog == null || !userId.equals(shareLog.getCreatedBy())) {
			throw new ValidateException("que.question.info.incomplete");
		}
		questionShareLogDao.remove(shareLogId, userId);
		Long questionId = shareLog.getQuestionId();
		Integer shareScopre = shareLog.getShareScope();
		if (ShareScopes.LEKE_SHARE == shareScopre) {
			questionDao.removeFromLekeShare(questionId, userId);

		} else {
			schoolQuestionHandler.remove(questionId, user.getCurrentSchool().getId(), user);
			questionDao.removeFromSchoolShare(questionId, userId);
		}
		QuestionContext.deleteCache(questionId);
	}

}
