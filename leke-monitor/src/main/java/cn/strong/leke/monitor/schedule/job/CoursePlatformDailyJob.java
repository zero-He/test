/**
 * 
 */
package cn.strong.leke.monitor.schedule.job;

import org.joda.time.LocalDate;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import cn.strong.leke.core.quartz.AbstractQuartzJob;
import cn.strong.leke.monitor.core.service.course.ICourseService;
import cn.strong.leke.monitor.util.StatUtils;

/**
 * 平台课堂日统计任务
 * 
 * @author liulongbiao
 *
 */
public class CoursePlatformDailyJob extends AbstractQuartzJob {

	@Autowired
	private ICourseService courseService;

	@Override
	protected void executeInternal(JobExecutionContext context) {
		int yestoday = StatUtils.ofDay(LocalDate.now().minusDays(1).toDate());
		courseService.updateCoursePlatformDaily(yestoday);
	}

}
