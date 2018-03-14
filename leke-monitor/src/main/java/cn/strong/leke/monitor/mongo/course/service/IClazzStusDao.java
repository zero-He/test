/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.service;

import java.util.List;
import java.util.Set;

import com.mongodb.client.result.UpdateResult;

import cn.strong.leke.monitor.mongo.course.model.ClazzStus;

/**
 * 班级学生名单DAO
 * 
 * @author liulongbiao
 *
 */
public interface IClazzStusDao {

	/**
	 * 添加班级学生名单
	 * 
	 * @param clazzId
	 * @param stuIds
	 */
	void addClazzStus(Long clazzId, List<Long> stuIds);

	/**
	 * 移除
	 * 
	 * @param clazzId
	 * @param stuIds
	 */
	UpdateResult removeClazzStus(Long clazzId, List<Long> stuIds);

	/**
	 * 设置班级学生名单
	 * 
	 * @param clazzId
	 * @param stuIds
	 */
	void setClazzStus(Long clazzId, List<Long> stuIds);

	/**
	 * 获取班级学生名单
	 * 
	 * @param clazzId
	 * @return
	 */
	ClazzStus getClazzStus(Long clazzId);

	/**
	 * 根据班级ID获取应到学生数
	 * 
	 * @param clazzId
	 * @return
	 */
	long getExpectStuCount(Long clazzId);

	/**
	 * 根据班级ID集合获取应到学生数
	 * 
	 * @param clazzIds
	 * @return
	 */
	long getExpectStuCount(Set<Long> clazzIds);
}
