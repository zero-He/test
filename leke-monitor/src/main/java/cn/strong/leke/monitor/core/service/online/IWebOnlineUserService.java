/**
 * 
 */
package cn.strong.leke.monitor.core.service.online;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.core.model.OnlineHeartbeat;
import cn.strong.leke.monitor.mongo.online.model.OnlineUser;
import cn.strong.leke.monitor.mongo.online.model.query.OnlineUserQuery;

/**
 * @author liulongbiao
 *
 */
public interface IWebOnlineUserService {

	/**
	 * 保存用户在线心跳
	 * 
	 * @param heartbeat
	 */
	void saveHeartbeat(OnlineHeartbeat heartbeat);

	/**
	 * 获取当前在线用户数量
	 * 
	 * @param ts
	 * @return
	 */
	long getCurrentOnlineUserCount();

	/**
	 * 查询在线用户
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<OnlineUser> queryOnlineUsers(OnlineUserQuery query, Page page);

	/**
	 * 执行当前用户快照
	 */
	void updateSnapshot();

	/**
	 * 执行日统计
	 */
	void updateDaily();
}
