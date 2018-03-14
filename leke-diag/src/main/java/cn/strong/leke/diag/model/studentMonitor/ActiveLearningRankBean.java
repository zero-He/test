package cn.strong.leke.diag.model.studentMonitor;

import cn.strong.leke.diag.model.teachingMonitor.RankBean;

/**
 * @author LIU.SHITING
 * @version 1.5
 * @date 2017-11-21 15:47:10
 */
public class ActiveLearningRankBean extends RankBean {

	private int learningNum;
	private int questionNum;

	public int getLearningNum() {
		return learningNum;
	}

	public void setLearningNum(int learningNum) {
		this.learningNum = learningNum;
	}

	public int getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}
}
