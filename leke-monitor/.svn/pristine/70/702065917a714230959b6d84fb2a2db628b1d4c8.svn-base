/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.model;

import java.util.Date;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * 单课五分钟统计点
 * 
 * @author liulongbiao
 *
 */
public class CourseSingleSnapshot {
	@_id
	@ObjectId
	private String id;
	private Date ts; // 五分钟起始点
	private Long csId; // 单课ID
	private Long schoolId; // 学校ID
	private Long actualStuCount; // 实到学生人数

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

	public Long getCsId() {
		return csId;
	}

	public void setCsId(Long csId) {
		this.csId = csId;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Long getActualStuCount() {
		return actualStuCount;
	}

	public void setActualStuCount(Long actualStuCount) {
		this.actualStuCount = actualStuCount;
	}

}
