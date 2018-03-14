/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.service;

import java.util.Date;
import java.util.List;

import cn.strong.leke.monitor.mongo.course.model.query.CourseSnapshotQuery;
import cn.strong.leke.monitor.mongo.course.model.query.CourseSnapshotStat;
import cn.strong.leke.remote.model.user.SchoolAreaRemote;

/**
 * @author liulongbiao
 *
 */
public interface ICourseSchoolSnapshotDao {

	/**
	 * 保存学校在某时刻的实到学生数
	 * 
	 * @param sa
	 * @param ts
	 * @param actualStuCount
	 */
	void saveActualStuCount(SchoolAreaRemote sa, Date ts, long actualStuCount);

	/**
	 * 保存学校在某时刻的预期学生数
	 * 
	 * @param sa
	 * @param ts
	 * @param expectStuCount
	 */
	void saveExpectStuCount(SchoolAreaRemote sa, Date ts, long expectStuCount);

	/**
	 * 获取当天最高在线学生数
	 * 
	 * @param day
	 * @return
	 */
	long getMaxActualStuCount(int day);

	/**
	 * 查询课堂在线统计
	 * 
	 * @param query
	 * @return
	 */
	List<CourseSnapshotStat> findCourseSnapshotStats(CourseSnapshotQuery query);
}
