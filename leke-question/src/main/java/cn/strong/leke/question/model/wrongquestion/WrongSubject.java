package cn.strong.leke.question.model.wrongquestion;

import java.math.BigDecimal;

import cn.strong.leke.model.BaseModel;

/**
 * 老师班级学科单题得分率设置
 * @author Zhang Fujun
 * @date 2017年1月11日
 */
public class WrongSubject extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2856725574618551497L;
	
	
	private Long classId;
	private Long subjectId;
	private BigDecimal rate;
	private Long userId;
	private Long schoolId;
	/**
	 * @return the classId
	 */
	public Long getClassId() {
		return classId;
	}
	/**
	 * @param classId the classId to set
	 */
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	/**
	 * @return the subjectId
	 */
	public Long getSubjectId() {
		return subjectId;
	}
	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * @return the rate
	 */
	public BigDecimal getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(BigDecimal rate) {
		this.rate = rate;
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
	/**
	 * @return the schoolId
	 */
	public Long getSchoolId() {
		return schoolId;
	}
	/**
	 * @param schoolId the schoolId to set
	 */
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	
	

}
