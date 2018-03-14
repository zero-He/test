package cn.strong.leke.homework.model.activities;

import java.util.List;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * 个人活动档案
 * @author Administrator
 *
 */
public class UserActivitiesRecord {
	
	@_id
	@ObjectId
	private String id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 姓名
	 */
	private String userName;
	/**
	 * 任务链
	 */
	private List<MissionStatus> missionChain;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<MissionStatus> getMissionChain() {
		return missionChain;
	}

	public void setMissionChain(List<MissionStatus> missionChain) {
		this.missionChain = missionChain;
	}

}