/**
 * 
 */
package cn.strong.leke.monitor.mongo.model.query;

import org.bson.Document;

import cn.strong.leke.common.utils.StringUtils;

/**
 * SQL 日统计查询
 * 
 * @author liulongbiao
 *
 */
public class SqlDailyQuery {
	private Integer day;
	private String serverName;
	private String sqlId;

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

	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	public Document toBSON() {
		if (day == null) {
			throw new IllegalStateException("day should not be null");
		}
		Document filter = new Document("day", day);
		if (StringUtils.isNotEmpty(serverName)) {
			filter.append("serverName", serverName);
		}
		if (StringUtils.isNotEmpty(sqlId)) {
			filter.append("sqlId", sqlId);
		}
		return filter;
	}
}
