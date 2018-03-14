/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.strong.leke.monitor.mongo.online.model.DeviceOnlineUser;

/**
 * 设备在线用户DAO
 * 
 * @author liulongbiao
 *
 */
public interface IDeviceOnlineUserDao {

	/**
	 * 保存设备在线用户信息
	 * 
	 * @param user
	 */
	void save(DeviceOnlineUser user);

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

	/**
	 * 获取某校某设备类型活跃用户数
	 * 
	 * @param ts
	 * @param schoolId
	 * @param d
	 * @return
	 */
	long countActiveUsersSince(Date ts, long schoolId, int d);
	
	/**
	 * 获取每日活跃用户
	 * @param ts
	 * @return
	 */
	List<DeviceOnlineUser> getActiveUser();
}
