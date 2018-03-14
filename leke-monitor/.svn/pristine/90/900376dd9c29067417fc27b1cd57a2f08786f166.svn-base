/**
 * 
 */
package cn.strong.leke.monitor.mongo.model.updates;

import org.bson.Document;

/**
 * 访问按日统计增量
 * 
 * @author liulongbiao
 *
 */
public class AccessDailyDelta {
	private Integer day; // yyyyMMdd 格式的日期
	private String serverName; // 服务器名
	private String servletPath; // 服务路径
	private Long executeTime; // 耗时

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

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

	public Long getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Long executeTime) {
		this.executeTime = executeTime;
	}

	public Document filter() {
		return new Document("day", day).append("serverName", serverName).append("servletPath",
				servletPath);
	}

	public Document update() {
		return new Document("$inc", new Document("count", 1).append("totalExecuteTime", executeTime))
					.append("$max", new Document("maxExecuteTime", executeTime));
	}
}
