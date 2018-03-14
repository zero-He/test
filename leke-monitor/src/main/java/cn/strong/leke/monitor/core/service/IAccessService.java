/**
 * 
 */
package cn.strong.leke.monitor.core.service;

import java.util.List;

import cn.strong.leke.monitor.core.model.AccessDailyDto;
import cn.strong.leke.monitor.core.model.AccessRangeStatDto;
import cn.strong.leke.monitor.listener.model.AccessMsg;
import cn.strong.leke.monitor.mongo.model.query.AccessDailyQuery;
import cn.strong.leke.monitor.mongo.model.query.AccessRangeQuery;

/**
 * @author liulongbiao
 *
 */
public interface IAccessService {

	/**
	 * 添加访问记录
	 * 
	 * @param msg
	 */
	void add(AccessMsg msg);

	/**
	 * 获取某一天的按日统计
	 * 
	 * @param query
	 * @return
	 */
	List<AccessDailyDto> findAccessDailyStats(AccessDailyQuery query);

	/**
	 * 获取某段日期范围内的访问统计
	 * 
	 * @param query
	 * @return
	 */
	List<AccessRangeStatDto> findAccessRangeStats(AccessRangeQuery query);
}
