/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.model;

import java.util.Date;
import java.util.List;

import cn.strong.leke.data.mongo.annotations._id;

/**
 * 单课出席的学生名单
 * 
 * @author liulongbiao
 *
 */
public class CourseSingleStudentsAttend {
	@_id
	private Long csId; // 单课ID
	private Date endTime; // 单课结束时间
	private List<Long> stuIds; // 有考勤的学生名单
	private Date modifiedOn; // 更新时间

	public Long getCsId() {
		return csId;
	}

	public void setCsId(Long csId) {
		this.csId = csId;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<Long> getStuIds() {
		return stuIds;
	}

	public void setStuIds(List<Long> stuIds) {
		this.stuIds = stuIds;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

}
