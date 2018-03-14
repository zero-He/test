package cn.strong.leke.diag.model.studentMonitor;

import cn.strong.leke.diag.model.teachingMonitor.RankBean;

/**
 * @author LIU.SHITING
 * @version 1.5
 * @date 2017-11-28 14:48:06
 */
public class ReadyStatusBean extends RankBean {

	private Long singleId;
	private Long classId;
	private String className;
	private Integer readyLessonNum = 0;
	private Integer allReady = 0;
	private Integer noReady = 0;
	private Integer partReady = 0;
	private double allReadyPro;
	private double noReadyPro;
	private double partReadyPro;
	private String startDate;
	private String endDate;
	//序号
	private Integer index;

	public Long getSingleId() {
		return singleId;
	}

	public void setSingleId(Long singleId) {
		this.singleId = singleId;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getReadyLessonNum() {
		return readyLessonNum;
	}

	public void setReadyLessonNum(Integer readyLessonNum) {
		this.readyLessonNum = readyLessonNum;
	}

	public Integer getAllReady() {
		return allReady;
	}

	public void setAllReady(Integer allReady) {
		this.allReady = allReady;
	}

	public Integer getNoReady() {
		return noReady;
	}

	public void setNoReady(Integer noReady) {
		this.noReady = noReady;
	}

	public Integer getPartReady() {
		return partReady;
	}

	public void setPartReady(Integer partReady) {
		this.partReady = partReady;
	}

	public double getAllReadyPro() {
		return allReadyPro;
	}

	public void setAllReadyPro(double allReadyPro) {
		this.allReadyPro = allReadyPro;
	}

	public double getNoReadyPro() {
		return noReadyPro;
	}

	public void setNoReadyPro(double noReadyPro) {
		this.noReadyPro = noReadyPro;
	}

	public double getPartReadyPro() {
		return partReadyPro;
	}

	public void setPartReadyPro(double partReadyPro) {
		this.partReadyPro = partReadyPro;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
