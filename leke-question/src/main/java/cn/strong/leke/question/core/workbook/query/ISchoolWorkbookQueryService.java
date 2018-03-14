/**
 * 
 */
package cn.strong.leke.question.core.workbook.query;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.RepositoryRecord;

/**
 * @author liulongbiao
 *
 */
public interface ISchoolWorkbookQueryService {
	/**
	 * 查询学校习题册
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> querySchoolWorkbooks(RepositoryWorkbookQuery query, Page page);
}
