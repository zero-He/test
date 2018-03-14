package cn.strong.leke.question.core.workbook.cmd.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.workbook.cmd.store.IWorkbookOperationHandler;
import cn.strong.leke.question.dao.mybatis.workbook.IWorkbookOperationDao;
import cn.strong.leke.question.model.workbook.WorkbookOperation;

@Service
public class WorkbookOperationHandler implements IWorkbookOperationHandler {

	@Resource
	private IWorkbookOperationDao workbookOperationDao;

	@Override
	public void add(Long workbookId, int operationType, User user) {
		Validation.notNull(workbookId, "习题册ID不能为空！");
		Long userId = user.getId();
		WorkbookOperation assoc = new WorkbookOperation();
		assoc.setCreatedBy(userId);
		assoc.setWorkbookId(workbookId);
		assoc.setOperationType(operationType);
		workbookOperationDao.add(assoc);
	}

}
