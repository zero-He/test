package cn.strong.leke.diag.model.teachingMonitor.evaluate;

import java.util.Date;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-08-08 17:03:36
 */
public class EvaluateBean {

	private Long evaluateId;
	private int summary;
	private double professionalLevel;
	private double rhythmLevel;
	private double interactionLevel;
	private String content;
	private Long createdBy;
	private String createdName;
	private Date createdOn;
	private Date startTime;
	private Date endTime;
	private Long teacherId;
	private String teacherName;
	private Long courseSingleId;
	private Long subjectId;
	private String subjectName;
	private Long gradeId;
	private String gradeName;
	private Long classId;
	private String className;

	private double total;
	private Long totalLessonSum;
	private double goodPro;
	private double centerPro;
	private double poorPro;
	private double flowerNum;


	public Long getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(Long evaluateId) {
		this.evaluateId = evaluateId;
	}

	public int getSummary() {
		return summary;
	}

	public void setSummary(int summary) {
		this.summary = summary;
	}

	public double getProfessionalLevel() {
		return professionalLevel;
	}

	public void setProfessionalLevel(double professionalLevel) {
		this.professionalLevel = professionalLevel;
	}

	public double getRhythmLevel() {
		return rhythmLevel;
	}

	public void setRhythmLevel(double rhythmLevel) {
		this.rhythmLevel = rhythmLevel;
	}

	public double getInteractionLevel() {
		return interactionLevel;
	}

	public void setInteractionLevel(double interactionLevel) {
		this.interactionLevel = interactionLevel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public Long getCourseSingleId() {
		return courseSingleId;
	}

	public void setCourseSingleId(Long courseSingleId) {
		this.courseSingleId = courseSingleId;
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

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getTotalLessonSum() {
		return totalLessonSum;
	}

	public void setTotalLessonSum(Long totalLessonSum) {
		this.totalLessonSum = totalLessonSum;
	}

	public double getCenterPro() {
		return centerPro;
	}

	public void setCenterPro(double centerPro) {
		this.centerPro = centerPro;
	}

	public double getPoorPro() {
		return poorPro;
	}

	public void setPoorPro(double poorPro) {
		this.poorPro = poorPro;
	}

	public double getGoodPro() {
		return goodPro;
	}

	public void setGoodPro(double goodPro) {
		this.goodPro = goodPro;
	}

	public double getFlowerNum() {
		return flowerNum;
	}

	public void setFlowerNum(double flowerNum) {
		this.flowerNum = flowerNum;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
