package cn.strong.leke.homework.model.mobile;

import java.util.List;

import cn.strong.leke.homework.model.mobile.MobileQuery;

public class MSchoolWorkQuery extends MobileQuery {

	private String keyword;
	private Long schoolId;
	private Long gradeId;
	private Long subjectId;
	private Integer classType;
	private List<Long> classId;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getClassType() {
		return classType;
	}

	public void setClassType(Integer classType) {
		this.classType = classType;
	}

	public List<Long> getClassId() {
		return classId;
	}

	public void setClassId(List<Long> classId) {
		this.classId = classId;
	}
}
