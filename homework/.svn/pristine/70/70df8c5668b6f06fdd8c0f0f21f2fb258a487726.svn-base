package cn.strong.leke.homework.model.query;

public class ApiParamForStuHomework {

	/**
	 * 学生id
	 */
	private Long studentId;
	/**
	 * 学科id
	 */
	private Long subjectId;
	/**
	 * 全部：2、待完成：0、待订正：1
	 */
	private Integer state;
	/**
	 * 默认按照紧急度排序，可以选择按作业截止时间，作业开始时间排序。
	 * 1.默认：按照作业截止时间与当前时间，从小到大排序。已超时作业>未提交作业
	 * 2.作业截止时间：按照作业截止时间由近及远倒序
	 * 3.作业开始时间：按照作业开始时间由近及远倒序
	 */
	private Integer sort;

	/**
	 *  mysql LIMIT start,limit 
	 */
	private Integer start;

	/**
	 *  mysql LIMIT start,limit 
	 */
	private Integer limit;

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
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * @return the start
	 */
	public Integer getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Integer start) {
		this.start = start;
	}

	/**
	 * @return the limit
	 */
	public Integer getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	/**
	 * @return the studentId
	 */
	public Long getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	/**
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
