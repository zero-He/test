package cn.strong.leke.homework.job;

import cn.strong.leke.framework.spring.SpringContextHolder;
import cn.strong.leke.homework.service.IJobService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 在线考试提醒
 * @Author LIU.SHITING
 * @Version 2.7
 * @Date 2017-05-19 16:55:37
 */
public class OnlineExamJob extends QuartzJobBean {

	private Logger logger = LoggerFactory.getLogger(OnlineExamJob.class);

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			IJobService jobService = SpringContextHolder.getBean(IJobService.class);
			jobService.excuteOnlineExam();
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new JobExecutionException(e.getMessage(), e);
		}
	}
}
