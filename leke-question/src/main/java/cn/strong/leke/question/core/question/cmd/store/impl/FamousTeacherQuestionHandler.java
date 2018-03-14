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
import cn.strong.leke.question.core.question.cmd.store.IFamousTeacherQuestionHandler;
import cn.strong.leke.question.dao.mybatis.question.IFamousTeacherQuestionCheckLogDao;
import cn.strong.leke.question.dao.mybatis.question.IFamousTeacherQuestionDao;
import cn.strong.leke.question.model.question.FamousTeacherQuestion;
import cn.strong.leke.question.model.question.FamousTeacherQuestionCheckLog;
import cn.strong.leke.repository.common.utils.RepoUtils;

/**
 * @author liulongbiao
 *
 */
@Service
public class FamousTeacherQuestionHandler implements IFamousTeacherQuestionHandler {

	private static final String ERR_NOT_EXIST = "que.question.not.exist";
	@Resource
	private IFamousTeacherQuestionDao famousTeacherQuestionDao;
	@Resource
	private IFamousTeacherQuestionCheckLogDao checkLogDao;

	@Override
	@Transactional
	public void add(Long questionId, Long teacherId, int status, String editLog, User user) {
		FamousTeacherQuestion assoc = new FamousTeacherQuestion();
		assoc.setQuestionId(questionId);
		assoc.setTeacherId(teacherId);
		assoc.setStatus(status);
		assoc.setEditLog(editLog);
		Long userId = user.getId();
		assoc.setCreatedBy(userId);
		assoc.setModifiedBy(userId);

		famousTeacherQuestionDao.add(assoc);
	}

	@Override
	@Transactional
	public int remove(Long questionId, Long teacherId, User user) {
		FamousTeacherQuestion assoc = new FamousTeacherQuestion();
		assoc.setQuestionId(questionId);
		assoc.setTeacherId(teacherId);
		assoc.setModifiedBy(user.getId());
		return famousTeacherQuestionDao.remove(assoc);
	}

	@Override
	@Transactional
	public void override(Long oldId, Long newId, Long teacherId, User user) {
		FamousTeacherQuestion old = famousTeacherQuestionDao.getByQuestionAndTeacher(oldId,
				teacherId);
		Validation.notNull(old, ERR_NOT_EXIST);
		remove(oldId, teacherId, user);
		String editLog = RepoUtils.getEditLog(user);
		add(newId, teacherId, CheckStatus.INIT, editLog, user);
	}

	@Override
	@Transactional
	public void checkEdit(Long oldId, Long newId, Long teacherId, User user) {
		FamousTeacherQuestion old = famousTeacherQuestionDao.getByQuestionAndTeacher(oldId,
				teacherId);
		Validation.notNull(old, ERR_NOT_EXIST);

		String editLog = RepoUtils.getEditLog(user);

		updateStatus(oldId, teacherId, CheckStatus.EDITED, editLog, user);
		remove(oldId, teacherId, user);
		add(newId, teacherId, CheckStatus.CHECKED, editLog, user);

		addCheckLog(oldId, teacherId, old.getStatus(), CheckStatus.EDITED, editLog, user);
	}

	private void updateStatus(Long questionId, Long teacherId, int status, String editLog, User user) {
		FamousTeacherQuestion update = new FamousTeacherQuestion();
		update.setQuestionId(questionId);
		update.setTeacherId(teacherId);
		update.setStatus(status);
		update.setEditLog(editLog);
		update.setModifiedBy(user.getId());
		famousTeacherQuestionDao.updateStatus(update);
	}

	private void addCheckLog(Long questionId, Long teacherId, int fromStatus, int toStatus,
			String checkNote, User user) {
		FamousTeacherQuestionCheckLog log = new FamousTeacherQuestionCheckLog();
		log.setQuestionId(questionId);
		log.setTeacherId(teacherId);
		log.setFromStatus(fromStatus);
		log.setToStatus(toStatus);
		log.setCheckNote(checkNote);
		log.setCheckedUser(user.getUserName());
		log.setCreatedBy(user.getId());
		checkLogDao.add(log);
	}

	@Override
	@Transactional
	public int checkPass(Long questionId, Long teacherId, User user) {
		FamousTeacherQuestion record = famousTeacherQuestionDao.getByQuestionAndTeacher(questionId,
				teacherId);
		if (record == null) {
			return 0;
		}
		updateStatus(questionId, teacherId, CheckStatus.CHECKED, null, user);
		addCheckLog(questionId, teacherId, record.getStatus(), CheckStatus.CHECKED, null, user);
		return 1;
	}

	@Override
	@Transactional
	public int checkReject(Long questionId, Long teacherId, String checkNote, User user) {
		FamousTeacherQuestion record = famousTeacherQuestionDao.getByQuestionAndTeacher(questionId,
				teacherId);
		if (record == null) {
			return 0;
		}

		updateStatus(questionId, teacherId, CheckStatus.REJECTED, checkNote, user);
		// remove(questionId, teacherId, user);

		addCheckLog(questionId, teacherId, record.getStatus(), CheckStatus.REJECTED, checkNote,
				user);
		return 1;
	}

	@Override
	public boolean contains(Long resId, Long teacherId) {
		if (resId == null) {
			return false;
		}
		FamousTeacherQuestion old = famousTeacherQuestionDao.getByQuestionAndTeacher(resId,
				teacherId);
		return old != null;
	}

}
