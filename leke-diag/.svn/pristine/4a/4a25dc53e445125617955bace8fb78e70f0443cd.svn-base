package cn.strong.leke.diag.model.teachingMonitor;

import java.util.Date;
import java.util.List;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 *
 * 描述: 课堂和备课包关联表
 *
 * @author raolei
 */
public class LessonBeikePkg {

	@_id
	@ObjectId
	private String id;
	
	private Long lessonId;// 单课ID(courseSingleId)
	
	private Long beikePkgId; // 备课包ID
	
	private Boolean scattered; // 是否是零散的
	
	private String commitId; // 提交记录 ID
	
	private String unitId;// 单元 ID
	
	private List<LessonResource> resources; // 资源列表
	
	private Long createdBy; // 创建者 ID
	
	private Date createdOn; // 创建时间
	
	private Long modifiedBy; // 修改者 ID
	
	private Date modifiedOn; // 修改时间
	
	private Boolean isDeleted; // 是否删除

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getLessonId() {
		return lessonId;
	}

	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}

	public Long getBeikePkgId() {
		return beikePkgId;
	}

	public void setBeikePkgId(Long beikePkgId) {
		this.beikePkgId = beikePkgId;
	}

	public Boolean getScattered() {
		return scattered;
	}

	public void setScattered(Boolean scattered) {
		this.scattered = scattered;
	}

	public String getCommitId() {
		return commitId;
	}

	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public List<LessonResource> getResources() {
		return resources;
	}

	public void setResources(List<LessonResource> resources) {
		this.resources = resources;
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

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
