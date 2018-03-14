package cn.strong.leke.diag.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "cn.strong.leke.diag.dao.wrongtopic.mybatis", sqlSessionFactoryRef = "wrongtopicSqlSessionFactory")
public class WrongtopicDBConfig {

	@Value("${jdbc.driverClassName}")
	private String driverClassName;
	@Value("${jdbc.wrongtopic.url}")
	private String url;
	@Value("${jdbc.wrongtopic.username}")
	private String username;
	@Value("${jdbc.wrongtopic.password}")
	private String password;

	public DataSource wrongtopicDataSource() {
		return BeanBuilder.buildDataSource(driverClassName, url, username, password);
	}

	@Bean
	public SqlSessionFactoryBean wrongtopicSqlSessionFactory(Interceptor offsetLimitIntercepter) throws Exception {
		return BeanBuilder.buildSqlSessionFactory(this.wrongtopicDataSource(), offsetLimitIntercepter);
	}
}
