package cn.strong.leke.homework.model;

import java.util.Date;
import java.util.List;

public class LayerAssign {

	private Long teacherId;
	private String teacherName;
	private Long schoolId;
	private Date startTime;
	private Date closeTime;
	private Date openAnswerTime;
	private Boolean isExam;
	private List<Section> sections;
	private AssignLogDO assignLog;
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

	public Boolean getIsExam() {
		return isExam;
	}

	public void setIsExam(Boolean isExam) {
		this.isExam = isExam;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public AssignLogDO getAssignLog() {
		return assignLog;
	}

	public void setAssignLog(AssignLogDO assignLog) {
		this.assignLog = assignLog;
	}

	public static class Section {
		private List<AssignResource> resources;
		private List<LayerClazz> classes;
		// 代人布置时才有，为空时取布置老师
		private Long teacherId; // 批改老师姓名
		private String teacherName; // 批改老师姓名

		public List<LayerClazz> getClasses() {
			return classes;
		}

		public void setClasses(List<LayerClazz> classes) {
			this.classes = classes;
		}

		public List<AssignResource> getResources() {
			return resources;
		}

		public void setResources(List<AssignResource> resources) {
			this.resources = resources;
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
	}

	public static class AssignResource {

		private Long resId;
		private String resName;
		private Integer resType;

		public AssignResource() {
		}

		public AssignResource(Long resId, Integer resType) {
			this.resId = resId;
			this.resType = resType;
		}

		public Long getResId() {
			return resId;
		}

		public void setResId(Long resId) {
			this.resId = resId;
		}

		public String getResName() {
			return resName;
		}

		public void setResName(String resName) {
			this.resName = resName;
		}

		public Integer getResType() {
			return resType;
		}

		public void setResType(Integer resType) {
			this.resType = resType;
		}
	}
}
