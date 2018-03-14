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
import cn.strong.leke.question.core.workbook.cmd.store.ISchoolWorkbookHandler;
import cn.strong.leke.question.dao.mybatis.workbook.ISchoolWorkbookCheckLogDao;
import cn.strong.leke.question.dao.mybatis.workbook.ISchoolWorkbookDao;
import cn.strong.leke.question.model.workbook.SchoolWorkbook;
import cn.strong.leke.question.model.workbook.SchoolWorkbookCheckLog;
import cn.strong.leke.repository.common.utils.RepoUtils;

/**
 * @author liulongbiao
 *
 */
@Service
public class SchoolWorkbookHandler implements ISchoolWorkbookHandler {

	private static final String ERR_NOT_EXIST = "que.workbook.not.exist";
	@Resource
	private ISchoolWorkbookDao schoolWorkbookDao;
	@Resource
	private ISchoolWorkbookCheckLogDao checkLogDao;

	@Override
	@Transactional
	public void add(Long workbookId, Long schoolId, int addType, int status, String editLog, User user) {
		SchoolWorkbook assoc = new SchoolWorkbook();
		assoc.setWorkbookId(workbookId);
		assoc.setSchoolId(schoolId);
		assoc.setAddType(addType);
		assoc.setStatus(status);
		assoc.setEditLog(editLog);
		Long userId = user.getId();
		assoc.setCreatedBy(userId);
		assoc.setModifiedBy(userId);

		schoolWorkbookDao.add(assoc);
	}

	@Override
	@Transactional
	public int remove(Long workbookId, Long schoolId, User user) {
		SchoolWorkbook assoc = new SchoolWorkbook();
		assoc.setWorkbookId(workbookId);
		assoc.setSchoolId(schoolId);
		assoc.setModifiedBy(user.getId());
		return schoolWorkbookDao.remove(assoc);
	}

	@Override
	@Transactional
	public void override(Long oldId, Long newId, Long schoolId, User user) {
		SchoolWorkbook old = schoolWorkbookDao.getByWorkbookAndSchool(oldId, schoolId);
		Validation.notNull(old, ERR_NOT_EXIST);
		remove(oldId, schoolId, user);
		String editLog = RepoUtils.getEditLog(user);
		add(newId, schoolId, old.getAddType(), CheckStatus.INIT, editLog, user);
	}

	@Override
	@Transactional
	public void checkEdit(Long oldId, Long newId, Long schoolId, User user) {
		SchoolWorkbook old = schoolWorkbookDao.getByWorkbookAndSchool(oldId, schoolId);
		Validation.notNull(old, ERR_NOT_EXIST);

		String editLog = RepoUtils.getEditLog(user);

		updateStatus(oldId, schoolId, CheckStatus.EDITED, editLog, user);
		remove(oldId, schoolId, user);
		add(newId, schoolId, old.getAddType(), CheckStatus.CHECKED, editLog, user);

		addCheckLog(oldId, schoolId, old.getStatus(), CheckStatus.EDITED, editLog, user);
	}

	private void updateStatus(Long workbookId, Long schoolId, int status, String editLog, User user) {
		SchoolWorkbook update = new SchoolWorkbook();
		update.setWorkbookId(workbookId);
		update.setSchoolId(schoolId);
		update.setStatus(status);
		update.setEditLog(editLog);
		update.setModifiedBy(user.getId());
		schoolWorkbookDao.updateStatus(update);
	}

	private Long addCheckLog(Long workbookId, Long schoolId, int fromStatus, int toStatus,
			String checkNote, User user) {
		SchoolWorkbookCheckLog log = new SchoolWorkbookCheckLog();
		log.setWorkbookId(workbookId);
		log.setSchoolId(schoolId);
		log.setFromStatus(fromStatus);
		log.setToStatus(toStatus);
		log.setCheckNote(checkNote);
		log.setCheckedUser(user.getUserName());
		log.setCreatedBy(user.getId());
		checkLogDao.add(log);
		return log.getCheckLogId();
	}

	@Override
	@Transactional
	public int checkPass(Long workbookId, Long schoolId, User user) {
		SchoolWorkbook record = schoolWorkbookDao.getByWorkbookAndSchool(workbookId, schoolId);
		if (record == null) {
			return 0;
		}
		updateStatus(workbookId, schoolId, CheckStatus.CHECKED, null, user);
		addCheckLog(workbookId, schoolId, record.getStatus(), CheckStatus.CHECKED,
				null, user);
		return 1;
	}

	@Override
	@Transactional
	public int checkReject(Long workbookId, Long schoolId, String checkNote, User user) {
		SchoolWorkbook record = schoolWorkbookDao.getByWorkbookAndSchool(workbookId, schoolId);
		if (record == null) {
			return 0;
		}

		updateStatus(workbookId, schoolId, CheckStatus.REJECTED, checkNote, user);
		// remove(workbookId, schoolId, user);

		addCheckLog(workbookId, schoolId, record.getStatus(), CheckStatus.REJECTED,
				checkNote, user);
		return 1;
	}

	@Override
	public boolean contains(Long resId, Long schoolId) {
		if (resId == null) {
			return false;
		}
		SchoolWorkbook old = schoolWorkbookDao.getByWorkbookAndSchool(resId, schoolId);
		return old != null;
	}

}
