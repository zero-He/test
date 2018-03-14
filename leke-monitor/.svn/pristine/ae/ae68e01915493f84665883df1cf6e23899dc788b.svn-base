/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.service;

import java.util.Set;

import cn.strong.leke.monitor.mongo.course.model.CourseSingleStudentsAttend;

/**
 * @author liulongbiao
 *
 */
public interface ICourseSingleStudentsAttendDao {

	/**
	 * 保存单课学生考勤
	 * 
	 * @param record
	 */
	void save(CourseSingleStudentsAttend record);

	/**
	 * 根据单课ID 获取单课学生考勤
	 * 
	 * @param csId
	 * @return
	 */
	CourseSingleStudentsAttend getById(Long csId);

	/**
	 * 根据单课ID 获取实到学生数
	 * 
	 * @param csId
	 * @return
	 */
	long getActualStuCount(Long csId);

	/**
	 * 根据单课ID 集合获取实到学生数
	 * 
	 * @param csIds
	 * @return
	 */
	long getActualStuCount(Set<Long> csIds);
}
