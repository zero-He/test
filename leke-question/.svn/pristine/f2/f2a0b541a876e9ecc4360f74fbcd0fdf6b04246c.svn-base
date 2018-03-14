/**
 * 
 */
package cn.strong.leke.question.core.workbook.cmd.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.base.SchoolCst;
import cn.strong.leke.model.question.Workbook;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.workbook.cmd.store.ILekeShareWorkbookHandler;
import cn.strong.leke.question.dao.mybatis.IWorkbookDao;

/**
 * @author liulongbiao
 *
 */
@Service
public class LekeShareWorkbookHandler implements ILekeShareWorkbookHandler {
	private static final String ERR_NOT_EXIST = "que.workbook.not.exist";
	@Resource
	private IWorkbookDao workbookDao;

	@Override
	public void add(Long workbookId, User user) {
		Workbook workbook = workbookDao.getWorkbook(workbookId);
		Validation.notNull(workbook, ERR_NOT_EXIST);
		Validation.isFalse(workbook.getSchoolId().equals(SchoolCst.LEKE), "该资源无法加入乐课分享库");
		workbookDao.addToLekeShare(workbookId, user.getId());
	}

	@Override
	public int remove(Long workbookId, User user) {
		return workbookDao.removeFromLekeShare(workbookId, user.getId());
	}

}
