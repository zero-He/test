package cn.strong.leke.monitor.core.model.lesson;


public class SchoolLessonDto extends WholeLessonBehaviorDto{

	private static final long serialVersionUID = 1L;
	private String type;
	private String schoolName;
	private int schoolStageId;
	private String schoolStageName;
	private String startTime;

	public int getSchoolStageId() {
		return schoolStageId;
	}
	public void setSchoolStageId(int schoolStageId) {
		this.schoolStageId = schoolStageId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolStageName() {
		return schoolStageName;
	}
	public void setSchoolStageName(String schoolStageName) {
		this.schoolStageName = schoolStageName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


}
