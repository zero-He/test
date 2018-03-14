package cn.strong.leke.diag.model.teachingMonitor;


import java.util.List;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-19 11:09:46
 */
public class ReturnResultBean {

	private Object countBean;
	private Object trendList;
	private Object compareBeanList;
	private Object rankFrontBeanList;
	private Object rankBackBeanList;

	public Object getCountBean() {
		return countBean;
	}

	public void setCountBean(Object countBean) {
		this.countBean = countBean;
	}

	public Object getTrendList() {
		return trendList;
	}

	public void setTrendList(Object trendList) {
		this.trendList = trendList;
	}

	public Object getCompareBeanList() {
		return compareBeanList;
	}

	public void setCompareBeanList(Object compareBeanList) {
		this.compareBeanList = compareBeanList;
	}

	public Object getRankFrontBeanList() {
		return rankFrontBeanList;
	}

	public void setRankFrontBeanList(Object rankFrontBeanList) {
		this.rankFrontBeanList = rankFrontBeanList;
	}

	public Object getRankBackBeanList() {
		return rankBackBeanList;
	}

	public void setRankBackBeanList(Object rankBackBeanList) {
		this.rankBackBeanList = rankBackBeanList;
	}

	private Object shareRankFrontBeanList;
	private Object shareRankBackBeanList;

	public Object getShareRankFrontBeanList() {
		return shareRankFrontBeanList;
	}

	public void setShareRankFrontBeanList(Object shareRankFrontBeanList) {
		this.shareRankFrontBeanList = shareRankFrontBeanList;
	}

	public Object getShareRankBackBeanList() {
		return shareRankBackBeanList;
	}

	public void setShareRankBackBeanList(Object shareRankBackBeanList) {
		this.shareRankBackBeanList = shareRankBackBeanList;
	}
}
