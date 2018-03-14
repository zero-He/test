package cn.strong.leke.homework.model.mongo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 作业题目批注
 * @author Zhang Fujun
 * @date 2017年12月4日
 */
public class HwQueCommentary implements Serializable {

	public HwQueCommentary() {
		this.includeUserIds = new HashSet<>();
		this.excludeUserIds = new HashSet<>();
	}

	/**
	 * 推送方式 ：1=个人，
	 */
	@JsonIgnore
	public static Integer WAY_PERSONAL = 1;
	/**
	 * 推送方式 ： 2= 错题学生推送 
	 */
	@JsonIgnore
	public static Integer WAY_PART_WRONG = 2;
	/**
	 * 推送方式 ：3=全局
	 */
	@JsonIgnore
	public static Integer WAY_WHOLE = 3;
	/**
	 * 
	 */
	private static final long serialVersionUID = -56712123089116843L;

	@_id
	@ObjectId
	private String id;
	//推送方式 ：1=个人， 2= 错题学生推送 ，3=全局
	private Integer type;
	// 推送方式是个人时的用户id，其他方式该字段无效
	private Set<Long> includeUserIds;
	//不推送的用户
	private Set<Long> excludeUserIds;
	private Long homeworkId;
	private Long questionId;
	//微课名称
	private String resName;
	//推送资源id
	private Long resId;
	private Boolean isDeleted;
	private Long createdBy;
	private Date createdOn;
	private Long modifiedBy;
	private Date modifiedOn;

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
	 * @return the excludeUserIds
	 */
	public Set<Long> getExcludeUserIds() {
		return excludeUserIds;
	}

	/**
	 * @param excludeUserIds the excludeUserIds to set
	 */
	public void setExcludeUserIds(Set<Long> excludeUserIds) {
		this.excludeUserIds = excludeUserIds;
	}

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
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the modifiedBy
	 */
	public Long getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the modifiedOn
	 */
	public Date getModifiedOn() {
		return modifiedOn;
	}

	/**
	 * @param modifiedOn the modifiedOn to set
	 */
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}
