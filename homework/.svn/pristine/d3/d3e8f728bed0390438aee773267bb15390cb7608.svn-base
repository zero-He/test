package cn.strong.leke.homework.model.mongo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import cn.strong.leke.data.mongo.annotations.BsonDecimal;

/**
 * 寒暑假微课信息
 * @author Zhang Fujun
 * @date 2016年12月15日
 */
public class HolidayMicrocourse {

	private Long microId;
	private String microName;
	//时长(秒数)
	private Long time;
	private String timeStr;
	//章节Id
	private Long matNodeId;
	//知识点id
	private Long knowledgId;
	//微课状态，0:未完成，1：已完成
	private Integer microState;
	/**
	 * 上次播放记录
	 */
	private Integer position;
	
	private String exerciseId;
	
	//微课作业正确率
	@BsonDecimal(scale = 4, round = RoundingMode.HALF_UP)
	private BigDecimal accuracy;



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
	 * @return the knowledgId
	 */
	public Long getKnowledgId() {
		return knowledgId;
	}

	/**
	 * @param knowledgId the knowledgId to set
	 */
	public void setKnowledgId(Long knowledgId) {
		this.knowledgId = knowledgId;
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
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}
	
}
