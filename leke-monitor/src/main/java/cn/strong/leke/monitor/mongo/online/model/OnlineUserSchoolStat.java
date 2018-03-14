/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import cn.strong.leke.data.mongo.annotations._id;

/**
 * 学校用户数统计
 * 
 * @author liulongbiao
 *
 */
public class OnlineUserSchoolStat extends OnlineUserStat {
	@_id
	private Long schoolId; // 学校ID
	private String schoolName; // 学校名称
	private Integer schoolCode; // 学校编码
	private Integer schoolNature; // 学校性质
	private List<Long> regionIds; // 区域ID列表
	private Date modifiedOn; // 修改时间

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Integer getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(Integer schoolCode) {
		this.schoolCode = schoolCode;
	}

	public Integer getSchoolNature() {
		return schoolNature;
	}

	public void setSchoolNature(Integer schoolNature) {
		this.schoolNature = schoolNature;
	}

	public List<Long> getRegionIds() {
		return regionIds != null ? regionIds : Collections.emptyList();
	}

	public void setRegionIds(List<Long> regionIds) {
		this.regionIds = regionIds;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

}
