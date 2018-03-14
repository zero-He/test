/**
 * 
 */
package cn.strong.leke.monitor.db.tutor.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 试题数据库配置
 * 
 * @author liulongbiao
 *
 */
@Configuration
public class TutorDBConfig {

	@Value("${jdbc.driverClassName}")
	private String driverClassName;
	@Value("${jdbc.url.tutor}")
	private String url;
	@Value("${jdbc.username.tutor}")
	private String username;
	@Value("${jdbc.password.tutor}")
	private String password;

	@Bean
	public DataSource tutorDataSource() {
		BasicDataSource result = new BasicDataSource();

		result.setUrl(url);
		result.setDriverClassName(driverClassName);
		result.setUsername(username);
		result.setPassword(password);
		
		result.setInitialSize(1);
		result.setMaxActive(8);
		result.setMaxIdle(2);
		result.setMinIdle(1);
		result.setTimeBetweenEvictionRunsMillis(20000);
		result.setMinEvictableIdleTimeMillis(28000);
		result.setMaxWait(10000);
		result.setPoolPreparedStatements(true);
		result.setMaxOpenPreparedStatements(10);
		result.setRemoveAbandoned(true);
		result.setRemoveAbandonedTimeout(180);
		result.setLogAbandoned(true);
		result.setValidationQuery("SELECT 1");
		result.setTestOnBorrow(true);

		return result;
	}

	@Bean
	public DataSourceTransactionManager tutorTransactionManager() {
		return new DataSourceTransactionManager(tutorDataSource());
	}

	@Bean
	public JdbcTemplate tutorJdbcTemplate() {
		return new JdbcTemplate(tutorDataSource());
	}

	@Bean
	public SqlSessionFactoryBean tutorSqlSessionFactory() {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(tutorDataSource());
		return sessionFactory;
	}
}
