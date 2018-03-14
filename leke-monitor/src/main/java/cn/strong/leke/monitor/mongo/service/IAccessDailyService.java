/**
 * 
 */
package cn.strong.leke.monitor.mongo.service;

import java.util.List;

import cn.strong.leke.monitor.mongo.model.AccessDaily;
import cn.strong.leke.monitor.mongo.model.query.AccessDailyQuery;
import cn.strong.leke.monitor.mongo.model.query.AccessRangeQuery;
import cn.strong.leke.monitor.mongo.model.query.AccessStat;
import cn.strong.leke.monitor.mongo.model.updates.AccessDailyDelta;

/**
 * @author liulongbiao
 *
 */
public interface IAccessDailyService {

	/**
	 * 更新日统计增量
	 * 
	 * @param delta
	 */
	void update(AccessDailyDelta delta);

	/**
	 * 获取某一天的按日统计
	 * 
	 * @param query
	 * @return
	 */
	List<AccessDaily> findAccessDailys(AccessDailyQuery query);

	/**
	 * 获取某段日期范围内的访问统计
	 * 
	 * @param query
	 * @return
	 */
	List<AccessStat> findAccessRangeStats(AccessRangeQuery query);
}
