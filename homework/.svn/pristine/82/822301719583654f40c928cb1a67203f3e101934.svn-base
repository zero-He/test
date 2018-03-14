package cn.strong.leke.homework.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.homework.service.HomeworkSnapshotService;

/**
 * 单课结束监听处理。
 */
public class LessonEndListener extends AbstractRabbitMQListener {

	private static Logger logger = LoggerFactory.getLogger(LessonEndListener.class);

	@Resource
	private HomeworkSnapshotService homeworkSnapshotService;
    @Resource
    private MessageSender previewCompleteSender;
    
	@Override
	public void handleMessage(Object object) throws Exception {
		Long lessonId = (Long) object;
		logger.info("homework lesson end: " + lessonId);
		this.homeworkSnapshotService.saveFlowAfterClassByLessonId(lessonId);
		previewCompleteSender.send(lessonId);
	}
}
