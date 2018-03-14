/**
 * 
 */
package cn.strong.leke.question.analysis.mongo.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * 智能组卷查询缓存
 * 
 * @author liulongbiao
 *
 */
public class SmartPaperQueryCache {
	@_id
	@ObjectId
	private String id;
	@ObjectId
	private String queryId; // 查询ID
	private Long typeId; // 题型ID
	private Map<Long, Double> questions; // 习题ID及对应的难度值
	private Date modifiedOn; // 更新时间，用于 ttl 索引自动删除

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Map<Long, Double> getQuestions() {
		return questions;
	}

	public void setQuestions(Map<Long, Double> questions) {
		this.questions = questions;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	/**
	 * 添加习题
	 * 
	 * @param questionId
	 * @param difficulty
	 */
	public void add(Long questionId, Double difficulty) {
		if (difficulty == null) {
			difficulty = 0.5;
		}
		if (questions == null) {
			questions = new HashMap<>();
		}
		questions.put(questionId, difficulty);
	}

	/**
	 * @return
	 */
	public int size() {
		return questions == null ? 0 : questions.size();
	}

	/**
	 * 是否没有题
	 * 
	 * @return
	 */
	public boolean wasEmpty() {
		return questions == null || questions.isEmpty();
	}
}
