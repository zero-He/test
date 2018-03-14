package cn.strong.leke.homework.model;

import java.util.List;
import java.util.Map;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

public class UserStats {

	@_id
	@ObjectId
	private String id;
	private Long userId; // 学生ID
	private String userName; // 学生姓名
	private Long classId; // 所在行政班ID
	private String className; // 所在班级名称
	private Long gradeId; // 所在年级ID
	private Long schoolId; // 所在学校ID
	private Integer totalNum; // 作业总数
	private Integer normalNum; // 正常数量
	private Integer delayNum; // 延迟数量
	private List<SubjWork> works; // 学科完成列表
	private List<SubjScore> scores; // 学科得分列表
	private Map<Integer, DayDtl> details; // 练习明细

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getClassId() {
		return classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Map<Integer, DayDtl> getDetails() {
		return details;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getNormalNum() {
		return normalNum;
	}

	public void setNormalNum(Integer normalNum) {
		this.normalNum = normalNum;
	}

	public Integer getDelayNum() {
		return delayNum;
	}

	public List<SubjWork> getWorks() {
		return works;
	}

	public void setWorks(List<SubjWork> works) {
		this.works = works;
	}

	public List<SubjScore> getScores() {
		return scores;
	}

	public void setScores(List<SubjScore> scores) {
		this.scores = scores;
	}

	public void setDelayNum(Integer delayNum) {
		this.delayNum = delayNum;
	}

	public void setDetails(Map<Integer, DayDtl> details) {
		this.details = details;
	}

	public static class DayDtl {
		private Integer totalNum;
		private Integer rightNum;

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
	}

	public static class SubjWork {
		private Long subjectId; // 学科ID
		private String subjectName; // 学科名称
		private Integer totalNum; // 作业总数
		private Integer normalNum; // 正常数量
		private Integer delayNum; // 延迟数量

		public Long getSubjectId() {
			return subjectId;
		}

		public void setSubjectId(Long subjectId) {
			this.subjectId = subjectId;
		}

		public String getSubjectName() {
			return subjectName;
		}

		public void setSubjectName(String subjectName) {
			this.subjectName = subjectName;
		}

		public Integer getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(Integer totalNum) {
			this.totalNum = totalNum;
		}

		public Integer getNormalNum() {
			return normalNum;
		}

		public void setNormalNum(Integer normalNum) {
			this.normalNum = normalNum;
		}

		public Integer getDelayNum() {
			return delayNum;
		}

		public void setDelayNum(Integer delayNum) {
			this.delayNum = delayNum;
		}
	}

	public static class SubjScore {
		private Long subjectId; // 学科ID
		private String subjectName; // 学科名称
		private Integer totalNum; // 作业数
		private Double totalScore; // 总得分
		private Double maxScore; // 最高分
		private Double minScore; // 最低分

		public Long getSubjectId() {
			return subjectId;
		}

		public void setSubjectId(Long subjectId) {
			this.subjectId = subjectId;
		}

		public String getSubjectName() {
			return subjectName;
		}

		public void setSubjectName(String subjectName) {
			this.subjectName = subjectName;
		}

		public Integer getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(Integer totalNum) {
			this.totalNum = totalNum;
		}

		public Double getTotalScore() {
			return totalScore;
		}

		public void setTotalScore(Double totalScore) {
			this.totalScore = totalScore;
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
	}
}
