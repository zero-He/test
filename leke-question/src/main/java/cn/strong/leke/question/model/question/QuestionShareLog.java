package cn.strong.leke.question.model.question;

import cn.strong.leke.repository.common.model.BaseShareLog;

/**
 *
 * 描述: 习题分享日志
 *
 * @author raolei
 * @created 2016年6月8日 下午4:54:27
 * @since v1.0.0
 */
public class QuestionShareLog extends BaseShareLog {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = -5686583646865878757L;
	private Long questionId;// 习题ID

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

}
