/**
 * 
 */
package cn.strong.leke.question.analysis.db.model;

import java.util.List;
import java.util.Set;

/**
 * 智能组卷习题数据库查询条件
 * 
 * @author liulongbiao
 *
 */
public class SmartPaperDbQuery {

	private Long schoolStageId; // 学段ID
	private Long subjectId; // 学科ID
	private Long questionTypeId; // 题型ID
	private SectionCriteria section; // 章节条件
	private List<Long> knowledgeIds; // 知识点ID列表
	private Set<Integer> shareScopes; // 习题范围

	private Long userId; // 用户ID
	private Long schoolId; // 学校ID
	private Set<Long> leagueMemberIds; // 联盟成员ID列表

	private int limit; // 题量限制

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

	public Long getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(Long questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public SectionCriteria getSection() {
		return section;
	}

	public void setSection(SectionCriteria section) {
		this.section = section;
	}

	public List<Long> getKnowledgeIds() {
		return knowledgeIds;
	}

	public void setKnowledgeIds(List<Long> knowledgeIds) {
		this.knowledgeIds = knowledgeIds;
	}

	public Set<Integer> getShareScopes() {
		return shareScopes;
	}

	public void setShareScopes(Set<Integer> shareScopes) {
		this.shareScopes = shareScopes;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Set<Long> getLeagueMemberIds() {
		return leagueMemberIds;
	}

	public void setLeagueMemberIds(Set<Long> leagueMemberIds) {
		this.leagueMemberIds = leagueMemberIds;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	// 链式调用方法

	/**
	 * 设置知识点
	 * 
	 * @param knowledgeIds
	 * @return
	 */
	public SmartPaperDbQuery knowledgeIds(List<Long> knowledgeIds) {
		this.knowledgeIds = knowledgeIds;
		return this;
	}

	/**
	 * 设置章节
	 * 
	 * @param section
	 * @return
	 */
	public SmartPaperDbQuery section(SectionCriteria section) {
		this.section = section;
		return this;
	}

	/**
	 * 章节查询条件
	 * 
	 * @author liulongbiao
	 *
	 */
	public static class SectionCriteria {
		private Long materialNodeId; // 章节ID
		private List<Long> knowledgeIds; // 知识点ID列表

		public Long getMaterialNodeId() {
			return materialNodeId;
		}

		public void setMaterialNodeId(Long materialNodeId) {
			this.materialNodeId = materialNodeId;
		}

		public List<Long> getKnowledgeIds() {
			return knowledgeIds;
		}

		public void setKnowledgeIds(List<Long> knowledgeIds) {
			this.knowledgeIds = knowledgeIds;
		}
	}
}
