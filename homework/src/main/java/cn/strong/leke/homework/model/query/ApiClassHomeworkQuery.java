package cn.strong.leke.homework.model.query;

import java.util.List;

import cn.strong.leke.homework.model.mobile.MobileQuery;

/**
 * 手机端：班主任和教务筛选
 * @Author LIU.SHITING
 * @Version 2.6
 * @Date 2017/4/19 9:10
 */
public class ApiClassHomeworkQuery extends MobileQuery {

	private Long schoolId;
	private String keyword;
	private Integer classType;
	private List<Long> classId;
	private Long gradeId;
	/*private List<Integer> subjectId;*/
	private Integer subjectId;

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
}
