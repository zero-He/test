/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.model;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * 在线人数日统计
 * 
 * @author liulongbiao
 *
 */
public class OnlineDaily {
	@_id
	@ObjectId
	private String id;
	private Integer day; // yyyyMMdd 格式的日期
	private Long total; // 当日所有活跃人数
	private Long max; // 当日最大同时在线人数

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

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getMax() {
		return max;
	}

	public void setMax(Long max) {
		this.max = max;
	}

}
