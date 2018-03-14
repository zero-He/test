/**
 * 
 */
package cn.strong.leke.monitor.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import cn.strong.leke.data.mongo.core.MongoClientFactoryBean;

/**
 * @author liulongbiao
 *
 */
@Configuration
@ComponentScan("cn.strong.leke.monitor.mongo")
public class MonitorMongoConfig {

	@Value("${mongo.monitor.replicaset}")
	private String replicaset;
	@Value("${mongo.monitor.credentials}")
	private String credentials;

	@Bean
	public MongoClientFactoryBean monitorMongo() throws Exception {
		MongoClientFactoryBean factory = new MongoClientFactoryBean();
		factory.setReplicaset(replicaset);
		factory.setCredentials(credentials);
		return factory;
	}

	@Configuration
	public static class DbConfig {

		@Value("${mongo.monitor.db}")
		private String dbName;

		@Resource(name = "monitorMongo")
		private MongoClient mongo;

		@Bean
		public MongoDatabase monitorDb() throws Exception {
			return mongo.getDatabase(dbName);
		}
	}
}
