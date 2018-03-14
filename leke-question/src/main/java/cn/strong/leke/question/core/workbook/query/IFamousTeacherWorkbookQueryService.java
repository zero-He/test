/**
 * 
 */
package cn.strong.leke.question.core.workbook.query;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.question.model.workbook.query.AgencyFmsTchWorkbookQuery;

/**
 * @author liulongbiao
 *
 */
public interface IFamousTeacherWorkbookQueryService {
	/**
	 * 查询名师习题册
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> queryFmsTchWorkbooks(RepositoryWorkbookQuery query, Page page);

	/**
	 * 查询代录记录
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> queryMyAgencyFmsTchWorkbooks(AgencyFmsTchWorkbookQuery query, Page page);
}
