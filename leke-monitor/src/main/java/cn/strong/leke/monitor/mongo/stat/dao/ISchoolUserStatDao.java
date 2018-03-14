/**
 * 
 */
package cn.strong.leke.monitor.mongo.stat.dao;

import java.util.Date;
import java.util.Set;

import cn.strong.leke.monitor.mongo.stat.model.SchoolUserStat;

/**
 * @author liulongbiao
 *
 */
public interface ISchoolUserStatDao {
	/**
	 * 保存学校注册用户量统计
	 * 
	 * @param stat
	 */
	void save(SchoolUserStat stat);

	/**
	 * 根据学校ID获取注册用户统计
	 * 
	 * @param schoolId
	 * @return
	 */
	SchoolUserStat getById(long schoolId);

	/**
	 * 获取某个时间戳以来注册用户统计有更新的学校ID
	 * 
	 * @param ts
	 * @return
	 */
	Set<Long> getActiveSchoolIdsSince(Date ts);
}
