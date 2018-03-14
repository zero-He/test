/**
 * 
 */
package cn.strong.leke.question.core.question.cmd.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.base.SchoolCst;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.cmd.store.ILekeShareQuestionHandler;
import cn.strong.leke.question.dao.mybatis.QuestionDao;

/**
 * @author liulongbiao
 *
 */
@Service
public class LekeShareQuestionHandler implements ILekeShareQuestionHandler {
	private static final String ERR_NOT_EXIST = "que.question.not.exist";
	@Resource
	private QuestionDao questionDao;

	@Override
	public void add(Long questionId, User user) {
		QuestionDTO question = questionDao.getQuestion(questionId);
		Validation.notNull(question, ERR_NOT_EXIST);
		Validation.isFalse(question.getSchoolId().equals(SchoolCst.LEKE), "该资源无法加入乐课分享库");
		questionDao.addToLekeShare(questionId, user.getId());
		QuestionContext.deleteCache(questionId);
	}

	@Override
	public int remove(Long questionId, User user) {
		return questionDao.removeFromLekeShare(questionId, user.getId());
	}

}
