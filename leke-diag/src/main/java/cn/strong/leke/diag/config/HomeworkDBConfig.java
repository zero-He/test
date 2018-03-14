package cn.strong.leke.diag.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "cn.strong.leke.diag.dao.homework.mybatis", sqlSessionFactoryRef = "homeworkSqlSessionFactory")
public class HomeworkDBConfig {

	@Value("${jdbc.driverClassName}")
	private String driverClassName;
	@Value("${jdbc.homework.url}")
	private String url;
	@Value("${jdbc.homework.username}")
	private String username;
	@Value("${jdbc.homework.password}")
	private String password;

	public DataSource homeworkDataSource() {
		return BeanBuilder.buildDataSource(driverClassName, url, username, password);
	}

	@Bean
	public SqlSessionFactoryBean homeworkSqlSessionFactory(Interceptor offsetLimitIntercepter) throws Exception {
		return BeanBuilder.buildSqlSessionFactory(this.homeworkDataSource(), offsetLimitIntercepter);
	}
}
