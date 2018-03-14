/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.model;

import java.util.Date;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * 实时课堂在线用户
 * 
 * @author liulongbiao
 *
 */
public class LessonOnlineUser {

	@_id
	@ObjectId
	private String id;
	private Date ts; // 时间戳
	private Long schoolId; // 学校ID
	private Long userId; // 用户ID
	private Long roleId; // 角色ID
	private Integer d; // 设备端： 1-flash端; 2-平板端; 3-桌面端

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

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Integer getD() {
		return d;
	}

	public void setD(Integer d) {
		this.d = d;
	}

}
