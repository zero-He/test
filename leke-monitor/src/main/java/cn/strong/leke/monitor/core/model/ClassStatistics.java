package cn.strong.leke.monitor.core.model;

import java.util.List;

/**
 * @ClassName: ClassStatistics
 * @Description: CRM课堂统计
 * @author huangkai
 * @date 2016年11月16日 上午9:22:09
 *
 */
public class ClassStatistics
{
	private Long sellerId;
	private String sellerName;
	private Long schoolId;
	private String schoolName;
	private Long schoolStageId;
	private String schoolStageName;
	private Long subjectId;
	private String subjectName;
	private Long gradeId;
	private String gradeName;
	private Long teacherId;
	private String teacherName;
	private Integer endClassNum;
	private Integer beikeClassNum;
	private Integer classNum;
	private String beikeRate;
	private String classRate;
	private Integer previewWorkNum;
	private Integer beiPreviewWorkNum;
	private String previewWorkRate;
	private Integer coursewareNum;
	private Integer beiCoursewareNum;
	private String coursewareRate;
	private Integer classWorkNum;
	private Integer beiClassWorkNum;
	private String classWorkRate;
	private Integer classTestNum;
	private String classTestRate;
	private Integer questionsAnswersNum;
	private String questionsAnswersRate;
	private Integer testAttenNum;
	private Integer askAttenNum;
	private Integer afterClassWorkNum;
	private Integer classWorkTotalNum;
	private Integer workFinishNum;
	private Integer workcorrectNum;
	private String workFinishRate;
	private String workcorrectRate;
	private String statisticsTimeStart;
	private String statisticsTimeEnd;
	private Long marketId;
	private String marketName;
	private List<Long> listMarketId;
	private String loginName;
	private List<Long> listSeller;
	private Long market;

	/**
	 * @Description: 客户经理ID
	 */
	public Long getSellerId()
	{
		return sellerId;
	}

	/**
	 * @Description: 客户经理ID
	 */
	public void setSellerId(Long sellerId)
	{
		this.sellerId = sellerId;
	}

	/**
	 * @Description: 客户经理姓名
	 */
	public String getSellerName()
	{
		return sellerName;
	}

	/**
	 * @Description: 客户经理姓名
	 */
	public void setSellerName(String sellerName)
	{
		this.sellerName = sellerName;
	}

	/**
	 * @Description: 学校ID
	 */
	public Long getSchoolId()
	{
		return schoolId;
	}

	/**
	 * @Description: 学校ID
	 */
	public void setSchoolId(Long schoolId)
	{
		this.schoolId = schoolId;
	}

	/**
	 * @Description: 学校名称
	 */
	public String getSchoolName()
	{
		return schoolName;
	}

	/**
	 * @Description: 学校名称
	 */
	public void setSchoolName(String schoolName)
	{
		this.schoolName = schoolName;
	}

	/**
	 * @Description: 学段ID
	 */
	public Long getSchoolStageId()
	{
		return schoolStageId;
	}

	/**
	 * @Description: 学段ID
	 */
	public void setSchoolStageId(Long schoolStageId)
	{
		this.schoolStageId = schoolStageId;
	}

	/**
	 * @Description: 学段名称
	 */
	public String getSchoolStageName()
	{
		return schoolStageName;
	}

	/**
	 * @Description: 学段名称
	 */
	public void setSchoolStageName(String schoolStageName)
	{
		this.schoolStageName = schoolStageName;
	}

	/**
	 * @Description: 学科ID
	 */
	public Long getSubjectId()
	{
		return subjectId;
	}

	/**
	 * @Description: 学科ID
	 */
	public void setSubjectId(Long subjectId)
	{
		this.subjectId = subjectId;
	}

	/**
	 * @Description: 学科名称
	 */
	public String getSubjectName()
	{
		return subjectName;
	}

	/**
	 * @Description: 学科名称
	 */
	public void setSubjectName(String subjectName)
	{
		this.subjectName = subjectName;
	}

