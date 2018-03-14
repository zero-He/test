/**
 * 
 */
package cn.strong.leke.question.mq.config;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.strong.leke.question.mq.listener.QuestionPaperCountListener;
import cn.strong.leke.repository.common.mq.core.IMQBinder;

/**
 * 消息队列初始化
 * 
 * @author liulongbiao
 *
 */
@Configuration
public class QuePaperCountMQConfig {
	@Resource
	private IMQBinder binder;
	@Value("${mq.e.question.papercount}")
	private String exchange;
	@Value("${mq.q.question.papercount}")
	private String queue;

	@PostConstruct
	public void init() {
		binder.bindDirect(exchange, queue);
	}

	@Bean
	public QuestionPaperCountListener questionPaperCountListener() {
		QuestionPaperCountListener result = new QuestionPaperCountListener();
		result.setQueueNames(new String[] { queue });
		return result;
	}
}
