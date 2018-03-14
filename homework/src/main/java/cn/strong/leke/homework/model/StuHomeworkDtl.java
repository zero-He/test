package cn.strong.leke.homework.model;

import java.util.ArrayList;
import java.util.List;

public class StuHomeworkDtl {
	private Long userId;
	private List<String> paperIds;
	private List<Long> hwDtlIds;
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the paperIds
	 */
	public List<String> getPaperIds() {
		return paperIds;
	}
	/**
	 * @param paperIds the paperIds to set
	 */
	public void setPaperIds(List<String> paperIds) {
		this.paperIds = paperIds;
	}
	/**
	 * @return the hwDtlIds
	 */
	public List<Long> getHwDtlIds() {
		if(hwDtlIds == null){
			hwDtlIds = new ArrayList<Long>();
		}
		return hwDtlIds;
	}
	/**
	 * @param hwDtlIds the hwDtlIds to set
	 */
	public void setHwDtlIds(List<Long> hwDtlIds) {
		this.hwDtlIds = hwDtlIds;
	}
}
