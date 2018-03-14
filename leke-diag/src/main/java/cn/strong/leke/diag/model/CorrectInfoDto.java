package cn.strong.leke.diag.model;

public class CorrectInfoDto {

	private Long homeworkId;
	private String homeworkName;
	private Long classId;
	private Long classType;
	private String className;
	private Long finishNum;
	private Long correctNum;

	public Long getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
	}

	public String getHomeworkName() {
		return homeworkName;
	}

	public void setHomeworkName(String homeworkName) {
		this.homeworkName = homeworkName;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public Long getClassType() {
		return classType;
	}

	public void setClassType(Long classType) {
		this.classType = classType;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getFinishNum() {
		return finishNum;
	}

	public void setFinishNum(Long finishNum) {
		this.finishNum = finishNum;
	}

	public Long getCorrectNum() {
		return correctNum;
	}

	public void setCorrectNum(Long correctNum) {
		this.correctNum = correctNum;
	}

}
