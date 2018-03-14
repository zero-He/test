package cn.strong.leke.diag.model.report;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.strong.leke.diag.dao.homework.model.WorkFinishAnaly;
import cn.strong.leke.diag.dao.lesson.model.AttendStatInfo2;
import cn.strong.leke.diag.model.stuanaly.UserAchievement;

public class BehaviorRptView extends RptView {

	private SummaryInfo summaryInfo;
	private UserAchievement achievement;
	private BehaviorInfo behaviorInfo;
	private AttendStatInfo2 attendStatInfo;
	private ViewFinishAnaly viewFinishAnaly;
	private WorkFinishAnaly workFinishAnaly;
	private OtherWiseInfo otherWiseInfo;
	private Map<Long, Integer> lekeVal;

	public SummaryInfo getSummaryInfo() {
		return summaryInfo;
	}

	public void setSummaryInfo(SummaryInfo summaryInfo) {
		this.summaryInfo = summaryInfo;
	}

	public UserAchievement getAchievement() {
		return achievement;
	}

	public void setAchievement(UserAchievement achievement) {
		this.achievement = achievement;
	}

	public BehaviorInfo getBehaviorInfo() {
		return behaviorInfo;
	}

	public void setBehaviorInfo(BehaviorInfo behaviorInfo) {
		this.behaviorInfo = behaviorInfo;
	}

	public AttendStatInfo2 getAttendStatInfo() {
		return attendStatInfo;
	}

	public void setAttendStatInfo(AttendStatInfo2 attendStatInfo) {
		this.attendStatInfo = attendStatInfo;
	}

	public ViewFinishAnaly getViewFinishAnaly() {
		return viewFinishAnaly;
	}

	public void setViewFinishAnaly(ViewFinishAnaly viewFinishAnaly) {
		this.viewFinishAnaly = viewFinishAnaly;
	}

	public WorkFinishAnaly getWorkFinishAnaly() {
		return workFinishAnaly;
	}

	public void setWorkFinishAnaly(WorkFinishAnaly workFinishAnaly) {
		this.workFinishAnaly = workFinishAnaly;
	}

	public OtherWiseInfo getOtherWiseInfo() {
		return otherWiseInfo;
	}

	public void setOtherWiseInfo(OtherWiseInfo otherWiseInfo) {
		this.otherWiseInfo = otherWiseInfo;
	}

	public Map<Long, Integer> getLekeVal() {
		return lekeVal;
	}

	public void setLekeVal(Map<Long, Integer> lekeVal) {
		this.lekeVal = lekeVal;
	}

	public static class SummaryInfo {

		private Integer totalNum; // 总课时数
		private Integer attendRate; // 出勤率
		private Integer previewRate; // 预习率
		private Integer reviewRate; // 复习率
		private Integer submitRate; // 作业提交率
		private Integer bugFixRate; // 作业订正率
		private Integer achieveNum; // 取得成就数
		private Integer lekeNum; // 获取乐币数

		public Integer getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(Integer totalNum) {
			this.totalNum = totalNum;
		}

		public Integer getAttendRate() {
			return attendRate;
		}

		public void setAttendRate(Integer attendRate) {
			this.attendRate = attendRate;
		}

		public Integer getPreviewRate() {
			return previewRate;
		}

		public void setPreviewRate(Integer previewRate) {
			this.previewRate = previewRate;
		}

		public Integer getReviewRate() {
			return reviewRate;
		}

		public void setReviewRate(Integer reviewRate) {
			this.reviewRate = reviewRate;
		}

		public Integer getSubmitRate() {
			return submitRate;
		}

		public void setSubmitRate(Integer submitRate) {
			this.submitRate = submitRate;
		}

		public Integer getBugFixRate() {
			return bugFixRate;
		}

		public void setBugFixRate(Integer bugFixRate) {
			this.bugFixRate = bugFixRate;
		}

		public Integer getAchieveNum() {
			return achieveNum;
		}

		public void setAchieveNum(Integer achieveNum) {
			this.achieveNum = achieveNum;
		}

		public Integer getLekeNum() {
			return lekeNum;
		}

		public void setLekeNum(Integer lekeNum) {
			this.lekeNum = lekeNum;
		}
	}

	public static class BehaviorInfo {

		private int rank1;
		private int rank2;
		private int rank3;
		private int callNum1;
		private int callNum2;
		private int raised;
		private int authed;
		private int noteNum;
		private int examNum1;
		private int examNum2;
		private int quickNum1;
		private int quickNum2;
		private int discuNum1;
		private int discuNum2;
		private int flowerNum;
		private int evalNum;

		public int getRank1() {
			return rank1;
		}

		public void setRank1(int rank1) {
			this.rank1 = rank1;
		}

		public int getRank2() {
			return rank2;
		}

		public void setRank2(int rank2) {
			this.rank2 = rank2;
		}

		public int getRank3() {
			return rank3;
		}

		public void setRank3(int rank3) {
			this.rank3 = rank3;
		}

