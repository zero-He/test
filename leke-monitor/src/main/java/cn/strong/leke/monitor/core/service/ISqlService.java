/**
 * 
 */
package cn.strong.leke.monitor.core.service;

import cn.strong.leke.monitor.listener.model.SqlMsg;

/**
 * @author liulongbiao
 *
 */
public interface ISqlService {

	/**
	 * 添加 SQL 记录
	 * 
	 * @param msg
	 */
	void add(SqlMsg msg);

}
