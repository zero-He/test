package cn.strong.leke.diag.model.studentMonitor;

import cn.strong.leke.diag.model.teachingMonitor.CommProp;

/**
 * @author Liu.ShiTing
 * @version 1.5
 * @date 2017-11-20 14:36:40
 */
public class OtherDetailBean extends CommProp {

	private String graspNum;
	private Double graspPro;
	private String askSolveSum;
	private Double askSolvePro;
	private Integer lessonNum;
	private Double avgTimeLong;
	private Double viewSeePro;
	private Integer createBookNum;
	private Double avgReadNum;

	public String getGraspNum() {
		return graspNum;
	}

	public void setGraspNum(String graspNum) {
		this.graspNum = graspNum;
	}

	public Double getGraspPro() {
		return graspPro;
	}

	public void setGraspPro(Double graspPro) {
		this.graspPro = graspPro;
	}

	public String getAskSolveSum() {
		return askSolveSum;
	}

	public void setAskSolveSum(String askSolveSum) {
		this.askSolveSum = askSolveSum;
	}

	public Double getAskSolvePro() {
		return askSolvePro;
	}

	public void setAskSolvePro(Double askSolvePro) {
		this.askSolvePro = askSolvePro;
	}

	public Integer getLessonNum() {
		return lessonNum;
	}

	public void setLessonNum(Integer lessonNum) {
		this.lessonNum = lessonNum;
	}

	public Double getAvgTimeLong() {
		return avgTimeLong;
	}

	public void setAvgTimeLong(Double avgTimeLong) {
		this.avgTimeLong = avgTimeLong;
	}

	public Double getViewSeePro() {
		return viewSeePro;
	}

	public void setViewSeePro(Double viewSeePro) {
		this.viewSeePro = viewSeePro;
	}

	public Integer getCreateBookNum() {
		return createBookNum;
	}

	public void setCreateBookNum(Integer createBookNum) {
		this.createBookNum = createBookNum;
	}

	public Double getAvgReadNum() {
		return avgReadNum;
	}

	public void setAvgReadNum(Double avgReadNum) {
		this.avgReadNum = avgReadNum;
	}
}
