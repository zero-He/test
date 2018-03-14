/**
 * 
 */
package cn.strong.leke.question.mq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.strong.leke.core.rabbit.core.DirectMQSenderFactoryBean;

/**
 * @author liulongbiao
 *
 */
@Configuration
public class JobExMQConfig {
	@Value("${mq.e.monitor.jobex}")
	private String exchange;
	@Value("${mq.q.monitor.jobex}")
	private String queue;

	@Bean
	public DirectMQSenderFactoryBean jobExSender() {
		DirectMQSenderFactoryBean factory = new DirectMQSenderFactoryBean();
		factory.setQueue(queue);
		factory.setExchange(exchange);
		return factory;
	}
}
