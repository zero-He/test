package cn.strong.leke.question.model.question;

import java.io.Serializable;

/**
 *
 * 描述: 习题待审核
 *
 * @author raolei
 * @created 2016年12月4日 上午9:54:58
 * @since v1.0.0
 */
public class QuestionCheckPending implements Serializable {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = 5862903952584596158L;

	private Long questionCheckPendingId;
	private Long questionId;
	private Long schoolStageId; // 学段ID
	private Long subjectId; // 科目ID
	private Boolean isDeleted;

	public Long getQuestionCheckPendingId() {
		return questionCheckPendingId;
	}
	public void setQuestionCheckPendingId(Long questionCheckPendingId) {
		this.questionCheckPendingId = questionCheckPendingId;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getSchoolStageId() {
		return schoolStageId;
	}

	public void setSchoolStageId(Long schoolStageId) {
		this.schoolStageId = schoolStageId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
