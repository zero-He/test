package cn.strong.leke.diag.model.report;

import java.util.List;

import cn.strong.leke.beike.model.MicrocourseDTO;

public class StuScoreRptView extends RptView {

	// 是否个人学生
	private Boolean isUnit;
	// 报告类型(周、月、学期、学年)
	private Integer rptType;
	// 概要信息（综合、学科）
	private Summary summary;
	// 综合信息（综合）
	private Overall overall;
	// 有成绩的学科（综合、学科）
	private List<SubjMin> subjs;
	// 成绩曲线（综合、学科）
	private List<TrendModel> trends;
	// 成绩排行榜（综合、学科）
	private List<ScoreRank> scoreRanks;
	// 学科成绩（综合）
	private List<SubjScore> subjScores;
	// 知识点分析（学科）
	private List<KnoGraspRate> knoRates;
	// 推荐微课（学科）
	private List<MicrocourseDTO> micros;

	public StuScoreRptView() {
		super();
	}

	public StuScoreRptView(Boolean success, String message) {
		super(success, message);
	}

	public Boolean getIsUnit() {
		return isUnit;
	}

	public void setIsUnit(Boolean isUnit) {
		this.isUnit = isUnit;
	}

	public Integer getRptType() {
		return rptType;
	}

	public void setRptType(Integer rptType) {
		this.rptType = rptType;
	}

	public Summary getSummary() {
		return summary;
	}

	public void setSummary(Summary summary) {
		this.summary = summary;
	}

	public Overall getOverall() {
		return overall;
	}

	public void setOverall(Overall overall) {
		this.overall = overall;
	}

	public List<TrendModel> getTrends() {
		return trends;
	}

	public void setTrends(List<TrendModel> trends) {
		this.trends = trends;
	}

	public List<ScoreRank> getScoreRanks() {
		return scoreRanks;
	}

	public void setScoreRanks(List<ScoreRank> scoreRanks) {
		this.scoreRanks = scoreRanks;
	}

	public List<SubjScore> getSubjScores() {
		return subjScores;
	}

	public void setSubjScores(List<SubjScore> subjScores) {
		this.subjScores = subjScores;
	}

	public List<SubjMin> getSubjs() {
		return subjs;
	}

	public void setSubjs(List<SubjMin> subjs) {
		this.subjs = subjs;
	}

	public List<KnoGraspRate> getKnoRates() {
		return knoRates;
	}

	public void setKnoRates(List<KnoGraspRate> knoRates) {
		this.knoRates = knoRates;
	}

	public List<MicrocourseDTO> getMicros() {
		return micros;
	}

	public void setMicros(List<MicrocourseDTO> micros) {
		this.micros = micros;
	}

	public static class Summary {

		private Integer totalNum;
		private Integer submitNum;
		private Integer delayNum;
		private Integer correctNum;

		private Double selfScore;
		private Double klassScore;
		private Integer userRank;

		private Integer levelA;
		private Integer levelB;
		private Integer levelC;
		private Integer levelD;

		private Integer levelE;
		private Integer queNum;
		private Double errNum;

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

		public Double getSelfScore() {
			return selfScore;
		}

		public void setSelfScore(Double selfScore) {
			this.selfScore = selfScore;
		}

		public Double getKlassScore() {
			return klassScore;
		}

		public void setKlassScore(Double klassScore) {
			this.klassScore = klassScore;
		}

		public Integer getUserRank() {
			return userRank;
		}

		public void setUserRank(Integer userRank) {
			this.userRank = userRank;
		}

		public Integer getLevelA() {
			return levelA;
		}

		public void setLevelA(Integer levelA) {
			this.levelA = levelA;
		}

		public Integer getLevelB() {
			return levelB;
		}

		public void setLevelB(Integer levelB) {
			this.levelB = levelB;
		}

		public Integer getLevelC() {
			return levelC;
		}

		public void setLevelC(Integer levelC) {
			this.levelC = levelC;
		}

		public Integer getLevelD() {
			return levelD;
		}

		public void setLevelD(Integer levelD) {
			this.levelD = levelD;
		}

		public Integer getLevelE() {
			return levelE;
		}

		public void setLevelE(Integer levelE) {
			this.levelE = levelE;
		}

		public Integer getQueNum() {
			return queNum;
		}

		public void setQueNum(Integer queNum) {
			this.queNum = queNum;
		}

		public Double getErrNum() {
			return errNum;
		}

		public void setErrNum(Double errNum) {
			this.errNum = errNum;
		}
	}

	public static class Overall {

		private Double score;
		private Integer rank;
		private Integer total;
		private String maxes1;
		private String maxes2;
		private String mines1;
		private String mines2;

		public Double getScore() {
			return score;
		}

		public void setScore(Double score) {
			this.score = score;
		}

		public Integer getRank() {
			return rank;
		}

		public void setRank(Integer rank) {
			this.rank = rank;
		}

		public Integer getTotal() {
			return total;
		}

		public void setTotal(Integer total) {
			this.total = total;
		}

		public String getMaxes1() {
			return maxes1;
		}

		public void setMaxes1(String maxes1) {
			this.maxes1 = maxes1;
		}

		public String getMaxes2() {
			return maxes2;
		}

		public void setMaxes2(String maxes2) {
			this.maxes2 = maxes2;
		}

		public String getMines1() {
			return mines1;
		}

		public void setMines1(String mines1) {
			this.mines1 = mines1;
		}

		public String getMines2() {
			return mines2;
		}

		public void setMines2(String mines2) {
			this.mines2 = mines2;
		}
	}

	public static class SubjScore {

		private String label;
		private Double self;
		private Double prev;
		private Double klass;

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public Double getSelf() {
			return self;
		}

		public void setSelf(Double self) {
			this.self = self;
		}

		public Double getPrev() {
			return prev;
		}

		public void setPrev(Double prev) {
			this.prev = prev;
		}

		public Double getKlass() {
			return klass;
		}

		public void setKlass(Double klass) {
			this.klass = klass;
		}
	}

	public static class SubjMin {

		private Long id;
		private String name;

		public SubjMin() {
		}

		public SubjMin(Long id, String name) {
			this.id = id;
			this.name = name;
		}

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
	}
}
