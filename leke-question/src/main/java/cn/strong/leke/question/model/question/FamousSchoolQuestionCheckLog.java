/**
 * 
 */
package cn.strong.leke.question.model.question;

import cn.strong.leke.repository.common.model.BaseCheckLog;

/**
 * 名校习题审核日志
 * 
 * @author liulongbiao
 *
 */
public class FamousSchoolQuestionCheckLog extends BaseCheckLog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7907247635989436394L;
	private Long questionId; // 习题ID
	private Long schoolId; // 名校ID

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

}
