package cn.strong.leke.homework.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.strong.leke.framework.spring.SpringContextHolder;
import cn.strong.leke.homework.service.IJobService;

/**
 * 周错题作业
 * @author Zhang Fujun
 * @date 2016年10月28日
 */
public class WeekHomeworkJob extends QuartzJobBean {

	private Logger logger = LoggerFactory.getLogger(WeekHomeworkJob.class);
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
//		logger.info("start task weekHomework ");
		try {
			IJobService jobService = SpringContextHolder.getBean(IJobService.class);
			jobService.excuteWeekHomework();
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
			throw new JobExecutionException(e.getMessage(),e);
		}
//		logger.info("finish task weekHomework ");
	}

}
