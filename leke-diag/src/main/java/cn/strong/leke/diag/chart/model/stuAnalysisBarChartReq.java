package cn.strong.leke.diag.chart.model;

import java.util.Date;

import cn.strong.leke.core.business.chart.ChartReq;

public class stuAnalysisBarChartReq extends ChartReq {
	private Long calssId;
	private Long studentId;
	private Long schoolId;
	private Date startTime;
	private Date endTime;

	/**
	 * @return the calssId
	 */
	public Long getCalssId() {
		return calssId;
	}

	/**
	 * @param calssId the calssId to set
	 */
	public void setCalssId(Long calssId) {
		this.calssId = calssId;
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

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
