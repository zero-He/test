package cn.strong.leke.homework.model;

import cn.strong.leke.model.base.Subject;

public class SubjectTeacher extends Subject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2769876796545893023L;

	private Long teacherId;
	
	private String teacherName;

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
}
