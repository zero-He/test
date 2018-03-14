package cn.strong.leke.scs.model;

public class CustomerVO extends Customer {

	private static final long serialVersionUID = -7232237725128234624L;

	private Integer balance;
	private Integer total;
	
	//以下 1.9 新加 2015-11-30，学校客户列表
	private String schoolStageNames; // 学校对应学段ID 数组
	private String schoolStageID; // 学校对应学段ID 数组
	
	private String schoolEmail;//学校的注册邮箱
	
	//是否强制使用客户端 0否 1是
	private Integer isWeb;

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getSchoolStageNames() {
		return schoolStageNames;
	}

	public void setSchoolStageNames(String schoolStageNames) {
		this.schoolStageNames = schoolStageNames;
	}

	public String getSchoolEmail() {
		return schoolEmail;
	}

	public void setSchoolEmail(String schoolEmail) {
		this.schoolEmail = schoolEmail;
	}

	public Integer getIsWeb() {
		return isWeb;
	}

	public void setIsWeb(Integer isWeb) {
		this.isWeb = isWeb;
	}
	
}
