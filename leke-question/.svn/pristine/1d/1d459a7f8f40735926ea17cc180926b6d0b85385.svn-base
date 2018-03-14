/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.model;

import java.util.List;
import java.util.Set;

import cn.strong.leke.model.question.querys.BaseQuestionQuery;

/**
 * 描述: 随机选题查询
 * @author  andy
 * @created 2014年6月18日 上午8:58:46
 * @since   v1.0.0
 */
public class RandSelectQuestionQuery extends BaseQuestionQuery {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = -350075224933948702L;
	private Long materialNodeId; // 教材节点ID
	private Long knowledgeId; // 知识点ID
	private List<Long> knowledgeIds; // 知识点列表
	private List<Long> materialNodeIds; // 章节ID列表
	private Integer questionNum; // 选题数量
	private Double difficulty; // 选题难度系数
	private Boolean subjective; // 主观性
	private Boolean hasSub;// 是否含子题
	private Set<Long> inQuestionTypeIds;// 题型
	private Integer shareScope; // 习题范围
	private Set<Long> excludeQueIds;// 已选择习题ID集合

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

	public List<Long> getKnowledgeIds() {
		return knowledgeIds;
	}

	public void setKnowledgeIds(List<Long> knowledgeIds) {
		this.knowledgeIds = knowledgeIds;
	}

	public List<Long> getMaterialNodeIds() {
		return materialNodeIds;
	}

	public void setMaterialNodeIds(List<Long> materialNodeIds) {
		this.materialNodeIds = materialNodeIds;
	}

	public Integer getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}

	public Double getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Double difficulty) {
		this.difficulty = difficulty;
	}

	public Boolean getSubjective() {
		return subjective;
	}

	public void setSubjective(Boolean subjective) {
		this.subjective = subjective;
	}

	public Boolean getHasSub() {
		return hasSub;
	}

	public void setHasSub(Boolean hasSub) {
		this.hasSub = hasSub;
	}

	public Set<Long> getInQuestionTypeIds() {
		return inQuestionTypeIds;
	}

	public void setInQuestionTypeIds(Set<Long> inQuestionTypeIds) {
		this.inQuestionTypeIds = inQuestionTypeIds;
	}

	public Integer getShareScope() {
		return shareScope;
	}

	public void setShareScope(Integer shareScope) {
		this.shareScope = shareScope;
	}

	public Set<Long> getExcludeQueIds() {
		return excludeQueIds;
	}

	public void setExcludeQueIds(Set<Long> excludeQueIds) {
		this.excludeQueIds = excludeQueIds;
	}

}
