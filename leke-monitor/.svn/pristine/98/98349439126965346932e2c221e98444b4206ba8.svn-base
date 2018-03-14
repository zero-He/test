package cn.strong.leke.monitor.mongo.online.model.query;


import java.util.List;

import org.bson.Document;

import cn.strong.leke.common.utils.StringUtils;

public class ActiveUserQuery {
	
	private String fromDate;
	private String endDate;
	private String cycle;
	private Integer d; // 设备端
	private String os; // 操作系统 
	private Long schoolId;
	private String schoolName;//学习名称
	private String startMonth;
	private String endMonth;
	private String startWeek;
	private String endWeek;
	private Long userId;
	private Integer day;
	private Integer week;
	private Integer month;
	private Long roleId;
	private Integer ts;
	private  String className;
	private String gradeName;
	private String terminal;
	private Long sellerId;
	private String sellerName;
	private String loginName;
	private Long marketId;
	private String marketName;
	private List<Long> listMarketId;
	private List<Long> listSeller;
	private Long market;
	private String departName;
	private Long departId;
	private List<String> classes;
	private List<String> grades;
	
	public Document toBSON() {
		if (cycle == null) {
			throw new IllegalStateException("cycle should not be null");
		}
		Document filter = new Document();
		if (d != null &&  d != 10 ) {
			filter.append("d", d);
		}
		if (StringUtils.isNotEmpty(className) && !className.equals("全部")) {
			filter.append("className", className);
		}
		if (StringUtils.isNotEmpty(gradeName) && !gradeName.equals("全部")) {
			filter.append("gradeName", gradeName);
		}
		if (week != null) {
			filter.append("week", week);
		}
		if (month != null) {
			filter.append("month", month);
		}
		if (day != null) {
			filter.append("ts", day);
		}
		if (roleId != null) {
			filter.append("roleId", roleId);
		}
			
		return filter;
	}
	
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public List<Long> getListMarketId() {
		return listMarketId;
	}

	public void setListMarketId(List<Long> listMarketId) {
		this.listMarketId = listMarketId;
	}

	public List<Long> getListSeller() {
		return listSeller;
	}

	public void setListSeller(List<Long> listSeller) {
		this.listSeller = listSeller;
	}

	public Long getMarket() {
		return market;
	}

	public void setMarket(Long market) {
		this.market = market;
	}



	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public Integer getTs() {
		return ts;
	}

	public void setTs(Integer ts) {
		this.ts = ts;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public Integer getD() {
		return d;
	}
	public void setD(Integer d) {
		this.d = d;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}
	public String getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}
	public String getStartWeek() {
		return startWeek;
	}
	public void setStartWeek(String startWeek) {
		this.startWeek = startWeek;
	}
	public String getEndWeek() {
		return endWeek;
	}
	public void setEndWeek(String endWeek) {
		this.endWeek = endWeek;
	}

	public List<String> getClasses() {
		return classes;
	}

	public void setClasses(List<String> classes) {
		this.classes = classes;
	}

	public List<String> getGrades() {
		return grades;
	}

	public void setGrades(List<String> grades) {
		this.grades = grades;
	}
  

}
