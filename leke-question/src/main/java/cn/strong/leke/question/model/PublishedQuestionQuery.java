/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.model;

import cn.strong.leke.model.question.querys.BaseQuestionQuery;

/**
 * 
 * 描述: 正式题目查询
 * 
 * @author liulb
 * @created 2014年5月23日 上午10:34:32
 * @since v1.0.0
 */
public class PublishedQuestionQuery extends BaseQuestionQuery {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = 404886172359266928L;
	private Long materialNodeId; // 教材节点ID
	private Long knowledgeId; // 知识点ID

	public Long getMaterialNodeId() {
		return materialNodeId;
	}

	public void setMaterialNodeId(Long materialNodeId) {
		this.materialNodeId = materialNodeId;
	}

	public Long getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

}
