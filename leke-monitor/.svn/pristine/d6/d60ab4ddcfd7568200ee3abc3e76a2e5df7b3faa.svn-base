package cn.strong.leke.monitor.core.model;

import java.util.Date;

/**
 * 老师课堂考勤明细
 * 
 * @Description
 * @author Deo
 * @createdate 2017年3月24日 上午10:18:02
 */
public class AttendanceTeacherDtlDTO extends AttendanceQuery
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1369795492242497541L;
	// 课堂上课时间
	private Date lessonStartTime;
	private Date lessonEndTime;
	// 课程时长
	private Long lessonMinutes;
	// 课堂名称
	private String lessonName;
	// 实际旁听时间
	private Date attendTime;
	// 旁听时长
	private Long attendHours;

	/**
	 * @return the lessonStartTime
	 */
	public Date getLessonStartTime()
	{
		return lessonStartTime;
	}

	/**
	 * @param lessonStartTime
	 *            the lessonStartTime to set
	 */
	public void setLessonStartTime(Date lessonStartTime)
	{
		this.lessonStartTime = lessonStartTime;
	}

	/**
	 * @return the lessonEndTime
	 */
	public Date getLessonEndTime()
	{
		return lessonEndTime;
	}

	/**
	 * @param lessonEndTime
	 *            the lessonEndTime to set
	 */
	public void setLessonEndTime(Date lessonEndTime)
	{
		this.lessonEndTime = lessonEndTime;
	}

	/**
	 * @return the lessonMinutes
	 */
	public Long getLessonMinutes()
	{
		return lessonMinutes;
	}

	/**
	 * @param lessonMinutes
	 *            the lessonMinutes to set
	 */
	public void setLessonMinutes(Long lessonMinutes)
	{
		this.lessonMinutes = lessonMinutes;
	}

	/**
	 * @return the lessonName
	 */
	public String getLessonName()
	{
		return lessonName;
	}

	/**
	 * @param lessonName
	 *            the lessonName to set
	 */
	public void setLessonName(String lessonName)
	{
		this.lessonName = lessonName;
	}

	/**
	 * @return the attendTime
	 */
	public Date getAttendTime()
	{
		return attendTime;
	}

	/**
	 * @param attendTime
	 *            the attendTime to set
	 */
	public void setAttendTime(Date attendTime)
	{
		this.attendTime = attendTime;
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

	public String getLessonTimeStr()
	{
		String str = "";
		if (this.getLessonStartTime() != null)
		{
			str += formatDate(this.getLessonStartTime(), "yyyy-MM-dd HH:mm");
		}
		if (this.getLessonEndTime() != null)
		{
			str += "~";
			str += formatDate(this.getLessonEndTime(), "HH:mm");
		}
		return str;
	}
	
	public String getAttendTimeStr()
	{
		String str = "";
		if (this.getAttendTime() != null)
		{
			str += formatDate(this.getAttendTime(), "yyyy-MM-dd HH:mm");
		}	
		return str;
	}
}
