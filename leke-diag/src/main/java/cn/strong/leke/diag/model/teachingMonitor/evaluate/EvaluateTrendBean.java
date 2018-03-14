package cn.strong.leke.diag.model.teachingMonitor.evaluate;

import cn.strong.leke.diag.model.teachingMonitor.CommProp;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-20 20:45:19
 */
public class EvaluateTrendBean extends CommProp {

	private int totalLevel;
	private double avgTotal;

	public int getTotalLevel() {
		return totalLevel;
	}

	public void setTotalLevel(int totalLevel) {
		this.totalLevel = totalLevel;
	}

	public double getAvgTotal() {
		return avgTotal;
	}

	public void setAvgTotal(double avgTotal) {
		this.avgTotal = avgTotal;
	}
}
