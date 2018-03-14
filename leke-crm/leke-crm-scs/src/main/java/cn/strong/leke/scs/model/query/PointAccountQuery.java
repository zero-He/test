package cn.strong.leke.scs.model.query;

/**
 * 点账户查询条件。
 * @author  andy
 * @created 2015年6月17日 下午4:32:46
 * @since   v1.0.0
 */
public class PointAccountQuery extends SortQuery {

	// 销售ID
	private Long sellerId;
	// 销售名称
	private String sellerName;
	// 账户拥有者名称
	private String ownerName;
	// 账户拥有者乐号
	private String loginName;

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
