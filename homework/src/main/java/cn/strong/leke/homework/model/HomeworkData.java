package cn.strong.leke.homework.model;

import java.util.List;

import cn.strong.leke.homework.model.result.StudentResult;

/**
 * 实体对象：随堂作业接口数据表
 */
public class HomeworkData {

	// 数据标识
	private Long dataId;
	// 老师作业标识
	private Long homeworkId;
	// 是否为只做错题
	private Boolean isRedo;
	// 写入作业ID
	private Long targetHomeworkId;
	// 作业版本号
	private Long version;
	// 作业数据
	private String answerData;
	// 是否删除
	private Boolean isDeleted;
	/**
	 * 学生数据，包括客观题批改结果，也可能包括主观题批改结果
	 */
	private List<StudentResult> studentResults;

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public Long getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
	}

	public Boolean getIsRedo() {
		return isRedo;
	}

	public void setIsRedo(Boolean isRedo) {
		this.isRedo = isRedo;
	}

	public Long getTargetHomeworkId() {
		return targetHomeworkId;
	}

	public void setTargetHomeworkId(Long targetHomeworkId) {
		this.targetHomeworkId = targetHomeworkId;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getAnswerData() {
		return answerData;
	}

	public void setAnswerData(String answerData) {
		this.answerData = answerData;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<StudentResult> getStudentResults() {
		return studentResults;
	}

	public void setStudentResults(List<StudentResult> studentResults) {
		this.studentResults = studentResults;
	}
}
