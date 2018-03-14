package cn.strong.leke.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import cn.strong.leke.data.mongo.core.MongoClientFactoryBean;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@Configuration
@ComponentScan("cn.strong.leke.homework.dao.mongo,cn.strong.leke.homework.manage")
public class MongoConfig {

	@Value("${mongodb.replicaset}")
	private String replicaset;
	@Value("${mongodb.credentials}")
	private String credentials;
	@Value("${mongodb.db}")
	private String db;

	@Bean
	public MongoClientFactoryBean mongo() throws Exception {
		MongoClientFactoryBean factory = new MongoClientFactoryBean();
		factory.setReplicaset(replicaset);
		factory.setCredentials(credentials);
		return factory;
	}

	@Bean
	public MongoDatabase db(MongoClient mongo) throws Exception {
		return mongo.getDatabase(db);
	}
}
