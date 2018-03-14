/**
 * 
 */
package cn.strong.leke.monitor.mongo.model.query;

import java.util.Date;

import org.bson.Document;
import org.springframework.util.Assert;

import cn.strong.leke.common.utils.StringUtils;

/**
 * SQL 记录查询
 * 
 * @author liulongbiao
 *
 */
public class SqlRecordQuery {
	private Long startTs;
	private Long endTs;
	private String hash;

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

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Document toBSON() {
		Assert.notNull(startTs, "startTs must be not null");
		Assert.notNull(endTs, "endTs must be not null");
		Document filter = new Document("ts", new Document("$gte", new Date(startTs)).append("$lt",
				new Date(endTs)));
		if (StringUtils.isNotEmpty(hash)) {
			filter.append("hash", hash);
		}
		return filter;
	}
}
