package cn.strong.leke.diag.model.report;

import cn.strong.leke.lesson.model.LessonVM;

public class LessonRptView {

	private LessonVM lesson;
	private ReviewInfo preview;
	private ReviewInfo review;
	private MiddleInfo middle;

	public LessonVM getLesson() {
		return lesson;
	}

	public void setLesson(LessonVM lesson) {
		this.lesson = lesson;
	}

	public ReviewInfo getPreview() {
		return preview;
	}

	public void setPreview(ReviewInfo preview) {
		this.preview = preview;
	}

	public ReviewInfo getReview() {
		return review;
	}

	public void setReview(ReviewInfo review) {
		this.review = review;
	}

	public MiddleInfo getMiddle() {
		return middle;
	}

	public void setMiddle(MiddleInfo middle) {
		this.middle = middle;
	}
}
