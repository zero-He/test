/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.model;

import java.util.Date;
import java.util.List;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * 单课信息
 * 
 * @author liulongbiao
 *
 */
public class CourseSingle {
	@_id
	@ObjectId
	private String id;
	private Long csId; // 单课ID
	private String csName; // 单课名称
	private Long clazzId; // 班级ID
	private String clazzName; // 班级名称
	private String subjectName; // 学科名称
	private Long schoolId; // 学校ID
	private String schoolName; // 学校名称
	private Integer schoolCode; // 学校编码
	private Integer schoolNature; // 学校性质
	private List<Long> regionIds; // 区域ID列表
	private List<Long> marketIds; // 市场ID列表
	private Date startTime; // 起始时间
	private Date endTime; // 结束时间
	private String period; // 时段
	private Integer hours; // 课时数
	private String teacherName; // 教师名称
	private String phone; // 教师电话
	private Boolean isRecord; // 是否录像
	private Integer courseType; // 课程类型
	private Long expectStuCount; // 应到学生人数
	private Long actualStuCount; // 实到学生人数
	private Boolean isOnline; // 是否在线
	private Boolean isEnded; // 是否已结束
	private Boolean isDeleted; // 是否已删除
	private Date modifiedOn; // 更新时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCsId() {
		return csId;
	}

	public void setCsId(Long csId) {
		this.csId = csId;
	}

	public String getCsName() {
		return csName;
	}

	public void setCsName(String csName) {
		this.csName = csName;
	}

	public Long getClazzId() {
		return clazzId;
	}

	public void setClazzId(Long clazzId) {
		this.clazzId = clazzId;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Integer getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(Integer schoolCode) {
		this.schoolCode = schoolCode;
	}

	public Integer getSchoolNature() {
		return schoolNature;
	}

	public void setSchoolNature(Integer schoolNature) {
		this.schoolNature = schoolNature;
	}

	public List<Long> getRegionIds() {
		return regionIds;
	}

	public void setRegionIds(List<Long> regionIds) {
		this.regionIds = regionIds;
	}

	public List<Long> getMarketIds() {
		return marketIds;
	}

	public void setMarketIds(List<Long> marketIds) {
		this.marketIds = marketIds;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getIsRecord() {
		return isRecord;
	}

	public void setIsRecord(Boolean isRecord) {
		this.isRecord = isRecord;
	}

	public Integer getCourseType() {
		return courseType;
	}

	public void setCourseType(Integer courseType) {
		this.courseType = courseType;
	}

	public Long getExpectStuCount() {
		return expectStuCount;
	}

	public void setExpectStuCount(Long expectStuCount) {
		this.expectStuCount = expectStuCount;
	}

	public Long getActualStuCount() {
		return actualStuCount;
	}

	public void setActualStuCount(Long actualStuCount) {
		this.actualStuCount = actualStuCount;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	public Boolean getIsEnded() {
		return isEnded;
	}

	public void setIsEnded(Boolean isEnded) {
		this.isEnded = isEnded;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

}
