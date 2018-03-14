package cn.strong.leke.homework.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StuSubjRes {

	private Long subjectId;
	private String subjectName;
	private Integer num;
	private Date lastTime;
	
	/**
	 * 待订正 
	 */
	private Integer bugFixNum;
	/**
	 * 待完成
	 */
	private Integer submitNum;

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * @return the bugFixNum
	 */
	public Integer getBugFixNum() {
		return bugFixNum;
	}

	/**
	 * @param bugFixNum the bugFixNum to set
	 */
	public void setBugFixNum(Integer bugFixNum) {
		this.bugFixNum = bugFixNum;
	}

	/**
	 * @return the submitNum
	 */
	public Integer getSubmitNum() {
		return submitNum;
	}

	/**
	 * @param submitNum the submitNum to set
	 */
	public void setSubmitNum(Integer submitNum) {
		this.submitNum = submitNum;
	}
}
