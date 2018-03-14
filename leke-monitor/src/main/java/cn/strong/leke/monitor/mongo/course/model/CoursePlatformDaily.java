/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.model;

import java.util.Date;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * 课堂学校日统计
 * 
 * @author liulongbiao
 *
 */
public class CoursePlatformDaily {
	@_id
	@ObjectId
	private String id;
	private Integer day; // 日期
	private Long schoolCount; // 学校数量
	private Long lessonCount; // 课堂数
	private Long expectStuCount; // 预期学生人数
	private Long actualStuCount; // 实到学生人数
	private Long expectStuTimes; // 预期学生人次
	private Long actualStuTimes; // 实到学生人次
	private Long maxActualStuCount; // 当日最高上课学生人数
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

	public Long getSchoolCount() {
		return schoolCount;
	}

	public void setSchoolCount(Long schoolCount) {
		this.schoolCount = schoolCount;
	}

	public Long getLessonCount() {
		return lessonCount;
	}

	public void setLessonCount(Long lessonCount) {
		this.lessonCount = lessonCount;
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

	public Long getMaxActualStuCount() {
		return maxActualStuCount;
	}

	public void setMaxActualStuCount(Long maxActualStuCount) {
		this.maxActualStuCount = maxActualStuCount;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

}
