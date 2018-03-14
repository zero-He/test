/**
 * 
 */
package cn.strong.leke.monitor.schedule.job;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;

import cn.strong.leke.core.quartz.AbstractQuartzJob;
import cn.strong.leke.monitor.core.service.online.ISchoolOnlineUserStatService;

/**
 * 学校在线用户统计更新任务
 * 
 * @author liulongbiao
 *
 */
public class SchoolOnlineUserStatJob extends AbstractQuartzJob {
	@Resource
	private ISchoolOnlineUserStatService schoolOnlineUserStatService;

	@Override
	protected void executeInternal(JobExecutionContext context) {
		schoolOnlineUserStatService.updateSchoolStats();
	}

}
