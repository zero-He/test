package cn.strong.leke.diag.dao.homework.model;

public class WorkFinishAnaly {

	private Long userId;
	private Integer totalNum; // 作业总数
	private Integer normalNum; // 正常提交数
	private Integer delayNum; // 延迟提交数
	private Integer doneBugFixNum; // 已订正数量
	private Integer undoBugFixNum; // 未订正数量

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getNormalNum() {
		return normalNum;
	}

	public void setNormalNum(Integer normalNum) {
		this.normalNum = normalNum;
	}

	public Integer getDelayNum() {
		return delayNum;
	}

	public void setDelayNum(Integer delayNum) {
		this.delayNum = delayNum;
	}

	public Integer getDoneBugFixNum() {
		return doneBugFixNum;
	}

	public void setDoneBugFixNum(Integer doneBugFixNum) {
		this.doneBugFixNum = doneBugFixNum;
	}

	public Integer getUndoBugFixNum() {
		return undoBugFixNum;
	}

	public void setUndoBugFixNum(Integer undoBugFixNum) {
		this.undoBugFixNum = undoBugFixNum;
	}
}
