/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication;

import java.util.List;

import cn.strong.leke.data.mongo.annotations._id;

/**
 * 习题相似度存储文档
 * 
 * @author liulongbiao
 * @created 2015年1月17日 上午10:32:20
 * @since v3.2.2
 */
public class QueSimDoc {

	@_id
	private Long questionId; // 习题ID
	private Long simHash; // simhash
	private List<Integer> buckets; // simhash buckets
	private Integer termCount; // 分词数量
	private List<WTerm> keywords; // 关键字；取权重最高的 20 个分词
	private Long schoolStageId; // 学段ID
	private Long subjectId; // 学科ID

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getSimHash() {
		return simHash;
	}

	public void setSimHash(Long simHash) {
		this.simHash = simHash;
	}

	public List<Integer> getBuckets() {
		return buckets;
	}

	public void setBuckets(List<Integer> buckets) {
		this.buckets = buckets;
	}

	public Integer getTermCount() {
		return termCount;
	}

	public void setTermCount(Integer termCount) {
		this.termCount = termCount;
	}

	public List<WTerm> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<WTerm> keywords) {
		this.keywords = keywords;
	}

	public Long getSchoolStageId() {
		return schoolStageId;
	}

	public void setSchoolStageId(Long schoolStageId) {
		this.schoolStageId = schoolStageId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

}
