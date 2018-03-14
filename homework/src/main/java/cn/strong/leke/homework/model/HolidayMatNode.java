package cn.strong.leke.homework.model;

import java.math.BigDecimal;

/**
 * 寒暑假作业
 * @author Zhang Fujun
 * @date 2016年12月15日
 */
/**
 * 微课知识点信息
 * @author Zhang Fujun
 * @date 2016年12月15日
 */
public class HolidayMatNode {
	private Long microId;
	private String microName;
	//微课状态，0:未完成，1：已完成
	private Integer microState;
	//微课作业状态，0:未完成，1：已完成
	private Integer microHwState;
	//时长(秒)
	private Long time;
	private String timeStr;
	//章节Id
	private Long matNodeId;
	private String matNodeName;
	//知识点id
	private Long kId;
	private String kName;
	private String exerciseId;
	private BigDecimal accuracy;

	/**
	 * @return the microId
	 */
	public Long getMicroId() {
		return microId;
	}

	/**
	 * @param microId the microId to set
	 */
	public void setMicroId(Long microId) {
		this.microId = microId;
	}

	/**
	 * @return the microName
	 */
	public String getMicroName() {
		return microName;
	}

	/**
	 * @param microName the microName to set
	 */
	public void setMicroName(String microName) {
		this.microName = microName;
	}

	/**
	 * @return the microState
	 */
	public Integer getMicroState() {
		return microState;
	}

	/**
	 * @param microState the microState to set
	 */
	public void setMicroState(Integer microState) {
		this.microState = microState;
	}

	/**
	 * @return the time
	 */
	public Long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Long time) {
		this.time = time;
	}

	/**
	 * @return the timeStr
	 */
	public String getTimeStr() {
		return timeStr;
	}

	/**
	 * @param timeStr the timeStr to set
	 */
	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

	/**
	 * @return the matNodeId
	 */
	public Long getMatNodeId() {
		return matNodeId;
	}

	/**
	 * @param matNodeId the matNodeId to set
	 */
	public void setMatNodeId(Long matNodeId) {
		this.matNodeId = matNodeId;
	}

	/**
	 * @return the matNodeName
	 */
	public String getMatNodeName() {
		return matNodeName;
	}

	/**
	 * @param matNodeName the matNodeName to set
	 */
	public void setMatNodeName(String matNodeName) {
		this.matNodeName = matNodeName;
	}

	/**
	 * @return the exerciseId
	 */
	public String getExerciseId() {
		return exerciseId;
	}

	/**
	 * @param exerciseId the exerciseId to set
	 */
	public void setExerciseId(String exerciseId) {
		this.exerciseId = exerciseId;
	}

	/**
	 * @return the accuracy
	 */
	public BigDecimal getAccuracy() {
		return accuracy;
	}

	/**
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(BigDecimal accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * @return the kId
	 */
	public Long getkId() {
		return kId;
	}

	/**
	 * @param kId the kId to set
	 */
	public void setkId(Long kId) {
		this.kId = kId;
	}

	/**
	 * @return the kName
	 */
	public String getkName() {
		return kName;
	}

	/**
	 * @param kName the kName to set
	 */
	public void setkName(String kName) {
		this.kName = kName;
	}

	/**
	 * @return the microHwState
	 */
	public Integer getMicroHwState() {
		return microHwState;
	}

	/**
	 * @param microHwState the microHwState to set
	 */
	public void setMicroHwState(Integer microHwState) {
		this.microHwState = microHwState;
	}
}
