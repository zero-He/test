package cn.strong.leke.diag.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import cn.strong.leke.data.mongo.core.MongoClientFactoryBean;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@Configuration
@ComponentScan({"cn.strong.leke.diag.dao.*.mongo","cn.strong.leke.diag.mongo.teachingMonitor","cn.strong.leke.diag.mongo.studentMonitor"})
public class MongoConfig {

	@Value("${mongodb.replicaset}")
	private String replicaset;
	@Value("${mongodb.credentials}")
	private String credentials;

	@Value("${mongodb.homework.dbName}")
	private String homeworkDbName;
	@Value("${mongodb.lesson.dbName}")
	private String lessonDbName;
	@Value("${mongodb.incentive.dbName}")
	private String incentiveDbName;
	@Value("${mongodb.beike.dbName}")
	private String beikeDbName;
	@Value("${mongodb.diag.dbName}")
	private String diagDbName;
	@Value("${mongodb.monitor.dbName}")
	private String monitorDbName;
	@Value("${mongodb.learn.dbName}")
	private String learnDbName;

	@Bean
	public MongoClientFactoryBean mongo() throws Exception {
		MongoClientFactoryBean factory = new MongoClientFactoryBean();
		factory.setReplicaset(replicaset);
		factory.setCredentials(credentials);
		return factory;
	}

	@Bean
	public MongoDatabase homeworkDb(MongoClient mongo) throws Exception {
		return mongo.getDatabase(this.homeworkDbName);
	}

	@Bean
	public MongoDatabase lessonDb(MongoClient mongo) throws Exception {
		return mongo.getDatabase(this.lessonDbName);
	}

	@Bean
	public MongoDatabase incentiveDb(MongoClient mongo) throws Exception {
		return mongo.getDatabase(this.incentiveDbName);
	}
	
	@Bean
	public MongoDatabase beikeDb(MongoClient mongo) throws Exception {
		return mongo.getDatabase(this.beikeDbName);
	}

	@Bean
	public MongoDatabase diagDb(MongoClient mongo) throws Exception {
		return mongo.getDatabase(this.diagDbName);
	}
	
	@Bean
	public MongoDatabase monitorDb(MongoClient mongo) throws Exception {
		return mongo.getDatabase(this.monitorDbName);
	}

	@Bean
	public MongoDatabase learnDb(MongoClient mongo) throws Exception {
		return mongo.getDatabase(this.learnDbName);
	}
}
