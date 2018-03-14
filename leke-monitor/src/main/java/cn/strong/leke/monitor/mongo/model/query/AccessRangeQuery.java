/**
 * 
 */
package cn.strong.leke.monitor.mongo.model.query;

import org.bson.Document;

import cn.strong.leke.common.utils.StringUtils;


/**
 * 访问范围查询
 * 
 * @author liulongbiao
 *
 */
public class AccessRangeQuery {
	private Integer startDay;
	private Integer endDay;
	private String serverName;

	public int getStartDay() {
		return startDay;
	}

	public void setStartDay(Integer startDay) {
		this.startDay = startDay;
	}

	public int getEndDay() {
		return endDay;
	}

	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Document toBSON() {
		if (startDay == null || endDay == null) {
			throw new IllegalStateException("startDay or endDay should not be null");
		}
		Document filter = new Document("day", new Document("$gte", startDay).append("$lte", endDay));
		if (StringUtils.isNotEmpty(serverName)) {
			filter.append("serverName", serverName);
		}
		return filter;
	}
}
