/**
 * 
 */
package cn.strong.leke.monitor.listener.model;

import cn.strong.leke.monitor.mongo.model.AccessBase;

/**
 * 访问记录消息
 * 
 * @author liulongbiao
 *
 */
public class AccessMsg extends AccessBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7268649699105215466L;
	private String userId; // 用户ID，接口调用方可能是不同的字符串
	private Long schoolId; // 用户当前学校ID
	private Long roleId; // 角色ID
	private Long time; // 访问时间戳
	private Long executeTime; // 耗时
	private String ip; // 用户IP
	private String params; // GET 请求参数
	private String device; // 设备类型

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Long getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Long executeTime) {
		this.executeTime = executeTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

}
