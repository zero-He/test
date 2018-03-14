package cn.strong.leke.diag.model.teachingMonitor.attendance;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-18 17:03:36
 */
public class AttendanceDetailBean extends AttendanceBean {

	private Long teacherId;
	private String teacherName;
	private Long subjectId;
	private String subjectName;

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


}
