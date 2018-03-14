package cn.strong.leke.diag.model.studentMonitor;

import java.io.Serializable;

import cn.strong.leke.diag.model.teachingMonitor.CommProp;

public class StudentAttend extends CommProp implements Serializable {

	private static final long serialVersionUID = 5846746747509387947L;
	
	public static final String LESSON = "lesson";
	public static final String LESSON_DTL = "lesson_dtl";
	public static final String STUDENT = "student";
	public static final String TEACHER = "teacher";
	public static final String CLASS = "class";
	public static final String CLASS_DTL = "class_dtl";
	public static final String SUBJECT = "subject";
	public static final String DAY = "day";

	//单科考勤ID
	private Long csAttenId;
	
	//上课时间
	private String startTime;
	
	//考勤状态
	private String attendState;
	
	private String schoolName;
	
	//课堂时长
	private Integer duration;
	
	//实到人数
	private Integer realCount;
	
	//应到人数
	private Integer totalCount;
	
	//迟到人次
	private Integer lateCount;
	
	//早退人次
	private Integer earlyCount;
	
	//迟到且早退人次
	private Integer lateAndEarlyCount;
	
	//缺勤人次
	private Integer absentCount;
	
	//全勤人次
	private Integer normalCount;
	
	//到课堂数、出勤课堂数
	private Integer attendCount;
	
	//课堂总数
	private Integer lessonCount;
	
	//班级学生人数
	private Integer studentCount;
	
	//老师人数
	private Integer teacherCount;
	
	//实点名次数
	private Integer calledNum;
	
	//应到次数
	private Integer totalCalled;
	
	private String attendCountRate = DEFAULT_RATE;
	
	private String normalCountRate = DEFAULT_RATE;
	
	private String lateCountRate = DEFAULT_RATE;
	
	private String earlyCountRate = DEFAULT_RATE;
	
	private String lateAndEarlyCountRate = DEFAULT_RATE;
	
	private String absentCountRate = DEFAULT_RATE;

	public Long getCsAttenId() {
		return csAttenId;
	}

	public void setCsAttenId(Long csAttenId) {
		this.csAttenId = csAttenId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getRealCount() {
		return realCount;
	}

	public void setRealCount(Integer realCount) {
		this.realCount = realCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getLateCount() {
		return lateCount;
	}

	public void setLateCount(Integer lateCount) {
		this.lateCount = lateCount;
	}

	public Integer getEarlyCount() {
		return earlyCount;
	}

	public void setEarlyCount(Integer earlyCount) {
		this.earlyCount = earlyCount;
	}

	public Integer getLateAndEarlyCount() {
		return lateAndEarlyCount;
	}

	public void setLateAndEarlyCount(Integer lateAndEarlyCount) {
		this.lateAndEarlyCount = lateAndEarlyCount;
	}

	public Integer getAbsentCount() {
		return absentCount;
	}

	public void setAbsentCount(Integer absentCount) {
		this.absentCount = absentCount;
	}

	public Integer getNormalCount() {
		return normalCount;
	}

	public void setNormalCount(Integer normalCount) {
		this.normalCount = normalCount;
	}

	public Integer getAttendCount() {
		return attendCount;
	}

	public void setAttendCount(Integer attendCount) {
		this.attendCount = attendCount;
	}

	public String getAttendCountRate() {
		return attendCountRate;
	}

	public void setAttendCountRate(String attendCountRate) {
		this.attendCountRate = attendCountRate;
	}

	public String getNormalCountRate() {
		return normalCountRate;
	}

	public void setNormalCountRate(String normalCountRate) {
		this.normalCountRate = normalCountRate;
	}

	public String getLateCountRate() {
		return lateCountRate;
	}

	public void setLateCountRate(String lateCountRate) {
		this.lateCountRate = lateCountRate;
	}

	public String getEarlyCountRate() {
		return earlyCountRate;
	}

	public void setEarlyCountRate(String earlyCountRate) {
		this.earlyCountRate = earlyCountRate;
	}

	public String getLateAndEarlyCountRate() {
		return lateAndEarlyCountRate;
	}

	public void setLateAndEarlyCountRate(String lateAndEarlyCountRate) {
		this.lateAndEarlyCountRate = lateAndEarlyCountRate;
	}

	public String getAbsentCountRate() {
		return absentCountRate;
	}

	public void setAbsentCountRate(String absentCountRate) {
		this.absentCountRate = absentCountRate;
	}

	public Integer getLessonCount() {
		return lessonCount;
	}

	public void setLessonCount(Integer lessonCount) {
		this.lessonCount = lessonCount;
	}

	public Integer getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(Integer studentCount) {
		this.studentCount = studentCount;
	}

	public String getAttendState() {
		return attendState;
	}

	public void setAttendState(String attendState) {
		this.attendState = attendState;
	}

	public Integer getCalledNum() {
		return calledNum;
	}

	public void setCalledNum(Integer calledNum) {
		this.calledNum = calledNum;
	}

	public Integer getTotalCalled() {
		return totalCalled;
	}

	public void setTotalCalled(Integer totalCalled) {
		this.totalCalled = totalCalled;
	}

	public Integer getTeacherCount() {
		return teacherCount;
	}

	public void setTeacherCount(Integer teacherCount) {
		this.teacherCount = teacherCount;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
}
