package cn.strong.leke.diag.model;

public class StuKnoRate {

	private Long id;
	private String name;
	private Long userId;
	private Integer totalNum;
	private Double rightNum;
	private Double rightRate;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Double getRightNum() {
		return rightNum;
	}

	public void setRightNum(Double rightNum) {
		this.rightNum = rightNum;
	}

	public Double getRightRate() {
		return rightRate;
	}

	public void setRightRate(Double rightRate) {
		this.rightRate = rightRate;
	}
}
