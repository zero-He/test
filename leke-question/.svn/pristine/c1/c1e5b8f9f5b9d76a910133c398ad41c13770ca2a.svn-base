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
import cn.strong.leke.question.core.workbook.query.IFamousTeacherWorkbookQueryService;
import cn.strong.leke.question.dao.mybatis.IWorkbookDao;
import cn.strong.leke.question.dao.mybatis.workbook.IFamousTeacherWorkbookDao;
import cn.strong.leke.question.model.workbook.FamousTeacherWorkbook;
import cn.strong.leke.question.model.workbook.query.AgencyFmsTchWorkbookQuery;

/**
 * @author liulongbiao
 *
 */
@Service
public class FamousTeacherWorkbookQueryService implements IFamousTeacherWorkbookQueryService {

	@Resource
	private IFamousTeacherWorkbookDao famousTeacherWorkbookDao;
	@Resource
	private IWorkbookDao workbookDao;

	@Override
	public List<RepositoryRecord> queryFmsTchWorkbooks(RepositoryWorkbookQuery query, Page page) {
		List<Long> workbookIds = workbookDao.queryFamousTeacherWorkbooks(query, page);
		return toFamousTeacherWorkbookRecords(workbookIds, query.getFamousTeacherId());
	}

	private List<RepositoryRecord> toFamousTeacherWorkbookRecords(List<Long> workbookIds,
			Long teacherId) {
		if (workbookIds.isEmpty()) {
			return Collections.emptyList();
		}

		Map<Long, FamousTeacherWorkbook> m1 = ListUtils.toMap(
				famousTeacherWorkbookDao.findByWorkbookIds(workbookIds, teacherId),
				FamousTeacherWorkbook::getWorkbookId);

		List<RepositoryRecord> records = RepositoryContext.ofWorkbooks(workbookIds);
		records.forEach(record -> {
			Long id = Long.valueOf(record.getResId());
			record.attr("assoc", m1.get(id));
		});

		return records;
	}

	@Override
	public List<RepositoryRecord> queryMyAgencyFmsTchWorkbooks(AgencyFmsTchWorkbookQuery query,
			Page page) {
		List<Long> workbookIds = famousTeacherWorkbookDao.queryMyAgencyFmsTchWorkbooks(query, page);
		return toFamousTeacherWorkbookRecords(workbookIds, query.getFamousTeacherId());
	}

}
