package cn.strong.leke.diag.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "cn.strong.leke.diag.dao.beike.mybatis", sqlSessionFactoryRef = "beikeSqlSessionFactory")
public class BeikeDBConfig {

	@Value("${jdbc.driverClassName}")
	private String driverClassName;
	@Value("${jdbc.beike.url}")
	private String url;
	@Value("${jdbc.beike.username}")
	private String username;
	@Value("${jdbc.beike.password}")
	private String password;

	public DataSource beikeDataSource() {
		return BeanBuilder.buildDataSource(driverClassName, url, username, password);
	}

	@Bean
	public SqlSessionFactoryBean beikeSqlSessionFactory(Interceptor offsetLimitIntercepter) throws Exception {
		return BeanBuilder.buildSqlSessionFactory(this.beikeDataSource(), offsetLimitIntercepter);
	}
}
