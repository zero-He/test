package cn.strong.leke.diag.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

public class BeanBuilder {

	private static ResourceLoader loader = new DefaultResourceLoader();

	public static DataSource buildDataSource(String driverClassName, String url, String username, String password) {
		BasicDataSource ds = new BasicDataSource();
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setDriverClassName(driverClassName);
		ds.setInitialSize(5);
		ds.setMaxActive(800);
		ds.setMaxIdle(30);
		ds.setMinIdle(25);
		ds.setTimeBetweenEvictionRunsMillis(20000);
		ds.setMinEvictableIdleTimeMillis(28000);
		ds.setMaxWait(10000);
		ds.setPoolPreparedStatements(true);
		ds.setMaxOpenPreparedStatements(10);
		ds.setRemoveAbandoned(true);
		ds.setRemoveAbandonedTimeout(180);
		ds.setLogAbandoned(true);
		ds.setValidationQuery("SELECT 1");
		ds.setTestOnBorrow(true);
		return ds;
	}

	public static SqlSessionFactoryBean buildSqlSessionFactory(DataSource dataSource, Interceptor offsetLimitIntercepter) {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		factory.setConfigLocation(loader.getResource("classpath:conf/mybatis.xml"));
		factory.setPlugins(new Interceptor[] { offsetLimitIntercepter });
		return factory;
	}
}
