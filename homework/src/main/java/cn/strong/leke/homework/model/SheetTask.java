package cn.strong.leke.homework.model;

import java.util.Date;
import java.util.List;

import cn.strong.leke.data.mongo.annotations._id;

public class SheetTask {

	@_id
	private String id;
	private String clientId;
	private Integer status;
	private Integer step;
	private Integer totalPkgNum;
	private Integer validPkgNum;
	private Integer totalPageNum;
	private Integer validPageNum;
	private Integer totalBookNum;
	private Integer validBookNum;
	private List<History> historys;
	private Long schoolId;
	private Boolean isDeleted;
	private Long createdBy;
	private Date createdOn;
	private Long modifiedBy;
	private Date modifiedOn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Integer getTotalPkgNum() {
		return totalPkgNum;
	}

	public void setTotalPkgNum(Integer totalPkgNum) {
		this.totalPkgNum = totalPkgNum;
	}

	public Integer getValidPkgNum() {
		return validPkgNum;
	}

	public void setValidPkgNum(Integer validPkgNum) {
		this.validPkgNum = validPkgNum;
	}

	public Integer getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(Integer totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public Integer getValidPageNum() {
		return validPageNum;
	}

	public void setValidPageNum(Integer validPageNum) {
		this.validPageNum = validPageNum;
	}

	public Integer getTotalBookNum() {
		return totalBookNum;
	}

	public void setTotalBookNum(Integer totalBookNum) {
		this.totalBookNum = totalBookNum;
	}

	public Integer getValidBookNum() {
		return validBookNum;
	}

	public void setValidBookNum(Integer validBookNum) {
		this.validBookNum = validBookNum;
	}

	public List<History> getHistorys() {
		return historys;
	}

	public void setHistorys(List<History> historys) {
		this.historys = historys;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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

	public static class SheetGroup {
		private Long homeworkId;
		private String homeworkName;
		private Date closeTime;
		private Long classId;
		private Integer classType;
		private String className;
		private Integer bookNum;

		public Long getHomeworkId() {
			return homeworkId;
		}

		public void setHomeworkId(Long homeworkId) {
			this.homeworkId = homeworkId;
		}

		public String getHomeworkName() {
			return homeworkName;
		}

		public void setHomeworkName(String homeworkName) {
			this.homeworkName = homeworkName;
		}

		public Date getCloseTime() {
			return closeTime;
		}

		public void setCloseTime(Date closeTime) {
			this.closeTime = closeTime;
		}

		public Long getClassId() {
			return classId;
		}

		public void setClassId(Long classId) {
			this.classId = classId;
		}

		public Integer getClassType() {
			return classType;
		}

		public void setClassType(Integer classType) {
			this.classType = classType;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public Integer getBookNum() {
			return bookNum;
		}

		public void setBookNum(Integer bookNum) {
			this.bookNum = bookNum;
		}
	}

	public static class History {
		private Date ts;
		private Integer step;

		public History() {
		}

		public History(Integer step) {
			this.step = step;
			this.ts = new Date();
		}

		public Date getTs() {
			return ts;
		}

		public void setTs(Date ts) {
			this.ts = ts;
		}

		public Integer getStep() {
			return step;
		}

		public void setStep(Integer step) {
			this.step = step;
		}
	}
}
