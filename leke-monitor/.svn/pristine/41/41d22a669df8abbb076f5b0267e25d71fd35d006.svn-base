/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.service;

import java.util.Date;

import cn.strong.leke.monitor.mongo.online.model.OnlineSnapshot;

/**
 * @author liulongbiao
 *
 */
public interface IOnlineSnapshotDao {

	/**
	 * 保存在线人数统计快照
	 * 
	 * @param snapshot
	 */
	void save(OnlineSnapshot snapshot);

	/**
	 * 获取某天最高在线人数
	 * 
	 * @param day
	 * @return
	 */
	long getMaxCountOfDay(Date day);
}
