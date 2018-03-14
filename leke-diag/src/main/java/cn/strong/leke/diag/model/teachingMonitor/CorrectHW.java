package cn.strong.leke.diag.model.teachingMonitor;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.strong.leke.common.utils.NumberUtils;

public class CorrectHW extends CommProp implements Serializable{

	private static final long serialVersionUID = 5832098510867926642L;
	
	//课堂数
	private Long lessonNum;
	
	//老师数
	private Long teacherNum;
	
	//共布置作业份数
	private Long assignNum;
	
	//含主观题作业数
	private Long subjectiveNum;
	
	//纯客观题作业数
	private Long objectiveNum;
	
	//应批改作业数
	private Long shouldCorrectNum;
	
	//实批改作业数=（自动批改份数 + 全部批改份数）
	private Long actualCorrectNum;
	
	//自动批改数
	private Long autoCorrectNum;
	
	//老师批改数
	private Long teacherCorrectNum;
	
	//学生批改数
	private Long studentCorrectNum;
	
	//全部批改数
	private Long allCorrectNum;
	
	//部分批改数
	private Long partCorrectNum;
	
	//未批改数
	private Long notCorrectNum;
	
	//平均每堂课作业布置作业份数
	private String avgHWPerLesson;
	
	//平均每位老师布置作业份数
	private String avgHWPerTeacher;
	
	//含主观题作业在共布置作业中占比
	private String subjectiveNumRate = DEFAULT_RATE;
	
	//纯客观题作业在共布置作业中占比
	private String objectiveNumRate = DEFAULT_RATE;
	
	//批改率 =（自动批改份数+全部批改份数）/ 应批改作业份数
	private String correctNumRate = DEFAULT_RATE;
	
	//自动批改率
	private String autoCorrectNumRate = DEFAULT_RATE;
	
	//全部批改率
	private String allCorrectNumRate = DEFAULT_RATE;
	
	//部分批改率
	private String partCorrectNumRate = DEFAULT_RATE;
	
	//未批改率
	private String notCorrectNumRate = DEFAULT_RATE;
	
	private String teacherCorrectNumRate = DEFAULT_RATE;
	
	private String studentCorrectNumRate = DEFAULT_RATE;
	

	public Long getLessonNum() {
		return lessonNum;
	}

	public void setLessonNum(Long lessonNum) {
		this.lessonNum = lessonNum;
	}

	public Long getTeacherNum() {
		return teacherNum;
	}

	public void setTeacherNum(Long teacherNum) {
		this.teacherNum = teacherNum;
	}

	public Long getAssignNum() {
		return assignNum;
	}

	public void setAssignNum(Long assignNum) {
		this.assignNum = assignNum;
	}

	public Long getSubjectiveNum() {
		return subjectiveNum;
	}

	public void setSubjectiveNum(Long subjectiveNum) {
		this.subjectiveNum = subjectiveNum;
	}

	public Long getObjectiveNum() {
		return objectiveNum;
	}

	public void setObjectiveNum(Long objectiveNum) {
		this.objectiveNum = objectiveNum;
	}

	public Long getShouldCorrectNum() {
		return shouldCorrectNum;
	}

	public void setShouldCorrectNum(Long shouldCorrectNum) {
		this.shouldCorrectNum = shouldCorrectNum;
	}
	
	public Long getActualCorrectNum() {
		return actualCorrectNum;
	}

	public void setActualCorrectNum(Long actualCorrectNum) {
		this.actualCorrectNum = actualCorrectNum;
	}

	public Long getAutoCorrectNum() {
		return autoCorrectNum;
	}

	public void setAutoCorrectNum(Long autoCorrectNum) {
		this.autoCorrectNum = autoCorrectNum;
	}

	public Long getAllCorrectNum() {
		return allCorrectNum;
	}

	public void setAllCorrectNum(Long allCorrectNum) {
		this.allCorrectNum = allCorrectNum;
	}

	public Long getPartCorrectNum() {
		return partCorrectNum;
	}

	public void setPartCorrectNum(Long partCorrectNum) {
		this.partCorrectNum = partCorrectNum;
	}

	public Long getNotCorrectNum() {
		return notCorrectNum;
	}

	public void setNotCorrectNum(Long notCorrectNum) {
		this.notCorrectNum = notCorrectNum;
	}

	public String getAvgHWPerLesson() {
		return avgHWPerLesson;
	}

	public void setAvgHWPerLesson(String avgHWPerLesson) {
		this.avgHWPerLesson = avgHWPerLesson;
	}

	public String getAvgHWPerTeacher() {
		return avgHWPerTeacher;
	}

	public void setAvgHWPerTeacher(String avgHWPerTeacher) {
		this.avgHWPerTeacher = avgHWPerTeacher;
	}

	public String getSubjectiveNumRate() {
		return subjectiveNumRate;
	}

	public void setSubjectiveNumRate(String subjectiveNumRate) {
		this.subjectiveNumRate = subjectiveNumRate;
	}

	public String getObjectiveNumRate() {
		return objectiveNumRate;
	}

	public void setObjectiveNumRate(String objectiveNumRate) {
		this.objectiveNumRate = objectiveNumRate;
	}

	public String getCorrectNumRate() {
		return correctNumRate;
	}

	public void setCorrectNumRate(String correctNumRate) {
		this.correctNumRate = correctNumRate;
	}

	public String getAutoCorrectNumRate() {
		return autoCorrectNumRate;
	}

	public void setAutoCorrectNumRate(String autoCorrectNumRate) {
		this.autoCorrectNumRate = autoCorrectNumRate;
	}

	public String getAllCorrectNumRate() {
		return allCorrectNumRate;
	}

	public void setAllCorrectNumRate(String allCorrectNumRate) {
		this.allCorrectNumRate = allCorrectNumRate;
	}

	public String getPartCorrectNumRate() {
		return partCorrectNumRate;
	}

	public void setPartCorrectNumRate(String partCorrectNumRate) {
		this.partCorrectNumRate = partCorrectNumRate;
	}

	public String getNotCorrectNumRate() {
		return notCorrectNumRate;
	}

	public void setNotCorrectNumRate(String notCorrectNumRate) {
		this.notCorrectNumRate = notCorrectNumRate;
	}

	public Long getTeacherCorrectNum() {
		return teacherCorrectNum;
	}

	public void setTeacherCorrectNum(Long teacherCorrectNum) {
		this.teacherCorrectNum = teacherCorrectNum;
	}

	public Long getStudentCorrectNum() {
		return studentCorrectNum;
	}

	public void setStudentCorrectNum(Long studentCorrectNum) {
		this.studentCorrectNum = studentCorrectNum;
	}

	public String getTeacherCorrectNumRate() {
		return teacherCorrectNumRate;
	}

	public void setTeacherCorrectNumRate(String teacherCorrectNumRate) {
		this.teacherCorrectNumRate = teacherCorrectNumRate;
	}

	public String getStudentCorrectNumRate() {
		return studentCorrectNumRate;
	}

	public void setStudentCorrectNumRate(String studentCorrectNumRate) {
		this.studentCorrectNumRate = studentCorrectNumRate;
	}
	
}
