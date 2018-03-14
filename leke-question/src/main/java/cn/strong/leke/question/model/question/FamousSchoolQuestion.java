/**
 * 
 */
package cn.strong.leke.question.model.question;

import cn.strong.leke.repository.common.model.BaseFamousSchoolRecord;

/**
 * 名师习题关联
 * 
 * @author liulongbiao
 *
 */
public class FamousSchoolQuestion extends BaseFamousSchoolRecord {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6237239359881591892L;
	private Long famousSchoolQuestionId; // 关联主键
	private Long questionId; // 习题ID

	public Long getFamousSchoolQuestionId() {
		return famousSchoolQuestionId;
	}

	public void setFamousSchoolQuestionId(Long famousSchoolQuestionId) {
		this.famousSchoolQuestionId = famousSchoolQuestionId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

}
