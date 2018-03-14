package cn.strong.leke.dwh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.strong.leke.dwh.storage.MongoSparkRepoStorage;
import cn.strong.leke.dwh.storage.SparkRepoStorage;

@Configuration
public class StorageConfig {

	@Bean
	public SparkRepoStorage sparkRepoStorage() throws Exception {
		return new MongoSparkRepoStorage();
	}
}

