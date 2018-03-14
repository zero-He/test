/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.model;

import java.util.Date;
import java.util.List;

import org.joda.time.LocalDateTime;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * 学校课表
 * 
 * @author liulongbiao
 *
 */
public class CourseTable {
	public static final String PERIOD_AM = "AM"; // 上午
	public static final String PERIOD_PM = "PM"; // 下午
	public static final String PERIOD_NT = "NT"; // 晚上

	/**
	 * 获取某个时刻的时段
	 * 
	 * @param ts
	 * @return
	 */
	public static String getPeriod(Date ts) {
		LocalDateTime ldt = new LocalDateTime(ts);
		int h = ldt.getHourOfDay();
		return h < 12 ? PERIOD_AM : (h < 18 ? PERIOD_PM : PERIOD_NT);
	}

	@_id
	@ObjectId
	private String id;
	private Integer day; // yyyyMMdd 格式的日期
	private Long schoolId; // 学校ID
	private String period; // 时段
	private String schoolName; // 学校名称
	private Integer schoolCode; // 学校编码
	private Integer schoolNature; // 学校属性
	private List<Long> regionIds; // 区域ID列表
	private List<Long> marketIds; // 市场ID列表
	private Date minStartTime; // 最小开始时间
	private Date maxEndTime; // 最大结束时间
	private Long lessonCount; // 课堂数
	private Long hours; // 课时数
	private Long expectStuCount; // 预期学生人数
	private Long expectStuTimes; // 预期学生人次
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

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
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

	public Date getMinStartTime() {
		return minStartTime;
	}

	public void setMinStartTime(Date minStartTime) {
		this.minStartTime = minStartTime;
	}

	public Date getMaxEndTime() {
		return maxEndTime;
	}

	public void setMaxEndTime(Date maxEndTime) {
		this.maxEndTime = maxEndTime;
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

	public Long getExpectStuTimes() {
		return expectStuTimes;
	}

	public void setExpectStuTimes(Long expectStuTimes) {
		this.expectStuTimes = expectStuTimes;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

}
