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
public class LessonMongoConfig {

	@Value("${mongo.lesson.replicaset}")
	private String replicaset;
	@Value("${mongo.lesson.credentials}")
	private String credentials;

	@Bean
	public MongoClientFactoryBean lessonMongo() throws Exception {
		MongoClientFactoryBean factory = new MongoClientFactoryBean();
		factory.setReplicaset(replicaset);
		factory.setCredentials(credentials);
		return factory;
	}

	@Configuration
	public static class DbConfig {

		@Value("${mongo.lesson.db}")
		private String dbName;

		@Resource(name = "lessonMongo")
		private MongoClient mongo;

		@Bean
		public MongoDatabase lessonDb() throws Exception {
			return mongo.getDatabase(dbName);
		}
	}


}
