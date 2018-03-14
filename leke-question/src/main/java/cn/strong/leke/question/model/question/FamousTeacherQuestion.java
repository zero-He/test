/**
 * 
 */
package cn.strong.leke.question.model.question;

import cn.strong.leke.repository.common.model.BaseFamousTeacherRecord;

/**
 * 名师习题
 * 
 * @author liulongbiao
 *
 */
public class FamousTeacherQuestion extends BaseFamousTeacherRecord {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3341768066747177977L;
	private Long famousTeacherQuestionId; // 关联主键
	private Long questionId; // 习题ID

	public Long getFamousTeacherQuestionId() {
		return famousTeacherQuestionId;
	}

	public void setFamousTeacherQuestionId(Long famousTeacherQuestionId) {
		this.famousTeacherQuestionId = famousTeacherQuestionId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

}