	/**
	 * @Description: 年级ID
	 */
	public Long getGradeId()
	{
		return gradeId;
	}

	/**
	 * @Description: 年级ID
	 */
	public void setGradeId(Long gradeId)
	{
		this.gradeId = gradeId;
	}

	/**
	 * @Description: 年级名称
	 */
	public String getGradeName()
	{
		return gradeName;
	}

	/**
	 * @Description: 年级名称
	 */
	public void setGradeName(String gradeName)
	{
		this.gradeName = gradeName;
	}

	/**
	 * @Description: 老师ID
	 */
	public Long getTeacherId()
	{
		return teacherId;
	}

	/**
	 * @Description: 老师ID
	 */
	public void setTeacherId(Long teacherId)
	{
		this.teacherId = teacherId;
	}

	/**
	 * @Description: 老师姓名
	 */
	public String getTeacherName()
	{
		return teacherName;
	}

	/**
	 * @Description: 老师姓名
	 */
	public void setTeacherName(String teacherName)
	{
		this.teacherName = teacherName;
	}

	/**
	 * @Description: 已结束课堂数
	 */
	public Integer getEndClassNum()
	{
		return endClassNum;
	}

	/**
	 * @Description: 已结束课堂数
	 */
	public void setEndClassNum(Integer endClassNum)
	{
		this.endClassNum = endClassNum;
	}

	/**
	 * @Description: 备课课堂数
	 */
	public Integer getBeikeClassNum()
	{
		return beikeClassNum;
	}

	/**
	 * @Description: 备课课堂数
	 */
	public void setBeikeClassNum(Integer beikeClassNum)
	{
		this.beikeClassNum = beikeClassNum;
	}

	/**
	 * @Description: 上课课堂数
	 */
	public Integer getClassNum()
	{
		return classNum;
	}

	/**
	 * @Description: 上课课堂数
	 */
	public void setClassNum(Integer classNum)
	{
		this.classNum = classNum;
	}

	/**
	 * @Description: 备课率
	 */
	public String getBeikeRate()
	{
		return beikeRate;
	}

	/**
	 * @Description: 备课率
	 */
	public void setBeikeRate(String beikeRate)
	{
		this.beikeRate = beikeRate;
	}

	/**
	 * @Description: 上课率
	 */
	public String getClassRate()
	{
		return classRate;
	}

	/**
	 * @Description: 上课率
	 */
	public void setClassRate(String classRate)
	{
		this.classRate = classRate;
	}

	/**
	 * @Description: 预习作业布置份数
	 */
	public Integer getPreviewWorkNum()
	{
		return previewWorkNum;
	}

	/**
	 * @Description: 预习作业布置份数
	 */
	public void setPreviewWorkNum(Integer previewWorkNum)
	{
		this.previewWorkNum = previewWorkNum;
	}

	/**
	 * @Description: 备预习作业布置份数
	 */
	public Integer getBeiPreviewWorkNum()
	{
		return beiPreviewWorkNum;
	}

	/**
	 * @Description: 备预习作业布置份数
	 */
	public void setBeiPreviewWorkNum(Integer beiPreviewWorkNum)
	{
		this.beiPreviewWorkNum = beiPreviewWorkNum;
	}

	/**
	 * @Description: 预习作业绑定率
	 */
	public String getPreviewWorkRate()
	{
		return previewWorkRate;
	}

	/**
	 * @Description: 预习作业绑定率
	 */
	public void setPreviewWorkRate(String previewWorkRate)
	{
		this.previewWorkRate = previewWorkRate;
	}

	/**
	 * @Description: 课件数
	 */
	public Integer getCoursewareNum()
	{
		return coursewareNum;
	}

	/**
	 * @Description: 课件数
	 */
	public void setCoursewareNum(Integer coursewareNum)
	{
		this.coursewareNum = coursewareNum;
	}

