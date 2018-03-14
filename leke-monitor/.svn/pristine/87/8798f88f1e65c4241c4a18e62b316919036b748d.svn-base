/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.model;

import java.util.Date;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * 五分钟时间点，当前在线人数统计快照
 * 
 * @author liulongbiao
 *
 */
public class OnlineSnapshot {
	@_id
	@ObjectId
	private String id;
	private Date ts; // 时间戳
	private Long count; // 在线人数

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
