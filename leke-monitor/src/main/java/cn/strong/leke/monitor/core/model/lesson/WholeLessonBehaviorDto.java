package cn.strong.leke.monitor.core.model.lesson;

import java.io.Serializable;
import java.util.Date;

public class WholeLessonBehaviorDto implements Serializable{

	private static final long serialVersionUID = 1L;
	//事件类型
	private String type;
	//上课堂数
	private int lessonNum;
	//发起次数
	private int sponsorNum;
	//平均发起次数
	private Double average;
	//发起率
	private String sponsorRate;
	// 统计开始时间
	private Date fromDate;
	// 统计结束时间
	private Date endDate;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getLessonNum() {
		return lessonNum;
	}
	public void setLessonNum(int lessonNum) {
		this.lessonNum = lessonNum;
	}
	public int getSponsorNum() {
		return sponsorNum;
	}
	public void setSponsorNum(int sponsorNum) {
		this.sponsorNum = sponsorNum;
	}
	public Double getAverage() {
		return average;
	}
	public void setAverage(Double average) {
		this.average = average;
	}
	public String getSponsorRate() {
		return sponsorRate;
	}
	public void setSponsorRate(String sponsorRate) {
		this.sponsorRate = sponsorRate;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
