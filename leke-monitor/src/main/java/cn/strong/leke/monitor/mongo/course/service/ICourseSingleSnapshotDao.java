/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.service;

import java.util.Date;

import cn.strong.leke.monitor.mongo.course.model.CourseSingleSnapshot;

/**
 * @author liulongbiao
 *
 */
public interface ICourseSingleSnapshotDao {

	/**
	 * 插入记录
	 * 
	 * @param record
	 */
	void save(CourseSingleSnapshot record);

	/**
	 * 获取某个学校在某个时间点的实际学生数
	 * 
	 * @param schoolId
	 * @param ts
	 * @return
	 */
	long getActualStuCount(Long schoolId, Date ts);
}
