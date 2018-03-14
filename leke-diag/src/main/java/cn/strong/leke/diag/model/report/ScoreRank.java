package cn.strong.leke.diag.model.report;

import java.util.HashMap;
import java.util.Map;

public class ScoreRank extends HashMap<Object, Object> {

	private static final long serialVersionUID = 9110907371922469518L;

	public Integer getIndex() {
		return (Integer) this.get("index");
	}

	public void setIndex(Integer index) {
		this.put("index", index);
	}

	public Long getUserId() {
		return (Long) this.get("userId");
	}

	public void setUserId(Long userId) {
		this.put("userId", userId);
	}

	public String getUserName() {
		return (String) this.get("userName");
	}

	public void setUserName(String userName) {
		this.put("userName", userName);
	}

	public Double getScore() {
		return (Double) this.get("score");
	}

	public void setScore(Double score) {
		this.put("score", score);
		this.put(0L, score);
	}

	public void setSubjScore(Long subjectId, Double score) {
		this.put(subjectId, score);
	}

	public void setSubjsScore(Map<Long, Double> subjs) {
		for (Map.Entry<Long, Double> subj : subjs.entrySet()) {
			this.setSubjScore(subj.getKey(), subj.getValue());
		}
	}

	public Double getSubjScore(Long subjectId) {
		return (Double) this.get(subjectId);
	}
}
