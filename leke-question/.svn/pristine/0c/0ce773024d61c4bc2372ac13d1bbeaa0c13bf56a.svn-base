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
import cn.strong.leke.question.core.workbook.query.IFamousSchoolWorkbookQueryService;
import cn.strong.leke.question.dao.mybatis.IWorkbookDao;
import cn.strong.leke.question.dao.mybatis.workbook.IFamousSchoolWorkbookDao;
import cn.strong.leke.question.model.workbook.FamousSchoolWorkbook;
import cn.strong.leke.question.model.workbook.query.AgencyFmsSchWorkbookQuery;

/**
 * @author liulongbiao
 *
 */
@Service
public class FamousSchoolWorkbookQueryService implements IFamousSchoolWorkbookQueryService {

	@Resource
	private IFamousSchoolWorkbookDao famousSchoolWorkbookDao;
	@Resource
	private IWorkbookDao workbookDao;

	@Override
	public List<RepositoryRecord> queryFmsSchWorkbooks(RepositoryWorkbookQuery query, Page page) {
		List<Long> workbookIds = workbookDao.queryFamousSchoolWorkbooks(query, page);
		return toFamousSchoolWorkbookRecords(workbookIds, query.getFamousSchoolId());
	}

	private List<RepositoryRecord> toFamousSchoolWorkbookRecords(List<Long> workbookIds,
			Long schoolId) {
		if (workbookIds.isEmpty()) {
			return Collections.emptyList();
		}

		Map<Long, FamousSchoolWorkbook> m1 = ListUtils.toMap(
				famousSchoolWorkbookDao.findByWorkbookIds(workbookIds, schoolId),
				FamousSchoolWorkbook::getWorkbookId);

		List<RepositoryRecord> records = RepositoryContext.ofWorkbooks(workbookIds);
		records.forEach(record -> {
			Long id = Long.valueOf(record.getResId());
			record.attr("assoc", m1.get(id));
		});

		return records;
	}

	@Override
	public List<RepositoryRecord> queryMyAgencyFmsSchWorkbooks(AgencyFmsSchWorkbookQuery query,
			Page page) {
		List<Long> workbookIds = famousSchoolWorkbookDao.queryMyAgencyFmsSchWorkbooks(query, page);
		return toFamousSchoolWorkbookRecords(workbookIds, query.getFamousSchoolId());
	}

}
