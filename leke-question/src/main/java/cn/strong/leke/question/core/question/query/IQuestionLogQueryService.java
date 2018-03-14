package cn.strong.leke.question.core.question.query;

import cn.strong.leke.question.model.InputStatisQuery;

/**
 *
 * 描述: 习题审核记录
 *
 * @author raolei
 * @created 2016年8月9日 下午3:53:55
 * @since v1.0.0
 */
public interface IQuestionLogQueryService {

	/**
	 *
	 * 描述: 统计审核的记录
	 *
	 * @author raolei
	 * @created 2016年8月9日 下午3:52:22
	 * @since v1.0.0
	 * @param query
	 * @return
	 * @return long
	 */
	long countCheckAmount(InputStatisQuery query);

}
