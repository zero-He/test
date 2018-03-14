package cn.strong.leke.diag.model;

/**
 * 老师 作业勤奋报告 按课程、学科分组统计列表数据
 * @author Zhang Fujun
 * @date 2015年10月27日
 */
public class CourseSubjectHomeworkStatisticsDto {

	private Long classId;
	private String className;
	private Long subjectId;
	private String subjectName;
	private Float homeworkCount;
	private Float finishRate;
	private Float delayRate;
	private Float noFinishRate;

	/**
	 * @return the classId
	 */
	public Long getClassId() {
		return classId;
	}

	/**
	 * @param classId the classId to set
	 */
	public void setClassId(Long classId) {
		this.classId = classId;
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
	 * @return the homeworkCount
	 */
	public Float getHomeworkCount() {
		return homeworkCount;
	}

	/**
	 * @param homeworkCount the homeworkCount to set
	 */
	public void setHomeworkCount(Float homeworkCount) {
		this.homeworkCount = homeworkCount;
	}

	/**
	 * @return the finishRate
	 */
	public Float getFinishRate() {
		if (finishRate != null) {
			return finishRate * 100;
		}
		return null;
	}

	/**
	 * @param finishRate the finishRate to set
	 */
	public void setFinishRate(Float finishRate) {
		this.finishRate = finishRate;
	}

	/**
	 * @return the delayRate
	 */
	public Float getDelayRate() {
		if (delayRate != null) {
			return delayRate * 100;
		}
		return null;
	}

	/**
	 * @param delayRate the delayRate to set
	 */
	public void setDelayRate(Float delayRate) {
		this.delayRate = delayRate;
	}

	/**
	 * @return the noFinishRate
	 */
	public Float getNoFinishRate() {
		if (noFinishRate != null) {
			return noFinishRate * 100;
		}
		return null;
	}

	/**
	 * @param noFinishRate the noFinishRate to set
	 */
	public void setNoFinishRate(Float noFinishRate) {
		this.noFinishRate = noFinishRate;
	}

}
