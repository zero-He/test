package cn.strong.leke.diag.dao.lesson.model;

public class UserAttendInfo extends BaseAttend {

	private static final long serialVersionUID = -3062459066321878141L;

	private Long userId;
	private String userName;
	private String userNamePy;
	private Integer totalNum;
	private Integer realNum;

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

	public String getUserNamePy() {
		return userNamePy;
	}

	public void setUserNamePy(String userNamePy) {
		this.userNamePy = userNamePy;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getRealNum() {
		return realNum;
	}

	public void setRealNum(Integer realNum) {
		this.realNum = realNum;
	}
}