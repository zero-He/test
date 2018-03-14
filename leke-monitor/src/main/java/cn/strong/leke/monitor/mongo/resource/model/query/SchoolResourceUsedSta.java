package cn.strong.leke.monitor.mongo.resource.model.query;


public class SchoolResourceUsedSta {
	
	private String schoolName;
	private Long schoolId;
	private int usedNum;
	private int downloadNum;
	private int quoteNum;
	private int compileNum;
	
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	public int getUsedNum() {
		return usedNum;
	}
	public void setUsedNum(int usedNum) {
		this.usedNum = usedNum;
	}
	public int getDownloadNum() {
		return downloadNum;
	}
	public void setDownloadNum(int downloadNum) {
		this.downloadNum = downloadNum;
	}
	public int getQuoteNum() {
		return quoteNum;
	}
	public void setQuoteNum(int quoteNum) {
		this.quoteNum = quoteNum;
	}
	public int getCompileNum() {
		return compileNum;
	}
	public void setCompileNum(int compileNum) {
		this.compileNum = compileNum;
	}


	

}
