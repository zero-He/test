/**
 * 
 */
package cn.strong.leke.monitor.mongo.model;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * SQL 按日统计
 * 
 * @author liulongbiao
 *
 */
public class SqlDaily {

	@_id
	@ObjectId
	private String id;
	private Integer day; // yyyyMMdd 格式的日期
	private String hash; // sql hash 值
	private String serverName; // 服务器名
	private String sqlId; // SQL ID
	private String className; // SQL 类名
	private Long count; // 次数
	private Long maxCostTime; // 最大耗时
	private Long totalCostTime; // 总耗时

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getMaxCostTime() {
		return maxCostTime;
	}

	public void setMaxCostTime(Long maxCostTime) {
		this.maxCostTime = maxCostTime;
	}

	public Long getTotalCostTime() {
		return totalCostTime;
	}

	public void setTotalCostTime(Long totalCostTime) {
		this.totalCostTime = totalCostTime;
	}

}
