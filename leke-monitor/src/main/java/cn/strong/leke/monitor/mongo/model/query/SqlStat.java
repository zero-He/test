/**
 * 
 */
package cn.strong.leke.monitor.mongo.model.query;

/**
 * SQL 统计
 * 
 * @author liulongbiao
 *
 */
public class SqlStat {

	private String hash;
	private Long count;
	private Long maxCostTime;
	private Long totalCostTime;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getMaxCostTime() {
		return maxCostTime;
	}

	public void setMaxCostTime(Long maxCostTime) {
		this.maxCostTime = maxCostTime;
	}

	public Long getTotalCostTime() {
		return totalCostTime;
	}

	public void setTotalCostTime(Long totalCostTime) {
		this.totalCostTime = totalCostTime;
	}

}
