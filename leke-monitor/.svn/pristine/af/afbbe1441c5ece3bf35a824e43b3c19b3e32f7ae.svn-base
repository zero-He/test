/**
 * 
 */
package cn.strong.leke.monitor.mongo.model.query;

import java.util.Date;

import org.bson.Document;
import org.springframework.util.Assert;

import cn.strong.leke.common.utils.StringUtils;

/**
 * 访问记录查询
 * 
 * @author liulongbiao
 *
 */
public class AccessRecordQuery {

	private Long startTs;
	private Long endTs;
	private String serverName;
	private String servletPath;

	public Long getStartTs() {
		return startTs;
	}

	public void setStartTs(Long startTs) {
		this.startTs = startTs;
	}

	public Long getEndTs() {
		return endTs;
	}

	public void setEndTs(Long endTs) {
		this.endTs = endTs;
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

	public Document toBSON() {
		Assert.notNull(startTs, "startTs must be not null");
		Assert.notNull(endTs, "endTs must be not null");
		Document filter = new Document("ts", new Document("$gte", new Date(startTs)).append("$lt",
				new Date(endTs)));
		if (StringUtils.isNotEmpty(serverName)) {
			filter.append("serverName", serverName);
		}
		if (StringUtils.isNotEmpty(servletPath)) {
			filter.append("servletPath", servletPath);
		}
		return filter;
	}
}
