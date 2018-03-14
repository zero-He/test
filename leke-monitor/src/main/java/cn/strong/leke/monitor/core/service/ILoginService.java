/**
 * 
 */
package cn.strong.leke.monitor.core.service;

import cn.strong.leke.monitor.listener.model.LoginMsg;

/**
 * @author liulongbiao
 *
 */
public interface ILoginService {

	/**
	 * 添加登录消息
	 * 
	 * @param msg
	 */
	void add(LoginMsg msg);

}
