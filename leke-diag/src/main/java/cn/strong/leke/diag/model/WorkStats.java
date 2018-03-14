package cn.strong.leke.diag.model;

import java.util.ArrayList;
import java.util.List;

import cn.strong.leke.data.mongo.annotations._id;

public class WorkStats {

	@_id
	private String id;
	private Long homeworkId;
	private List<Sum> sums = new ArrayList<Sum>();
	private List<Chart> charts = new ArrayList<Chart>();
	private List<Named> nameds = new ArrayList<Named>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
	}

	public List<Sum> getSums() {
		return sums;
	}

	public void setSums(List<Sum> sums) {
		this.sums = sums;
	}

	public List<Chart> getCharts() {
		return charts;
	}

	public void setCharts(List<Chart> charts) {
		this.charts = charts;
	}

	public List<Named> getNameds() {
		return nameds;
	}

	public void setNameds(List<Named> nameds) {
		this.nameds = nameds;
	}

	public static class Sum {

		private Long questionId;// 题目ID
		private Integer questionNo;// 题目序号
		private Integer totalNum;// 答题人数
		private Integer rightNum;// 正确人数
		private Double rightRate;// 正确率
		private Double scoreRate;// 得分率

		// 学生得分率
		// private BigDecimal stuRate;
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

		public Integer getRightNum() {
			return rightNum;
		}

		public void setRightNum(Integer rightNum) {
			this.rightNum = rightNum;
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

	public static class Chart {

		private Long questionId;
		private String datas;
		private List<Named> names;

		public Chart() {
		}

		public Chart(Long questionId, String datas) {
			this.questionId = questionId;
			this.datas = datas;
		}

		public Chart(Long questionId, String datas, List<Named> names) {
			this.questionId = questionId;
			this.datas = datas;
			this.names = names;
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

		public List<Named> getNames() {
			return names;
		}

		public void setNames(List<Named> names) {
			this.names = names;
		}
	}

	public static class Named {

		private String label;
		private List<String> users;

		public Named() {
		}

		public Named(String label, List<String> users) {
			this.label = label;
			this.users = users;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public List<String> getUsers() {
			return users;
		}

		public void setUsers(List<String> users) {
			this.users = users;
		}
	}
}
