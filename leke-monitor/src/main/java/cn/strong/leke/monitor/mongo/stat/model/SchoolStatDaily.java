/**
 * 
 */
package cn.strong.leke.monitor.mongo.stat.model;

import cn.strong.leke.data.mongo.annotations._id;

/**
 * 按天注册学校统计
 * 
 * @author liulongbiao
 *
 */
public class SchoolStatDaily {
	@_id
	private int day; // 日期
	private SchoolStat sum; // 累计学校统计
	private SchoolStat added; // 新增学校统计

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public SchoolStat getSum() {
		return sum;
	}

	public void setSum(SchoolStat sum) {
		this.sum = sum;
	}

	public SchoolStat getAdded() {
		return added;
	}

	public void setAdded(SchoolStat added) {
		this.added = added;
	}

}
