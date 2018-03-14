package cn.strong.leke.diag.model.studentMonitor;

import java.util.List;

/**
 * @author Liu.ShiTing
 * @version 1.5
 * @date 2017-11-22 15:20:56
 */
public class OtherLessonBean {

	private Long userId;
	private Long courseSingleId;
	private List<Long> courseSingleIds;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCourseSingleId() {
		return courseSingleId;
	}

	public void setCourseSingleId(Long courseSingleId) {
		this.courseSingleId = courseSingleId;
	}

	public List<Long> getCourseSingleIds() {
		return courseSingleIds;
	}

	public void setCourseSingleIds(List<Long> courseSingleIds) {
		this.courseSingleIds = courseSingleIds;
	}
}
