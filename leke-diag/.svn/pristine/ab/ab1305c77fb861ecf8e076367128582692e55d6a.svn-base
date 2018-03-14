package cn.strong.leke.diag.model.tchanaly;

import cn.strong.leke.diag.dao.homework.model.WorkStatInfo;
import cn.strong.leke.diag.dao.lesson.model.AttendStatInfo2;
import cn.strong.leke.diag.dao.lesson.model.EvaluateInfo;
import cn.strong.leke.diag.model.report.RptView;

public class TchanalyTeachBehaviorRptView extends RptView {

	private String userName;
	// 备课信息
	private BeikeStatInfo beikeInfo;
	// 评价信息
	private EvaluateInfo evalInfo;
	// 课堂互动
	private InteractStatInfo interactInfo;
	// 作业批改
	private WorkStatInfo workInfo;
	// 课堂考勤
	private AttendStatInfo2 attendInfo;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public EvaluateInfo getEvalInfo() {
		return evalInfo;
	}

	public void setEvalInfo(EvaluateInfo evalInfo) {
		this.evalInfo = evalInfo;
	}

	public BeikeStatInfo getBeikeInfo() {
		return beikeInfo;
	}

	public void setBeikeInfo(BeikeStatInfo beikeInfo) {
		this.beikeInfo = beikeInfo;
	}

	public WorkStatInfo getWorkInfo() {
		return workInfo;
	}

	public void setWorkInfo(WorkStatInfo workInfo) {
		this.workInfo = workInfo;
	}

	public AttendStatInfo2 getAttendInfo() {
		return attendInfo;
	}

	public void setAttendInfo(AttendStatInfo2 attendInfo) {
		this.attendInfo = attendInfo;
	}

	public InteractStatInfo getInteractInfo() {
		return interactInfo;
	}

	public void setInteractInfo(InteractStatInfo interactInfo) {
		this.interactInfo = interactInfo;
	}

	public static class BeikeStatInfo {
		private Integer totalNum = 0;
		private Integer beikeNum = 0;
		private Integer resType1 = 0;
		private Integer resType2 = 0;
		private Integer resType3 = 0;

		public Integer getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(Integer totalNum) {
			this.totalNum = totalNum;
		}

		public Integer getBeikeNum() {
			return beikeNum;
		}

		public void setBeikeNum(Integer beikeNum) {
			this.beikeNum = beikeNum;
		}

		public Integer getResType1() {
			return resType1;
		}

		public void setResType1(Integer resType1) {
			this.resType1 = resType1;
		}

		public Integer getResType2() {
			return resType2;
		}

		public void setResType2(Integer resType2) {
			this.resType2 = resType2;
		}

		public Integer getResType3() {
			return resType3;
		}

		public void setResType3(Integer resType3) {
			this.resType3 = resType3;
		}
	}

	public static class InteractStatInfo {
		private Integer authedNum; // 授权次数
		private Integer calledNum; // 点名次数
		private Integer quickNum; // 快速问答次数
		private Integer discuNum; // 分组讨论次数
		private Integer examNum; // 随堂测试次数
		private Integer delayNum; // 拖堂次数
		private Integer totalNum; // 总互动次数
		private Integer flowerNum; // 鲜花数量
		private Integer prevTotalNum; // 上周期互动次数
		private Integer prevFlowerNum; // 上周期鲜花数量

		public Integer getAuthedNum() {
			return authedNum;
		}

		public void setAuthedNum(Integer authedNum) {
			this.authedNum = authedNum;
		}

		public Integer getCalledNum() {
			return calledNum;
		}

		public void setCalledNum(Integer calledNum) {
			this.calledNum = calledNum;
		}

		public Integer getQuickNum() {
			return quickNum;
		}

		public void setQuickNum(Integer quickNum) {
			this.quickNum = quickNum;
		}

		public Integer getDiscuNum() {
			return discuNum;
		}

		public void setDiscuNum(Integer discuNum) {
			this.discuNum = discuNum;
		}

		public Integer getExamNum() {
			return examNum;
		}

		public void setExamNum(Integer examNum) {
			this.examNum = examNum;
		}

		public Integer getDelayNum() {
			return delayNum;
		}

		public void setDelayNum(Integer delayNum) {
			this.delayNum = delayNum;
		}

		public Integer getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(Integer totalNum) {
			this.totalNum = totalNum;
		}

		public Integer getFlowerNum() {
			return flowerNum;
		}

		public void setFlowerNum(Integer flowerNum) {
			this.flowerNum = flowerNum;
		}

		public Integer getPrevTotalNum() {
			return prevTotalNum;
		}

		public void setPrevTotalNum(Integer prevTotalNum) {
			this.prevTotalNum = prevTotalNum;
		}

		public Integer getPrevFlowerNum() {
			return prevFlowerNum;
		}

		public void setPrevFlowerNum(Integer prevFlowerNum) {
			this.prevFlowerNum = prevFlowerNum;
		}
	}

}