		public int getCallNum1() {
			return callNum1;
		}

		public void setCallNum1(int callNum1) {
			this.callNum1 = callNum1;
		}

		public int getCallNum2() {
			return callNum2;
		}

		public void setCallNum2(int callNum2) {
			this.callNum2 = callNum2;
		}

		public int getRaised() {
			return raised;
		}

		public void setRaised(int raised) {
			this.raised = raised;
		}

		public int getAuthed() {
			return authed;
		}

		public void setAuthed(int authed) {
			this.authed = authed;
		}

		public int getNoteNum() {
			return noteNum;
		}

		public void setNoteNum(int noteNum) {
			this.noteNum = noteNum;
		}

		public int getExamNum1() {
			return examNum1;
		}

		public void setExamNum1(int examNum1) {
			this.examNum1 = examNum1;
		}

		public int getExamNum2() {
			return examNum2;
		}

		public void setExamNum2(int examNum2) {
			this.examNum2 = examNum2;
		}

		public int getQuickNum1() {
			return quickNum1;
		}

		public void setQuickNum1(int quickNum1) {
			this.quickNum1 = quickNum1;
		}

		public int getQuickNum2() {
			return quickNum2;
		}

		public void setQuickNum2(int quickNum2) {
			this.quickNum2 = quickNum2;
		}

		public int getDiscuNum1() {
			return discuNum1;
		}

		public void setDiscuNum1(int discuNum1) {
			this.discuNum1 = discuNum1;
		}

		public int getDiscuNum2() {
			return discuNum2;
		}

		public void setDiscuNum2(int discuNum2) {
			this.discuNum2 = discuNum2;
		}

		public int getFlowerNum() {
			return flowerNum;
		}

		public void setFlowerNum(int flowerNum) {
			this.flowerNum = flowerNum;
		}

		public int getEvalNum() {
			return evalNum;
		}

		public void setEvalNum(int evalNum) {
			this.evalNum = evalNum;
		}
	}

	public static class ViewFinishAnaly {

		private int totalNum;
		private int preview1;
		private int preview2;
		private int previewRate;
		private int review1;
		private int review2;
		private int reviewRate;
		private List<Integer> previews1 = Arrays.asList(0, 0, 0);
		private List<Integer> previews2 = Arrays.asList(0, 0, 0);
		private List<Integer> reviews1 = Arrays.asList(0, 0, 0);
		private List<Integer> reviews2 = Arrays.asList(0, 0, 0);

		public int getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(int totalNum) {
			this.totalNum = totalNum;
		}

		public int getPreview1() {
			return preview1;
		}

		public void setPreview1(int preview1) {
			this.preview1 = preview1;
		}

		public int getPreview2() {
			return preview2;
		}

		public void setPreview2(int preview2) {
			this.preview2 = preview2;
		}

		public int getReview1() {
			return review1;
		}

		public void setReview1(int review1) {
			this.review1 = review1;
		}

		public int getReview2() {
			return review2;
		}

		public void setReview2(int review2) {
			this.review2 = review2;
		}

		public List<Integer> getPreviews1() {
			return previews1;
		}

		public void setPreviews1(List<Integer> previews1) {
			this.previews1 = previews1;
		}

		public List<Integer> getPreviews2() {
			return previews2;
		}

		public void setPreviews2(List<Integer> previews2) {
			this.previews2 = previews2;
		}

		public List<Integer> getReviews1() {
			return reviews1;
		}

		public void setReviews1(List<Integer> reviews1) {
			this.reviews1 = reviews1;
		}

		public List<Integer> getReviews2() {
			return reviews2;
		}

		public void setReviews2(List<Integer> reviews2) {
			this.reviews2 = reviews2;
		}

		public int getPreviewRate() {
			return previewRate;
		}

		public void setPreviewRate(int previewRate) {
			this.previewRate = previewRate;
		}

		public int getReviewRate() {
			return reviewRate;
		}

		public void setReviewRate(int reviewRate) {
			this.reviewRate = reviewRate;
		}
	}

	public static class OtherWiseInfo {
		private Integer practiceNum;
		private Integer killWrongNum;
		private Integer writeNoteNum;
		private Integer doubtNum;

		public Integer getPracticeNum() {
			return practiceNum;
		}

		public void setPracticeNum(Integer practiceNum) {
			this.practiceNum = practiceNum;
		}

		public Integer getKillWrongNum() {
			return killWrongNum;
		}

		public void setKillWrongNum(Integer killWrongNum) {
			this.killWrongNum = killWrongNum;
		}

		public Integer getWriteNoteNum() {
			return writeNoteNum;
		}

		public void setWriteNoteNum(Integer writeNoteNum) {
			this.writeNoteNum = writeNoteNum;
		}

		public Integer getDoubtNum() {
			return doubtNum;
		}

		public void setDoubtNum(Integer doubtNum) {
			this.doubtNum = doubtNum;
		}
	}
}
