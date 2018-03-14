package cn.strong.leke.homework.model;

import java.util.Date;
import java.util.List;

import cn.strong.leke.homework.model.LayerAssign.AssignResource;

/**
 * 批量布置作业DO对象
 * @author Zhang Fujun
 * @date 2016年5月19日
 */
public class AssignLogDTO {

	private Long teacherId;
	private String teacherName;
	private Long schoolId;
	private Date startTime;
	private Date closeTime;
	private Date openAnswerTime;
	private List<AssignResource> resources;
	private List<AssignLogTeaHw> assignLogTeaHw;

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

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
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

	public Date getOpenAnswerTime() {
		return openAnswerTime;
	}

	public void setOpenAnswerTime(Date openAnswerTime) {
		this.openAnswerTime = openAnswerTime;
	}

	public List<AssignResource> getResources() {
		return resources;
	}

	public void setResources(List<AssignResource> resources) {
		this.resources = resources;
	}

	public List<AssignLogTeaHw> getAssignLogTeaHw() {
		return assignLogTeaHw;
	}

	public void setAssignLogTeaHw(List<AssignLogTeaHw> assignLogTeaHw) {
		this.assignLogTeaHw = assignLogTeaHw;
	}
}
