/**
 * 
 */
package cn.strong.leke.monitor.mongo.stat.model;

import java.io.Serializable;

/**
 * 注册学校统计
 * 
 * @author liulongbiao
 *
 */
public class SchoolStat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5388662070866584877L;

	private int total; // 总学校数量
	private int basic; // 基础教育学校
	private int social; // 社会培训机构
	private int unit; // 个人入驻学校

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getBasic() {
		return basic;
	}

	public void setBasic(int basic) {
		this.basic = basic;
	}

	public int getSocial() {
		return social;
	}

	public void setSocial(int social) {
		this.social = social;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

}
