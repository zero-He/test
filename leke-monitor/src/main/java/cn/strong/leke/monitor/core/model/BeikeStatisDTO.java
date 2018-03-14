package cn.strong.leke.monitor.core.model;

import java.util.Date;

/**
 * 全网备课DTO
 * 
 * @Description
 * @author Deo
 * @createdate 2016年11月17日 上午10:10:59
 */
public class BeikeStatisDTO extends BeikeStatis
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3680943303354644545L;
	// 统计开始时间
	private Date fromDate;
	// 统计结束时间
	private Date endDate;
	// 学校数
	private Long schoolNums;
	// 学校名称
	private String schoolName;
	// 学校ID
	private Long schoolId;
	// 年级名称
	private String gradeName;
	// 年级ID
	private Long gradeId;
	// 学科名称
	private String subjectName;
	// 学科ID
	private Long subjectId;

	/**
	 * @return the fromDate
	 */
	public Date getFromDate()
	{
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(Date fromDate)
	{
		this.fromDate = fromDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate()
	{
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public Long getSchoolNums()
	{
		return schoolNums;
	}

	public void setSchoolNums(Long schoolNums)
	{
		this.schoolNums = schoolNums;
	}

	/**
	 * @return the schoolName
	 */
	public String getSchoolName()
	{
		return schoolName;
	}

	/**
	 * @param schoolName
	 *            the schoolName to set
	 */
	public void setSchoolName(String schoolName)
	{
		this.schoolName = schoolName;
	}

	/**
	 * @return the schoolId
	 */
	public Long getSchoolId()
	{
		return schoolId;
	}

	/**
	 * @param schoolId
	 *            the schoolId to set
	 */
	public void setSchoolId(Long schoolId)
	{
		this.schoolId = schoolId;
	}

	/**
	 * @return the gradeName
	 */
	public String getGradeName()
	{
		return gradeName;
	}

	/**
	 * @param gradeName
	 *            the gradeName to set
	 */
	public void setGradeName(String gradeName)
	{
		this.gradeName = gradeName;
	}

	/**
	 * @return the gradeId
	 */
	public Long getGradeId()
	{
		return gradeId;
	}

	/**
	 * @param gradeId
	 *            the gradeId to set
	 */
	public void setGradeId(Long gradeId)
	{
		this.gradeId = gradeId;
	}

	/**
	 * @return the subjectName
	 */
	public String getSubjectName()
	{
		return subjectName;
	}

	/**
	 * @param subjectName
	 *            the subjectName to set
	 */
	public void setSubjectName(String subjectName)
	{
		this.subjectName = subjectName;
	}

	/**
	 * @return the subjectId
	 */
	public Long getSubjectId()
	{
		return subjectId;
	}

	/**
	 * @param subjectId
	 *            the subjectId to set
	 */
	public void setSubjectId(Long subjectId)
	{
		this.subjectId = subjectId;
	}

	/**
	 * 备课率
	 * 
	 * @return the beikeRate
	 */
	public String getBeikeRate()
	{
		Double a = (Double.valueOf(super.getBeikeLessons()) / Double.valueOf(super.getMustLessons() > 0 ? super.getMustLessons() : 1L)) * 100;
		return String.format("%.2f", a) + "%";
	}

	/**
	 * 课前课件占比
	 */
	public String getBeforeCwRate()
	{
		Double a = Double.valueOf(super.getBeforeCwQuoteNums()) + Double.valueOf(super.getBeforeCwEditNums());
		Double b = Double.valueOf(super.getBeforeCwUpNums()) + Double.valueOf(super.getBeforeCwQuoteNums())
				+ Double.valueOf(super.getBeforeCwEditNums());
		return String.format("%.2f", (a / (b > 0 ? b : 1)) * 100) + "%";
	}

	/**
	 * 课中课件占比
	 */
	public String getInClassCwRate()
	{
		Double a = Double.valueOf(super.getInClassCwQuoteNums()) + Double.valueOf(super.getInClassCwEditNums());
		Double b = Double.valueOf(super.getInClassCwUpNums()) + Double.valueOf(super.getInClassCwQuoteNums())
				+ Double.valueOf(super.getInClassCwEditNums());
		return String.format("%.2f", (a / (b > 0 ? b : 1)) * 100) + "%";
	}

	/**
	 * 课前作业占比
	 */
	public String getBeforeHwRate()
	{
		Double a = Double.valueOf(super.getBeforeHwQuoteNums()) + Double.valueOf(super.getBeforeHwEditNums());
		Double b = Double.valueOf(super.getBeforeHwUpNums()) + Double.valueOf(super.getBeforeHwQuoteNums())
				+ Double.valueOf(super.getBeforeHwEditNums());
		return String.format("%.2f", (a / (b > 0 ? b : 1)) * 100) + "%";
	}

	/**
	 * 课中作业占比
	 */
	public String getInClassHwRate()
	{
		Double a = Double.valueOf(super.getInClassHwQuoteNums()) + Double.valueOf(super.getInClassHwEditNums());
		Double b = Double.valueOf(super.getInClassHwUpNums()) + Double.valueOf(super.getInClassHwQuoteNums())
				+ Double.valueOf(super.getInClassHwEditNums());
		return String.format("%.2f", (a / (b > 0 ? b : 1)) * 100) + "%";
	}
	/**
	 * 课后作业占比
	 */
	public String getAfterHwRate()
	{
		Double a = Double.valueOf(super.getAfterHwQuoteNums()) + Double.valueOf(super.getAfterHwEditNums());
		Double b = Double.valueOf(super.getAfterHwUpNums()) + Double.valueOf(super.getAfterHwQuoteNums())
				+ Double.valueOf(super.getAfterHwEditNums());
		return String.format("%.2f", (a / (b > 0 ? b : 1)) * 100) + "%";
	}

	/**
	 * 备课包占比
	 */
	public String getBkgRate()
	{
		Double a = Double.valueOf(super.getBkgQuoteNums()) + Double.valueOf(super.getBkgEditNums());
		Double b = Double.valueOf(super.getBkgUpNums()) + Double.valueOf(super.getBkgQuoteNums()) + Double.valueOf(super.getBkgEditNums());
		return String.format("%.2f", (a / (b > 0 ? b : 1)) * 100) + "%";
	}

	/**
	 * 课前微课占比
	 */
	public String getBeforeWkRate()
	{
		Double a = Double.valueOf(super.getBeforeWkQuoteNums());
		Double b = Double.valueOf(super.getBeforeWkUpNums()) + Double.valueOf(super.getBeforeWkQuoteNums());
		return String.format("%.2f", (a / (b > 0 ? b : 1)) * 100) + "%";
	}
	
	/**
	 * 课后微课占比
	 */
	public String getAfterWkRate()
	{
		Double a = Double.valueOf(super.getAfterWkQuoteNums());
		Double b = Double.valueOf(super.getAfterWkUpNums()) + Double.valueOf(super.getAfterWkQuoteNums());
		return String.format("%.2f", (a / (b > 0 ? b : 1)) * 100) + "%";
	}

	/**
	 * 课件绑定率
	 */
	public String getCoursewareRate()
	{
		// 课前课件+课中课件课堂数/备课课堂数
		Double a = Double.valueOf(super.getCwLessonNums());
		Double b = Double.valueOf(super.getBeikeLessons());
		return String.format("%.2f", (a / (b > 0 && b != null ? b : 1)) * 100) + "%";
	}

	/**
	 * 作业绑定率
	 */
	public String getHomeWorkRate()
	{
		// 课前作业+课中作业/备课课堂数
		Double a = Double.valueOf(super.getHwLessonNums());
		Double b = Double.valueOf(super.getBeikeLessons());
		return String.format("%.2f", (a / (b > 0 && b != null ? b : 1)) * 100) + "%";
	}
}
