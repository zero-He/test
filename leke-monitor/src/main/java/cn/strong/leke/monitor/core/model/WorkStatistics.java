package cn.strong.leke.monitor.core.model;

import java.sql.Date;
import java.util.List;

import cn.strong.leke.model.BaseModel;

/**
 * @ClassName: WorkStatistics
 * @Description: 新增统计
 * @author huangkai
 * @date 2016年11月22日 上午10:35:40
 * @version V1.0
 */
public class WorkStatistics
{
	private Long sellerId;
	private String sellerName;
	private Long schoolId;
	private String schoolName;
	private Long marketId;
	private String marketName;
	private Integer studentNum;
	private Integer parentNum;
	private Integer teacherNum;
	private Integer allUserNum;
	private Integer workStudentNum;
	private Integer buyNum;
	private String lekeSn;
	private String studentName;
	private Long gradeId;
	private String gradeName;
	private Date useDate;
	private String statisticsTimeStart;
	private String statisticsTimeEnd;
	private Long schoolStageId;
	private String schoolStageName;
	private List<Long> listMarketId;
	private String type;
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
	 * @Description: 客户经理
	 */
	public String getSellerName()
	{
		return sellerName;
	}

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
	 * @Description: 新增学生数
	 */
	public Integer getStudentNum()
	{
		return studentNum;
	}

	/**
	 * @Description: 新增学生数
	 */
	public void setStudentNum(Integer studentNum)
	{
		this.studentNum = studentNum;
	}

	/**
	 * @Description: 作业系统新增学生数
	 */
	public Integer getWorkStudentNum()
	{
		return workStudentNum;
	}

	/**
	 * @Description: 作业系统新增学生数
	 */
	public void setWorkStudentNum(Integer workStudentNum)
	{
		this.workStudentNum = workStudentNum;
	}

	/**
	 * @Description: 新增购课人数
	 */
	public Integer getBuyNum()
	{
		return buyNum;
	}

	/**
	 * @Description: 新增购课人数
	 */
	public void setBuyNum(Integer buyNum)
	{
		this.buyNum = buyNum;
	}

	/**
	 * @Description: 乐号
	 */
	public String getLekeSn()
	{
		return lekeSn;
	}

	/**
	 * @Description: 乐号
	 */
	public void setLekeSn(String lekeSn)
	{
		this.lekeSn = lekeSn;
	}

	/**
	 * @Description: 姓名
	 */
	public String getStudentName()
	{
		return studentName;
	}

	/**
	 * @Description: 姓名
	 */
	public void setStudentName(String studentName)
	{
		this.studentName = studentName;
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
	 * @Description: 首次使用时间
	 */
	public Date getUseDate()
	{
		return useDate;
	}

	/**
	 * @Description: 首次使用时间格式化
	 */
	public String getUseDateStr()
	{
		return BaseModel.formatDate(useDate);
	}

	/**
	 * @Description: 首次使用时间
	 */
	public void setUseDate(Date useDate)
	{
		this.useDate = useDate;
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
	 * @Description: 类型
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @Description: 类型
	 */
	public void setType(String type)
	{
		this.type = type;
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

	public Integer getParentNum() {
		return parentNum;
	}

	public void setParentNum(Integer parentNum) {
		this.parentNum = parentNum;
	}

	public Integer getTeacherNum() {
		return teacherNum;
	}

	public void setTeacherNum(Integer teacherNum) {
		this.teacherNum = teacherNum;
	}

	public Integer getAllUserNum() {
		return allUserNum;
	}

	public void setAllUserNum(Integer allUserNum) {
		this.allUserNum = allUserNum;
	}
	
}
