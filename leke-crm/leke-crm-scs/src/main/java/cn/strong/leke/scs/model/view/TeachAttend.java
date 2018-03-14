package cn.strong.leke.scs.model.view;

import java.util.Date;

public class TeachAttend {

	private Long csAttendId;
	private String courseName;
	private String courseSetName;
	private Date planStartTime;
	private Date planEndTime;
	private Integer count;
	private Integer totalCount;
	private Integer realCount;

	public Long getCsAttendId() {
		return csAttendId;
	}

	public void setCsAttendId(Long csAttendId) {
		this.csAttendId = csAttendId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseSetName() {
		return courseSetName;
	}

	public void setCourseSetName(String courseSetName) {
		this.courseSetName = courseSetName;
	}

	public Date getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;
	}

	public Date getPlanEndTime() {
		return planEndTime;
	}

	public void setPlanEndTime(Date planEndTime) {
		this.planEndTime = planEndTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getRealCount() {
		return realCount;
	}

	public void setRealCount(Integer realCount) {
		this.realCount = realCount;
	}
}
