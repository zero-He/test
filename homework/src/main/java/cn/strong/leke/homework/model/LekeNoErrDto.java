package cn.strong.leke.homework.model;

public class LekeNoErrDto {

	private String pageId;
	private String lekeNo;
	private String stuno;
	private Long userId;
	private String userName;
	private Long homeworkId;
	private Long homeworkDtlId;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getLekeNo() {
		return lekeNo;
	}

	public void setLekeNo(String lekeNo) {
		this.lekeNo = lekeNo;
	}

	public String getStuno() {
		return stuno;
	}

	public void setStuno(String stuno) {
		this.stuno = stuno;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
	}

	public Long getHomeworkDtlId() {
		return homeworkDtlId;
	}

	public void setHomeworkDtlId(Long homeworkDtlId) {
		this.homeworkDtlId = homeworkDtlId;
	}
}