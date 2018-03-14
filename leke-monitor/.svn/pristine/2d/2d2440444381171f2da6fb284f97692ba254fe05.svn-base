/**
 * 
 */
package cn.strong.leke.monitor.mongo.model;

import java.io.Serializable;

/**
 * 访问记录基类
 * 
 * @author liulongbiao
 *
 */
public class AccessBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1451851701326828433L;
	private String serverName; // 服务器名
	private String servletPath; // 服务路径

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServletPath() {
		return servletPath;
	}

	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}

	/**
	 * 转换成 URL
	 * 
	 * @return
	 */
	public String toUrl() {
		return this.serverName + this.servletPath;
	}
}
