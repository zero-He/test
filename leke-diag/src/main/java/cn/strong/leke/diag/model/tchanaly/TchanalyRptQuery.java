package cn.strong.leke.diag.model.tchanaly;

public class TchanalyRptQuery {

	private Long teacherId;
	private Integer cycleId;
	private String klassId; // classId-subjectId
	private Long classId;
	private Long subjectId;
	private String viewName;

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getCycleId() {
		return cycleId;
	}

	public void setCycleId(Integer cycleId) {
		this.cycleId = cycleId;
	}

	public String getKlassId() {
		return klassId;
	}

	public void setKlassId(String klassId) {
		this.klassId = klassId;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
}
