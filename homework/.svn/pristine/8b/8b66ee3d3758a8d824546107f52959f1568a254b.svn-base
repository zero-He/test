package cn.strong.leke.homework.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import cn.strong.leke.data.mongo.annotations.BsonDecimal;
import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;
import cn.strong.leke.model.question.QuestionResult;

public class WorkDetail {

	@_id
	@ObjectId
	private String id;
	private Long homeworkDtlId;// 学生作业ID
	private Long homeworkId;// 老师作业ID
	private Long studentId;// 学生ID
	private String studentName;// 学生姓名
	private Integer correctCount;// 批改题量
	private Integer questionNum;// 试卷题量
	@BsonDecimal(scale = 4, round = RoundingMode.HALF_UP)
	private BigDecimal score;// 作业得分
	@BsonDecimal(scale = 4, round = RoundingMode.HALF_UP)
	private BigDecimal scoreRate;// 作业得分率
	private String commentary;// 批改评注
	private List<QuestionResult> questions;// 题目信息
	private List<GroupScore> queScores;// 大题得分
	private List<GroupScore> knoScores; // 知识点得分
	private List<AnswerInfo> answerInfos;// 答题信息暂存
	private Date submitTime;// 提交时间
	private String sheetBookId; // 线下答题卡ID
	private Boolean isDeleted;
	private Long createdBy;
	private Date createdOn;
	private Long modifiedBy;
	private Date modifiedOn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getHomeworkDtlId() {
		return homeworkDtlId;
	}

	public void setHomeworkDtlId(Long homeworkDtlId) {
		this.homeworkDtlId = homeworkDtlId;
	}

	public Long getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
	}

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

	public Integer getCorrectCount() {
		return correctCount;
	}

	public void setCorrectCount(Integer correctCount) {
		this.correctCount = correctCount;
	}

	public Integer getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
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

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public List<QuestionResult> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionResult> questions) {
		this.questions = questions;
	}

	public List<GroupScore> getQueScores() {
		return queScores;
	}

	public void setQueScores(List<GroupScore> queScores) {
		this.queScores = queScores;
	}

	public List<GroupScore> getKnoScores() {
		return knoScores;
	}

	public void setKnoScores(List<GroupScore> knoScores) {
		this.knoScores = knoScores;
	}

	public List<AnswerInfo> getAnswerInfos() {
		return answerInfos;
	}

	public void setAnswerInfos(List<AnswerInfo> answerInfos) {
		this.answerInfos = answerInfos;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getSheetBookId() {
		return sheetBookId;
	}

	public void setSheetBookId(String sheetBookId) {
		this.sheetBookId = sheetBookId;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	/**
	 * 分组数据结构。
	 */
	public static class GroupModel {

		private Long id;
		private String name; // 名称
		private List<Long> qids; // 题目列表
		private BigDecimal score; // 题目总分

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

		public BigDecimal getScore() {
			return score;
		}

		public void setScore(BigDecimal score) {
			this.score = score;
		}
	}

	/**
	 * 分组得分数据
	 */
	public static class GroupScore implements java.io.Serializable {

		private static final long serialVersionUID = 6713970164858582131L;

		private Long id; // 知识点ID或者大题序号
		private String name; // 知识点名称或者大题名称
		private Integer totalNum; // 总题数
		@BsonDecimal(scale = 4, round = RoundingMode.HALF_UP)
		private BigDecimal totalScore; // 总分
		@BsonDecimal(scale = 4, round = RoundingMode.HALF_UP)
		private BigDecimal rightNum; // 正确题数
		@BsonDecimal(scale = 4, round = RoundingMode.HALF_UP)
		private BigDecimal selfScore; // 得分

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

		public Integer getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(Integer totalNum) {
			this.totalNum = totalNum;
		}

		public BigDecimal getTotalScore() {
			return totalScore;
		}

		public void setTotalScore(BigDecimal totalScore) {
			this.totalScore = totalScore;
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
}
