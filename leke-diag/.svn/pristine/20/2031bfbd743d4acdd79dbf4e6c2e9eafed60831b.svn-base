package cn.strong.leke.diag.task;

import cn.strong.leke.diag.service.teachingMonitor.ResourceXService;
import cn.strong.leke.framework.spring.SpringContextHolder;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 资源数据收集任务
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017/7/21 16:18
 */
public class ResourceDataCollectTask extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(ResourceDataCollectTask.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		try {
			ResourceXService resourceService = SpringContextHolder.getBean(ResourceXService.class);
			logger.info("------------------------------------------开始对资源数据汇总------------------------------------------");
			resourceService.excuteResourceCollectTask();
			logger.info("------------------------------------------资源数据汇总结束------------------------------------------");
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new JobExecutionException(e.getMessage(), e);
		}
	}
}
