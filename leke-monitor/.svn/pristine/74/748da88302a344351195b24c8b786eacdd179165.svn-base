/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.model;

import java.util.Date;
import java.util.List;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * 课堂学校五分钟统计点
 * 
 * @author liulongbiao
 *
 */
public class CourseSchoolSnapshot {
	@_id
	@ObjectId
	private String id;
	private Date ts; // 五分钟起始点
	private Long schoolId; // 学校ID
	private List<Long> regionIds; // 区域ID列表
	private List<Long> marketIds; // 市场ID列表
	private Long expectStuCount; // 预期学生人数
	private Long actualStuCount; // 实到学生人数
	private Date modifiedOn; // 更新时间

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

	public List<Long> getMarketIds() {
		return marketIds;
	}

	public void setMarketIds(List<Long> marketIds) {
		this.marketIds = marketIds;
	}

	public Long getExpectStuCount() {
		return expectStuCount;
	}

	public void setExpectStuCount(Long expectStuCount) {
		this.expectStuCount = expectStuCount;
	}

	public Long getActualStuCount() {
		return actualStuCount;
	}

	public void setActualStuCount(Long actualStuCount) {
		this.actualStuCount = actualStuCount;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

}
