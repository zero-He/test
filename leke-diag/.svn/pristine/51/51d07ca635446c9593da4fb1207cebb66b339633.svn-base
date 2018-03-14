package cn.strong.leke.diag.model;

/**
 * 老师 作业勤奋报告 按课程、学科分组统计列表数据
 * @author Zhang Fujun
 * @date 2015年10月27日
 */
public class CourseSubjectHomeworkStatisticsStudentsDto {

	private Long classId;
	private String className;
	private Long subjectId;
	private String subjectName;
	private Float homeworkCount;
	private Float finishCount;
	private Float delayCount;
	private Float noFinishCount;
	private String studentName;
	private Long studentId;

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
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
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
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * @return the homeworkCount
	 */
	public Float getHomeworkCount() {
		return homeworkCount;
	}

	/**
	 * @param homeworkCount the homeworkCount to set
	 */
	public void setHomeworkCount(Float homeworkCount) {
		this.homeworkCount = homeworkCount;
	}

	/**
	 * @return the finishRate
	 */
	public Float getFinishRate() {
		return this.finishCount * 100 / this.homeworkCount;
	}

	/**
	 * @return the delayRate
	 */
	public Float getDelayRate() {
		return this.delayCount * 100 / this.homeworkCount;
	}

	/**
	 * @return the noFinishRate
	 */
	public Float getNoFinishRate() {
		return this.noFinishCount * 100 / this.homeworkCount;
	}

	/**
	 * @return the finishCount
	 */
	public Float getFinishCount() {
		return finishCount;
	}

	/**
	 * @param finishCount the finishCount to set
	 */
	public void setFinishCount(Float finishCount) {
		this.finishCount = finishCount;
	}

	/**
	 * @return the delayCount
	 */
	public Float getDelayCount() {
		return delayCount;
	}

	/**
	 * @param delayCount the delayCount to set
	 */
	public void setDelayCount(Float delayCount) {
		this.delayCount = delayCount;
	}

	/**
	 * @return the noFinishCount
	 */
	public Float getNoFinishCount() {
		return noFinishCount;
	}

	/**
	 * @param noFinishCount the noFinishCount to set
	 */
	public void setNoFinishCount(Float noFinishCount) {
		this.noFinishCount = noFinishCount;
	}

	/**
	 * @return the studentName
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
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
