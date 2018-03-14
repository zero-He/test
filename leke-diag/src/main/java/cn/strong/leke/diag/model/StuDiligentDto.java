package cn.strong.leke.diag.model;

import java.math.BigDecimal;

/**
 * 学生作业提交情况。
 * @author  andy
 * @created 2014年8月5日 上午9:50:18
 * @since   v1.0.0
 */
public class StuDiligentDto {

	// 学生ID
	private Long studentId;
	// 学生姓名
	private String studentName;
	// 正常提交次数
	private Long normal;
	// 延迟提交次数
	private Long delay;
	// 未提交次数
	private Long unsubmit;
	// 正常提交占比
	private BigDecimal normalRate;
	// 延迟提交占比
	private BigDecimal delayRate;
	// 未提交占比
	private BigDecimal unsubmitRate;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Long getNormal() {
		return normal;
	}

	public void setNormal(Long normal) {
		this.normal = normal;
	}

	public Long getDelay() {
		return delay;
	}

	public void setDelay(Long delay) {
		this.delay = delay;
	}

	public Long getUnsubmit() {
		return unsubmit;
	}

	public void setUnsubmit(Long unsubmit) {
		this.unsubmit = unsubmit;
	}

	public BigDecimal getNormalRate() {
		return normalRate;
	}

	public void setNormalRate(BigDecimal normalRate) {
		this.normalRate = normalRate;
	}

	public BigDecimal getDelayRate() {
		return delayRate;
	}

	public void setDelayRate(BigDecimal delayRate) {
		this.delayRate = delayRate;
	}

	public BigDecimal getUnsubmitRate() {
		return unsubmitRate;
	}

	public void setUnsubmitRate(BigDecimal unsubmitRate) {
		this.unsubmitRate = unsubmitRate;
	}
}
