/**
 * 
 */
package cn.strong.leke.monitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

/**
 * @author liulongbiao
 *
 */
@Configuration
@ComponentScan({ "cn.strong.leke.monitor.db.tutor.config" })
public class AppConfig {

	@Bean
	public ThreadPoolExecutorFactoryBean monitorExecutor() {
		ThreadPoolExecutorFactoryBean factory = new ThreadPoolExecutorFactoryBean();
		factory.setMaxPoolSize(128);
		factory.setCorePoolSize(8);
		factory.setKeepAliveSeconds(60 * 5);
		factory.setDaemon(true);
		return factory;
	}
}
