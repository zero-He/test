package cn.strong.leke.homework.model.mongo;

import java.util.Date;
import java.util.List;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;


/**
 * 寒暑假作业和微课
 * @author Zhang Fujun
 * @date 2016年12月15日
 */
public class HolidayHwMicro {

	// 练习标识
	@_id
	@ObjectId
	private String _id;
	private Integer year;
	//1:寒假，2：暑假
	private Integer holiday;
	//1：作业 2：微课
	private Integer type;
	////学生Id
	private Long userId;
	private String userName;
	private Long schoolId;
	private Long gradeId;
	private String gradeName;
	private Long classId;
	private String className;
	private Long subjectId;
	private String subjectName;
	private Date firstTime;
	private Date lastTime;
	////总作业数
	private Long total;
	//完成数,
	private Long finish;
	//作业时，关联的学生作业id
	private List<Long> homeworkDtlIds;
	
	//微课时，标记上下学期 ，1：上学期，2：下学期
	private Integer semester;
	//微课时，教材版本
	private Long matVersion;
	private String matVersionName;
	//微课时，教材版本的课本
	private Long bookId;
	private String bookName;
	
	//微课信息
	private List<HolidayMicrocourse> microcourses;
	//章节知识点信息
	private List<MatNode> matNodes;
	
	private Date createdOn;
	private Long createdBy;
	private Boolean isDeleted;
	private Date modifiedOn;
	private Long modifiedBy;
	
	
	/**
	 * @return the _id
	 */
	public String get_id() {
		return _id;
	}
	/**
	 * @param _id the _id to set
	 */
	public void set_id(String _id) {
		this._id = _id;
	}
	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	/**
	 * @return the holiday
	 */
	public Integer getHoliday() {
		return holiday;
	}
	/**
	 * @param holiday the holiday to set
	 */
	public void setHoliday(Integer holiday) {
		this.holiday = holiday;
	}
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the subjectId
	 */
	public Long getSubjectId() {
		return subjectId;
	}
	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	/**
	 * @return the calssId
	 */
	public Long getClassId() {
		return classId;
	}
	/**
	 * @param calssId the calssId to set
	 */
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	/**
	 * @return the firstTime
	 */
	public Date getFirstTime() {
		return firstTime;
	}
	/**
	 * @param firstTime the firstTime to set
	 */
	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}
	/**
	 * @return the lastTime
	 */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * @param lastTime the lastTime to set
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}
	/**
	 * @return the finish
	 */
	public Long getFinish() {
		return finish;
	}
	/**
	 * @param finish the finish to set
	 */
	public void setFinish(Long finish) {
		this.finish = finish;
	}
	/**
	 * @return the homeworkDtlIds
	 */
	public List<Long> getHomeworkDtlIds() {
		return homeworkDtlIds;
	}
	/**
	 * @param homeworkDtlIds the homeworkDtlIds to set
	 */
	public void setHomeworkDtlIds(List<Long> homeworkDtlIds) {
		this.homeworkDtlIds = homeworkDtlIds;
	}
	/**
	 * @return the semester
	 */
	public Integer getSemester() {
		return semester;
	}
	/**
	 * @param semester the semester to set
	 */
	public void setSemester(Integer semester) {
		this.semester = semester;
	}
	/**
	 * @return the microcourses
	 */
	public List<HolidayMicrocourse> getMicrocourses() {
		return microcourses;
	}
	/**
	 * @param microcourses the microcourses to set
	 */
	public void setMicrocourses(List<HolidayMicrocourse> microcourses) {
		this.microcourses = microcourses;
	}
	/**
	 * @return the matNodes
	 */
	public List<MatNode> getMatNodes() {
		return matNodes;
	}
	/**
	 * @param matNodes the matNodes to set
	 */
	public void setMatNodes(List<MatNode> matNodes) {
		this.matNodes = matNodes;
	}
	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}
	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	/**
	 * @return the modifiedOn
	 */
	public Date getModifiedOn() {
		return modifiedOn;
	}
	/**
	 * @param modifiedOn the modifiedOn to set
	 */
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	/**
	 * @return the modifiedBy
	 */
	public Long getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @return the matVersion
	 */
	public Long getMatVersion() {
		return matVersion;
	}
	/**
	 * @param matVersion the matVersion to set
	 */
	public void setMatVersion(Long matVersion) {
		this.matVersion = matVersion;
	}
	/**
	 * @return the matVersionName
	 */
	public String getMatVersionName() {
		return matVersionName;
	}
	/**
	 * @param matVersionName the matVersionName to set
	 */
	public void setMatVersionName(String matVersionName) {
		this.matVersionName = matVersionName;
	}
	/**
	 * @return the bookId
	 */
	public Long getBookId() {
		return bookId;
	}
	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	/**
	 * @return the bookName
	 */
	public String getBookName() {
		return bookName;
	}
	/**
	 * @param bookName the bookName to set
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the gradeId
	 */
	public Long getGradeId() {
		return gradeId;
	}
	/**
	 * @param gradeId the gradeId to set
	 */
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	/**
	 * @return the gradeName
	 */
	public String getGradeName() {
		return gradeName;
	}
	/**
	 * @param gradeName the gradeName to set
	 */
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	/**
	 * @return the schoolId
	 */
	public Long getSchoolId() {
		return schoolId;
	}
	/**
	 * @param schoolId the schoolId to set
	 */
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	
}
