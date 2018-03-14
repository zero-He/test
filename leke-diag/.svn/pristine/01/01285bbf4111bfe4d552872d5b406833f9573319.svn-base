package cn.strong.leke.diag.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.strong.leke.diag.manage.UserStatsService;
import cn.strong.leke.framework.spring.SpringContextHolder;

public class UserStatsTask extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(UserStatsTask.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		UserStatsService userStatsService = SpringContextHolder.getBean(UserStatsService.class);

		try {
			userStatsService.executeUserStats();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new JobExecutionException(e.getMessage(), e);
		}
	}

}
