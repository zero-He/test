package cn.strong.leke.question.model.question;

import cn.strong.leke.repository.common.model.BaseCheckLog;

/**
 *
 * 描述: 学校习题审核记录
 *
 * @author raolei
 */
public class SchoolQuestionCheckLog extends BaseCheckLog {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = -703594538055393200L;
	private Long questionId; // 习题ID
	private Long schoolId; // 学校ID

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
