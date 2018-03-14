/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.model.query;

/**
 * 课堂日统计
 * 
 * @author liulongbiao
 *
 */
public class DailyCourseStat {

	private Integer day; // 日期
	private Long schoolCount = 0L; // 上课学校数
	private Long lessonCount = 0L; // 课堂数
	private Long hours = 0L; // 课时数
	private Long expectStuCount = 0L; // 应到学生人数
	private Long actualStuCount = 0L; // 实到学生人数
	private Long expectStuTimes = 0L; // 应到学生人次
	private Long actualStuTimes = 0L; // 已到学生人次

	public DailyCourseStat() {
		super();
	}

	public DailyCourseStat(Integer day) {
		super();
		this.day = day;
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

}
