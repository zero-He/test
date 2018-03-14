package cn.strong.leke.question.model.question.query;

import java.util.Date;
import java.util.List;

import cn.strong.leke.model.base.SchoolStageSubject;

/**
 *
 * 描述: 录入习题量统计
 *
 * @author raolei
 * @created 2016年8月4日 上午10:47:22
 * @since v1.0.0
 */
public class AmountQuestionQuery {

	public final static Integer FROM_PAPER = 1;// 来源试卷
	public final static Integer FROM_WB = 2;// 来源习题册

	private Long userId;// 统计的用户ID
	private List<Long> userIds;// 统计的用户Id
	private Long schoolStageId;// 学段
	private Long subjectId;// 学科
	private Date minCreatedOn;// 开始日期
	private Date maxCreatedOn;// 结束日期
	private List<SchoolStageSubject> schoolStageSubjects;
	private Integer questionFrom;// 习题创建来源


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSchoolStageId() {
		return schoolStageId;
	}

	public void setSchoolStageId(Long schoolStageId) {
		this.schoolStageId = schoolStageId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Date getMinCreatedOn() {
		return minCreatedOn;
	}

	public void setMinCreatedOn(Date minCreatedOn) {
		this.minCreatedOn = minCreatedOn;
	}

	public Date getMaxCreatedOn() {
		return maxCreatedOn;
	}

	public void setMaxCreatedOn(Date maxCreatedOn) {
		this.maxCreatedOn = maxCreatedOn;
	}

	public List<SchoolStageSubject> getSchoolStageSubjects() {
		return schoolStageSubjects;
	}

	public void setSchoolStageSubjects(List<SchoolStageSubject> schoolStageSubjects) {
		this.schoolStageSubjects = schoolStageSubjects;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	public Integer getQuestionFrom() {
		return questionFrom;
	}

	public void setQuestionFrom(Integer questionFrom) {
		this.questionFrom = questionFrom;
	}

}
