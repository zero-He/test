/**
 * 
 */
package cn.strong.leke.monitor.config;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author liulongbiao
 *
 */
@Configuration
public class MQInitConfig {

	private static String[] QUEUES = new String[] {
		"monitor.common.mle",
		"monitor.jobex",
		"monitor.cset.stus",
		"monitor.course.table",
		"monitor.course.attend",
		"monitor.course.teacher.status",
		"monitor.course.online",
		"monitor.onlineuser"
	};

	@Autowired
	private RabbitAdmin admin;

	@PostConstruct
	public void init() {
		for (String qnm : QUEUES) {
			Queue queue = new Queue("q." + qnm);
			admin.declareQueue(queue);
			DirectExchange exchange = new DirectExchange("e." + qnm);
			admin.declareExchange(exchange);
			admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("q." + qnm));
		}
	}
}
