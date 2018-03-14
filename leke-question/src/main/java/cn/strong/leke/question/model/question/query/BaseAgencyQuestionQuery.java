/**
 * 
 */
package cn.strong.leke.question.model.question.query;

import cn.strong.leke.repository.common.model.BaseAgencyRecordQuery;

/**
 * 基础代录习题查询
 * 
 * @author liulongbiao
 *
 */
public class BaseAgencyQuestionQuery extends BaseAgencyRecordQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5040852025122754085L;
	private Long questionTypeId; // 题型

	public Long getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(Long questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

}
