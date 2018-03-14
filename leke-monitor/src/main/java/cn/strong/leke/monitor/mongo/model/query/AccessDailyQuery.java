/**
 * 
 */
package cn.strong.leke.monitor.mongo.model.query;

import org.bson.Document;

import cn.strong.leke.common.utils.StringUtils;

/**
 * 访问日统计查询
 * 
 * @author liulongbiao
 *
 */
public class AccessDailyQuery {
	private Integer day;
	private String serverName;

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

	public Document toBSON() {
		if (day == null) {
			throw new IllegalStateException("day should not be null");
		}
		Document filter = new Document("day", day);
		if (StringUtils.isNotEmpty(serverName)) {
			filter.append("serverName", serverName);
		}
		return filter;
	}
}
