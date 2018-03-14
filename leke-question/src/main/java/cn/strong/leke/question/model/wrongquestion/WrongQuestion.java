package cn.strong.leke.question.model.wrongquestion;

import java.math.BigDecimal;
import java.util.Date;

import cn.strong.leke.model.BaseModel;

/**
 * 错题本题目
 * @author Zhang Fujun
 * @date 2017年1月11日
 */
public class WrongQuestion extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5304280625415324830L;
	
	private Long classId;
	private Long subjectId;
	private Long questionId;
	private Long paperId;
	private Long questionTypeId;
	private String source;
	private BigDecimal rate;
	private Long errorTotal;
	private String sourceName;
	private Long homeworkId;
	private Date assignDate;
	private Long userId;
	private Long schoolId;
	/**
	 * 错题学生总人数
	 */
	private Integer wrongStuTotal;

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

	public Long getPaperId() {
		return paperId;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}

	public Long getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(Long questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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
	 * @return the errorTotal
	 */
	public Long getErrorTotal() {
		return errorTotal;
	}

	/**
	 * @param errorTotal the errorTotal to set
	 */
	public void setErrorTotal(Long errorTotal) {
		this.errorTotal = errorTotal;
	}

	/**
	 * @return the sourceName
	 */
	public String getSourceName() {
		return sourceName;
	}

	/**
	 * @param sourceName the sourceName to set
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	/**
	 * @return the assignDate
	 */
	public Date getAssignDate() {
		return assignDate;
	}

	/**
	 * @param assignDate the assignDate to set
	 */
	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
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
	
	public String getAssignDateStr() {
		return BaseModel.formatDate(this.assignDate, BaseModel.FMT_ISO_DATETIME);
	}

	public String getModifiedOnStr() {
		return BaseModel.formatDate(this.getModifiedOn());
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
	 * @return the wrongStuTotal
	 */
	public Integer getWrongStuTotal() {
		return wrongStuTotal;
	}

	/**
	 * @param wrongStuTotal the wrongStuTotal to set
	 */
	public void setWrongStuTotal(Integer wrongStuTotal) {
		this.wrongStuTotal = wrongStuTotal;
	}


}
