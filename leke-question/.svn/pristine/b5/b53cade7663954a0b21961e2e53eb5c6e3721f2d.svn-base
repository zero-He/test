/**
 * 
 */
package cn.strong.leke.question.core.question.cmd.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.cmd.store.IFavoriteQuestionHandler;
import cn.strong.leke.question.core.question.cmd.store.IPersonalQuestionHandler;
import cn.strong.leke.question.dao.mybatis.QuestionDao;
import cn.strong.leke.question.dao.mybatis.question.ITeacherQuestionDao;

/**
 * @author liulongbiao
 *
 */
@Service
public class FavoriteQuestionHandler implements IFavoriteQuestionHandler {

	private static final String ERR_FAVED = "que.teaQue.already.exist";
	private static final String ERR_OWN = "que.teaQue.info.own";
	@Resource
	private ITeacherQuestionDao teacherQuestionDao;
	@Resource
	private QuestionDao questionDao;
	@Resource
	private IPersonalQuestionHandler personalQuestionHandler;

	@Override
	@Transactional
	public void add(Long questionId, Long userId, User user) {
		Validation.isFalse(personalQuestionHandler.contains(questionId, userId), ERR_OWN);
		Validation.isFalse(contains(questionId, userId), ERR_FAVED);

		teacherQuestionDao.addFavorite(questionId, userId);

		questionDao.incFavCount(questionId);
		QuestionContext.incCountInCache(questionId, "favCount");
	}

	@Override
	@Transactional
	public int remove(Long questionId, Long userId, User user) {
		return teacherQuestionDao.deleteFavorites(new Long[] { questionId }, userId);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean contains(Long questionId, Long userId) {
		if (questionId == null) {
			return false;
		}
		int count = teacherQuestionDao.countFavorite(questionId, userId);
		return count > 0;
	}
}
