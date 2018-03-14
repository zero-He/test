package cn.strong.leke.homework.listener;

import javax.annotation.Resource;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.homework.model.ClassAddUserEvent;
import cn.strong.leke.homework.service.HomeworkService;

public class DistributeListener extends AbstractRabbitMQListener {

	@Resource
	private HomeworkService homeworkService;

	@Override
	public void handleMessage(Object object) throws Exception {
		ClassAddUserEvent event = (ClassAddUserEvent) object;
		this.homeworkService.saveCourseSetUserMQ(event);
	}

}
