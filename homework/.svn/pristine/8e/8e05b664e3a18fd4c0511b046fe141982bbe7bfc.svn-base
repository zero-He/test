package cn.strong.leke.homework.model;

import java.util.List;

/**
 * 描述: 答案信息对象，对应一个题目有答案信息。<br>
 * 注意：如果包含子题，请查看<code>subs</code>。
 * @author andy
 * @since v1.0.0
 */
public class AnswerInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1010146225690410253L;

	// 题目标识
	private Long questionId;
	// 答案内容
	private String answerContent;
	// 子题答案列表
	private List<AnswerInfo> subs;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public List<AnswerInfo> getSubs() {
		return subs;
	}

	public void setSubs(List<AnswerInfo> subs) {
		this.subs = subs;
	}
}
