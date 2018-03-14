package cn.strong.leke.homework.listener;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.strong.leke.beike.model.LessonBeikePkgMQ;
import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.homework.service.HomeworkAssignService;
import cn.strong.leke.homework.util.HomeworkCst;

public class LessonBeikeListener extends AbstractRabbitMQListener {
	
	private static final Logger logger = LoggerFactory.getLogger(LessonBeikeListener.class);

	@Resource
	private HomeworkAssignService homeworkAssignService;
	@Resource
	private MessageSender classCourseSingleBeikeSender;

	@Override
	public void handleMessage(Object object) throws Exception {
		LessonBeikePkgMQ beikePkgMQ = (LessonBeikePkgMQ) object;
		this.homeworkAssignService.executeLessonBeikePkgWithTx(beikePkgMQ);

		boolean changeCourseware = beikePkgMQ.getResources().stream()
				.anyMatch(v -> v.getType() != HomeworkCst.HOMEWORK_RES_PAPER);
		boolean changeHomework = beikePkgMQ.getResources().stream()
				.anyMatch(v -> v.getType() == HomeworkCst.HOMEWORK_RES_PAPER);
		if (changeCourseware || changeHomework) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("courseSingleId", beikePkgMQ.getLessonId());
			map.put("changeHomework", changeHomework);
			map.put("changeCourseware", changeCourseware);
			String message = JsonUtils.toJSON(map);
			logger.info("Notice Class Change Homework: " + message);
			this.classCourseSingleBeikeSender.send(message);
		}
	}
}
