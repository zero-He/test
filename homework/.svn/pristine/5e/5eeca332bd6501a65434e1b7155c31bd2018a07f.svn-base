package cn.strong.leke.homework.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.homework.service.HomeworkDtlService;

/**
 * 处理预习作业完成状况监听处理。
 */
public class PreviewCompleteListener extends AbstractRabbitMQListener {

	private static Logger logger = LoggerFactory.getLogger(PreviewCompleteListener.class);

	@Resource
	private HomeworkDtlService homeworkDtlService;

	@Override
	public void handleMessage(Object object) throws Exception {
		Long lessonId = (Long) object;
		logger.info("preview homework complete : " + lessonId);
		this.homeworkDtlService.doHomeworkCompleteWithTx(lessonId);
	}
}
