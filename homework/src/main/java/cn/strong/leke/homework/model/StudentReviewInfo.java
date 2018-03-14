package cn.strong.leke.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class StudentReviewInfo {

	private Long userId;
	private Integer state; // 1: 全部预习，2：部分预习
	private Integer score; // 平均得分
	@JsonIgnore
	private Integer total;
	@JsonIgnore
	private Integer finish;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getFinish() {
		return finish;
	}

	public void setFinish(Integer finish) {
		this.finish = finish;
	}
}
