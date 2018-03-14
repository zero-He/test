package cn.strong.leke.diag.dao.lesson.model;

import java.util.Date;

public class EvaluateDTO {

	private Long evaluateId;
	private Integer summary;
	private Integer professionalLevel;
	private Integer rhythmLevel;
	private Integer interactionLevel;
	private String content;
	private Boolean isAno;
	private Boolean isDefault;
	private Long createdBy;
	private String createdName;
	private Date createdOn;

	public Long getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(Long evaluateId) {
		this.evaluateId = evaluateId;
	}

	public Integer getSummary() {
		return summary;
	}

	public void setSummary(Integer summary) {
		this.summary = summary;
	}

	public Integer getProfessionalLevel() {
		return professionalLevel;
	}

	public void setProfessionalLevel(Integer professionalLevel) {
		this.professionalLevel = professionalLevel;
	}

	public Integer getRhythmLevel() {
		return rhythmLevel;
	}

	public void setRhythmLevel(Integer rhythmLevel) {
		this.rhythmLevel = rhythmLevel;
	}

	public Integer getInteractionLevel() {
		return interactionLevel;
	}

	public void setInteractionLevel(Integer interactionLevel) {
		this.interactionLevel = interactionLevel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsAno() {
		return isAno;
	}

	public void setIsAno(Boolean isAno) {
		this.isAno = isAno;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedName() {
		return createdName;
	}

	public void setCreatedName(String createdName) {
		this.createdName = createdName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}
