package cn.strong.leke.diag.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import cn.strong.leke.data.mongo.annotations.BsonDecimal;
import cn.strong.leke.data.mongo.annotations._id;
import cn.strong.leke.model.question.QuestionResult;

public class WorkDetail {

	@_id
	private String _id;
	private Long homeworkDtlId;// 学生作业ID
	private Long homeworkId;// 老师作业ID
	private Long studentId;// 学生ID
	private String studentName;// 学生姓名
	private List<QuestionResult> questions;// 题目信息
	private List<GroupScore> queScores;// 大题得分
	private List<GroupScore> knoScores; // 知识点得分

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
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
