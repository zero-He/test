package cn.strong.leke.diag.model.report;

import java.util.List;

public class ClsSubjRptView extends RptView {

	private Integer rptType;
	private Summary summary;
	private List<TrendModel> trends;
	private List<ScoreRank> scoreRanks;
	private List<KnoGraspRate> knoRates;

	public ClsSubjRptView() {
		super();
	}

	public ClsSubjRptView(Boolean success, String message) {
		super(success, message);
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

	public List<ScoreRank> getScoreRanks() {
		return scoreRanks;
	}

	public void setScoreRanks(List<ScoreRank> scoreRanks) {
		this.scoreRanks = scoreRanks;
	}

	public List<TrendModel> getTrends() {
		return trends;
	}

	public void setTrends(List<TrendModel> trends) {
		this.trends = trends;
	}

	public List<KnoGraspRate> getKnoRates() {
		return knoRates;
	}

	public void setKnoRates(List<KnoGraspRate> knoRates) {
		this.knoRates = knoRates;
	}

	public static class Summary {

		private Double avgScore;
		private Double maxScore;
		private Double minScore;
		private Integer totalNum;
		private Integer levelA;
		private Integer levelB;
		private Integer levelC;
		private Integer levelD;
		private Integer levelE;
		private Integer otherNum;

		public Double getAvgScore() {
			return avgScore;
		}

		public void setAvgScore(Double avgScore) {
			this.avgScore = avgScore;
		}

		public Double getMaxScore() {
			return maxScore;
		}

		public void setMaxScore(Double maxScore) {
			this.maxScore = maxScore;
		}

		public Double getMinScore() {
			return minScore;
		}

		public void setMinScore(Double minScore) {
			this.minScore = minScore;
		}

		public Integer getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(Integer totalNum) {
			this.totalNum = totalNum;
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

		public Integer getOtherNum() {
			return otherNum;
		}

		public void setOtherNum(Integer otherNum) {
			this.otherNum = otherNum;
		}
	}
}