	/**
	 * @Description: 备课件数
	 */
	public Integer getBeiCoursewareNum()
	{
		return beiCoursewareNum;
	}

	/**
	 * @Description: 备课件数
	 */
	public void setBeiCoursewareNum(Integer beiCoursewareNum)
	{
		this.beiCoursewareNum = beiCoursewareNum;
	}

	/**
	 * @Description: 课件绑定率
	 */
	public String getCoursewareRate()
	{
		return coursewareRate;
	}

	/**
	 * @Description: 课件绑定率
	 */
	public void setCoursewareRate(String coursewareRate)
	{
		this.coursewareRate = coursewareRate;
	}

	/**
	 * @Description: 随堂作业布置份数
	 */
	public Integer getClassWorkNum()
	{
		return classWorkNum;
	}

	/**
	 * @Description: 随堂作业布置份数
	 */
	public void setClassWorkNum(Integer classWorkNum)
	{
		this.classWorkNum = classWorkNum;
	}

	/**
	 * @Description: 备随堂作业布置份数
	 */
	public Integer getBeiClassWorkNum()
	{
		return beiClassWorkNum;
	}

	/**
	 * @Description: 备随堂作业布置份数
	 */
	public void setBeiClassWorkNum(Integer beiClassWorkNum)
	{
		this.beiClassWorkNum = beiClassWorkNum;
	}

	/**
	 * @Description: 随堂作业绑定率
	 */
	public String getClassWorkRate()
	{
		return classWorkRate;
	}

	/**
	 * @Description: 随堂作业绑定率
	 */
	public void setClassWorkRate(String classWorkRate)
	{
		this.classWorkRate = classWorkRate;
	}

	/**
	 * @Description: 随堂测试次数
	 */
	public Integer getClassTestNum()
	{
		return classTestNum;
	}

	/**
	 * @Description: 随堂测试次数
	 */
	public void setClassTestNum(Integer classTestNum)
	{
		this.classTestNum = classTestNum;
	}

	/**
	 * @Description: 随堂测试发起率
	 */
	public String getClassTestRate()
	{
		return classTestRate;
	}

	/**
	 * @Description: 随堂测试发起率
	 */
	public void setClassTestRate(String classTestRate)
	{
		this.classTestRate = classTestRate;
	}

	/**
	 * @Description: 快速问答次数
	 */
	public Integer getQuestionsAnswersNum()
	{
		return questionsAnswersNum;
	}

	/**
	 * @Description: 快速问答次数
	 */
	public void setQuestionsAnswersNum(Integer questionsAnswersNum)
	{
		this.questionsAnswersNum = questionsAnswersNum;
	}

	/**
	 * @Description: 快速问答发起率
	 */
	public String getQuestionsAnswersRate()
	{
		return questionsAnswersRate;
	}

	/**
	 * @Description: 快速问答发起率
	 */
	public void setQuestionsAnswersRate(String questionsAnswersRate)
	{
		this.questionsAnswersRate = questionsAnswersRate;
	}

	/**
	 * @Description: 发起随堂测试课堂数
	 */
	public Integer getTestAttenNum()
	{
		return testAttenNum;
	}

	/**
	 * @Description: 发起随堂测试课堂数
	 */
	public void setTestAttenNum(Integer testAttenNum)
	{
		this.testAttenNum = testAttenNum;
	}

	/**
	 * @Description: 发起随堂问答课堂数
	 */
	public Integer getAskAttenNum()
	{
		return askAttenNum;
	}

	/**
	 * @Description: 发起随堂问答课堂数
	 */
	public void setAskAttenNum(Integer askAttenNum)
	{
		this.askAttenNum = askAttenNum;
	}

	/**
	 * @Description: 课后作业布置份数
	 */
	public Integer getAfterClassWorkNum()
	{
		return afterClassWorkNum;
	}

	/**
	 * @Description: 课后作业布置份数
	 */
	public void setAfterClassWorkNum(Integer afterClassWorkNum)
	{
		this.afterClassWorkNum = afterClassWorkNum;
	}

