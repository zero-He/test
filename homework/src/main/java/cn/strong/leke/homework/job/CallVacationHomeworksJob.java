package cn.strong.leke.homework.job;

import cn.strong.leke.framework.spring.SpringContextHolder;
import cn.strong.leke.homework.service.IJobService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 催交寒暑假作业定时任务
 * @Author LIU.SHITING
 * @Version 2.6
 * @Date 2017/5/12 11:56
 */
public class CallVacationHomeworksJob extends QuartzJobBean {

	private Logger logger = LoggerFactory.getLogger(CallVacationHomeworksJob.class);

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		try {
			IJobService jobService = SpringContextHolder.getBean(IJobService.class);
			jobService.excuteCallVacationHomework();
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new JobExecutionException(e.getMessage(), e);
		}
	}

}
