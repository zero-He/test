/**
 * 
 */
package cn.strong.leke.question.core.workbook.cmd.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.strong.leke.common.utils.Booleans;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.base.SchoolCst;
import cn.strong.leke.model.question.Workbook;
import cn.strong.leke.model.repository.RepoCsts.CheckStatus;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.workbook.cmd.store.ILekeBoutiqueWorkbookHandler;
import cn.strong.leke.question.dao.mybatis.IWorkbookDao;
import cn.strong.leke.repository.common.utils.RepoUtils;

/**
 * @author liulongbiao
 *
 */
@Service
public class LekeBoutiqueWorkbookHandler implements ILekeBoutiqueWorkbookHandler {
	private static final String ERR_NOT_EXIST = "que.workbook.not.exist";
	@Resource
	private IWorkbookDao workbookDao;

	@Override
	@Transactional
	public void add(Long workbookId, int status, String editLog, User user) {
		Workbook workbook = workbookDao.getWorkbook(workbookId);
		Validation.notNull(workbook, ERR_NOT_EXIST);
		Validation.isTrue(workbook.getSchoolId().equals(SchoolCst.LEKE), "该资源无法添加到乐课精品库");
		// 暂时不需要处理状态
		workbookDao.addToLekeBoutique(workbookId, user.getId());
	}

	@Override
	@Transactional
	public int remove(Long workbookId, User user) {
		return workbookDao.removeFromLekeBoutique(workbookId, user.getId());
	}

	@Override
	@Transactional
	public void override(Long oldId, Long newId, User user) {
		remove(oldId, user);
		String editLog = RepoUtils.getEditLog(user);
		add(newId, CheckStatus.INIT, editLog, user);
	}

	@Override
	@Transactional
	public void checkEdit(Long oldId, Long newId, User user) {
		Workbook old = workbookDao.getWorkbook(oldId);
		Validation.notNull(old, ERR_NOT_EXIST);

		String editLog = RepoUtils.getEditLog(user);

		updateStatus(oldId, CheckStatus.EDITED, editLog, user);
		remove(oldId, user);
		add(newId, CheckStatus.CHECKED, editLog, user);

		int oldStatus = CheckStatus.INIT; // old.getStatus();
		addCheckLog(oldId, oldStatus, CheckStatus.EDITED, editLog, user);
	}

	private void updateStatus(Long workbookId, int status, String editLog, User user) {
	}

	private void addCheckLog(Long workbookId, int fromStatus, int toStatus, String checkNote,
			User user) {
	}

	@Override
	@Transactional
	public int checkPass(Long resId, User user) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Transactional
	public int checkReject(Long resId, String checkNote, User user) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void doCorrect(Long resId, User user) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean contains(Long resId) {
		if (resId == null) {
			return false;
		}
		Workbook old = workbookDao.getWorkbook(resId);
		return old != null && Booleans.isTrue(old.getSharePlatform())
				&& old.getSchoolId().equals(SchoolCst.LEKE);
	}

	@Override
	public void up(Long resId, User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public int down(Long resId, User user) {
		throw new UnsupportedOperationException();

	}

}
