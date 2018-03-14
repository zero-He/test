/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.service;

import java.util.List;

import cn.strong.leke.monitor.mongo.online.model.OnlineUserSchoolStat;
import cn.strong.leke.monitor.mongo.online.model.OnlineUserStat;

/**
 * @author liulongbiao
 *
 */
public interface IOnlineUserSchoolStatDao {
	/**
	 * 保存学校统计(到临时表)
	 * 
	 * @param stat
	 */
	void save(OnlineUserSchoolStat stat);

	/**
	 * 获取学校用户统计
	 * 
	 * @param schoolId
	 * @return
	 */
	OnlineUserSchoolStat getById(long schoolId);

	/**
	 * 获取平台统计
	 * 
	 * @return
	 */
	OnlineUserStat sumAll();

	/**
	 * 根据区域ID获取用户统计
	 * 
	 * @param regionId
	 * @return
	 */
	OnlineUserStat sumByRegion(Long regionId);

	/**
	 * 获取在线用户数大于 0 的学校ID
	 * 
	 * @return
	 */
	List<Long> findSchoolsHasOnline();

	/**
	 * 根据区域ID获取学校统计
	 * 
	 * @param regionId
	 * @return
	 */
	List<OnlineUserSchoolStat> findByRegion(Long regionId);

	/**
	 * 移除指定学校的统计记录
	 * 
	 * @param schoolId
	 */
	void removeById(Long schoolId);
}
