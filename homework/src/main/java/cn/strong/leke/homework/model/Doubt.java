/* 
 * 包名: cn.strong.leke.homework.model
 *
 * 文件名：Doubt.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: luf
 *
 * 日期：2014-6-18
 */
package cn.strong.leke.homework.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    luf
 * @version   Avatar 
 */
public class Doubt implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9165819080248209217L;
	/**我要提问*/
	public static Integer SOURCE_MINE = 1;
	/**作业答疑*/
	public static Integer SOURCE_HOMEWORK = 2;
	/**课内提问*/
	public static Integer SOURCE_LESSON = 3;
	/**点播课*/
	public static Integer SOURCE_VOD = 4;

	//提问id
	private Long doubtId;
	
	//学科id
	private Long subjectId;
	
	// 提问类型
	private Integer doubtType;
	
	//提问标题id
	private String doubtTitle;
	
	//提问内容
	private String doubtContent;

	// 作业ID
	private Long homeworkDtlId;
	
	//题目id
	private Long questionId;
	
	//提问类型  1 - 任课教师 2 - 乐课网老师
	private Long targetType;
	
	//学校id
	private Long schoolId;
	
	//学校名称
	private String schoolName;
	
	//学生名称
	private String userName;
	
	//最佳回答
	private Long bestExpainId;
	
	//提问录音
	private String doubtAudio;
	
	//录音时长字符串
	private String duration;
	
	//是否被删除
	private boolean isDeleted;
	
	//创建人
	private Long createdBy;
	
	//创建时间
	private Date createdOn;
	
	//提问教师id
	private Long teacherId;
	
	//是否已解决
	private Boolean resolved;

	//教师解析列表
	private List<Explain> explains;
	
	//解析时间
	private Date explainTime;
	
	//来源
	private Integer source;
	
	//关键Id
	private Long sourceId;
	
	//来源类型 1.作业、2.暑假作业-微课、3.暑假作业-作业、4.推送作业、5.点播课作业、6.错题本、7.好题本、8.自主学习-学习模式、9.自主学习-联系模式
	private Integer sourceType;
	
	//来源名称
	private String sourceName;
	//年级或课本
	private Long materialId;
	
	// 教材节点ID
	private Long materialNodeId; 
	
	//节点路径名称
	private String materialPathName;
	
	//出版社
	private Long pressId;
	
	// 学段ID
	private Long schoolStageId; 
	
	//学生删除
	private Integer studentIsDeleted;
	
	//老师删除
	private Integer teacherIsDeleted;
	
	//学生收藏
	private Integer studentCollect;
	
	//老师收藏
	private Integer teacherCollect;
	
	//单课Id 课堂传入时使用
	private Long lessonId;
	
	public Long getDoubtId() {
		return doubtId;
	}

	public void setDoubtId(Long doubtId) {
		this.doubtId = doubtId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getDoubtType() {
		return doubtType;
	}

	public void setDoubtType(Integer doubtType) {
		this.doubtType = doubtType;
	}

	public String getDoubtTitle() {
		return doubtTitle;
	}

	public void setDoubtTitle(String doubtTitle) {
		this.doubtTitle = doubtTitle;
	}

	public String getDoubtContent() {
		return doubtContent;
	}

	public void setDoubtContent(String doubtContent) {
		this.doubtContent = doubtContent;
	}

	public Long getHomeworkDtlId() {
		return homeworkDtlId;
	}

	public void setHomeworkDtlId(Long homeworkDtlId) {
		this.homeworkDtlId = homeworkDtlId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getTargetType() {
		return targetType;
	}

	public void setTargetType(Long targetType) {
		this.targetType = targetType;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getBestExpainId() {
		return bestExpainId;
	}

	public void setBestExpainId(Long bestExpainId) {
		this.bestExpainId = bestExpainId;
	}

	public String getDoubtAudio() {
		return doubtAudio;
	}

	public void setDoubtAudio(String doubtAudio) {
		this.doubtAudio = doubtAudio;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Boolean getResolved() {
		return resolved;
	}

	public void setResolved(Boolean resolved) {
		this.resolved = resolved;
	}

	public List<Explain> getExplains() {
		return explains;
	}

	public void setExplains(List<Explain> explains) {
		this.explains = explains;
	}

	public Date getExplainTime() {
		return explainTime;
	}

	public void setExplainTime(Date explainTime) {
		this.explainTime = explainTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Long getMaterialNodeId() {
		return materialNodeId;
	}

	public void setMaterialNodeId(Long materialNodeId) {
		this.materialNodeId = materialNodeId;
	}

	public String getMaterialPathName() {
		return materialPathName;
	}

	public void setMaterialPathName(String materialPathName) {
		this.materialPathName = materialPathName;
	}

	public Long getSchoolStageId() {
		return schoolStageId;
	}

	public void setSchoolStageId(Long schoolStageId) {
		this.schoolStageId = schoolStageId;
	}

	public Integer getStudentIsDeleted() {
		return studentIsDeleted;
	}

	public void setStudentIsDeleted(Integer studentIsDeleted) {
		this.studentIsDeleted = studentIsDeleted;
	}

	public Integer getTeacherIsDeleted() {
		return teacherIsDeleted;
	}

	public void setTeacherIsDeleted(Integer teacherIsDeleted) {
		this.teacherIsDeleted = teacherIsDeleted;
	}

	public Integer getStudentCollect() {
		return studentCollect;
	}

	public void setStudentCollect(Integer studentCollect) {
		this.studentCollect = studentCollect;
	}

	public Integer getTeacherCollect() {
		return teacherCollect;
	}

	public void setTeacherCollect(Integer teacherCollect) {
		this.teacherCollect = teacherCollect;
	}

	public Long getPressId() {
		return pressId;
	}

	public void setPressId(Long pressId) {
		this.pressId = pressId;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public Long getLessonId() {
		return lessonId;
	}

	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
}
