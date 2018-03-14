package cn.strong.leke.monitor.mongo.course.model.query;

import java.util.Set;

import cn.strong.leke.monitor.mongo.online.model.query.ActiveUserQuery;

public class PlatformActiveStat extends ActiveUserQuery{
	
	private int allValidUser; //累计有效用户
	private int activeUsersCount;//活跃用户
	private String activePro;//活跃率
	private Set<Long> activeUsers;
	
	public int getAllValidUser() {
		return allValidUser;
	}
	public void setAllValidUser(int allValidUser) {
		this.allValidUser = allValidUser;
	}
	public int getActiveUsersCount() {
		return activeUsersCount;
	}
	public void setActiveUsersCount(int activeUsersCount) {
		this.activeUsersCount = activeUsersCount;
	}
	public String getActivePro() {
		return activePro;
	}
	public void setActivePro(String activePro) {
		this.activePro = activePro;
	}
	public Set<Long> getActiveUsers() {
		return activeUsers;
	}
	public void setActiveUsers(Set<Long> activeUsers) {
		this.activeUsers = activeUsers;
	}
	
	
}
