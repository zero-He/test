/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.model;

import java.util.List;

import cn.strong.leke.model.question.QuestionKnowledge;
import cn.strong.leke.model.question.QuestionSection;
import cn.strong.leke.model.question.SchoolQuestionOutlineNode;

/**
 * 习题共享
 * 
 * @author liulongbiao
 */
public class QuestionShareEvent {

	private Long questionId; // 习题ID
	private Boolean shareSchool; // 是否共享到学校
	private Boolean sharePlatform; // 是否共享到乐课网
	private Long schoolStageId; // 学段ID
	private Long subjectId; // 学科ID
	private List<QuestionSection> sections;// 章节
	private QuestionKnowledge knowledge; // 知识点
	private SchoolQuestionOutlineNode outlineNode;// 大纲

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Boolean getShareSchool() {
		return shareSchool;
	}

	public void setShareSchool(Boolean shareSchool) {
		this.shareSchool = shareSchool;
	}

	public Boolean getSharePlatform() {
		return sharePlatform;
	}

	public void setSharePlatform(Boolean sharePlatform) {
		this.sharePlatform = sharePlatform;
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

	public List<QuestionSection> getSections() {
		return sections;
	}

	public void setSections(List<QuestionSection> sections) {
		this.sections = sections;
	}

	public QuestionKnowledge getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(QuestionKnowledge knowledge) {
		this.knowledge = knowledge;
	}

	public SchoolQuestionOutlineNode getOutlineNode() {
		return outlineNode;
	}

	public void setOutlineNode(SchoolQuestionOutlineNode outlineNode) {
		this.outlineNode = outlineNode;
	}

}
