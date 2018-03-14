package cn.strong.leke.homework.model.mobile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import cn.strong.leke.homework.util.JsonPaper;
import cn.strong.leke.homework.util.JsonQuestion;
import cn.strong.leke.model.question.QuestionResult;

public class WorkModel {

	private JsonPaper paper;
	private Map<Long, JsonQuestion> ques;
	private Map<Long, QuestionResult> results;
	private WorkInfo workInfo;

	public JsonPaper getPaper() {
		return paper;
	}

	public void setPaper(JsonPaper paper) {
		this.paper = paper;
	}

	public Map<Long, JsonQuestion> getQues() {
		return ques;
	}

	public void setQues(Map<Long, JsonQuestion> ques) {
		this.ques = ques;
	}

	public Map<Long, QuestionResult> getResults() {
		return results;
	}

	public void setResults(Map<Long, QuestionResult> results) {
		this.results = results;
	}

	public WorkInfo getWorkInfo() {
		return workInfo;
	}

	public void setWorkInfo(WorkInfo workInfo) {
		this.workInfo = workInfo;
	}

	public static class WorkInfo {

		private Long homeworkId;
		private Long homeworkDtlId;
		private Long studentId;
		private String studentName;
		private Integer usedTime;
		private Date submitTime;
		private Date correctTime;
		private BigDecimal score;
		private BigDecimal scoreRate;
		private BigDecimal avgScore;
		private String comments;
		private Boolean selfCheck;
		private Boolean openAnswer;

		public Long getHomeworkId() {
			return homeworkId;
		}

		public void setHomeworkId(Long homeworkId) {
			this.homeworkId = homeworkId;
		}

		public Long getHomeworkDtlId() {
			return homeworkDtlId;
		}

		public void setHomeworkDtlId(Long homeworkDtlId) {
			this.homeworkDtlId = homeworkDtlId;
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

		public Integer getUsedTime() {
			return usedTime;
		}

		public void setUsedTime(Integer usedTime) {
			this.usedTime = usedTime;
		}

		public Date getSubmitTime() {
			return submitTime;
		}

		public void setSubmitTime(Date submitTime) {
			this.submitTime = submitTime;
		}

		public Date getCorrectTime() {
			return correctTime;
		}

		public void setCorrectTime(Date correctTime) {
			this.correctTime = correctTime;
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

		public BigDecimal getAvgScore() {
			return avgScore;
		}

		public void setAvgScore(BigDecimal avgScore) {
			this.avgScore = avgScore;
		}

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
		}

		public Boolean getSelfCheck() {
			return selfCheck;
		}

		public void setSelfCheck(Boolean selfCheck) {
			this.selfCheck = selfCheck;
		}

		public Boolean getOpenAnswer() {
			return openAnswer;
		}

		public void setOpenAnswer(Boolean openAnswer) {
			this.openAnswer = openAnswer;
		}
	}
}
