/**
 * 
 */
package cn.strong.leke.question.core.workbook.query;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.Workbook;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.InputStatisDTO;

/**
 * 习题册查询接口
 * 
 * @author liulongbiao
 *
 */
public interface IWorkbookQueryService {

	/**
	 * 根据ID获取习题册信息
	 * 
	 * @param workbookId
	 * @return
	 */
	Workbook getById(Long workbookId);
	
	
	/**
	 *
	 * 描述: 查询乐课教研员录入量统计
	 *
	 */
	List<InputStatisDTO> queryWorkbookAmount(InputStatisDTO query);
	
	/**
	 *
	 * 描述: 查询整个乐课录入量统计
	 *
	 */
	Long queryWorkbookTotalAmount();

	/**
	 *
	 * 描述: 根据scope查询
	 *
	 * @author raolei
	 * @return List<RepositoryRecord>
	 */
	List<RepositoryRecord> queryByScope(RepositoryWorkbookQuery query, Page page, User user);
}
