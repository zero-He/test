package cn.strong.leke.diag.model;

public class Exp {
	//当前经验
	private Integer exp;
	//等级
	private Integer level;
	//离下次升级所需经验
	private Integer lackExp;

	private String honor;

	private Integer percent;

	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getLackExp() {
		return lackExp;
	}

	public void setLackExp(Integer lackExp) {
		this.lackExp = lackExp;
	}

	public String getHonor() {
		return honor;
	}

	public void setHonor(String honor) {
		this.honor = honor;
	}

	public Integer getPercent() {
		return percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}

}
