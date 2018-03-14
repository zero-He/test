package cn.strong.leke.homework.model;

import java.math.BigDecimal;

import cn.strong.leke.model.BaseModel;
import cn.strong.leke.model.question.QuestionResult;

/**
 * 实体对象：练习题表
 */
public class ExerciseItem extends BaseModel {

	private static final long serialVersionUID = 7708069589853900474L;

	// 练习题标识
	private Long itemId;
	// 父练习题标识
	private Long parentId;
	// 练习标识
	private Long exerciseId;
	// 题目标识
	private Long questionId;
	// 答题内容
	private String answerContent;
	// 批改内容
	private String correctContent;
	// 是否正确
	private Boolean isCorrect;
	// 正确率
	private BigDecimal correctRate;
	// 批改信息（非持久化）
	private QuestionResult questionResult;
	
	/**
	 * 练习题标识
	 */
	public Long getItemId() {
		return this.itemId;
	}

	/**
	 * 练习题标识
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	/**
	 * 父练习题标识
	 */
	public Long getParentId() {
		return this.parentId;
	}

	/**
	 * 父练习题标识
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * 练习标识
	 */
	public Long getExerciseId() {
		return this.exerciseId;
	}

	/**
	 * 练习标识
	 */
	public void setExerciseId(Long exerciseId) {
		this.exerciseId = exerciseId;
	}
	
	/**
	 * 题目标识
	 */
	public Long getQuestionId() {
		return this.questionId;
	}

	/**
	 * 题目标识
	 */
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	
	/**
	 * 答题内容
	 */
	public String getAnswerContent() {
		return this.answerContent;
	}

	/**
	 * 答题内容
	 */
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
	
	/**
	 * 批改内容
	 */
	public String getCorrectContent() {
		return correctContent;
	}

	/**
	 * 批改内容
	 */
	public void setCorrectContent(String correctContent) {
		this.correctContent = correctContent;
	}

	/**
	 * 是否正确
	 */
	public Boolean getIsCorrect() {
		return this.isCorrect;
	}

	/**
	 * 是否正确
	 */
	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	/**
	 * 正确率
	 */
	public BigDecimal getCorrectRate() {
		return this.correctRate;
	}

	/**
	 * 正确率
	 */
	public void setCorrectRate(BigDecimal correctRate) {
		this.correctRate = correctRate;
	}

	public QuestionResult getQuestionResult() {
		return questionResult;
	}

	public void setQuestionResult(QuestionResult questionResult) {
		this.questionResult = questionResult;
	}
}
