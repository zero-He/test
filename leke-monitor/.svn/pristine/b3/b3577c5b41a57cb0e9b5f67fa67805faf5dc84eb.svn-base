/**
 * 
 */
package cn.strong.leke.monitor.mongo.model.updates;

import org.bson.Document;

/**
 * SQL 按日统计增量
 * 
 * @author liulongbiao
 *
 */
public class SqlDailyDelta {
	private Integer day; // yyyyMMdd 格式的日期
	private String hash; // sql hash 值
	private String serverName; // 服务器名
	private String sqlId; // SQL ID
	private String className; // SQL 类名
	private Long costTime; // 耗时

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getCostTime() {
		return costTime;
	}

	public void setCostTime(Long costTime) {
		this.costTime = costTime;
	}

	public Document filter() {
		return new Document("day", day).append("hash", hash);
	}

	public Document update() {
		return new Document("$inc", new Document("count", 1).append("totalCostTime", costTime))
				.append("$max", new Document("maxCostTime", costTime))
				.append("$setOnInsert",
						new Document("serverName", serverName).append("sqlId", sqlId)
							.append("className", className));
	}
}
