package cn.strong.leke.diag.model.report;

import java.math.BigDecimal;
import java.util.List;

import cn.strong.leke.beike.model.MicrocourseDTO;
import cn.strong.leke.diag.util.ScoreLevelUtils.ScoreLevel;

/**
 * 作业报告数据结构。
 * @author  andy
 * @since   v1.0.0
 */
public class HwRptView {

	// 试卷总分
	private BigDecimal totalScore;
	// 每个级别的最低分
	private List<ScoreLevel> levels;
	// 概要信息
	private Summary summary;
	// 大题分析
	private List<GroupModel> queGroups;
	// 知识点分析
	private List<GroupModel> knoGroups;
	// 得分排名
	private List<ScoreModel> scoreRanks;
	// 推荐微课
	private List<MicrocourseDTO> micros;

	public BigDecimal getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}

	public List<ScoreLevel> getLevels() {
		return levels;
	}

	public void setLevels(List<ScoreLevel> levels) {
		this.levels = levels;
	}

	public Summary getSummary() {
		return summary;
	}

	public void setSummary(Summary summary) {
		this.summary = summary;
	}

	public List<GroupModel> getQueGroups() {
		return queGroups;
	}

	public void setQueGroups(List<GroupModel> queGroups) {
		this.queGroups = queGroups;
	}

	public List<GroupModel> getKnoGroups() {
		return knoGroups;
	}

	public void setKnoGroups(List<GroupModel> knoGroups) {
		this.knoGroups = knoGroups;
	}

	public List<ScoreModel> getScoreRanks() {
		return scoreRanks;
	}

	public void setScoreRanks(List<ScoreModel> scoreRanks) {
		this.scoreRanks = scoreRanks;
	}

	public List<MicrocourseDTO> getMicros() {
		return micros;
	}

	public void setMicros(List<MicrocourseDTO> micros) {
		this.micros = micros;
	}

	/**
	 * 概要信息。
	 */
	public static class Summary {
		
		private Long homeworkId;
		private String homeworkName;
		// 提交&批改
		private Integer totalNum; // 总份数
		private Integer submitNum; // 提交份数
		private Integer delayNum; // 迟交份数
		private Integer correctNum; // 批改份数
		// 分数
		private BigDecimal maxScore; // 班级最高分
		private BigDecimal minScore; // 班级最低分
		private BigDecimal avgScore; // 班级平均分
		private BigDecimal selfScore; // 学生得分
		// 用时
		private Integer avgUsedTime; // 班级平均用时
		private Integer selfUsedTime; // 学生用时
		// 排名
		private Integer submitRank; // 提交排名
		private Integer scoreRank; // 成绩排名

		public Long getHomeworkId() {
			return homeworkId;
		}

		public void setHomeworkId(Long homeworkId) {
			this.homeworkId = homeworkId;
		}

		public String getHomeworkName() {
			return homeworkName;
		}

		public void setHomeworkName(String homeworkName) {
			this.homeworkName = homeworkName;
		}

		public Integer getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(Integer totalNum) {
			this.totalNum = totalNum;
		}

		public Integer getSubmitNum() {
			return submitNum;
		}

		public void setSubmitNum(Integer submitNum) {
			this.submitNum = submitNum;
		}

		public Integer getDelayNum() {
			return delayNum;
		}

		public void setDelayNum(Integer delayNum) {
			this.delayNum = delayNum;
		}

		public Integer getCorrectNum() {
			return correctNum;
		}

		public void setCorrectNum(Integer correctNum) {
			this.correctNum = correctNum;
		}

		public BigDecimal getMaxScore() {
			return maxScore;
		}

		public void setMaxScore(BigDecimal maxScore) {
			this.maxScore = maxScore;
		}

		public BigDecimal getMinScore() {
			return minScore;
		}

		public void setMinScore(BigDecimal minScore) {
			this.minScore = minScore;
		}

		public BigDecimal getAvgScore() {
			return avgScore;
		}

		public void setAvgScore(BigDecimal avgScore) {
			this.avgScore = avgScore;
		}

		public BigDecimal getSelfScore() {
			return selfScore;
		}

		public void setSelfScore(BigDecimal selfScore) {
			this.selfScore = selfScore;
		}

		public Integer getAvgUsedTime() {
			return avgUsedTime;
		}

		public void setAvgUsedTime(Integer avgUsedTime) {
			this.avgUsedTime = avgUsedTime;
		}

		public Integer getSelfUsedTime() {
			return selfUsedTime;
		}

		public void setSelfUsedTime(Integer selfUsedTime) {
			this.selfUsedTime = selfUsedTime;
		}

		public Integer getSubmitRank() {
			return submitRank;
		}

		public void setSubmitRank(Integer submitRank) {
			this.submitRank = submitRank;
		}

		public Integer getScoreRank() {
			return scoreRank;
		}

		public void setScoreRank(Integer scoreRank) {
			this.scoreRank = scoreRank;
		}
	}

	/**
	 * 分组分析模型
	 */
	public static class GroupModel {

		private Long id;
		private String name;
		private List<Long> qids;
		private BigDecimal totalScore;
		private BigDecimal classScore;
		private BigDecimal rightNum;
		private BigDecimal selfScore;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<Long> getQids() {
			return qids;
		}

		public void setQids(List<Long> qids) {
			this.qids = qids;
		}

		public BigDecimal getTotalScore() {
			return totalScore;
		}

		public void setTotalScore(BigDecimal totalScore) {
			this.totalScore = totalScore;
		}

		public BigDecimal getClassScore() {
			return classScore;
		}

		public void setClassScore(BigDecimal classScore) {
			this.classScore = classScore;
		}

		public BigDecimal getRightNum() {
			return rightNum;
		}

		public void setRightNum(BigDecimal rightNum) {
			this.rightNum = rightNum;
		}

		public BigDecimal getSelfScore() {
			return selfScore;
		}

		public void setSelfScore(BigDecimal selfScore) {
			this.selfScore = selfScore;
		}
	}

	/**
	 * 用户得分模型。
	 */
	public static class ScoreModel {
		private Long userId;
		private String userName;
		private Integer level;
		private BigDecimal score;
		private BigDecimal scoreRate;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public Integer getLevel() {
			return level;
		}

		public void setLevel(Integer level) {
			this.level = level;
		}

		public BigDecimal getScore() {
			return score;
		}

		public void setScore(BigDecimal score) {
			this.score = score;
		}

		public BigDecimal getScoreRate() {
			return scoreRate;
		}

		public void setScoreRate(BigDecimal scoreRate) {
			this.scoreRate = scoreRate;
		}
	}
}
