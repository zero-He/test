package cn.strong.leke.scs.model;

import java.util.Date;

import cn.strong.leke.model.BaseModel;

public class Customer extends BaseModel {

	private static final long serialVersionUID = 4358294001533116153L;
	// 客户ID
	private Long customerId;
	// 客户名称
	private String customerName;
	// 客户类型
	private Integer customerType;
	// 乐课ID
	private Long lekeId;
	// 乐课乐号
	private String lekeLoginName;
	// 客户手机
	private String phone;
	// 销售ID
	private Long sellerId;
	// 销售姓名
	private String sellerName;
	// 客户状态
	private Integer status;
	// 绑定时间
	private Date bindTime;
	// 首次交易时间
	private Date consumeTime;
	// 备注信息
	private String remark;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public Long getLekeId() {
		return lekeId;
	}

	public void setLekeId(Long lekeId) {
		this.lekeId = lekeId;
	}

	public String getLekeLoginName() {
		return lekeLoginName;
	}

	public void setLekeLoginName(String lekeLoginName) {
		this.lekeLoginName = lekeLoginName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getBindTime() {
		return bindTime;
	}

	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}

	public Date getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 绑定状态
	 * 
	 * @return
	 */
	public String getStatusStr()
	{
		String str = "";
		switch (this.getStatus())
		{
		case 0:
			str = "待审核";
			break;
		case 1:
			str = "已绑定";
			break;
		case 2:
			str = "拒绝绑定";
			break;
		default:
			str = "待审核";
			break;
		}
		return str;
	}
}
