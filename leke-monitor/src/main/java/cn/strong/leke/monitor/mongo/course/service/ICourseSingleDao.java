/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.service;

import java.util.Date;
import java.util.List;

import cn.strong.leke.monitor.mongo.course.model.CourseSingle;
import cn.strong.leke.monitor.mongo.course.model.query.DayRangeCourseSingleQuery;
import cn.strong.leke.monitor.mongo.course.model.query.PeriodCourseStat;

/**
 * @author liulongbiao
 *
 */
public interface ICourseSingleDao {

	/**
	 * 保存单课信息
	 * 
	 * @param cs
	 */
	void save(CourseSingle cs);

	/**
	 * 获取单课信息
	 * 
	 * @param csId
	 * @return
	 */
	CourseSingle getById(Long csId);

	/**
	 * 移除单课信息
	 * 
	 * @param csId
	 */
	void removeById(Long csId);

	/**
	 * 获取某个学校在某个时间点的应到学生数
	 * 
	 * @param schoolId
	 * @param ts
	 * @return
	 */
	long getExpectStuCount(Long schoolId, Date ts);

	/**
	 * 更新单课实到学生数
	 * 
	 * @param csId
	 * @param actualStuCount
	 */
	void updateActualStuCount(Long csId, long actualStuCount);

	/**
	 * 更新单课结束标识
	 * 
	 * @param csId
	 * @param isEnded
	 */
	void updateIsEnded(Long csId, boolean isEnded);

	/**
	 * 更新单课教师在线标识
	 * 
	 * @param csId
	 * @param isOnline
	 */
	void updateIsOnline(Long csId, boolean isOnline);

	/**
	 * 根据套课ID查找所有未上课的单课
	 * 
	 * @param clazzId
	 * @return
	 */
	List<CourseSingle> findUnstartedByClazzId(Long clazzId);

	/**
	 * 根据套课ID 更新未上课的单课的应到学生数
	 * 
	 * @param clazzId
	 * @param expectStuCount
	 */
	void updateExpectStuCountForUnstarted(Long clazzId, long expectStuCount);

	/**
	 * 根据学校ID和日期获取当天按时段课堂统计
	 * 
	 * @param schoolId
	 * @param day
	 * @return
	 */
	List<PeriodCourseStat> findPeriodCourseStat(Long schoolId, int day);

	/**
	 * 查找符合条件的单课
	 * 
	 * @param query
	 * @return
	 */
	List<CourseSingle> findCourseSingles(DayRangeCourseSingleQuery query);
}
