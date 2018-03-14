package cn.strong.leke.homework.model.mobile;

public class MobileQuery {

	private Integer curPage;
	private Integer pageSize;
	private Boolean counter;

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public Boolean getCounter() {
		return counter;
	}

	public void setCounter(Boolean counter) {
		this.counter = counter;
	}

	public Integer getStart() {
		if (this.curPage <= 0) {
			return 0;
		}
		return (this.curPage - 1) * this.pageSize;
	}
	
	public Integer getLimit() {
		return this.pageSize;
	}
}
