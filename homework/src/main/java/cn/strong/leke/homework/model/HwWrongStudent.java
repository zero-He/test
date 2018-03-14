package cn.strong.leke.homework.model;

import java.util.List;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * 
 * @author Zhang Fujun
 * @date 2017年5月9日
 */
public class HwWrongStudent {

	@_id
	@ObjectId
	private String id;
	private Long questionId;// 作业题目id
	private Long homeworkId;// 老师作业ID
	private List<Long> students;


	/**
	 * @return the homeworkId
	 */
	public Long getHomeworkId() {
		return homeworkId;
	}

	/**
	 * @param homeworkId the homeworkId to set
	 */
	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
	}

	/**
	 * @return the wrongStuTotal
	 */
	public Integer getTotal() {
		if(this.students == null){
			return 0;
		}
		return this.students.size();
	}

	/**
	 * @return the students
	 */
	public List<Long> getStudents() {
		return students;
	}

	/**
	 * @param students the students to set
	 */
	public void setStudents(List<Long> students) {
		this.students = students;
	}

	/**
	 * @return the questionId
	 */
	public Long getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}
