/**
 * 
 */
package cn.strong.leke.monitor.mq.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.json.JSON;
import cn.strong.leke.core.rabbit.core.MQListenerTemplate;
import cn.strong.leke.monitor.core.service.online.ILessonOnlineUserService;
import cn.strong.leke.monitor.mq.model.LessonOnlineUserMsg;

/**
 * 实时课堂在线用户监听器
 * 
 * @author liulongbiao
 *
 */
@Component
public class LessonOnlineUserListener {
	private static final Logger LOG = LoggerFactory.getLogger(LessonOnlineUserListener.class);

	@Value("${mq.q.monitor.course.online}")
	private String queue;
	@Resource
	private MQListenerTemplate template;
	@Resource
	private ILessonOnlineUserService lessonOnlineUserService;

	@RabbitListener(queues = { "${mq.q.monitor.course.online}" })
	public void handle(Message message) {
		String json = new String(message.getBody());
		LOG.info("received: {}", json);
		template.execute(queue, json, () -> {
			LessonOnlineUserMsg msg = JSON.parse(json, LessonOnlineUserMsg.class);
			if(msg == null || msg.getLessonId() == null) {
				LOG.warn("消息缺少课堂ID: {}", json);
				return;
			}
			if(CollectionUtils.isEmpty(msg.getUsers())) {
				LOG.warn("课堂在线用户数据为空: {}", json);
				return;
			}
			lessonOnlineUserService.process(msg);
		});
	}
}
