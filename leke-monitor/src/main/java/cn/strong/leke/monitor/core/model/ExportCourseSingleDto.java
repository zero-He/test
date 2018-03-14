package cn.strong.leke.monitor.core.model;

/**
 *
 * 描述:
 *
 * @author raolei
 * @created 2015年11月4日 上午10:34:52
 * @since v1.0.0
 */
public class ExportCourseSingleDto {

	private String clazzName; // 班级名称
	private String csName; // 单课名称
	private String subjectName; // 学科名称
	private String startTimeStr;// 上课开始时间
	private String endTimeStr;// 上课结束时间
	private String teacherName;// 授课老师
	private Long actualStuCount;// 实到人次
	private Long expectStuCount;// 应到人次
	private String ratioCounts;// 到课率

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public String getCsName() {
		return csName;
	}

	public void setCsName(String csName) {
		this.csName = csName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public Long getActualStuCount() {
		return actualStuCount;
	}

	public void setActualStuCount(Long actualStuCount) {
		this.actualStuCount = actualStuCount;
	}

	public Long getExpectStuCount() {
		return expectStuCount;
	}

	public void setExpectStuCount(Long expectStuCount) {
		this.expectStuCount = expectStuCount;
	}

	public String getRatioCounts() {
		return ratioCounts;
	}

	public void setRatioCounts(String ratioCounts) {
		this.ratioCounts = ratioCounts;
	}

}
