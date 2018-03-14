package cn.strong.leke.homework.model.query;


/**
 * 寒暑假作业查询
 * @author Zhang Fujun
 * @date 2016年12月15日
 */
public class HolidayHwQuery {
	private Long studentId;
	//1:作业，2：微课
	private Integer type;
	
	private Long classId;
	
	private Long subjectId;
	//寒暑假类型 1:寒假 2:暑假
	private Integer holiday;

	private Long year;
	
	
	/**
	 * @return the studentId
	 */
	public Long getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return the classId
	 */
	public Long getClassId() {
		return classId;
	}

	/**
	 * @param classId the classId to set
	 */
	public void setClassId(Long classId) {
		this.classId = classId;
	}

	/**
	 * @return the subjectId
	 */
	public Long getSubjectId() {
		return subjectId;
	}

	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getHoliday() {
		return holiday;
	}

	public void setHoliday(Integer holiday) {
		this.holiday = holiday;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}
}
