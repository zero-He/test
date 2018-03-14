package cn.strong.leke.diag.model.studentMonitor;

/**
 * @author LIU.SHITING
 * @version 1.5
 * @date 2017-11-28 10:41:54
 */
public class ReadyBean extends ReadyTrendBean{

	private int allReady;
	private int noReady;
	private int partReady;

	public int getAllReady() {
		return allReady;
	}

	public void setAllReady(int allReady) {
		this.allReady = allReady;
	}

	public int getNoReady() {
		return noReady;
	}

	public void setNoReady(int noReady) {
		this.noReady = noReady;
	}

	public int getPartReady() {
		return partReady;
	}

	public void setPartReady(int partReady) {
		this.partReady = partReady;
	}

}
