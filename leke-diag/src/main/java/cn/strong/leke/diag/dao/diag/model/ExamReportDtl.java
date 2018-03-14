package cn.strong.leke.diag.dao.diag.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ExamReportDtl implements Serializable {

	private static final long serialVersionUID = 7213170463936911488L;
	
	//成绩单明细ID
	private Long examReportDtlId;

	//成绩单ID
	private Long examReportId;
	
	//成绩单标题
	private String examReportName;
	
	//学生标识
	private Long studentId;
	
	//学生姓名
	private String studentName;
	
	//乐号
	private String loginName;
	
	//学科ID
	private Long subjectId;
	
	//学科名称
	private String subjectName;
	
	//班级ID
	private Long classId;
	
	//班级名称
	private String className;
	
	//成绩
	private BigDecimal score;
	
	//最高分
	private BigDecimal maxScore;
	
	//最低分
	private BigDecimal minScore;
	
	//平均分
	private BigDecimal avgScore;
	
	//寄语
	private String greatings;
	
	//学校名称
	private String schoolName;
	
	//创建日期
	private String createdOn;

	public Long getExamReportDtlId() {
		return examReportDtlId;
	}

	public void setExamReportDtlId(Long examReportDtlId) {
		this.examReportDtlId = examReportDtlId;
	}

	public Long getExamReportId() {
		return examReportId;
	}

	public void setExamReportId(Long examReportId) {
		this.examReportId = examReportId;
	}

	public String getExamReportName() {
		return examReportName;
	}

	public void setExamReportName(String examReportName) {
		this.examReportName = examReportName;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

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

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public BigDecimal getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(BigDecimal maxScore) {
		this.maxScore = maxScore;
	}

	public BigDecimal getMinScore() {
		return minScore;
	}

	public void setMinScore(BigDecimal minScore) {
		this.minScore = minScore;
	}

	public BigDecimal getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(BigDecimal avgScore) {
		this.avgScore = avgScore;
	}
	
	public String getGreatings() {
		return greatings;
	}

	public void setGreatings(String greatings) {
		this.greatings = greatings;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof ExamReportDtl))
			return false;
		if(this.getLoginName().equals(((ExamReportDtl)obj).getLoginName()) &&
		   this.getSubjectId().longValue() == ((ExamReportDtl)obj).getSubjectId().longValue()) {
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(this.getLoginName(), this.getSubjectId());
	}

}
