/**
 * 
 */
package cn.strong.leke.question.core.question.cmd.store.impl;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.cmd.store.IPersonalQuestionHandler;
import cn.strong.leke.question.dao.mybatis.QuestionDao;
import cn.strong.leke.question.dao.mybatis.question.IQuestionUserResGroupDao;

/**
 * @author liulongbiao
 *
 */
@Service
public class PersonalQuestionHandler implements IPersonalQuestionHandler {
	private static final String ERR_NOT_EXIST = "que.question.not.exist";
	@Resource
	private QuestionDao questionDao;
	@Resource
	private IQuestionUserResGroupDao questionUserResGroupDao;

	@Override
	@Transactional
	public void add(Long questionId, Long userId, User user) {
		Validation.notNull(questionId, "que.question.info.incomplete");
		QuestionDTO question = questionDao.getQuestion(questionId);
		Validation.notNull(question, ERR_NOT_EXIST);
		Long roleId = user.getCurrentRole().getId();
		if (question.getCreatedBy().equals(userId)) {
			questionDao.addToPersonal(questionId, userId, user.getId());
		} else {
			if (!RoleCst.RESEARCHER.equals(roleId)) {
				throw new ValidateException("不是自己的资源，不能加入到个人库");
			}
		}


	}

	@Override
	@Transactional
	public int remove(Long questionId, Long userId, User user) {
		return questionDao.removeFromPersonal(questionId, userId, user.getId());
	}

	@Override
	@Transactional
	public void override(Long oldId, Long newId, Long userId, User user) {
		Validation.isTrue(contains(oldId, userId), "该资源不是自己的资源");
		remove(oldId, userId, user);
		add(newId, userId, user);

		// process userResGroupId
		Set<Long> oldIds = newHashSet(oldId);
		Set<Long> newIds = newHashSet(newId);
		questionUserResGroupDao.findByQuestionAndUser(oldId, userId).forEach(assoc -> {
			Long grpId = assoc.getUserResGroupId();
			questionUserResGroupDao.deleteBatchQuestionUserResGroup(grpId, oldIds, userId);
			questionUserResGroupDao.addBatchQuestionUserResGroup(grpId, newIds, userId);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public boolean contains(Long questionId, Long userId) {
		if (questionId == null) {
			return false;
		}
		QuestionDTO question = questionDao.getQuestion(questionId);
		Validation.notNull(question, ERR_NOT_EXIST);
		Boolean sharePersonal = question.getSharePersonal();
		return question.getCreatedBy().equals(userId) && sharePersonal != null
				&& sharePersonal.equals(true);
	}

}
