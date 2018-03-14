/**
 * 
 */
package cn.strong.leke.question.model.outline;

import java.io.Serializable;

/**
 * 基础提纲查询
 * 
 * @author qw
 *
 */
public class BaseOutlineQuery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4044433468323434312L;

	private Long outlineId; // 大纲ID
	private String outlineName; // 大纲名称
	private String description; // 描述
	private Long schoolStageId; // 学段ID
	private String schoolStageName; // 学段名称
	private Long gradeId; // 年级ID
	private String gradeName; // 年级名称
	private Long subjectId; // 科目ID
	private String subjectName; // 科目名称
	private Long materialId; // 教材ID
	private String materialName; // 教材名称
	private Long schoolId; // 学校ID
	private String schoolName; // 学校名称
	
	public Long getOutlineId() {
		return outlineId;
	}
	
	public void setOutlineId(Long outlineId) {
		this.outlineId = outlineId;
	}
	
	public String getOutlineName() {
		return outlineName;
	}
	
	public void setOutlineName(String outlineName) {
		this.outlineName = outlineName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getSchoolStageId() {
		return schoolStageId;
	}
	
	public void setSchoolStageId(Long schoolStageId) {
		this.schoolStageId = schoolStageId;
	}
	
	public String getSchoolStageName() {
		return schoolStageName;
	}
	
	public void setSchoolStageName(String schoolStageName) {
		this.schoolStageName = schoolStageName;
	}
	
	public Long getGradeId() {
		return gradeId;
	}
	
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	
	public String getGradeName() {
		return gradeName;
	}
	
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
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
	
	public Long getMaterialId() {
		return materialId;
	}
	
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	
	public String getMaterialName() {
		return materialName;
	}
	
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	public Long getSchoolId() {
		return schoolId;
	}
	
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	
	public String getSchoolName() {
		return schoolName;
	}
	
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

}
