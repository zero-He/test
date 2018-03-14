package cn.strong.leke.homework.model.activity;

/**
 * 订正任务数据对象
 * @author Zhang Fujun
 * @date 2017年9月21日
 */
public class BugFixHwInfo {

	/**
	 * 需要订正的总数（含已订正过的）
	 */
	private Long bugfixTotal;
	/**
	 * 在 bugfixTotal 中的已订正数
	 */
	private Long finishTotal;
	/**
	 * @return the bugfixTotal
	 */
	public Long getBugfixTotal() {
		return bugfixTotal;
	}
	/**
	 * @param bugfixTotal the bugfixTotal to set
	 */
	public void setBugfixTotal(Long bugfixTotal) {
		this.bugfixTotal = bugfixTotal;
	}
	/**
	 * @return the finishTotal
	 */
	public Long getFinishTotal() {
		return finishTotal;
	}
	/**
	 * @param finishTotal the finishTotal to set
	 */
	public void setFinishTotal(Long finishTotal) {
		this.finishTotal = finishTotal;
	}
	
}
