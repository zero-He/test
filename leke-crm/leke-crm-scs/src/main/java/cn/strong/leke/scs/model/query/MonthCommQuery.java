package cn.strong.leke.scs.model.query;

import java.util.Date;

/**
 * 销售月佣金查询。
 * @author  andy
 * @created 2015年6月16日 下午1:39:13
 * @since   v1.0.0
 */
public class MonthCommQuery extends SortQuery {

	// 月份(201501)
	private Date month;
	// 销售姓名
	private String sellerName;

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
}
