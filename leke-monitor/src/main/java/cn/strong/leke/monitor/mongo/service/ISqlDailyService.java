/**
 * 
 */
package cn.strong.leke.monitor.mongo.service;

import java.util.List;

import cn.strong.leke.monitor.mongo.model.SqlDaily;
import cn.strong.leke.monitor.mongo.model.query.SqlDailyQuery;
import cn.strong.leke.monitor.mongo.model.updates.SqlDailyDelta;

/**
 * SQL 按日统计服务
 * 
 * @author liulongbiao
 *
 */
public interface ISqlDailyService {

	/**
	 * 更新按日统计
	 * 
	 * @param delta
	 */
	void update(SqlDailyDelta delta);

	/**
	 * 查询 SQL 日统计
	 * 
	 * @param query
	 * @return
	 */
	List<SqlDaily> findSqlDailys(SqlDailyQuery query);
}
