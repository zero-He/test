package cn.strong.leke.monitor.core.model;


/**
 * 考勤统计页面显示对象（班主任）
 * 
 * @Description
 * @author Deo
 * @createdate 2017年3月22日 下午1:45:37
 */
public class AttendanceTeacherDTO extends AttendanceQuery
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1266190635473829717L;

	// 计划旁听课堂数
	private Long mustAttendLessonNum;

	// 实际旁听课堂数
	private Long actualLessonTimes;
	// 旁听率
	// private Float attendanceRate;
	// 旁听时长
	private Long attendHours;
	// 旁听次数
	private Long attendTimes;


	/**
	 * @return the mustAttendLessonNum
	 */
	public Long getMustAttendLessonNum()
	{
		return mustAttendLessonNum;
	}

	/**
	 * @param mustAttendLessonNum
	 *            the mustAttendLessonNum to set
	 */
	public void setMustAttendLessonNum(Long mustAttendLessonNum)
	{
		this.mustAttendLessonNum = mustAttendLessonNum;
	}

	/**
	 * @return the actualLessonTimes
	 */
	public Long getActualLessonTimes()
	{
		return actualLessonTimes;
	}

	/**
	 * @param actualLessonTimes
	 *            the actualLessonTimes to set
	 */
	public void setActualLessonTimes(Long actualLessonTimes)
	{
		this.actualLessonTimes = actualLessonTimes;
	}

	/**
	 * @return the attendHours
	 */
	public Long getAttendHours()
	{
		return attendHours;
	}

	/**
	 * @param attendHours
	 *            the attendHours to set
	 */
	public void setAttendHours(Long attendHours)
	{
		this.attendHours = attendHours;
	}

	/**
	 * @return the attendTimes
	 */
	public Long getAttendTimes()
	{
		return attendTimes;
	}

	/**
	 * @param attendTimes
	 *            the attendTimes to set
	 */
	public void setAttendTimes(Long attendTimes)
	{
		this.attendTimes = attendTimes;
	}

	/**
	 * @return the attendanceRate
	 */
	public String getAttendanceRate()
	{
		float val = 1f;
		if (this.getActualLessonTimes() != null && this.getMustAttendLessonNum() != 0L)
		{
			val = (Float.valueOf(this.getActualLessonTimes()) / Float.valueOf(this.getMustAttendLessonNum())) * 100;
		}
		return String.format("%.2f", val) + "%";
	}

}
