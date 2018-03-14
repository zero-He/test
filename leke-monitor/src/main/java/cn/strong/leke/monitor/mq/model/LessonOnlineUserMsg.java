/**
 * 
 */
package cn.strong.leke.monitor.mq.model;

import java.util.Date;
import java.util.List;

import cn.strong.leke.context.user.cst.RoleCst;

/**
 * 课堂在线用户消息
 * 
 * @author liulongbiao
 *
 */
public class LessonOnlineUserMsg {
	private Long lessonId; // 课堂ID
	private Long schoolId; // 学校ID
	private List<UserItem> users; // 在线用户列表
	private Date ts; // 时间戳

	public Long getLessonId() {
		return lessonId;
	}

	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public List<UserItem> getUsers() {
		return users;
	}

	public void setUsers(List<UserItem> users) {
		this.users = users;
	}

	public Date getTs() {
		return ts == null ? new Date() : ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	/**
	 * 课堂在线用户
	 *
	 */
	public static class UserItem {
		private Long userId; // 用户ID
		private Long roleId; // 角色ID
		private Integer d; // 设备端： 1-flash端; 2-平板端; 3-桌面端

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Long getRoleId() {
			return roleId == null ? RoleCst.STUDENT : roleId;
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
}
