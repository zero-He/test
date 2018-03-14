package cn.strong.leke.monitor.schedule.job;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import cn.strong.leke.core.quartz.AbstractQuartzJob;
import cn.strong.leke.monitor.core.service.online.IDeviceOnlineUserService;

public class UserActiveDailyJob extends AbstractQuartzJob{
	@Autowired
	private IDeviceOnlineUserService deviceOnlineUserService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) {
		deviceOnlineUserService.saveDayActiveUser();
	}

}
