package cn.strong.leke.question.model.question;

import cn.strong.leke.model.BaseModel;

/**
 *
 * 习题和用户资源分组关联表
 *
 * @author liulongbiao
 */
public class QuestionUserResGroup extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8704730288794324299L;
	private Long assocId; // 关联主键
	private Long questionId; // 习题ID
	private Long userResGroupId; // 用户资源分组ID

	public Long getAssocId() {
		return assocId;
	}

	public void setAssocId(Long assocId) {
		this.assocId = assocId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getUserResGroupId() {
		return userResGroupId;
	}

	public void setUserResGroupId(Long userResGroupId) {
		this.userResGroupId = userResGroupId;
	}

}
