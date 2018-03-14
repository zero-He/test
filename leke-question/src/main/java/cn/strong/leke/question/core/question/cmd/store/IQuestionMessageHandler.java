package cn.strong.leke.question.core.question.cmd.store;

import cn.strong.leke.model.question.QuestionMessage;

/**
 *
 * 描述: 处理习题消息
 *
 * @author raolei
 * @created 2016年10月25日 下午8:51:44
 * @since v1.0.0
 */
public interface IQuestionMessageHandler {

	/**
	 *
	 * 描述: 处理习题试卷的来源和引用次数
	 *
	 * @author raolei
	 * @created 2016年10月25日 下午8:53:41
	 * @since v1.0.0
	 * @param questionMessage
	 * @return void
	 */
	void updateQuestionMessages(QuestionMessage questionMessage);

}
