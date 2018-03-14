/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.scs.model;

import java.util.Date;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.model.BaseModel;

/**
 *
 * 描述:
 *
 * @author  yuanyi
 * @created 2015年9月1日 下午3:56:33
 * @since   v1.0.0
 */
public class TutorUser extends BaseModel {
	/**
	 * Description: 
	 */
	private static final long serialVersionUID = 1L;
	//'主键'
	private Long userId;
	//'用户名'
	private String userName;
	//'登录名'
	private String loginName;
	//'性别'
	private Integer sex;
	//'出生日期'
	private Date birthday;
	//'手机'
	private String phone;
	//'邮箱'
	private String email;
	//'微信'
	private String weixin;
	//'qq'
	private String qq;
	//'邮箱是否激活'
	private Boolean isEmailEnabl;
	//'用户是否激活'
	private Boolean isEnabled;
	//'是否冻结'
	private Boolean isFrozen;
	//'头像URL'
	private String photoUrl;
	//'注册时间'
	private Date regDate;
	//'来源平台 1-乐课网 2-施课网 3-执教网 4-其它'
	private Integer netType;
	//'注册方式 0-手工添加  1-注册  2-导入'
	private Integer origin;
	//'注册类型 1-手机 2-邮箱 3-合作网站用户'
	private Integer regType;
	//'学校id'
	private Long schoolId;
	//'客户经理'
	private String sellerName;
	//'学校名称'
	private String schoolName;
	
	public String getCreatedOnStr()
	{
		Date date = super.getCreatedOn();
		//if(StringUtils.isNotEmpty(date.toString())) -- bug date为null tostring报错 byDeo 170322
		if (date != null)
		{
			return super.formatDateTime(date).toString();
		}
		return "";
	}
	
	
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * @return the sex
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the weixin
	 */
	public String getWeixin() {
		return weixin;
	}
	/**
	 * @param weixin the weixin to set
	 */
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * @param qq the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * @return the isEmailEnabl
	 */
	public Boolean getIsEmailEnabl() {
		return isEmailEnabl;
	}
	/**
	 * @param isEmailEnabl the isEmailEnabl to set
	 */
	public void setIsEmailEnabl(Boolean isEmailEnabl) {
		this.isEmailEnabl = isEmailEnabl;
	}
	/**
	 * @return the isEnabled
	 */
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	/**
	 * @param isEnabled the isEnabled to set
	 */
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	/**
	 * @return the isFrozen
	 */
	public Boolean getIsFrozen() {
		return isFrozen;
	}
	/**
	 * @param isFrozen the isFrozen to set
	 */
	public void setIsFrozen(Boolean isFrozen) {
		this.isFrozen = isFrozen;
	}
	/**
	 * @return the photoUrl
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}
	/**
	 * @param photoUrl the photoUrl to set
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	/**
	 * @return the regDate
	 */
	public Date getRegDate() {
		return regDate;
	}
	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	/**
	 * @return the netType
	 */
	public Integer getNetType() {
		return netType;
	}
	/**
	 * @param netType the netType to set
	 */
	public void setNetType(Integer netType) {
		this.netType = netType;
	}
	/**
	 * @return the origin
	 */
	public Integer getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(Integer origin) {
		this.origin = origin;
	}
	/**
	 * @return the regType
	 */
	public Integer getRegType() {
		return regType;
	}
	/**
	 * @param regType the regType to set
	 */
	public void setRegType(Integer regType) {
		this.regType = regType;
	}
	/**
	 * @return the schoolId
	 */
	public Long getSchoolId() {
		return schoolId;
	}
	/**
	 * @param schoolId the schoolId to set
	 */
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	/**
	 * @return the sellerName
	 */
	public String getSellerName() {
		return sellerName;
	}
	/**
	 * @param sellerName the sellerName to set
	 */
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	/**
	 * @return the schoolName
	 */
	public String getSchoolName() {
		return schoolName;
	}
	/**
	 * @param schoolName the schoolName to set
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}


	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
