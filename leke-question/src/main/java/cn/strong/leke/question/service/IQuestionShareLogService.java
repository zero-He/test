package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.repository.QuestionRepositoryRecord;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.question.query.QuestionShareLogQuery;

/**
 *
 * 描述: 习题分享日志
 *
 * @author raolei
 * @created 2016年6月8日 下午6:13:57
 * @since v1.0.0
 */
public interface IQuestionShareLogService {

	/**
	 *
	 * 描述: 习题分享到学校记录
	 *
	 * @author raolei
	 * @created 2016年6月8日 上午10:32:36
	 * @since v1.0.0
	 * @param QuestionId
	 * @param user
	 * @return void
	 */
	void shareQuestionToSchool(Long questionId, User user);

	/**
	 *
	 * 描述: 习题分享到乐课记录
	 *
	 * @author raolei
	 * @created 2016年6月8日 上午10:32:45
	 * @since v1.0.0
	 * @param QuestionId
	 * @param user
	 * @return void
	 */
	void shareQuestionToLeke(Long questionId, User user);



	/**
	 *
	 * 描述:分页查询习题分享和审核等记录
	 *
	 * @author raolei
	 * @created 2016年6月12日 下午3:23:13
	 * @since v1.0.0
	 * @param query
	 * @param page
	 * @return
	 * @return List<ResShareCheck>
	 */
	List<QuestionRepositoryRecord> queryQuestionShareInfo(QuestionShareLogQuery query, Page page);

}
