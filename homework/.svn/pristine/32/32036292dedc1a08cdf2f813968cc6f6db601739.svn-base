package cn.strong.leke.homework.listener;

import javax.annotation.Resource;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.homework.service.HomeworkMainService;

/**
 * 课堂互批数据存储
 * @author  andy
 * @created 2017年6月26日 下午4:23:14
 * @since   v1.0.0
 */
public class HomeworkMutualCorrectListener extends AbstractRabbitMQListener {

	@Resource
	private HomeworkMainService homeworkMainService;

	@Override
	public void handleMessage(Object object) throws Exception {
		Long dataId = (Long) object;
		this.homeworkMainService.handleMutualCorrectSubmitMessageWithTx(dataId);
	}

}
