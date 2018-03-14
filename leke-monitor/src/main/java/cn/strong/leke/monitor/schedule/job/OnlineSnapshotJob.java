/**
 * 
 */
package cn.strong.leke.monitor.schedule.job;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import cn.strong.leke.core.quartz.AbstractQuartzJob;
import cn.strong.leke.monitor.core.service.online.IWebOnlineUserService;

/**
 * 在线用户数五分钟快照任务
 * 
 * @author liulongbiao
 *
 */
public class OnlineSnapshotJob extends AbstractQuartzJob {

	@Autowired
	private IWebOnlineUserService onlineService;

	@Override
	protected void executeInternal(JobExecutionContext context) {
		onlineService.updateSnapshot();
	}

}
