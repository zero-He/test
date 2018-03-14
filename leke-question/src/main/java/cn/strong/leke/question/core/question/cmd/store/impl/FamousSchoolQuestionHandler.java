/**
 * 
 */
package cn.strong.leke.question.core.question.cmd.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.repository.RepoCsts.CheckStatus;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.cmd.store.IFamousSchoolQuestionHandler;
import cn.strong.leke.question.dao.mybatis.question.IFamousSchoolQuestionCheckLogDao;
import cn.strong.leke.question.dao.mybatis.question.IFamousSchoolQuestionDao;
import cn.strong.leke.question.model.question.FamousSchoolQuestion;
import cn.strong.leke.question.model.question.FamousSchoolQuestionCheckLog;
import cn.strong.leke.repository.common.utils.RepoUtils;

/**
 * @author liulongbiao
 *
 */
@Service
public class FamousSchoolQuestionHandler implements IFamousSchoolQuestionHandler {

	private static final String ERR_NOT_EXIST = "que.question.not.exist";
	@Resource
	private IFamousSchoolQuestionDao famousSchoolQuestionDao;
	@Resource
	private IFamousSchoolQuestionCheckLogDao checkLogDao;

	@Override
	@Transactional
	public void add(Long resId, Long schoolId, int status, String editLog, User user) {
		FamousSchoolQuestion assoc = new FamousSchoolQuestion();
		assoc.setQuestionId(resId);
		assoc.setSchoolId(schoolId);
		assoc.setStatus(status);
		assoc.setEditLog(editLog);
		Long userId = user.getId();
		assoc.setCreatedBy(userId);
		assoc.setModifiedBy(userId);

		famousSchoolQuestionDao.add(assoc);
	}

	@Override
	@Transactional
	public int remove(Long resId, Long schoolId, User user) {
		FamousSchoolQuestion assoc = new FamousSchoolQuestion();
		assoc.setQuestionId(resId);
		assoc.setSchoolId(schoolId);
		assoc.setModifiedBy(user.getId());
		return famousSchoolQuestionDao.remove(assoc);
	}

	@Override
	@Transactional
	public void override(Long oldId, Long newId, Long schoolId, User user) {
		FamousSchoolQuestion old = famousSchoolQuestionDao.getByQuestionAndSchool(oldId, schoolId);
		Validation.notNull(old, ERR_NOT_EXIST);
		remove(oldId, schoolId, user);
		String editLog = RepoUtils.getEditLog(user);
		add(newId, schoolId, CheckStatus.INIT, editLog, user);
	}

	@Override
	@Transactional
	public void checkEdit(Long oldId, Long newId, Long schoolId, User user) {
		FamousSchoolQuestion old = famousSchoolQuestionDao.getByQuestionAndSchool(oldId, schoolId);
		Validation.notNull(old, ERR_NOT_EXIST);

		String editLog = RepoUtils.getEditLog(user);

		updateStatus(oldId, schoolId, CheckStatus.EDITED, editLog, user);
		remove(oldId, schoolId, user);
		add(newId, schoolId, CheckStatus.CHECKED, editLog, user);

		addCheckLog(oldId, schoolId, old.getStatus(), CheckStatus.EDITED, editLog, user);
	}

	private void updateStatus(Long questionId, Long schoolId, int status, String editLog, User user) {
		FamousSchoolQuestion update = new FamousSchoolQuestion();
		update.setQuestionId(questionId);
		update.setSchoolId(schoolId);
		update.setStatus(status);
		update.setEditLog(editLog);
		update.setModifiedBy(user.getId());
		famousSchoolQuestionDao.updateStatus(update);
	}

	private void addCheckLog(Long questionId, Long schoolId, int fromStatus, int toStatus,
			String checkNote, User user) {
		FamousSchoolQuestionCheckLog log = new FamousSchoolQuestionCheckLog();
		log.setQuestionId(questionId);
		log.setSchoolId(schoolId);
		log.setFromStatus(fromStatus);
		log.setToStatus(toStatus);
		log.setCheckNote(checkNote);
		log.setCheckedUser(user.getUserName());
		log.setCreatedBy(user.getId());
		checkLogDao.add(log);
	}

	@Override
	@Transactional
	public int checkPass(Long questionId, Long schoolId, User user) {
		FamousSchoolQuestion record = famousSchoolQuestionDao.getByQuestionAndSchool(questionId,
				schoolId);
		if (record == null) {
			return 0;
		}
		updateStatus(questionId, schoolId, CheckStatus.CHECKED, null, user);
		addCheckLog(questionId, schoolId, record.getStatus(), CheckStatus.CHECKED, null, user);
		return 1;
	}

	@Override
	@Transactional
	public int checkReject(Long questionId, Long schoolId, String checkNote, User user) {
		FamousSchoolQuestion record = famousSchoolQuestionDao.getByQuestionAndSchool(questionId,
				schoolId);
		if (record == null) {
			return 0;
		}

		updateStatus(questionId, schoolId, CheckStatus.REJECTED, checkNote, user);
		// remove(questionId, schoolId, user);

		addCheckLog(questionId, schoolId, record.getStatus(), CheckStatus.REJECTED, checkNote, user);
		return 1;
	}

	@Override
	public boolean contains(Long resId, Long schoolId) {
		if (resId == null) {
			return false;
		}
		FamousSchoolQuestion old = famousSchoolQuestionDao.getByQuestionAndSchool(resId, schoolId);
		return old != null;
	}

}
