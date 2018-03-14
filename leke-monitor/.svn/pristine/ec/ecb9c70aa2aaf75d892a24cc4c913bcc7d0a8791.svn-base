/**
 * 
 */
package cn.strong.leke.monitor.mongo.model;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * 访问记录按日统计
 * 
 * @author liulongbiao
 *
 */
public class AccessDaily extends AccessBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2758740991118450229L;

	@_id
	@ObjectId
	private String id;
	private Integer day; // yyyyMMdd 格式的日期
	private Long count; // 访问次数
	private Long maxExecuteTime; // 最大耗时
	private Long totalExecuteTime; // 总耗时

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

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getMaxExecuteTime() {
		return maxExecuteTime;
	}

	public void setMaxExecuteTime(Long maxExecuteTime) {
		this.maxExecuteTime = maxExecuteTime;
	}

	public Long getTotalExecuteTime() {
		return totalExecuteTime;
	}

	public void setTotalExecuteTime(Long totalExecuteTime) {
		this.totalExecuteTime = totalExecuteTime;
	}

}
