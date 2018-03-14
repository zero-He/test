package cn.strong.leke.diag.model;

/** 老师：学生成绩分析，按课程和学科id查询 老师下的各单课成绩信息
 * @author Zhang Fujun
 * @date 2015年11月11日
 */
public class CourseSubjectHomeworkDetailsDto {
	private String homeworkName;
	private Float maxScore;
	private Float avgScore;
	private Float minScore;

	/**
	 * @return the homeworkName
	 */
	public String getHomeworkName() {
		return homeworkName;
	}

	/**
	 * @param homeworkName the homeworkName to set
	 */
	public void setHomeworkName(String homeworkName) {
		this.homeworkName = homeworkName;
	}

	/**
	 * @return the maxScore
	 */
	public Float getMaxScore() {
		return maxScore;
	}

	/**
	 * @param maxScore the maxScore to set
	 */
	public void setMaxScore(Float maxScore) {
		this.maxScore = maxScore;
	}

	/**
	 * @return the avgScore
	 */
	public Float getAvgScore() {
		return avgScore;
	}

	/**
	 * @param avgScore the avgScore to set
	 */
	public void setAvgScore(Float avgScore) {
		this.avgScore = avgScore;
	}

	/**
	 * @return the minScore
	 */
	public Float getMinScore() {
		return minScore;
	}

	/**
	 * @param minScore the minScore to set
	 */
	public void setMinScore(Float minScore) {
		this.minScore = minScore;
	}

}
