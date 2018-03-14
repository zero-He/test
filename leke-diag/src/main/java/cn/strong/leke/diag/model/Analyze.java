package cn.strong.leke.diag.model;

public class Analyze {

	// 分析ID
	private Long analyzeId;
	// 作业ID
	private Long homeworkId;
	// 题目ID
	private Long questionId;
	// 题目序号
	private Integer questionNo;
	// 答题人数
	private Integer totalNum;
	// 正确人数
	private Integer correctNum;
	// 正确率
	private Double rightRate;
	// 得分率
	private Double scoreRate;

	public Long getAnalyzeId() {
		return analyzeId;
	}

	public void setAnalyzeId(Long analyzeId) {
		this.analyzeId = analyzeId;
	}

	public Long getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Integer getQuestionNo() {
		return questionNo;
	}

	public void setQuestionNo(Integer questionNo) {
		this.questionNo = questionNo;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getCorrectNum() {
		return correctNum;
	}

	public void setCorrectNum(Integer correctNum) {
		this.correctNum = correctNum;
	}

	public Double getRightRate() {
		return rightRate;
	}

	public void setRightRate(Double rightRate) {
		this.rightRate = rightRate;
	}

	public Double getScoreRate() {
		return scoreRate;
	}

	public void setScoreRate(Double scoreRate) {
		this.scoreRate = scoreRate;
	}
}
