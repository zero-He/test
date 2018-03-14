/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.service;

import java.util.List;

import cn.strong.leke.monitor.mongo.course.model.CourseTable;
import cn.strong.leke.monitor.mongo.course.model.query.DayRangeCourseQuery;

/**
 * @author liulongbiao
 *
 */
public interface ICourseTableDao {

	/**
	 * 保存课表记录
	 * 
	 * @param record
	 */
	void save(CourseTable record);

	/**
	 * 根据学校ID 和日期移除课表数据
	 * 
	 * @param schoolId
	 * @param day
	 */
	void deleteBySchoolAndDay(Long schoolId, int day);

	/**
	 * 获取课表记录
	 * 
	 * @param query
	 * @return
	 */
	List<CourseTable> findCourseTables(DayRangeCourseQuery query);
}
