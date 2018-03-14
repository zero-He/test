package cn.strong.leke.monitor.core.model;

import cn.strong.leke.model.BaseModel;

/**
 * 备课统计基本信息
 * 
 * @Description
 * @author Deo
 * @createdate 2016年11月16日 下午1:44:58
 */
public class BeikeStatis extends BaseModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7164163469099239977L;

	// 应上课堂数
	private Long mustLessons = 0L;
	// 备课课堂数
	private Long beikeLessons = 0L;

	// 课前课件上传数
	private Long beforeCwUpNums = 0L;
	// 课前课件引用数
	private Long beforeCwQuoteNums = 0L;
	// 课前课件编辑引用数
	private Long beforeCwEditNums = 0L;

	// 课中课件上传数
	private Long inClassCwUpNums = 0L;
	// 课中课件引用数
	private Long inClassCwQuoteNums = 0L;
	// 课中课件编辑引用数
	private Long inClassCwEditNums = 0L;

	// 课前作业上传数
	private Long beforeHwUpNums = 0L;
	// 课前作业引用数
	private Long beforeHwQuoteNums = 0L;
	// 课前作业编辑引用数
	private Long beforeHwEditNums = 0L;

	// 课中作业上传数
	private Long inClassHwUpNums = 0L;
	// 课中作业引用数
	private Long inClassHwQuoteNums = 0L;
	// 课中作业编辑引用数
	private Long inClassHwEditNums = 0L;
	
	// 课后作业上传数
	private Long afterHwUpNums = 0L;
	// 课后作业引用数
	private Long afterHwQuoteNums = 0L;
	// 课后作业编辑引用数
	private Long afterHwEditNums = 0L;

	// 备课包上传数
	private Long bkgUpNums = 0L;
	// 备课包引用数
	private Long bkgQuoteNums = 0L;
	// 备课包编辑引用数
	private Long bkgEditNums = 0L;

	// 课前微课上传数
	private Long beforeWkUpNums = 0L;
	// 课前微课引用数
	private Long beforeWkQuoteNums = 0L;
	
	// 课前微课上传数
	private Long afterWkUpNums = 0L;
	// 课前微课引用数
	private Long afterWkQuoteNums = 0L;

	// 课件课堂数
	private Long CwLessonNums = 0L;
	// 作业课堂数
	private Long HwLessonNums = 0L;
	/**
	 * @return the mustLessons
	 */
	public Long getMustLessons()
	{
		return mustLessons;
	}

	/**
	 * @param mustLessons
	 *            the mustLessons to set
	 */
	public void setMustLessons(Long mustLessons)
	{
		this.mustLessons = mustLessons;
	}

	/**
	 * @return the beikeLessons
	 */
	public Long getBeikeLessons()
	{
		return beikeLessons;
	}

	/**
	 * @param beikeLessons
	 *            the beikeLessons to set
	 */
	public void setBeikeLessons(Long beikeLessons)
	{
		this.beikeLessons = beikeLessons;
	}

	/**
	 * @return the beforeCwUpNums
	 */
	public Long getBeforeCwUpNums()
	{
		return beforeCwUpNums;
	}

	/**
	 * @param beforeCwUpNums
	 *            the beforeCwUpNums to set
	 */
	public void setBeforeCwUpNums(Long beforeCwUpNums)
	{
		this.beforeCwUpNums = beforeCwUpNums;
	}

	/**
	 * @return the beforeCwQuoteNums
	 */
	public Long getBeforeCwQuoteNums()
	{
		return beforeCwQuoteNums;
	}

	/**
	 * @param beforeCwQuoteNums
	 *            the beforeCwQuoteNums to set
	 */
	public void setBeforeCwQuoteNums(Long beforeCwQuoteNums)
	{
		this.beforeCwQuoteNums = beforeCwQuoteNums;
	}

	/**
	 * @return the beforeCwEditNums
	 */
	public Long getBeforeCwEditNums()
	{
		return beforeCwEditNums;
	}

	/**
	 * @param beforeCwEditNums
	 *            the beforeCwEditNums to set
	 */
	public void setBeforeCwEditNums(Long beforeCwEditNums)
	{
		this.beforeCwEditNums = beforeCwEditNums;
	}

	/**
	 * @return the inClassCwUpNums
	 */
	public Long getInClassCwUpNums()
	{
		return inClassCwUpNums;
	}

	/**
	 * @param inClassCwUpNums
	 *            the inClassCwUpNums to set
	 */
	public void setInClassCwUpNums(Long inClassCwUpNums)
	{
		this.inClassCwUpNums = inClassCwUpNums;
	}

	/**
	 * @return the inClassCwQuoteNums
	 */
	public Long getInClassCwQuoteNums()
	{
		return inClassCwQuoteNums;
	}

	/**
	 * @param inClassCwQuoteNums
	 *            the inClassCwQuoteNums to set
	 */
	public void setInClassCwQuoteNums(Long inClassCwQuoteNums)
	{
		this.inClassCwQuoteNums = inClassCwQuoteNums;
	}

	/**
	 * @return the inClassCwEditNums
	 */
	public Long getInClassCwEditNums()
	{
		return inClassCwEditNums;
	}

	/**
	 * @param inClassCwEditNums
	 *            the inClassCwEditNums to set
	 */
	public void setInClassCwEditNums(Long inClassCwEditNums)
	{
		this.inClassCwEditNums = inClassCwEditNums;
	}

	/**
	 * @return the beforeHwUpNums
	 */
	public Long getBeforeHwUpNums()
	{
		return beforeHwUpNums;
	}

	/**
	 * @param beforeHwUpNums
	 *            the beforeHwUpNums to set
	 */
	public void setBeforeHwUpNums(Long beforeHwUpNums)
	{
		this.beforeHwUpNums = beforeHwUpNums;
	}

	/**
	 * @return the beforeHwQuoteNums
	 */
	public Long getBeforeHwQuoteNums()
	{
		return beforeHwQuoteNums;
	}

	/**
	 * @param beforeHwQuoteNums
	 *            the beforeHwQuoteNums to set
	 */
	public void setBeforeHwQuoteNums(Long beforeHwQuoteNums)
	{
		this.beforeHwQuoteNums = beforeHwQuoteNums;
	}

	/**
	 * @return the beforeHwEditNums
	 */
	public Long getBeforeHwEditNums()
	{
		return beforeHwEditNums;
	}

	/**
	 * @param beforeHwEditNums
	 *            the beforeHwEditNums to set
	 */
	public void setBeforeHwEditNums(Long beforeHwEditNums)
	{
		this.beforeHwEditNums = beforeHwEditNums;
	}

	/**
	 * @return the inClassHwUpNums
	 */
	public Long getInClassHwUpNums()
	{
		return inClassHwUpNums;
	}

	/**
	 * @param inClassHwUpNums
	 *            the inClassHwUpNums to set
	 */
	public void setInClassHwUpNums(Long inClassHwUpNums)
	{
		this.inClassHwUpNums = inClassHwUpNums;
	}

	/**
	 * @return the inClassHwQuoteNums
	 */
	public Long getInClassHwQuoteNums()
	{
		return inClassHwQuoteNums;
	}

	/**
	 * @param inClassHwQuoteNums
	 *            the inClassHwQuoteNums to set
	 */
	public void setInClassHwQuoteNums(Long inClassHwQuoteNums)
	{
		this.inClassHwQuoteNums = inClassHwQuoteNums;
	}

	/**
	 * @return the inClassHwEditNums
	 */
	public Long getInClassHwEditNums()
	{
		return inClassHwEditNums;
	}

	/**
	 * @param inClassHwEditNums
	 *            the inClassHwEditNums to set
	 */
	public void setInClassHwEditNums(Long inClassHwEditNums)
	{
		this.inClassHwEditNums = inClassHwEditNums;
	}

	/**
	 * @return the bkgUpNums
	 */
	public Long getBkgUpNums()
	{
		return bkgUpNums;
	}

	/**
	 * @param bkgUpNums
	 *            the bkgUpNums to set
	 */
	public void setBkgUpNums(Long bkgUpNums)
	{
		this.bkgUpNums = bkgUpNums;
	}

	/**
	 * @return the bkgQuoteNums
	 */
	public Long getBkgQuoteNums()
	{
		return bkgQuoteNums;
	}

	/**
	 * @param bkgQuoteNums
	 *            the bkgQuoteNums to set
	 */
	public void setBkgQuoteNums(Long bkgQuoteNums)
	{
		this.bkgQuoteNums = bkgQuoteNums;
	}

	/**
	 * @return the bkgEditNums
	 */
	public Long getBkgEditNums()
	{
		return bkgEditNums;
	}

	/**
	 * @param bkgEditNums
	 *            the bkgEditNums to set
	 */
	public void setBkgEditNums(Long bkgEditNums)
	{
		this.bkgEditNums = bkgEditNums;
	}


	/**
	 * @return the afterHwUpNums
	 */
	public Long getAfterHwUpNums()
	{
		return afterHwUpNums;
	}

	/**
	 * @param afterHwUpNums the afterHwUpNums to set
	 */
	public void setAfterHwUpNums(Long afterHwUpNums)
	{
		this.afterHwUpNums = afterHwUpNums;
	}

	/**
	 * @return the afterHwQuoteNums
	 */
	public Long getAfterHwQuoteNums()
	{
		return afterHwQuoteNums;
	}

	/**
	 * @param afterHwQuoteNums the afterHwQuoteNums to set
	 */
	public void setAfterHwQuoteNums(Long afterHwQuoteNums)
	{
		this.afterHwQuoteNums = afterHwQuoteNums;
	}

	/**
	 * @return the afterHwEditNums
	 */
	public Long getAfterHwEditNums()
	{
		return afterHwEditNums;
	}

	/**
	 * @param afterHwEditNums the afterHwEditNums to set
	 */
	public void setAfterHwEditNums(Long afterHwEditNums)
	{
		this.afterHwEditNums = afterHwEditNums;
	}

	/**
	 * @return the beforeWkUpNums
	 */
	public Long getBeforeWkUpNums()
	{
		return beforeWkUpNums;
	}

	/**
	 * @param beforeWkUpNums the beforeWkUpNums to set
	 */
	public void setBeforeWkUpNums(Long beforeWkUpNums)
	{
		this.beforeWkUpNums = beforeWkUpNums;
	}

	/**
	 * @return the beforeWkQuoteNums
	 */
	public Long getBeforeWkQuoteNums()
	{
		return beforeWkQuoteNums;
	}

	/**
	 * @param beforeWkQuoteNums the beforeWkQuoteNums to set
	 */
	public void setBeforeWkQuoteNums(Long beforeWkQuoteNums)
	{
		this.beforeWkQuoteNums = beforeWkQuoteNums;
	}

	/**
	 * @return the afterWkUpNums
	 */
	public Long getAfterWkUpNums()
	{
		return afterWkUpNums;
	}

	/**
	 * @param afterWkUpNums the afterWkUpNums to set
	 */
	public void setAfterWkUpNums(Long afterWkUpNums)
	{
		this.afterWkUpNums = afterWkUpNums;
	}

	/**
	 * @return the afterWkQuoteNums
	 */
	public Long getAfterWkQuoteNums()
	{
		return afterWkQuoteNums;
	}

	/**
	 * @param afterWkQuoteNums the afterWkQuoteNums to set
	 */
	public void setAfterWkQuoteNums(Long afterWkQuoteNums)
	{
		this.afterWkQuoteNums = afterWkQuoteNums;
	}

	/**
	 * @return the cwLessonNums
	 */
	public Long getCwLessonNums()
	{
		return CwLessonNums;
	}

	/**
	 * @param cwLessonNums the cwLessonNums to set
	 */
	public void setCwLessonNums(Long cwLessonNums)
	{
		CwLessonNums = cwLessonNums;
	}

	/**
	 * @return the hwLessonNums
	 */
	public Long getHwLessonNums()
	{
		return HwLessonNums;
	}

	/**
	 * @param hwLessonNums the hwLessonNums to set
	 */
	public void setHwLessonNums(Long hwLessonNums)
	{
		HwLessonNums = hwLessonNums;
	}

}
