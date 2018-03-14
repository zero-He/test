package cn.strong.leke.question.core.question.cmd.store;

import cn.strong.leke.model.user.User;


public interface IQuestionLevelHandler {

	/**
	 *
	 * 描述: 设置习题为精品
	 *
	 * @author raolei
	 * @created 2017年7月17日 下午3:16:01
	 */
	void setQuestionPrime(Long questionId, User user);

	/**
	 *
	 * 描述: 设置习题为普通
	 *
	 * @author raolei
	 * @created 2017年7月17日 下午3:16:01
	 */
	void setQuestionGeneral(Long questionId, User user);
}
