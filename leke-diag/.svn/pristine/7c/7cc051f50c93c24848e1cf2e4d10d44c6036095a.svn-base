package cn.strong.leke.diag.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = {"cn.strong.leke.diag.dao.lesson.mybatis", "cn.strong.leke.diag.dao.teachingMonitor", "cn.strong.leke.diag.dao.studentMonitor"}, sqlSessionFactoryRef = "lessonSqlSessionFactory")
public class LessonDBConfig {

	@Value("${jdbc.driverClassName}")
	private String driverClassName;
	@Value("${jdbc.lesson.url}")
	private String url;
	@Value("${jdbc.lesson.username}")
	private String username;
	@Value("${jdbc.lesson.password}")
	private String password;

	public DataSource lessonDataSource() {
		return BeanBuilder.buildDataSource(driverClassName, url, username, password);
	}

	@Bean
	public SqlSessionFactoryBean lessonSqlSessionFactory(Interceptor offsetLimitIntercepter) throws Exception {
		return BeanBuilder.buildSqlSessionFactory(this.lessonDataSource(), offsetLimitIntercepter);
	}
}
