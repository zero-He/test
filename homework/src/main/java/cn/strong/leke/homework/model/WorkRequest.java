package cn.strong.leke.homework.model;

import java.util.List;

import cn.strong.leke.homework.util.HomeworkCst;
import cn.strong.leke.model.question.QuestionResult;

public class WorkRequest {

	private Long userId;
	private Long homeworkId;
	private Long homeworkDtlId;
	private Integer dataSource = HomeworkCst.HOMEWORK_DATA_SOURCE_WEBSITE;
	/**
	 * 是否是重批
	 */
	private Boolean againCorrect = Boolean.FALSE;
	private String commentary;
	private List<AnswerInfo> answerInfoList;
	private List<QuestionResult> questionResultList;
	private Boolean isAutoSave;
	// 作业用时
	private Integer usedTime;
	private Integer position;
	private Integer duration;
	private Boolean isPlayEnd;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
	}

	public Long getHomeworkDtlId() {
		return homeworkDtlId;
	}

	public void setHomeworkDtlId(Long homeworkDtlId) {
		this.homeworkDtlId = homeworkDtlId;
	}

	public Integer getDataSource() {
		return dataSource;
	}

	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}

	public Boolean getAgainCorrect() {
		return againCorrect;
	}

	public void setAgainCorrect(Boolean againCorrect) {
		this.againCorrect = againCorrect;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public List<AnswerInfo> getAnswerInfoList() {
		return answerInfoList;
	}

	public void setAnswerInfoList(List<AnswerInfo> answerInfoList) {
		this.answerInfoList = answerInfoList;
	}

	public List<QuestionResult> getQuestionResultList() {
		return questionResultList;
	}

	public void setQuestionResultList(List<QuestionResult> questionResultList) {
		this.questionResultList = questionResultList;
	}

	/**
	 * @return the usedTime
	 */
	public Integer getUsedTime() {
		return usedTime;
	}

	/**
	 * @param usedTime the usedTime to set
	 */
	public void setUsedTime(Integer usedTime) {
		this.usedTime = usedTime;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Boolean getIsPlayEnd() {
		return isPlayEnd;
	}

	public void setIsPlayEnd(Boolean isPlayEnd) {
		this.isPlayEnd = isPlayEnd;
	}

	/**
	 * @return the isAutoSave
	 */
	public Boolean getIsAutoSave() {
		return isAutoSave;
	}

	/**
	 * @param isAutoSave the isAutoSave to set
	 */
	public void setIsAutoSave(Boolean isAutoSave) {
		this.isAutoSave = isAutoSave;
	}
}
