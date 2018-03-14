package cn.strong.leke.homework.model.activity;

import java.math.BigDecimal;

/**
 * 自主练习
 * @author Zhang Fujun
 * @date 2017年9月21日
 */
public class SimpleExercise {
	private String loginName;
	private String userName;
	private String exerciseId;
	private Integer rightNum;
	private Integer totalNum;
	private BigDecimal accuracy;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the exerciseId
	 */
	public String getExerciseId() {
		return exerciseId;
	}

	/**
	 * @param exerciseId the exerciseId to set
	 */
	public void setExerciseId(String exerciseId) {
		this.exerciseId = exerciseId;
	}

	/**
	 * @return the rightNum
	 */
	public Integer getRightNum() {
		return rightNum;
	}

	/**
	 * @param rightNum the rightNum to set
	 */
	public void setRightNum(Integer rightNum) {
		this.rightNum = rightNum;
	}

	/**
	 * @return the totalNum
	 */
	public Integer getTotalNum() {
		return totalNum;
	}

	/**
	 * @param totalNum the totalNum to set
	 */
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * @return the accuracy
	 */
	public BigDecimal getAccuracy() {
		return accuracy;
	}

	/**
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(BigDecimal accuracy) {
		this.accuracy = accuracy;
	}

}
