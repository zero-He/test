package cn.strong.leke.question.core.question.cmd.store;

import cn.strong.leke.model.user.User;

/**
 *
 * 描述: 习题纠错奖励
 *
 * @author raolei
 * @created 2016年11月15日 下午4:16:27
 * @since v1.0.0
 */
public interface IQuestionFeedbackAwardHandler {

	/**
	 *
	 * 描述:发送纠错奖励
	 *
	 * @author raolei
	 * @created 2016年11月15日 下午4:22:52
	 * @since v1.0.0
	 * @param questionId
	 * @param user
	 * @return void
	 */
	void sendAward(Long questionId, User user);
}
