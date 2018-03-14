package cn.strong.leke.diag.model.report;

public class ResReviewState {

	private Long studentId;
	private Long courseSingleId;
	private Boolean state;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getCourseSingleId() {
		return courseSingleId;
	}

	public void setCourseSingleId(Long courseSingleId) {
		this.courseSingleId = courseSingleId;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}
}
