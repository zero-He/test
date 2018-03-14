package cn.strong.leke.homework.model.query;

/**
 * 批量布置作业查询条件 
 * @author Zhang Fujun
 * @date 2016年5月19日
 */
public class AssignLogQueryDTO {

	//作业名称
	private String homeworkName;
	
	/**
	 * 布置作业操作人
	 */
	private Long userId;

	/**
	 * @return the homeworkName
	 */
	public String getHomeworkName() {
		return homeworkName;
	}

	/**
	 * @param homeworkName the homeworkName to set
	 */
	public void setHomeworkName(String homeworkName) {
		this.homeworkName = homeworkName;
	}

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
}
