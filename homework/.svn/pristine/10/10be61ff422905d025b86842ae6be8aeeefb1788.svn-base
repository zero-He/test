package cn.strong.leke.homework.listener;

import javax.annotation.Resource;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.homework.model.HomeworkSubmitMQ;
import cn.strong.leke.homework.service.HomeworkMainService;

// q.homework.submit
public class HomeworkSubmitListener extends AbstractRabbitMQListener {

	@Resource
	private HomeworkMainService homeworkMainService;

	@Override
	public void handleMessage(Object object) throws Exception {
		this.homeworkMainService.handleHomeworkSubmitMessageWithTx((HomeworkSubmitMQ) object);
	}
}
