/**
 * 
 */
package cn.strong.leke.monitor.core.service;

import cn.strong.leke.monitor.listener.model.MleMsg;

/**
 * @author liulongbiao
 *
 */
public interface IMleService {

	/**
	 * 处理消息
	 * 
	 * @param msg
	 */
	void add(MleMsg msg);

}
