package cn.strong.leke.question.core.workbook.query;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.RepoDayGroup;
import cn.strong.leke.model.repository.WorkbookRepositoryRecord;

public interface ILekeWorkbookQueryService {

	public List<WorkbookRepositoryRecord> queryLekeWorkbooks(RepositoryWorkbookQuery query, Page page);

	int countLekeWorkbook(RepositoryWorkbookQuery query);

	List<RepoDayGroup> groupCreatedOnLekeWorkbook(RepositoryWorkbookQuery query);

}
