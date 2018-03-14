package cn.strong.leke.diag.model;

public class UserCount {

	private Long userId;
	private Integer num;

	public UserCount() {
	}

	public UserCount(Long userId, Integer num) {
		this.userId = userId;
		this.num = num;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
}
