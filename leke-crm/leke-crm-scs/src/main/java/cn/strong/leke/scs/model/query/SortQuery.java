package cn.strong.leke.scs.model.query;

/**
 * 排序条件。
 * @author  andy
 * @created 2015年6月16日 上午10:11:37
 * @since   v1.0.0
 */
public class SortQuery {

	public static final String SORT_ASC = "ASC";
	public static final String SORT_DESC = "DESC";

	// 排序字段
	private String order;
	private String sort = SORT_ASC;

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
