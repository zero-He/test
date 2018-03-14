/* 
 * 包名: cn.strong.leke.question.model
 *
 * 文件名：QueTaskConfig.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-6-10
 */
package cn.strong.leke.question.model;

import cn.strong.leke.model.BaseModel;

/**
 * 习题领取设置实体
 * @author    lavender
 * @version   Avatar 
 */
public class QueTaskConfig extends BaseModel {

	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * configId:id
	 */
	private Long configId;
	
	/**
	 * taskCount:领取数量
	 */
	private Integer taskCount;
	public Long getConfigId() {
		return configId;
	}
	public void setConfigId(Long configId) {
		this.configId = configId;
	}
	public Integer getTaskCount() {
		return taskCount;
	}
	public void setTaskCount(Integer taskCount) {
		this.taskCount = taskCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
