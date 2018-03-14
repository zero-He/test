package cn.strong.leke.homework.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import cn.strong.leke.data.mongo.annotations.BsonDecimal;

public class SheetResult implements java.io.Serializable {

	private static final long serialVersionUID = -4349756318336438927L;
	
	private Long questionId;
	private List<String> answers;
	@BsonDecimal(scale = 4, round = RoundingMode.HALF_UP)
	private BigDecimal score;
	private Integer errorNo;
	private int idx;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public Integer getErrorNo() {
		return errorNo;
	}

	public void setErrorNo(Integer errorNo) {
		this.errorNo = errorNo;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}
}
