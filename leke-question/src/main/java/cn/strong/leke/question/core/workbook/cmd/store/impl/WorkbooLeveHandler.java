package cn.strong.leke.question.core.workbook.cmd.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.constant.RepoLevelCst;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.base.SchoolCst;
import cn.strong.leke.model.question.Workbook;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.workbook.cmd.store.IWorkbooLeveHandler;
import cn.strong.leke.question.core.workbook.cmd.store.IWorkbookOperationHandler;
import cn.strong.leke.question.dao.mybatis.IWorkbookDao;

@Service
public class WorkbooLeveHandler implements IWorkbooLeveHandler {

	@Resource
	private IWorkbookDao workbookDao;
	@Resource
	private IWorkbookOperationHandler workbookOperationHandler;

	@Override
	public void setWorkbookPrime(Long workbookId, User user) {
		verify(workbookId);
		Long userId = user.getId();
		workbookDao.setWorkbooLeve(workbookId, RepoLevelCst.LEVEL_PRIME, userId);
		workbookOperationHandler.add(workbookId, RepoLevelCst.LEVEL_PRIME, user);
	}

	@Override
	public void setWorkbookGeneral(Long workbookId, User user) {
		verify(workbookId);
		Long userId = user.getId();
		workbookDao.setWorkbooLeve(workbookId, RepoLevelCst.LEVEL_GENERAL, userId);
		workbookOperationHandler.add(workbookId, RepoLevelCst.LEVEL_GENERAL, user);

	}

	private void verify(Long workbookId) {
		Workbook wb = workbookDao.getWorkbook(workbookId);
		if (wb == null) {
			throw new ValidateException("习题册不存在！");
		}
		if (SchoolCst.LEKE != wb.getSchoolId()) {
			throw new ValidateException("习题册不属于精品库！");
		}
	}
}
