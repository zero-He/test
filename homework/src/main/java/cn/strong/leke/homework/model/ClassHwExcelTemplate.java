package cn.strong.leke.homework.model;

public class ClassHwExcelTemplate {

	/**
	 * 老师
	 */
	private String teacherName;
	/**
	 * 学科
	 */
	private String subjectName;
	
	private String homeworkName;
	/**
	 * 班级名称
	 */
	private String className;
	/**
	 * 作业布置/截止时间
	 */
	private String startFinishTime;
	
	/**
	 * 完成情况：1/3
	 */
	private String finishInfo;
	/**
	 * 批改情况：1/3
	 */
	private String correctInfo;
	/**
	 * 平均分
	 */
	private String avgScore;
	
	/**
	 * @return the teacherName
	 */
	public String getTeacherName() {
		return teacherName;
	}
	/**
	 * @param teacherName the teacherName to set
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
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
	 * @return the startFinishTime
	 */
	public String getStartFinishTime() {
		return startFinishTime;
	}
	/**
	 * @param startFinishTime the startFinishTime to set
	 */
	public void setStartFinishTime(String startFinishTime) {
		this.startFinishTime = startFinishTime;
	}
	/**
	 * @return the finishInfo
	 */
	public String getFinishInfo() {
		return finishInfo;
	}
	/**
	 * @param finishInfo the finishInfo to set
	 */
	public void setFinishInfo(String finishInfo) {
		this.finishInfo = finishInfo;
	}
	/**
	 * @return the correctInfo
	 */
	public String getCorrectInfo() {
		return correctInfo;
	}
	/**
	 * @param correctInfo the correctInfo to set
	 */
	public void setCorrectInfo(String correctInfo) {
		this.correctInfo = correctInfo;
	}
	/**
	 * @return the avgScore
	 */
	public String getAvgScore() {
		return avgScore;
	}
	/**
	 * @param avgScore the avgScore to set
	 */
	public void setAvgScore(String avgScore) {
		this.avgScore = avgScore;
	}
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
}
