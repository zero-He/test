/**
 * 
 */
package cn.strong.leke.monitor.mongo.appcenter.model;

import java.util.Date;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * 用户和 imei 关联表
 * 
 * @author liulongbiao
 *
 */
public class UserImei {

	@_id
	@ObjectId
	private String id;
	private String userId; // 用户ID
	private String userName;
	private String loginName;
	private String imei; // 移动设备识别码
	private Date ts; // 最后更新时间
	private String tsStr;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getTsStr() {
		return tsStr;
	}

	public void setTsStr(String tsStr) {
		this.tsStr = tsStr;
	}

}
