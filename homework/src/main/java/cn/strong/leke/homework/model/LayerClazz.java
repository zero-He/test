package cn.strong.leke.homework.model;

import java.util.List;

import cn.strong.leke.model.user.SimpleUser;

public class LayerClazz {

	private Long classId;
	private String className;
	private Integer classType;
	private List<Group> groups;
	private List<SimpleUser> users;

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getClassType() {
		return classType;
	}

	public void setClassType(Integer classType) {
		this.classType = classType;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<SimpleUser> getUsers() {
		return users;
	}

	public void setUsers(List<SimpleUser> users) {
		this.users = users;
	}

	public static class Group {
		private Long groupId;
		private String groupName;

		public Long getGroupId() {
			return groupId;
		}

		public void setGroupId(Long groupId) {
			this.groupId = groupId;
		}

		public String getGroupName() {
			return groupName;
		}

		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}
	}
}
