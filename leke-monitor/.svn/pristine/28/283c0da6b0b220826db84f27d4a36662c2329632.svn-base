/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.service;

import java.util.List;

import cn.strong.leke.monitor.mongo.course.model.CourseSchoolDaily;
import cn.strong.leke.monitor.mongo.course.model.query.DailyCourseStat;
import cn.strong.leke.monitor.mongo.course.model.query.DayRangeCourseQuery;

/**
 * @author liulongbiao
 *
 */
public interface ICourseSchoolDailyDao {

	/**
	 * 添加课堂学校日统计记录
	 * 
	 * @param daily
	 */
	void save(CourseSchoolDaily daily);

	/**
	 * 获取课堂日统计
	 * 
	 * @param day
	 * @return 返回非 null 的课堂平台日统计
	 */
	DailyCourseStat getDailyCourseStat(int day);

	/**
	 * 查找课堂日统计
	 * 
	 * @param query
	 * @return
	 */
	List<DailyCourseStat> findDailyCourseStats(DayRangeCourseQuery query);
}
