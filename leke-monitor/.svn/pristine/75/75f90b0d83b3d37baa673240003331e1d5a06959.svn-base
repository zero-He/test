/**
 * 
 */
package cn.strong.leke.monitor.config;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.remoting.RemoteAccessException;

import com.mongodb.MongoException;

import cn.strong.leke.core.mq.rabbit.MessageQueueException;
import cn.strong.leke.core.mq.rabbit.MessageRequiredRepeatRunException;
import cn.strong.leke.core.rabbit.config.MQBaseConfig;
import cn.strong.leke.core.rabbit.config.MleSenderConfig;
import cn.strong.leke.core.rabbit.core.MQListenerTemplate;
import cn.strong.leke.core.rabbit.core.MQSender;
import redis.clients.jedis.exceptions.JedisException;

/**
 * MQListenerTemplate 配置
 * 
 * @author liulongbiao
 *
 */
@Configuration
@Import({ MQBaseConfig.class, MleSenderConfig.class })
@ComponentScan({ "cn.strong.leke.monitor.mq" })
public class MQConfig {

	@Resource(name = "mleSender")
	private MQSender mleSender;

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory mqConnectionFactory,
			MessageConverter mqMessageConverter) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(mqConnectionFactory);
		factory.setMessageConverter(mqMessageConverter);
		factory.setConcurrentConsumers(1);
		factory.setMaxConcurrentConsumers(16);
		return factory;
	}

	@SuppressWarnings("unchecked")
	@Bean
	public MQListenerTemplate mqListenerTemplate() {
		MQListenerTemplate result = new MQListenerTemplate();
		result.retryOn(MessageRequiredRepeatRunException.class, DataAccessException.class, RemoteAccessException.class,
				MessageQueueException.class, JedisException.class, MongoException.class);
		result.setMleSender(mleSender);
		return result;
	}
}
