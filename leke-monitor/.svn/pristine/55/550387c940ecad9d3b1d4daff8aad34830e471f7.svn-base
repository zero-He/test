/**
 * 
 */
package cn.strong.leke.monitor.mongo.model.query;

import java.util.Date;

import org.bson.Document;
import org.springframework.util.Assert;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.monitor.util.BsonCriteria;

/**
 * 监听异常记录查询
 * 
 * @author liulongbiao
 *
 */
public class MleRecordQuery {
	private Long startTs;
	private Long endTs;
	private String queueName;

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

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public Document toBSON() {
		Assert.notNull(startTs, "startTs must be not null");
		Assert.notNull(endTs, "endTs must be not null");
		BsonCriteria filter = new BsonCriteria().gte("ts", new Date(startTs)).lt("ts", new Date(endTs));
		if (StringUtils.isNotEmpty(queueName)) {
			filter.append("queueName", queueName);
		}
		return filter;
	}
}
