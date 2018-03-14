package cn.strong.leke.homework.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.StopWatch;

import cn.strong.leke.framework.spring.SpringContextHolder;
import cn.strong.leke.homework.service.HomeworkService;

public class StudentHwIncentiveJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(StudentHwIncentiveJob.class);
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info(" start student homework incentive job");
		StopWatch watch = new StopWatch();
		watch.start();
		try {
			HomeworkService service = SpringContextHolder.getBean(HomeworkService.class);
			service.execStudentHwIncentvie();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new JobExecutionException(e.getMessage(), e);
		}
		watch.stop();
		logger.info("执行时间(秒数)："+watch.getTotalTimeSeconds());
		logger.info(" end student homework incentive job");
	}
}
