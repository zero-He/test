/**
 * 
 */
package cn.strong.leke.monitor.schedule.job;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import cn.strong.leke.core.quartz.AbstractQuartzJob;
import cn.strong.leke.monitor.core.service.online.IWebOnlineUserService;

/**
 * 在线用户数日统计
 * 
 * @author liulongbiao
 *
 */
public class OnlineDailyJob extends AbstractQuartzJob {
	@Autowired
	private IWebOnlineUserService onlineService;

	@Override
	protected void executeInternal(JobExecutionContext context) {
		onlineService.updateDaily();
	}

}
