package cn.strong.leke.homework.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 作业明细信息。
 * @author  andy
 * @since   v1.0.0
 */
public class HomeworkDtlInfo implements java.io.Serializable {

	private static final long serialVersionUID = -4320868905419124258L;

	private Long homeworkId;
	private Long homeworkDtlId;
	private Long teacherId;
	private Long studentId;
	private String studentName;
	private Long schoolId;
	private String homeworkName;
	private Integer homeworkType;
	private Long subjectId;
	private String subjectName;
	private Integer usePhase;
	private Integer comboPhase;
	private Integer resType;
	private String rawType;
	private Boolean isExam;
	/**
	 * 资源ID
	 */
	private Long paperId;
	/**
	 * 非资源库 的试卷Id
	 * mongo 中的试卷id
	 */
	private String hwPaperId;
	private Boolean subjective;
	private Long courseSingleId;
	private Date closeTime;
	private Boolean isSelfCheck;
	private String soundFile;
	private Date startTime; // 开始做作业时间
	private Date submitTime;
	private Integer usedTime;
	private Integer submitStatus;
	private Integer submitSource;
	private Integer correctCount;
	private Date correctTime;
	private Integer correctSource;
	private Long correctUserId;
	private BigDecimal score;
	private BigDecimal scoreRate;
	private Integer bugFixStage;
	private Integer bugFixCount;
	private Date bugFixTime;
	private Integer bugFixSource;
	private Date reviewTime;
	private Integer reviewSource;
	private Long modifiedBy;
	private Date modifiedOn;
	private Integer version;
	private Long errorTotal;
	private Long parentId;

	public Long getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
	}

	public Long getHomeworkDtlId() {
		return homeworkDtlId;
	}

	public void setHomeworkDtlId(Long homeworkDtlId) {
		this.homeworkDtlId = homeworkDtlId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getSubmitStatus() {
		return submitStatus;
	}

	public void setSubmitStatus(Integer submitStatus) {
		this.submitStatus = submitStatus;
	}

	public String getHomeworkName() {
		return homeworkName;
	}

	public void setHomeworkName(String homeworkName) {
		this.homeworkName = homeworkName;
	}

	public Integer getHomeworkType() {
		return homeworkType;
	}

	public void setHomeworkType(Integer homeworkType) {
		this.homeworkType = homeworkType;
	}

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

	public Long getPaperId() {
		return paperId;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}

	public Integer getUsePhase() {
		return usePhase;
	}

	public void setUsePhase(Integer usePhase) {
		this.usePhase = usePhase;
	}

	public Integer getResType() {
		return resType;
	}

	public void setResType(Integer resType) {
		this.resType = resType;
	}

	public String getRawType() {
		return rawType;
	}

	public void setRawType(String rawType) {
		this.rawType = rawType;
	}

	public Boolean getSubjective() {
		return subjective;
	}

	public void setSubjective(Boolean subjective) {
		this.subjective = subjective;
	}

	public Long getCourseSingleId() {
		return courseSingleId;
	}

	public void setCourseSingleId(Long courseSingleId) {
		this.courseSingleId = courseSingleId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Boolean getIsSelfCheck() {
		return isSelfCheck;
	}

	public void setIsSelfCheck(Boolean isSelfCheck) {
		this.isSelfCheck = isSelfCheck;
	}

	public String getSoundFile() {
		return soundFile;
	}

	public void setSoundFile(String soundFile) {
		this.soundFile = soundFile;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Integer getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Integer usedTime) {
		this.usedTime = usedTime;
	}

	public Integer getSubmitSource() {
		return submitSource;
	}

	public void setSubmitSource(Integer submitSource) {
		this.submitSource = submitSource;
	}

	public Integer getCorrectCount() {
		return correctCount;
	}

	public void setCorrectCount(Integer correctCount) {
		this.correctCount = correctCount;
	}

	public Date getCorrectTime() {
		return correctTime;
	}

	public void setCorrectTime(Date correctTime) {
		this.correctTime = correctTime;
	}

	public Integer getCorrectSource() {
		return correctSource;
	}

	public void setCorrectSource(Integer correctSource) {
		this.correctSource = correctSource;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public BigDecimal getScoreRate() {
		return scoreRate;
	}

	public void setScoreRate(BigDecimal scoreRate) {
		this.scoreRate = scoreRate;
	}

	public Integer getBugFixStage() {
		return bugFixStage;
	}

	public void setBugFixStage(Integer bugFixStage) {
		this.bugFixStage = bugFixStage;
	}

	public Integer getBugFixCount() {
		return bugFixCount;
	}

	public void setBugFixCount(Integer bugFixCount) {
		this.bugFixCount = bugFixCount;
	}

	public Date getBugFixTime() {
		return bugFixTime;
	}

	public void setBugFixTime(Date bugFixTime) {
		this.bugFixTime = bugFixTime;
	}

	public Integer getBugFixSource() {
		return bugFixSource;
	}

	public void setBugFixSource(Integer bugFixSource) {
		this.bugFixSource = bugFixSource;
	}

	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	public Integer getReviewSource() {
		return reviewSource;
	}

	public void setReviewSource(Integer reviewSource) {
		this.reviewSource = reviewSource;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @return the hwPaperId
	 */
	public String getHwPaperId() {
		return hwPaperId;
	}

	/**
	 * @param hwPaperId the hwPaperId to set
	 */
	public void setHwPaperId(String hwPaperId) {
		this.hwPaperId = hwPaperId;
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
	 * @return the correctUserId
	 */
	public Long getCorrectUserId() {
		return correctUserId;
	}

	/**
	 * @param correctUserId the correctUserId to set
	 */
	public void setCorrectUserId(Long correctUserId) {
		this.correctUserId = correctUserId;
	}

	/**
	 * @return the isExam
	 */
	public Boolean getIsExam() {
		return isExam;
	}

	/**
	 * @param isExam the isExam to set
	 */
	public void setIsExam(Boolean isExam) {
		this.isExam = isExam;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the comboPhase
	 */
	public Integer getComboPhase() {
		return comboPhase;
	}

	/**
	 * @param comboPhase the comboPhase to set
	 */
	public void setComboPhase(Integer comboPhase) {
		this.comboPhase = comboPhase;
	}
}
