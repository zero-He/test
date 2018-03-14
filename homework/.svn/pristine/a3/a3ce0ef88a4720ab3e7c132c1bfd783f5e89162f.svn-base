package cn.strong.leke.homework.job;

import java.util.Calendar;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.StopWatch;

import cn.strong.leke.common.utils.datetime.Week;
import cn.strong.leke.framework.spring.SpringContextHolder;
import cn.strong.leke.homework.manage.ExerciseService;

/**
 * 自主练习周排行榜
 * @author Zhang Fujun
 * @date 2016年11月14日
 */
public class ExerciseWeekRankJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(ExerciseWeekRankJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

//		logger.info("start Exercise-Week-Rank task……");
		StopWatch watch = new StopWatch();
		watch.start();
		ExerciseService service = SpringContextHolder.getBean(ExerciseService.class);
		try {
			//生成当前周的 自主练习排名
			Calendar calendar = Calendar.getInstance();
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			Week week = new Week(calendar.getTime());
		    calendar.setTime(week.getEndDate());
			service.saveWeekRank(calendar.get(Calendar.YEAR), calendar.get(Calendar.WEEK_OF_YEAR));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new JobExecutionException(e.getMessage(), e);
		}
		watch.stop();
//		logger.info("finish Exercise-Week-Rank task,耗时："+ watch.getTotalTimeSeconds() +"秒");
	}

}
