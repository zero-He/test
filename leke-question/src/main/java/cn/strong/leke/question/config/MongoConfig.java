/**
 * 
 */
package cn.strong.leke.question.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import cn.strong.leke.data.mongo.core.MongoClientFactoryBean;

/**
 * @author liulongbiao
 *
 */
@Configuration
public class MongoConfig {
	@Value("${mongo.question.replicaset}")
	private String replicaset;
	@Value("${mongo.question.credentials}")
	private String credentials;

	@Bean
	public MongoClientFactoryBean queMongo() throws Exception {
		MongoClientFactoryBean factory = new MongoClientFactoryBean();
		factory.setReplicaset(replicaset);
		factory.setCredentials(credentials);
		return factory;
	}

	@Configuration
	public static class DbConfig {

		@Value("${mongo.question.db}")
		private String dbName;

		@Resource(name = "queMongo")
		private MongoClient mongo;

		@Bean
		public MongoDatabase queDb() throws Exception {
			return mongo.getDatabase(dbName);
		}
	}
}
