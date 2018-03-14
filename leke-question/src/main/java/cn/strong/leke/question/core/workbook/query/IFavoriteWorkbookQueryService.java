package cn.strong.leke.question.core.workbook.query;

import java.util.List;

import cn.strong.leke.model.user.User;

/**
 *
 * 描述: 习题收藏查询
 *
 * @author raolei
 * @created 2016年8月11日 下午2:57:26
 * @since v1.0.0
 */
public interface IFavoriteWorkbookQueryService {

	/**
	 *
	 * 描述: 查询习题册是否收藏过
	 *
	 * @author raolei
	 * @created 2016年8月11日 下午2:55:59
	 * @since v1.0.0
	 * @param workbookIds
	 * @param userId
	 * @return
	 * @return List<Long>
	 */
	List<Long> filterFavoriteWorkbooks(List<Long> workbookIds, User user);
}
