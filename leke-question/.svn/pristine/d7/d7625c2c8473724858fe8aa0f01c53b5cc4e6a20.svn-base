/**
 * 
 */
package cn.strong.leke.question.analysis.core.model;

import java.util.List;

/**
 * 智能组卷生成结果
 * 
 * @author liulongbiao
 *
 */
public class SmartPaperGenerateResult {

	private List<Grp> groups;

	public List<Grp> getGroups() {
		return groups;
	}

	public void setGroups(List<Grp> groups) {
		this.groups = groups;
	}

	/**
	 * 智能组卷大题
	 * 
	 * @author liulongbiao
	 *
	 */
	public static class Grp {
		private String groupTitle; // 大题标题
		private Long questionTypeId;
		private List<Que> questions;

		public String getGroupTitle() {
			return groupTitle;
		}

		public void setGroupTitle(String groupTitle) {
			this.groupTitle = groupTitle;
		}

		public Long getQuestionTypeId() {
			return questionTypeId;
		}

		public void setQuestionTypeId(Long questionTypeId) {
			this.questionTypeId = questionTypeId;
		}

		public List<Que> getQuestions() {
			return questions;
		}

		public void setQuestions(List<Que> questions) {
			this.questions = questions;
		}

		/**
		 * 大题是否为空
		 * 
		 * @return
		 */
		public boolean wasEmpty() {
			return questions == null || questions.isEmpty();
		}

	}

	/**
	 * 智能组卷习题信息
	 * 
	 * @author liulongbiao
	 *
	 */
	public static class Que {

		private Long questionId; // 题目ID
		private Long questionTypeId; // 类型ID
		private Double difficulty; // 题目难度
		private Boolean subjective;
		private Boolean handwritten;
		private Boolean hasSub;

		public Long getQuestionId() {
			return questionId;
		}

		public void setQuestionId(Long questionId) {
			this.questionId = questionId;
		}

		public Long getQuestionTypeId() {
			return questionTypeId;
		}

		public void setQuestionTypeId(Long questionTypeId) {
			this.questionTypeId = questionTypeId;
		}

		public Double getDifficulty() {
			return difficulty == null ? 0.5 : difficulty;
		}

		public void setDifficulty(Double difficulty) {
			this.difficulty = difficulty;
		}

		public Boolean getSubjective() {
			return subjective;
		}

		public void setSubjective(Boolean subjective) {
			this.subjective = subjective;
		}

		public Boolean getHandwritten() {
			return handwritten;
		}

		public void setHandwritten(Boolean handwritten) {
			this.handwritten = handwritten;
		}

		public Boolean getHasSub() {
			return hasSub;
		}

		public void setHasSub(Boolean hasSub) {
			this.hasSub = hasSub;
		}

	}
}
