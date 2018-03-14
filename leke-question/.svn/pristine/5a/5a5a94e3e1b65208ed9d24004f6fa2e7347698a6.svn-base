package cn.strong.leke.question.core.workbook.query.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.repository.RepositoryContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.RepoDayGroup;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.repository.WorkbookRepositoryRecord;
import cn.strong.leke.question.core.workbook.query.ILekeWorkbookQueryService;
import cn.strong.leke.question.dao.mybatis.IWorkbookDao;

@Service
public class LekeWorkbookQueryService implements ILekeWorkbookQueryService {
	@Resource
	private IWorkbookDao workbookDao;

	@Override
	public List<WorkbookRepositoryRecord> queryLekeWorkbooks(RepositoryWorkbookQuery query, Page page) {
		List<Long> workbookIds = workbookDao.queryLekeWorkbooks(query, page);
		List<RepositoryRecord> workbooks = RepositoryContext.ofWorkbooks(workbookIds);
		return ListUtils.map(workbooks, wk -> {
			return (WorkbookRepositoryRecord) wk;
		});
	}

	@Override
	public int countLekeWorkbook(RepositoryWorkbookQuery query) {
		return workbookDao.countLekeWorkbook(query);
	}

	@Override
	public List<RepoDayGroup> groupCreatedOnLekeWorkbook(RepositoryWorkbookQuery query) {
		return workbookDao.groupCreatedOnLekeWorkbook(query);
	}

}
