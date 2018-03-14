/**
 * 
 */
package cn.strong.leke.monitor.core.model;

import cn.strong.leke.monitor.mongo.model.query.AccessStat;

/**
 * 访问记录范围统计
 * 
 * @author liulongbiao
 *
 */
public class AccessRangeStatDto extends AccessStat {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5770763988316562207L;
	private String name;
	private Integer type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
