package cn.strong.leke.diag.model.studentMonitor;

/**
 * @author  Liu.ShiTing
 * @version  1.5
 * @date  2017-11-20 13:59:08
 */
public class ActiveLearningCompareBean extends ActiveLearningTrendBean {

	private Long subjectId;
	private String subjectName;
	private Long classId;
	private String className;

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
}
