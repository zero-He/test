/**
 * 
 */
package cn.strong.leke.question.core.workbook.query;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.question.model.workbook.query.AgencyFmsSchWorkbookQuery;

/**
 * @author liulongbiao
 *
 */
public interface IFamousSchoolWorkbookQueryService {
	/**
	 * 查询名校习题册
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> queryFmsSchWorkbooks(RepositoryWorkbookQuery query, Page page);

	/**
	 * 查询代录记录
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> queryMyAgencyFmsSchWorkbooks(AgencyFmsSchWorkbookQuery query, Page page);
}
