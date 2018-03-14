package cn.strong.leke.homework.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.lesson.model.Lesson;

public class LessonDeleteListener extends AbstractRabbitMQListener {
	
	private static final Logger logger = LoggerFactory.getLogger(LessonDeleteListener.class);

	@Resource
	private HomeworkService	homeworkService;

	@Override
	public void handleMessage(Object object) throws Exception {
		Lesson lesson = (Lesson) object;
		logger.info("LessonDelete listener……" + object);
		homeworkService.delHwByCourseSinglId(lesson.getCourseSingleId(), lesson.getModifiedBy());
	}
}
