package cn.strong.leke.homework.model.query;

import java.util.List;

import cn.strong.leke.model.question.QuestionResult;

public class ApiParamForReviewWorkSave {
	
	private String commentary;
	
	private Long homeworkId;
	
	private Long homeworkDtlId;
	
	private List<QuestionResult> correctJson;

	/**
	 * @return the commentary
	 */
	public String getCommentary() {
		return commentary;
	}

	/**
	 * @param commentary the commentary to set
	 */
	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	/**
	 * @return the homeworkId
	 */
	public Long getHomeworkId() {
		return homeworkId;
	}

	/**
	 * @param homeworkId the homeworkId to set
	 */
	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
	}

	/**
	 * @return the homeworkDtlId
	 */
	public Long getHomeworkDtlId() {
		return homeworkDtlId;
	}

	/**
	 * @param homeworkDtlId the homeworkDtlId to set
	 */
	public void setHomeworkDtlId(Long homeworkDtlId) {
		this.homeworkDtlId = homeworkDtlId;
	}

	/**
	 * @return the correctJson
	 */
	public List<QuestionResult> getCorrectJson() {
		return correctJson;
	}

	/**
	 * @param correctJson the correctJson to set
	 */
	public void setCorrectJson(List<QuestionResult> correctJson) {
		this.correctJson = correctJson;
	}

}
