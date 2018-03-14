/**
 * 
 */
package cn.strong.leke.monitor.schedule.job;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.strong.leke.core.quartz.AbstractQuartzJob;
import cn.strong.leke.monitor.core.service.stat.ISchoolStatService;

/**
 * 注册学校数按天统计任务
 * 
 * @author liulongbiao
 *
 */
public class SchoolStatDailyJob extends AbstractQuartzJob {
	private static final Logger LOG = LoggerFactory.getLogger(SchoolStatDailyJob.class);
	@Resource
	private ISchoolStatService schoolStatService;

	@Override
	protected void executeInternal(JobExecutionContext context) {
		LOG.info("开始统计注册学校数...");
		schoolStatService.updateDaily();
	}

}
