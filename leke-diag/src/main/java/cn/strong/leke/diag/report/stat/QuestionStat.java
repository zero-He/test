package cn.strong.leke.diag.report.stat;

/**
 * 题目统计结果。
 * @author  andy
 * @created 2014年8月8日 上午8:38:57
 * @since   v1.0.0
 */
public class QuestionStat {

	// 题目ID
	private Long questionId;
	// 统计数据内容
	private String datas;

	public QuestionStat() {
		super();
	}

	public QuestionStat(Long questionId, String datas) {
		super();
		this.questionId = questionId;
		this.datas = datas;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getDatas() {
		return datas;
	}

	public void setDatas(String datas) {
		this.datas = datas;
	}
}