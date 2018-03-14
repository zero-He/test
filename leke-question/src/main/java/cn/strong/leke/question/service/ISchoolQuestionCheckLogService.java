package cn.strong.leke.question.service;

import cn.strong.leke.question.model.question.SchoolQuestionCheckLog;

/**
 *
 * 描述: 学校习题审核记录
 *
 * @author raolei
 * @created 2016年6月8日 下午5:21:40
 * @since v1.0.0
 */
public interface ISchoolQuestionCheckLogService {

	/**
	 *
	 * 描述: 增加一条审核记录
	 *
	 * @author raolei
	 */
	void add(SchoolQuestionCheckLog checkLog);

	/**
	 *
	 * 描述: 查找最新的一条审核记录
	 *
	 * @author raolei
	 */
	SchoolQuestionCheckLog getCheckLog(Long checkLogId);

}
