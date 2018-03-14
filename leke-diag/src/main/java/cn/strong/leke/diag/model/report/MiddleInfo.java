package cn.strong.leke.diag.model.report;

import java.util.List;

import cn.strong.leke.diag.dao.homework.model.LessonExamAnaly;
import cn.strong.leke.diag.dao.lesson.model.EvaluateInfo;
import cn.strong.leke.lessonlog.model.AttendStatInfo;
import cn.strong.leke.lessonlog.model.InteractInfo;
import cn.strong.leke.lessonlog.model.TopRankScore;

public class MiddleInfo extends RptView {

	private Integer flower;
	private EvaluateInfo lessonEval;
	private EvaluateInfo teacherEval;
	private AttendStatInfo attendStatInfo;
	private List<InteractInfo> interacts;
	private List<TopRankScore> tops;
	private List<LessonExamAnaly> exams;
	private List<InteractInfo> calleds;
	private List<InteractInfo> quicks;
	private List<InteractInfo> discus;

	public MiddleInfo() {
		super();
	}

	public MiddleInfo(Boolean success, String message) {
		super(success, message);
	}

	public Integer getFlower() {
		return flower;
	}

	public void setFlower(Integer flower) {
		this.flower = flower;
	}

	public EvaluateInfo getLessonEval() {
		return lessonEval;
	}

	public void setLessonEval(EvaluateInfo lessonEval) {
		this.lessonEval = lessonEval;
	}

	public EvaluateInfo getTeacherEval() {
		return teacherEval;
	}

	public void setTeacherEval(EvaluateInfo teacherEval) {
		this.teacherEval = teacherEval;
	}

	public AttendStatInfo getAttendStatInfo() {
		return attendStatInfo;
	}

	public void setAttendStatInfo(AttendStatInfo attendStatInfo) {
		this.attendStatInfo = attendStatInfo;
	}

	public List<InteractInfo> getInteracts() {
		return interacts;
	}

	public void setInteracts(List<InteractInfo> interacts) {
		this.interacts = interacts;
	}

	public List<TopRankScore> getTops() {
		return tops;
	}

	public void setTops(List<TopRankScore> tops) {
		this.tops = tops;
	}

	public List<LessonExamAnaly> getExams() {
		return exams;
	}

	public void setExams(List<LessonExamAnaly> exams) {
		this.exams = exams;
	}

	public List<InteractInfo> getCalleds() {
		return calleds;
	}

	public void setCalleds(List<InteractInfo> calleds) {
		this.calleds = calleds;
	}

	public List<InteractInfo> getQuicks() {
		return quicks;
	}

	public void setQuicks(List<InteractInfo> quicks) {
		this.quicks = quicks;
	}

	public List<InteractInfo> getDiscus() {
		return discus;
	}

	public void setDiscus(List<InteractInfo> discus) {
		this.discus = discus;
	}
}