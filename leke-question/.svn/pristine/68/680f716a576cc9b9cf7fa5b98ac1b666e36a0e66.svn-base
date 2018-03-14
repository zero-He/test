package cn.strong.leke.question.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.question.WorkbookPraise;
import cn.strong.leke.question.dao.mybatis.IWorkbookDao;
import cn.strong.leke.question.dao.mybatis.IWorkbookPraiseDao;
import cn.strong.leke.question.service.IWorkbookPraiseService;

@Service
public class WorkbookPraiseService implements IWorkbookPraiseService {

	@Resource
	private IWorkbookPraiseDao workbookPraiseDao;

	@Resource
	private IWorkbookDao workbookDao;
	@Override
	public void addWorkbookPraise(WorkbookPraise workbookPraise) {
		if (workbookPraise == null || workbookPraise.getWorkbookId() == null
				|| workbookPraise.getCreatedBy() == null) {
			throw new ValidateException("que.workbookPraise.info.incomplete");
		}
		Long workbookId = workbookPraise.getWorkbookId();
		Long userId = workbookPraise.getCreatedBy();
		if (workbookPraiseDao.countWorkbookPraise(workbookId, userId) > 0) {
			throw new ValidateException("pap.workbookPraise.exist");
		}
		workbookPraiseDao.addWorkbookPraise(workbookPraise);
		workbookDao.incPraiseCount(workbookId);
	}

}
