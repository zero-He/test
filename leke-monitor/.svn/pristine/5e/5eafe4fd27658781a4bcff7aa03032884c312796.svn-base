/**
 * 
 */
package cn.strong.leke.monitor.mq.listener;

import java.util.Arrays;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.strong.leke.common.utils.json.JSON;
import cn.strong.leke.core.rabbit.core.MQListenerTemplate;
import cn.strong.leke.model.monitor.msg.CourseOnlineMsg;
import cn.strong.leke.monitor.core.service.course.ICourseService;

/**
 * 课堂在线学生数统计监听器
 * 
 * @author liulongbiao
 *
 */
@Component
public class LessonOnlineStatListener {
	private static final Logger LOG = LoggerFactory.getLogger(LessonOnlineStatListener.class);

	@Value("${mq.q.monitor.lesson.online.stat}")
	private String queue;
	@Resource
	private MQListenerTemplate template;
	@Resource
	private ICourseService courseService;

	@RabbitListener(queues = { "${mq.q.monitor.lesson.online.stat}" })
	public void handle(CourseOnlineMsg msg) {
		template.execute(queue, msg, () -> {
			if (msg == null || msg.getCsId() == null || msg.getStuCount() == null) {
				LOG.warn("消息不完整: {}", JSON.stringify(msg));
				return;
			}
			courseService.handleCourseOnlineMsgs(Arrays.asList(msg));
		});
	}
}
