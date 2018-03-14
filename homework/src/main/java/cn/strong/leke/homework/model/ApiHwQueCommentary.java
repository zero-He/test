package cn.strong.leke.homework.model;

import java.util.Set;

public class ApiHwQueCommentary {

	private Long homeworkId;
	private Long questionId;
	private Long resId;
	private String resName;
	//推送方式 ：1=个人， 2= 错题学生推送 ，3=全局
	private Integer type;
	//包含的 用户
	private Set<Long> includeUserIds;
	//排除的  用户
	private Long excludeUserId;
	//学生id
	private Long studentId;

	/**
	 * @return the homeworkId
	 */
	public Long getHomeworkId() {
		return homeworkId;
	}

	/**
	 * @param homeworkId the homeworkId to set
	 */
	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
	}

	/**
	 * @return the questionId
	 */
	public Long getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the resId
	 */
	public Long getResId() {
		return resId;
	}

	/**
	 * @param resId the resId to set
	 */
	public void setResId(Long resId) {
		this.resId = resId;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return the includeUserIds
	 */
	public Set<Long> getIncludeUserIds() {
		return includeUserIds;
	}

	/**
	 * @param includeUserIds the includeUserIds to set
	 */
	public void setIncludeUserIds(Set<Long> includeUserIds) {
		this.includeUserIds = includeUserIds;
	}

	/**
	 * @return the excludeUserId
	 */
	public Long getExcludeUserId() {
		return excludeUserId;
	}

	/**
	 * @param excludeUserId the excludeUserId to set
	 */
	public void setExcludeUserId(Long excludeUserId) {
		this.excludeUserId = excludeUserId;
	}

	/**
	 * @return the resName
	 */
	public String getResName() {
		return resName;
	}

	/**
	 * @param resName the resName to set
	 */
	public void setResName(String resName) {
		this.resName = resName;
	}

	/**
	 * @return the studentId
	 */
	public Long getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

}
