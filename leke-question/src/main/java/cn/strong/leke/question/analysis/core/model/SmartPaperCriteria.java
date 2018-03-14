/**
 * 
 */
package cn.strong.leke.question.analysis.core.model;

import java.util.List;
import java.util.Set;

import cn.strong.leke.question.analysis.db.model.SmartPaperDbQuery.SectionCriteria;

/**
 * 智能组卷生成条件
 * 
 * @author liulongbiao
 *
 */
public class SmartPaperCriteria {

	private Long schoolStageId; // 学段ID
	private Long subjectId; // 学科ID
	private List<SectionCriteria> sections; // 章节条件
	private List<Long> knowledgeIds; // 知识点ID列表
	private Set<Integer> shareScopes; // 习题范围

	private List<TypeCnt> typeCnts; // 题型题量
	private Integer diffLevel; // 难度偏好

	private Long userId; // 用户ID
	private Long schoolId; // 学校ID
	private Set<Long> leagueMemberIds; // 联盟成员ID列表

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

	public List<SectionCriteria> getSections() {
		return sections;
	}

	public void setSections(List<SectionCriteria> sections) {
		this.sections = sections;
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

	public List<TypeCnt> getTypeCnts() {
		return typeCnts;
	}

	public void setTypeCnts(List<TypeCnt> typeCnts) {
		this.typeCnts = typeCnts;
	}

	public Integer getDiffLevel() {
		return diffLevel;
	}

	public void setDiffLevel(Integer diffLevel) {
		this.diffLevel = diffLevel;
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

	/**
	 * 获取偏好难度值
	 * 
	 * @return
	 */
	public double prefDiff() {
		return diffLevel == null ? 0.5 : diffLevel * 0.2 - 0.1;
	}

	/**
	 * 题型题量
	 * 
	 * @author liulongbiao
	 *
	 */
	public static class TypeCnt {

		private Long questionTypeId; // 题型ID
		private int count; // 题量

		public Long getQuestionTypeId() {
			return questionTypeId;
		}

		public void setQuestionTypeId(Long questionTypeId) {
			this.questionTypeId = questionTypeId;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

	}
}
