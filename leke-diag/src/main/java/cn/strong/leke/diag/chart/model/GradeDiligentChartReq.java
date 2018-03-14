package cn.strong.leke.diag.chart.model;

import java.util.Date;

import cn.strong.leke.core.business.chart.ChartReq;

/**
 * 教务勤奋报告查询请求对象
 * @author  andy
 * @created 2014年7月16日 上午9:15:19
 * @since   v1.0.0
 */
public class GradeDiligentChartReq extends ChartReq {

	private Long gradeId;
	private Long subjectId;
	private Date startTime;
	private Date endTime;
	private Integer classType;
	
	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getClassType() {
		return classType;
	}

	public void setClassType(Integer classType) {
		this.classType = classType;
	}
}
