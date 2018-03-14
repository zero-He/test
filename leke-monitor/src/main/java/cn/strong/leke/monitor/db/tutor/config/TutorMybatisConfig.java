/**
 * 
 */
package cn.strong.leke.monitor.db.tutor.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 试题库 Mybatis 配置
 * 
 * @author liulongbiao
 *
 */
@Configuration
@MapperScan(value = "cn.strong.leke.monitor.db.tutor.mapper", sqlSessionFactoryRef = "tutorSqlSessionFactory")
public class TutorMybatisConfig {

}
