/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.mongo.online.model.OnlineUser;
import cn.strong.leke.monitor.mongo.online.model.query.OnlineUserQuery;

/**
 * @author liulongbiao
 *
 */
public interface IOnlineUserDao {

	/**
	 * 保存在线用户心跳
	 * 
	 * @param heartbeat
	 */
	void save(OnlineUser heartbeat);

	/**
	 * 获取指定时间戳以来的活跃用户数
	 * 
	 * @param ts
	 * @return
	 */
	long getActiveUserCountSince(Date ts);

	/**
	 * 查询在线用户
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<OnlineUser> queryOnlineUsers(OnlineUserQuery query, Page page);

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
