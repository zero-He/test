/**
 * 
 */
package cn.strong.leke.question.core.workbook.cmd.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.repository.RepoCsts.CheckStatus;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.workbook.cmd.store.IFamousTeacherWorkbookHandler;
import cn.strong.leke.question.dao.mybatis.workbook.IFamousTeacherWorkbookCheckLogDao;
import cn.strong.leke.question.dao.mybatis.workbook.IFamousTeacherWorkbookDao;
import cn.strong.leke.question.model.workbook.FamousTeacherWorkbook;
import cn.strong.leke.question.model.workbook.FamousTeacherWorkbookCheckLog;
import cn.strong.leke.repository.common.utils.RepoUtils;

/**
 * @author liulongbiao
 *
 */
@Service
public class FamousTeacherWorkbookHandler implements IFamousTeacherWorkbookHandler {

	private static final String ERR_NOT_EXIST = "que.workbook.not.exist";
	@Resource
	private IFamousTeacherWorkbookDao famousTeacherWorkbookDao;
	@Resource
	private IFamousTeacherWorkbookCheckLogDao checkLogDao;

	@Override
	@Transactional
	public void add(Long workbookId, Long teacherId, int status, String editLog, User user) {
		FamousTeacherWorkbook assoc = new FamousTeacherWorkbook();
		assoc.setWorkbookId(workbookId);
		assoc.setTeacherId(teacherId);
		assoc.setStatus(status);
		assoc.setEditLog(editLog);
		Long userId = user.getId();
		assoc.setCreatedBy(userId);
		assoc.setModifiedBy(userId);

		famousTeacherWorkbookDao.add(assoc);
	}

	@Override
	@Transactional
	public int remove(Long workbookId, Long teacherId, User user) {
		FamousTeacherWorkbook assoc = new FamousTeacherWorkbook();
		assoc.setWorkbookId(workbookId);
		assoc.setTeacherId(teacherId);
		assoc.setModifiedBy(user.getId());
		return famousTeacherWorkbookDao.remove(assoc);
	}

	@Override
	@Transactional
	public void override(Long oldId, Long newId, Long teacherId, User user) {
		FamousTeacherWorkbook old = famousTeacherWorkbookDao.getByWorkbookAndTeacher(oldId,
				teacherId);
		Validation.notNull(old, ERR_NOT_EXIST);
		remove(oldId, teacherId, user);
		String editLog = RepoUtils.getEditLog(user);
		add(newId, teacherId, CheckStatus.INIT, editLog, user);
	}

	@Override
	@Transactional
	public void checkEdit(Long oldId, Long newId, Long teacherId, User user) {
		FamousTeacherWorkbook old = famousTeacherWorkbookDao.getByWorkbookAndTeacher(oldId,
				teacherId);
		Validation.notNull(old, ERR_NOT_EXIST);

		String editLog = RepoUtils.getEditLog(user);

		updateStatus(oldId, teacherId, CheckStatus.EDITED, editLog, user);
		remove(oldId, teacherId, user);
		add(newId, teacherId, CheckStatus.CHECKED, editLog, user);

		addCheckLog(oldId, teacherId, old.getStatus(), CheckStatus.EDITED, editLog, user);
	}

	private void updateStatus(Long workbookId, Long teacherId, int status, String editLog, User user) {
		FamousTeacherWorkbook update = new FamousTeacherWorkbook();
		update.setWorkbookId(workbookId);
		update.setTeacherId(teacherId);
		update.setStatus(status);
		update.setEditLog(editLog);
		update.setModifiedBy(user.getId());
		famousTeacherWorkbookDao.updateStatus(update);
	}

	private void addCheckLog(Long workbookId, Long teacherId, int fromStatus, int toStatus,
			String checkNote, User user) {
		FamousTeacherWorkbookCheckLog log = new FamousTeacherWorkbookCheckLog();
		log.setWorkbookId(workbookId);
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
	public int checkPass(Long workbookId, Long teacherId, User user) {
		FamousTeacherWorkbook record = famousTeacherWorkbookDao.getByWorkbookAndTeacher(workbookId,
				teacherId);
		if (record == null) {
			return 0;
		}
		updateStatus(workbookId, teacherId, CheckStatus.CHECKED, null, user);
		addCheckLog(workbookId, teacherId, record.getStatus(), CheckStatus.CHECKED, null, user);
		return 1;
	}

	@Override
	@Transactional
	public int checkReject(Long workbookId, Long teacherId, String checkNote, User user) {
		FamousTeacherWorkbook record = famousTeacherWorkbookDao.getByWorkbookAndTeacher(workbookId,
				teacherId);
		if (record == null) {
			return 0;
		}

		updateStatus(workbookId, teacherId, CheckStatus.REJECTED, checkNote, user);
		// remove(workbookId, teacherId, user);

		addCheckLog(workbookId, teacherId, record.getStatus(), CheckStatus.REJECTED, checkNote,
				user);
		return 1;
	}

	@Override
	public boolean contains(Long resId, Long teacherId) {
		if (resId == null) {
			return false;
		}
		FamousTeacherWorkbook old = famousTeacherWorkbookDao.getByWorkbookAndTeacher(resId,
				teacherId);
		return old != null;
	}

}
