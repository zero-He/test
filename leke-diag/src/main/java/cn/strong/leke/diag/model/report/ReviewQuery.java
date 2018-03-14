package cn.strong.leke.diag.model.report;

import java.util.Date;

public class ReviewQuery {

	private Long lessonId;
	private Date closeTime;

	public Long getLessonId() {
		return lessonId;
	}

	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
}
