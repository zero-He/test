/**
 * 
 */
package cn.strong.leke.monitor.mq.sender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.strong.leke.core.rabbit.core.DirectMQSenderFactoryBean;

/**
 * @author liulongbiao
 *
 */
@Configuration
public class LessonOnlineStatSenderConfig {
	@Value("${mq.e.monitor.lesson.online.stat}")
	private String exchange;
	@Value("${mq.q.monitor.lesson.online.stat}")
	private String queue;

	@Bean
	public DirectMQSenderFactoryBean lessonOnlineStatSender() {
		DirectMQSenderFactoryBean factory = new DirectMQSenderFactoryBean();
		factory.setQueue(queue);
		factory.setExchange(exchange);
		return factory;
	}
}
