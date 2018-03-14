/**
 * 
 */
package cn.strong.leke.monitor.core.model;

import cn.strong.leke.monitor.mongo.model.AccessDaily;

/**
 * @author liulongbiao
 *
 */
public class AccessDailyDto extends AccessDaily {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2169705080963733819L;
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
