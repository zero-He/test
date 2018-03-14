package cn.strong.leke.monitor.core.model;

public class IndiClassStatisticsDTO {
	//已结束课堂数
	private Long finishedClassNum;
	//上课课堂数
	private Long attendedClassNum;
	//应上课人次
	private Long mustClassTimes;
	//实上课人次
	private Long actualClassTimes;
	//应上课学生数
	private Long mustClassNum;
	//实上课学生数
	private Long actualClassNum;
	//上课率
	private String classRate;
	//到课率
	private String attendanceRate;
	
	public Long getFinishedClassNum() {
		return finishedClassNum;
	}
	public void setFinishedClassNum(Long finishedClassNum) {
		this.finishedClassNum = finishedClassNum;
	}
	public Long getAttendedClassNum() {
		return attendedClassNum;
	}
	public void setAttendedClassNum(Long attendedClassNum) {
		this.attendedClassNum = attendedClassNum;
	}
	public Long getMustClassTimes() {
		return mustClassTimes;
	}
	public void setMustClassTimes(Long mustClassTimes) {
		this.mustClassTimes = mustClassTimes;
	}
	public Long getActualClassTimes() {
		return actualClassTimes;
	}
	public void setActualClassTimes(Long actualClassTimes) {
		this.actualClassTimes = actualClassTimes;
	}
	public Long getMustClassNum() {
		return mustClassNum;
	}
	public void setMustClassNum(Long mustClassNum) {
		this.mustClassNum = mustClassNum;
	}
	public Long getActualClassNum() {
		return actualClassNum;
	}
	public void setActualClassNum(Long actualClassNum) {
		this.actualClassNum = actualClassNum;
	}
	
	public String getClassRate() {
		return classRate;
	}
	public void setClassRate(String classRate) {
		this.classRate = classRate;
	}
	public String getAttendanceRate() {
		return attendanceRate;
	}
	public void setAttendanceRate(String attendanceRate) {
		this.attendanceRate = attendanceRate;
	}
}
