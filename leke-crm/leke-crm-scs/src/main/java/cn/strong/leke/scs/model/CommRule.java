package cn.strong.leke.scs.model;

import java.util.Date;

import cn.strong.leke.model.BaseModel;

/**
 * 佣金规则表。
 * @author  andy
 * @created 2015年6月15日 下午2:27:56
 * @since   v1.0.0
 */
public class CommRule extends BaseModel {

	private static final long serialVersionUID = -4103919778709502510L;
	// 佣金规则ID
	private Long ruleId;
	// 佣金类型
	private Integer commType;
	// 规则明细
	private String detail;
	// 生效时间
	private Date validTime;
	// 失效时间
	private Date expireTime;

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getCommType() {
		return commType;
	}

	public void setCommType(Integer commType) {
		this.commType = commType;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
}
