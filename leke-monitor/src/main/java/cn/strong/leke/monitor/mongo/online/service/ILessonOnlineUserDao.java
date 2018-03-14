/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.service;

import java.util.Date;
import java.util.Set;

import cn.strong.leke.monitor.mongo.online.model.LessonOnlineUser;

/**
 * 课堂在线用户DAO
 * 
 * @author liulongbiao
 *
 */
public interface ILessonOnlineUserDao {

	/**
	 * 保存在线用户
	 * 
	 * @param user
	 */
	void save(LessonOnlineUser user);

	/**
	 * 获取指定时间戳以来的有活跃用户的学校ID
	 * 
	 * @param ts
	 * @return
	 */
	Set<Long> getActiveSchoolIdsSince(Date ts);

	/**
	 * 获取某校活跃用户ID
	 * 
	 * @param ts
	 * @param schoolId
	 * @return
	 */
	Set<Long> findActiveUserIdsSince(Date ts, long schoolId);
}
