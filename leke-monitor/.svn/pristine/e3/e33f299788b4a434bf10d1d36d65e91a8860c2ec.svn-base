/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.model;

import java.util.Date;
import java.util.List;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * 平台在线用户学校日统计
 * 
 * @author liulongbiao
 *
 */
public class OnlineUserSchoolDaily extends OnlineUserStat {
	@_id
	@ObjectId
	private String id;
	private int day; // 日期
	private Long schoolId; // 学校ID
	private String schoolName; // 学校名称
	private Integer schoolCode; // 学校编码
	private Integer schoolNature; // 学校性质
	private List<Long> regionIds; // 区域ID列表
	private Date modifiedOn; // 修改时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

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
		return regionIds;
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
