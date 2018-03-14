/**
 * 
 */
package cn.strong.leke.monitor.schedule.job;

import java.util.Date;

import javax.annotation.Resource;

import org.joda.time.LocalDateTime;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.strong.leke.core.quartz.AbstractQuartzJob;
import cn.strong.leke.monitor.core.service.stat.ISchoolUserStatService;

/**
 * 更新学校累计注册用户数任务
 * 
 * @author liulongbiao
 *
 */
public class SchoolUserStatJob extends AbstractQuartzJob {

	private static final Logger LOG = LoggerFactory.getLogger(SchoolStatDailyJob.class);
	@Resource
	private ISchoolUserStatService schoolUserStatService;

	@Override
	protected void executeInternal(JobExecutionContext context) {
		LOG.info("开始更新学校累计注册用户数...");
		Date lastHour = LocalDateTime.now().minusHours(1).toDate();
		schoolUserStatService.updateChangedSince(lastHour);
	}
}
