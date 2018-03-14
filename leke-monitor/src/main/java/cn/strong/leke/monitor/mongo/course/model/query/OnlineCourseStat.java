/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.model.query;

/**
 * 在线课堂统计
 * 
 * @author liulongbiao
 *
 */
public class OnlineCourseStat {
	private Long schoolCount = 0L; // 当前上课学校数
	private Long lessonCount = 0L; // 当前课堂数
	private Long actualStuCount = 0L; // 当前上课学生数

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

	public Long getActualStuCount() {
		return actualStuCount;
	}

	public void setActualStuCount(Long actualStuCount) {
		this.actualStuCount = actualStuCount;
	}

}
