/**
 * 
 */
package cn.strong.leke.question.core.workbook.query.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.repository.RepositoryContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.question.core.workbook.query.ISchoolWorkbookQueryService;
import cn.strong.leke.question.dao.mybatis.IWorkbookDao;
import cn.strong.leke.question.dao.mybatis.workbook.ISchoolWorkbookDao;
import cn.strong.leke.question.model.workbook.SchoolWorkbook;

/**
 * @author liulongbiao
 *
 */
@Service
public class SchoolWorkbookQueryService implements ISchoolWorkbookQueryService {

	@Resource
	private ISchoolWorkbookDao schoolWorkbookDao;
	@Resource
	private IWorkbookDao workbookDao;

	@Override
	public List<RepositoryRecord> querySchoolWorkbooks(RepositoryWorkbookQuery query, Page page) {
		List<Long> workbookIds = workbookDao.querySchoolWorkbooks(query, page);
		return toSchoolWorkbookRecords(workbookIds, query.getSchoolId());
	}

	private List<RepositoryRecord> toSchoolWorkbookRecords(List<Long> workbookIds, Long schoolId) {
		if (workbookIds.isEmpty() || schoolId == null) {
			return Collections.emptyList();
		}

		Map<Long, SchoolWorkbook> m1 = ListUtils.toMap(
				schoolWorkbookDao.findByWorkbookIds(workbookIds, schoolId),
				SchoolWorkbook::getWorkbookId);

		List<RepositoryRecord> records = RepositoryContext.ofWorkbooks(workbookIds);
		records.forEach(record -> {
			Long id = Long.valueOf(record.getResId());
			record.attr("assoc", m1.get(id));
		});

		return records;
	}

}
