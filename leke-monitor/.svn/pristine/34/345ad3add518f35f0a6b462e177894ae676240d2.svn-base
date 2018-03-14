/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.model;

import java.util.Date;
import java.util.List;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * 平台在线人数学校五分钟统计点
 * 
 * @author liulongbiao
 *
 */
public class OnlineUserSchoolSnapshot extends OnlineUserStat {
	@_id
	@ObjectId
	private String id;
	private Date ts; // 五分钟起始点
	private Long schoolId; // 学校ID
	private List<Long> regionIds; // 区域ID列表
	private Date modifiedOn; // 修改时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
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
