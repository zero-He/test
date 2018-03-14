/**
 * 
 */
package cn.strong.leke.question.mq.config;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.strong.leke.question.mq.listener.WrongQuestionListener;
import cn.strong.leke.repository.common.mq.core.IMQBinder;

/**
 * 老师错题本题目
 * @author Zhang Fujun
 * @date 2017年1月12日
 */
@Configuration
public class WrongQuestionMQConfig {
	@Resource
	private IMQBinder binder;
	@Value("${mq.e.question.wrongquestion}")
	private String exchange;
	@Value("${mq.q.question.wrongquestion}")
	private String queue;

	@PostConstruct
	public void init() {
		binder.bindDirect(exchange, queue);
	}

	@Bean
	public WrongQuestionListener wrongQuestionListener() {
		WrongQuestionListener result = new WrongQuestionListener();
		result.setQueueNames(new String[] { queue });
		return result;
	}
}
