package cn.strong.leke.diag.model.teachingMonitor;

import java.util.Date;
import java.util.List;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 *
 * 描述: 备课单元提交记录
 *
 * @author xujinsong
 */
public class BeikeUnitCommit {

	@_id
	@ObjectId
	private String id;
	
	private String attitude;// 态度
	
	private String difficulty; // 教学难点
	
	private String knowledge; // 知识点
	
	private String method; // 教学方法
	
	private String prevCommitId;// 前一个版本提交ID
	
	private String process;// 教学过程
	
	private String reflection;// 
	
	private List<LessonResource> resources; // 资源列表
	
	private String topic; //主题
	
	private String unitId; //单元ID
	
	private Long createdBy; // 创建者 ID
	
	private Date createdOn; // 创建时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAttitude() {
		return attitude;
	}

	public void setAttitude(String attitude) {
		this.attitude = attitude;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(String knowledge) {
		this.knowledge = knowledge;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPrevCommitId() {
		return prevCommitId;
	}

	public void setPrevCommitId(String prevCommitId) {
		this.prevCommitId = prevCommitId;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getReflection() {
		return reflection;
	}

	public void setReflection(String reflection) {
		this.reflection = reflection;
	}

	public List<LessonResource> getResources() {
		return resources;
	}

	public void setResources(List<LessonResource> resources) {
		this.resources = resources;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
