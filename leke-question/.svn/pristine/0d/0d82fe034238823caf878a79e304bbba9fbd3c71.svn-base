package cn.strong.leke.question.util;

import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.framework.spring.SpringContextHolder;

public class QuestionIndexContext {

	private final static MessageSender questionShareSender = SpringContextHolder.getBean("questionShareSender");

	/**
	 *
	 * 描述: 更新习题全文索引消息
	 *
	 * @author raolei
	 * @created 2017年8月17日 下午2:46:45
	 */
	public static void sendUpdateIndex(Long... questionIds) {
		for (Long questionId : questionIds) {
			if (questionId != null) {
				questionShareSender.send(questionId);
			}
		}
	}
}
