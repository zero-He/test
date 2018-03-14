/**
 * 
 */
package cn.strong.leke.question.analysis.mongo.model;

import java.util.List;

import cn.strong.leke.data.mongo.annotations._id;

/**
 * 教材关联知识点信息(原则上只有末级节点才有)
 * 
 * @author liulongbiao
 *
 */
public class MatNodeKlgs {

	@_id
	private Long materialNodeId; // 教材节点ID
	private String materialNodeName; // 教材节点名称
	private List<Klg> knowledges;

	public Long getMaterialNodeId() {
		return materialNodeId;
	}

	public void setMaterialNodeId(Long materialNodeId) {
		this.materialNodeId = materialNodeId;
	}

	public String getMaterialNodeName() {
		return materialNodeName;
	}

	public void setMaterialNodeName(String materialNodeName) {
		this.materialNodeName = materialNodeName;
	}

	public List<Klg> getKnowledges() {
		return knowledges;
	}

	public void setKnowledges(List<Klg> knowledges) {
		this.knowledges = knowledges;
	}

	/**
	 * 知识点
	 * 
	 * @author liulongbiao
	 *
	 */
	public static class Klg {
		private Long knowledgeId;
		private String knowledgeName;
		private Long schoolStageId; // 学段ID
		private Long subjectId; // 科目ID
		private Long parentId; // 父节点ID
		private Integer weight; // 权重

		public Long getKnowledgeId() {
			return knowledgeId;
		}

		public void setKnowledgeId(Long knowledgeId) {
			this.knowledgeId = knowledgeId;
		}

		public String getKnowledgeName() {
			return knowledgeName;
		}

		public void setKnowledgeName(String knowledgeName) {
			this.knowledgeName = knowledgeName;
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

		public Long getParentId() {
			return parentId;
		}

		public void setParentId(Long parentId) {
			this.parentId = parentId;
		}

		public Integer getWeight() {
			return weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}

	}
}
