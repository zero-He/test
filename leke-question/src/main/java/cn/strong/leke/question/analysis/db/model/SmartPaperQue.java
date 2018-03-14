/**
 * 
 */
package cn.strong.leke.question.analysis.db.model;

/**
 * 智能组卷习题基本信息
 * 
 * @author liulongbiao
 *
 */
public class SmartPaperQue {
	private Long questionId; // 题目ID
	private Long questionTypeId; // 类型ID
	private Double difficulty; // 题目难度

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(Long questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public Double getDifficulty() {
		return difficulty == null ? 0.5 : difficulty;
	}

	public void setDifficulty(Double difficulty) {
		this.difficulty = difficulty;
	}

}
