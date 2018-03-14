package cn.strong.leke.diag.report;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.diag.model.report.LessonRptView;
import cn.strong.leke.diag.model.report.MiddleInfo;
import cn.strong.leke.diag.model.report.MiddleQuery;
import cn.strong.leke.diag.model.report.ReviewInfo;
import cn.strong.leke.diag.model.report.ReviewQuery;
import cn.strong.leke.lesson.model.LessonVM;
import cn.strong.leke.remote.service.lesson.ILessonRemoteService;

@Component
public class LessonAnalyzeReportHandler implements ReportHandler<Long, LessonRptView> {

	@Resource
	private ILessonRemoteService lessonRemoteService;

	@Resource
	private LessonPreviewReportHandler lessonPreviewReportHandler;
	@Resource
	private LessonMiddleReportHandler lessonMiddleReportHandler;
	@Resource
	private LessonReviewReportHandler lessonReviewReportHandler;

	@Override
	public LessonRptView execute(Long lessonId) {
		LessonVM lesson = this.lessonRemoteService.getLessonVMByLessonId(lessonId);
		ReviewInfo preview = null, review = null;
		MiddleInfo middle = null;

		// 预习
		ReviewQuery reviewQuery = new ReviewQuery();
		reviewQuery.setLessonId(lessonId);
		reviewQuery.setCloseTime(lesson.getStartTime());
		preview = this.lessonPreviewReportHandler.execute(reviewQuery);

		// 复习
		if (lesson.getIsEnd()) {
			// 课堂
			MiddleQuery middleQuery = new MiddleQuery();
			middleQuery.setLessonId(lesson.getCourseSingleId());
			middleQuery.setTeacherId(lesson.getTeacherId());
			middleQuery.setStartTime(lesson.getStartTime());
			middleQuery.setCloseTime(lesson.getEndTime());
			middle = this.lessonMiddleReportHandler.execute(middleQuery);
			
			reviewQuery.setCloseTime(null);
			review = this.lessonReviewReportHandler.execute(reviewQuery);
		}

		LessonRptView view = new LessonRptView();
		view.setLesson(lesson);
		view.setPreview(preview);
		view.setMiddle(middle);
		view.setReview(review);
		return view;
	}
}