	/**
	 * @Description: 作业布置数
	 */
	public Integer getClassWorkTotalNum()
	{
		return classWorkTotalNum;
	}

	/**
	 * @Description: 作业布置数
	 */
	public void setClassWorkTotalNum(Integer classWorkTotalNum)
	{
		this.classWorkTotalNum = classWorkTotalNum;
	}

	/**
	 * @Description: 作业提交数
	 */
	public Integer getWorkFinishNum()
	{
		return workFinishNum;
	}

	/**
	 * @Description: 作业提交数
	 */
	public void setWorkFinishNum(Integer workFinishNum)
	{
		this.workFinishNum = workFinishNum;
	}

	/**
	 * @Description: 作业批改数
	 */
	public Integer getWorkcorrectNum()
	{
		return workcorrectNum;
	}

	/**
	 * @Description: 作业批改数
	 */
	public void setWorkcorrectNum(Integer workcorrectNum)
	{
		this.workcorrectNum = workcorrectNum;
	}

	/**
	 * @Description: 作业提交率
	 */
	public String getWorkFinishRate()
	{
		return workFinishRate;
	}

	/**
	 * @Description: 作业提交率
	 */
	public void setWorkFinishRate(String workFinishRate)
	{
		this.workFinishRate = workFinishRate;
	}

	/**
	 * @Description: 作业批改率
	 */
	public String getWorkcorrectRate()
	{
		return workcorrectRate;
	}

	/**
	 * @Description: 作业批改率
	 */
	public void setWorkcorrectRate(String workcorrectRate)
	{
		this.workcorrectRate = workcorrectRate;
	}

	/**
	 * @Description: 统计时间开始
	 */
	public String getStatisticsTimeStart()
	{
		return statisticsTimeStart;
	}

	/**
	 * @Description: 统计时间开始
	 */
	public void setStatisticsTimeStart(String statisticsTimeStart)
	{
		this.statisticsTimeStart = statisticsTimeStart;
	}

	/**
	 * @Description: 统计时间结束
	 */
	public String getStatisticsTimeEnd()
	{
		return statisticsTimeEnd;
	}

	/**
	 * @Description: 统计时间结束
	 */
	public void setStatisticsTimeEnd(String statisticsTimeEnd)
	{
		this.statisticsTimeEnd = statisticsTimeEnd;
	}

	/**
	 * @Description: 所属部门ID
	 */
	public Long getMarketId()
	{
		return marketId;
	}

	/**
	 * @Description: 所属部门ID
	 */
	public void setMarketId(Long marketId)
	{
		this.marketId = marketId;
	}

	/**
	 * @Description: 所属部门名称
	 */
	public String getMarketName()
	{
		return marketName;
	}

	/**
	 * @Description: 所属部门名称
	 */
	public void setMarketName(String marketName)
	{
		this.marketName = marketName;
	}

	/**
	 * @Description: 部门集合
	 */
	public List<Long> getListMarketId()
	{
		return listMarketId;
	}

	/**
	 * @Description: 部门集合
	 */
	public void setListMarketId(List<Long> listMarketId)
	{
		this.listMarketId = listMarketId;
	}

	/**
	 * @Description: 乐课号
	 */
	public String getLoginName()
	{
		return loginName;
	}

	/**
	 * @Description: 乐课号
	 */
	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}

	/**
	 * @Description: 客户经理集合
	 */
	public List<Long> getListSeller()
	{
		return listSeller;
	}

	/**
	 * @Description: 客户经理集合
	 */
	public void setListSeller(List<Long> listSeller)
	{
		this.listSeller = listSeller;
	}
	
	/**
	 * @Description: 营销部
	 */
	public Long getMarket()
	{
		return market;
	}

	/**
	 * @Description: 营销部
	 */
	public void setMarket(Long market)
	{
		this.market = market;
	}
}
