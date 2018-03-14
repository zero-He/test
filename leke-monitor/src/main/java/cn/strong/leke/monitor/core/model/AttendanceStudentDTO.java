package cn.strong.leke.monitor.core.model;

import java.util.Date;

/**
 * 考勤统计页面显示对象（学生）
 * 
 * @Description
 * @author Deo
 * @createdate 2017年3月22日 下午1:45:37
 */
public class AttendanceStudentDTO extends AttendanceQuery
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8693880097148562789L;

	// 应上课人数
	private Long mustClassNum;
	// 应上课人次
	private Long mustClassTimes;
	// 实际上课人次
	private Long actualClassTimes;
	// 到课率
	// private Float attendanceRate;

	/** 明细列表用 **/

	// 课堂上课时间
	private Date lessonStartTime;
	private Date lessonEndTime;
	// 课堂名称
	private String lessonName;

	/**
	 * @return the mustClassNum
	 */
	public Long getMustClassNum()
	{
		return mustClassNum;
	}

	/**
	 * @param mustClassNum
	 *            the mustClassNum to set
	 */
	public void setMustClassNum(Long mustClassNum)
	{
		this.mustClassNum = mustClassNum;
	}

	/**
	 * @return the mustClassTimes
	 */
	public Long getMustClassTimes()
	{
		return mustClassTimes;
	}

	/**
	 * @param mustClassTimes
	 *            the mustClassTimes to set
	 */
	public void setMustClassTimes(Long mustClassTimes)
	{
		this.mustClassTimes = mustClassTimes;
	}

	/**
	 * @return the actualClassTimes
	 */
	public Long getActualClassTimes()
	{
		return actualClassTimes;
	}

	/**
	 * @param actualClassTimes
	 *            the actualClassTimes to set
	 */
	public void setActualClassTimes(Long actualClassTimes)
	{
		this.actualClassTimes = actualClassTimes;
	}

	/**
	 * @return the attendanceRate
	 */
	public String getAttendanceRate()
	{
		float val = 1f;
		if (this.getMustClassTimes() != null && this.getMustClassTimes() != 0L)
		{
			val = (Float.valueOf(this.getActualClassTimes()) / Float.valueOf(this.getMustClassTimes())) * 100;
		}
		return String.format("%.2f", val) + "%";
	}



	/**
	 * @return the lessonStartTime
	 */
	public Date getLessonStartTime()
	{
		return lessonStartTime;
	}

	/**
	 * @param lessonStartTime the lessonStartTime to set
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
	 * @param lessonEndTime the lessonEndTime to set
	 */
	public void setLessonEndTime(Date lessonEndTime)
	{
		this.lessonEndTime = lessonEndTime;
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

}
