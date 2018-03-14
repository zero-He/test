/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.model.query;

import java.util.Date;

/**
 * 课堂在线统计
 * 
 * @author liulongbiao
 *
 */
public class CourseSnapshotStat {
	private Date ts; // 五分钟起始点
	private Long expectStuCount; // 预期学生人数
	private Long actualStuCount; // 实到学生人数

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
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

}
