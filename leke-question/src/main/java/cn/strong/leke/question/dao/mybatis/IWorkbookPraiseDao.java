package cn.strong.leke.question.dao.mybatis;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.model.question.WorkbookPraise;

public interface IWorkbookPraiseDao {

	/**
	 *
	 * 描述:添加点赞记录
	 *
	 * @author raolei
	 * @created 2015年11月30日 下午3:13:03
	 * @since v1.0.0
	 * @param workbookPraise
	 * @return
	 * @return int
	 */
	int addWorkbookPraise(WorkbookPraise workbookPraise);

	/**
	 *
	 * 描述:
	 *
	 * @author raolei
	 * @created 2015年12月1日 下午3:42:59
	 * @since v1.0.0
	 * @param workbookId
	 * @param userId
	 * @return
	 * @return int
	 */
	int countWorkbookPraise(@Param("workbookId") Long workbookId,
			@Param("userId") Long userId);
}
