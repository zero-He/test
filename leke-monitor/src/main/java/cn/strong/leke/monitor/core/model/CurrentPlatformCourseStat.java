/**
 * 
 */
package cn.strong.leke.monitor.core.model;

import org.apache.http.util.Asserts;

import cn.strong.leke.monitor.mongo.course.model.query.DailyCourseStat;
import cn.strong.leke.monitor.mongo.course.model.query.OnlineCourseStat;

/**
 * 当前平台课堂统计
 * 
 * @author liulongbiao
 *
 */
public class CurrentPlatformCourseStat {

	private Long currentSchoolCount; // 当前上课学校数
	private Long dailySchoolCount; // 今日上课学校数
	private Long currentLessonCount; // 当前课堂数
	private Long dailyLessonCount; // 今日课堂数
	private Long currentActualStuCount; // 当前上课学生数
	private Long dailyExpectStuCount; // 今日应到学生数
	private Long dailyActualStuTimes; // 今日已到学生人次
	private Long dailyExpectStuTimes; // 今日应到学生人次

	public CurrentPlatformCourseStat() {
		super();
	}

	public CurrentPlatformCourseStat(DailyCourseStat daily, OnlineCourseStat online) {
		this();
		Asserts.notNull(daily, "daily stat is null");
		Asserts.notNull(online, "online stat is null");
		this.currentSchoolCount = online.getSchoolCount();
		this.dailySchoolCount = daily.getSchoolCount();
		this.currentLessonCount = online.getLessonCount();
		this.dailyLessonCount = daily.getLessonCount();
		this.currentActualStuCount = online.getActualStuCount();
		this.dailyExpectStuCount = daily.getExpectStuCount();
		this.dailyActualStuTimes = daily.getActualStuTimes();
		this.dailyExpectStuTimes = daily.getExpectStuTimes();
	}

	public Long getCurrentSchoolCount() {
		return currentSchoolCount;
	}

	public void setCurrentSchoolCount(Long currentSchoolCount) {
		this.currentSchoolCount = currentSchoolCount;
	}

	public Long getDailySchoolCount() {
		return dailySchoolCount;
	}

	public void setDailySchoolCount(Long dailySchoolCount) {
		this.dailySchoolCount = dailySchoolCount;
	}

	public Long getCurrentLessonCount() {
		return currentLessonCount;
	}

	public void setCurrentLessonCount(Long currentLessonCount) {
		this.currentLessonCount = currentLessonCount;
	}

	public Long getDailyLessonCount() {
		return dailyLessonCount;
	}

	public void setDailyLessonCount(Long dailyLessonCount) {
		this.dailyLessonCount = dailyLessonCount;
	}

	public Long getCurrentActualStuCount() {
		return currentActualStuCount;
	}

	public void setCurrentActualStuCount(Long currentActualStuCount) {
		this.currentActualStuCount = currentActualStuCount;
	}

	public Long getDailyExpectStuCount() {
		return dailyExpectStuCount;
	}

	public void setDailyExpectStuCount(Long dailyExpectStuCount) {
		this.dailyExpectStuCount = dailyExpectStuCount;
	}

	public Long getDailyActualStuTimes() {
		return dailyActualStuTimes;
	}

	public void setDailyActualStuTimes(Long dailyActualStuTimes) {
		this.dailyActualStuTimes = dailyActualStuTimes;
	}

	public Long getDailyExpectStuTimes() {
		return dailyExpectStuTimes;
	}

	public void setDailyExpectStuTimes(Long dailyExpectStuTimes) {
		this.dailyExpectStuTimes = dailyExpectStuTimes;
	}

}
