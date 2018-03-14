/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.service;

import java.util.List;

import cn.strong.leke.monitor.mongo.course.model.CoursePlatformDaily;

/**
 * @author liulongbiao
 *
 */
public interface ICoursePlatformDailyDao {
	/**
	 * 添加课堂平台日统计记录
	 * 
	 * @param daily
	 */
	void save(CoursePlatformDaily daily);

	/**
	 * 获取日期范围内的课堂平台日统计
	 * 
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	List<CoursePlatformDaily> findCoursePlatformDaily(int startDay, int endDay);
}
