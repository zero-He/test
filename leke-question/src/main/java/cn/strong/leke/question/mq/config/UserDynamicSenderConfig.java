/**
 * 
 */
package cn.strong.leke.question.mq.config;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.strong.leke.core.mq.rabbit.RabbitMQSender;
import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.repository.common.mq.core.IMQBinder;

/**
 * @author liulongbiao
 *
 */
@Configuration
public class UserDynamicSenderConfig {
	@Resource
	private IMQBinder binder;
	@Value("${mq.e.incentive.userdynamic}")
	private String exchange;
	@Value("${mq.q.incentive.userdynamic}")
	private String queue;

	@PostConstruct
	public void init() {
		binder.bindDirect(exchange, queue);
	}

	@Bean
	public MessageSender userDynamicSender() {
		RabbitMQSender result = new RabbitMQSender();
		result.setExchange(exchange);
		result.setRoutingKey(queue);
		return result;
	}
}
