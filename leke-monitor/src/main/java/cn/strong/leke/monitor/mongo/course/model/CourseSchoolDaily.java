/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.model;

import java.util.Date;
import java.util.List;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * 课堂学校日统计
 * 
 * @author liulongbiao
 *
 */
public class CourseSchoolDaily {
	@_id
	@ObjectId
	private String id;
	private Integer day; // 日期
	private Long schoolId; // 学校ID
	private String schoolName; // 学校名称
	private Integer schoolCode; // 学校编码
	private Integer schoolNature; // 学校性质
	private List<Long> regionIds; // 区域ID列表
	private List<Long> marketIds; // 市场ID列表
	private Long lessonCount; // 课堂数
	private Long hours; // 课时数
	private Long expectStuCount; // 预期学生人数
	private Long actualStuCount; // 实到学生人数
	private Long expectStuTimes; // 预期学生人次
	private Long actualStuTimes; // 实到学生人次
	private Date modifiedOn; // 更新时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
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

	public Long getLessonCount() {
		return lessonCount;
	}

	public void setLessonCount(Long lessonCount) {
		this.lessonCount = lessonCount;
	}

	public Long getHours() {
		return hours;
	}

	public void setHours(Long hours) {
		this.hours = hours;
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

	public Long getExpectStuTimes() {
		return expectStuTimes;
	}

	public void setExpectStuTimes(Long expectStuTimes) {
		this.expectStuTimes = expectStuTimes;
	}

	public Long getActualStuTimes() {
		return actualStuTimes;
	}

	public void setActualStuTimes(Long actualStuTimes) {
		this.actualStuTimes = actualStuTimes;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

}
