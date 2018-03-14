package cn.strong.leke.diag.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "cn.strong.leke.diag.dao.note.mybatis", sqlSessionFactoryRef = "noteSqlSessionFactory")
public class NoteDBConfig {

	@Value("${jdbc.driverClassName}")
	private String driverClassName;
	@Value("${jdbc.note.url}")
	private String url;
	@Value("${jdbc.note.username}")
	private String username;
	@Value("${jdbc.note.password}")
	private String password;

	public DataSource noteDataSource() {
		return BeanBuilder.buildDataSource(driverClassName, url, username, password);
	}

	@Bean
	public SqlSessionFactoryBean noteSqlSessionFactory(Interceptor offsetLimitIntercepter) throws Exception {
		return BeanBuilder.buildSqlSessionFactory(this.noteDataSource(), offsetLimitIntercepter);
	}
}
