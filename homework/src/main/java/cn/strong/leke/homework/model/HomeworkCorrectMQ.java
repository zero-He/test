package cn.strong.leke.homework.model;

import java.util.List;

public class HomeworkCorrectMQ implements java.io.Serializable {

	private static final long serialVersionUID = 1601751537544145532L;

	private Long homeworkDtlId;
	
	/**
	 * 是否重批
	 */
	private Boolean isAgain;
	
	/**
	 * 新增了要订正的题目
	 */
	private List<Long> newWrongQuestions;

	public Long getHomeworkDtlId() {
		return homeworkDtlId;
	}

	public void setHomeworkDtlId(Long homeworkDtlId) {
		this.homeworkDtlId = homeworkDtlId;
	}

	/**
	 * @return the isAgain
	 */
	public Boolean getIsAgain() {
		return isAgain;
	}

	/**
	 * @param isAgain the isAgain to set
	 */
	public void setIsAgain(Boolean isAgain) {
		this.isAgain = isAgain;
	}

	/**
	 * @return the newWrongQuestions
	 */
	public List<Long> getNewWrongQuestions() {
		return newWrongQuestions;
	}

	/**
	 * @param newWrongQuestions the newWrongQuestions to set
	 */
	public void setNewWrongQuestions(List<Long> newWrongQuestions) {
		this.newWrongQuestions = newWrongQuestions;
	}

}
