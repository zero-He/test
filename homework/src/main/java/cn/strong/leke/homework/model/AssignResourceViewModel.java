package cn.strong.leke.homework.model;

import java.io.Serializable;

public class AssignResourceViewModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7088085301100888285L;
	
	private Long resId;
	private String resName;
	private Integer resType;
	private String suffix;
	/**
	 * @return the resId
	 */
	public Long getResId() {
		return resId;
	}
	/**
	 * @param resId the resId to set
	 */
	public void setResId(Long resId) {
		this.resId = resId;
	}
	/**
	 * @return the resName
	 */
	public String getResName() {
		return resName;
	}
	/**
	 * @param resName the resName to set
	 */
	public void setResName(String resName) {
		this.resName = resName;
	}
	/**
	 * @return the resType
	 */
	public Integer getResType() {
		return resType;
	}
	/**
	 * @param resType the resType to set
	 */
	public void setResType(Integer resType) {
		this.resType = resType;
	}
	/**
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}
	/**
	 * @param suffix the suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	
}
