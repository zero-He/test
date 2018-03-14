package cn.strong.leke.diag.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("cn.strong.leke.diag.service,cn.strong.leke.diag.report,cn.strong.leke.diag.manage")
public class ServiceConfig {

}
