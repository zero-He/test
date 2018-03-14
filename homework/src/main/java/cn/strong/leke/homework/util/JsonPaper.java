package cn.strong.leke.homework.util;

import java.math.BigDecimal;
import java.util.List;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.model.paper.PaperDTO;

public class JsonPaper {
	private Long paperId; // 组卷ID
	private String title; // 组卷标题
	private Long subjectId; // 学科ID
	private String subjectName; // 学科名称
	private Integer paperType; // 组卷类型
	private Boolean subjective; // 是否包含主观题
	private PaperDetail detail; // 组卷明细
	private PaperAttachment attachment; // 附件

	public Long getPaperId() {
		return paperId;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getPaperType() {
		return paperType;
	}

	public void setPaperType(Integer paperType) {
		this.paperType = paperType;
	}

	public Boolean getSubjective() {
		return subjective;
	}

	public void setSubjective(Boolean subjective) {
		this.subjective = subjective;
	}

	public PaperDetail getDetail() {
		return detail;
	}

	public void setDetail(PaperDetail detail) {
		this.detail = detail;
	}

	public PaperAttachment getAttachment() {
		return attachment;
	}

	public void setAttachment(PaperAttachment attachment) {
		this.attachment = attachment;
	}

	public static class PaperAttachment {
		private Long paperAttachmentId; // 试卷附件主键ID
		private String fileId;
		private String sourceExt; // 源文件扩展名
		private Integer destPageCount; // 转换文件页数

		public String getSourceExt() {
			return sourceExt;
		}

		public void setSourceExt(String sourceExt) {
			this.sourceExt = sourceExt;
		}

		public Integer getDestPageCount() {
			return destPageCount;
		}

		public void setDestPageCount(Integer destPageCount) {
			this.destPageCount = destPageCount;
		}

		/**
		 * @return the paperAttachmentId
		 */
		public Long getPaperAttachmentId() {
			return paperAttachmentId;
		}

		/**
		 * @param paperAttachmentId the paperAttachmentId to set
		 */
		public void setPaperAttachmentId(Long paperAttachmentId) {
			this.paperAttachmentId = paperAttachmentId;
		}

		/**
		 * @return the fileId
		 */
		public String getFileId() {
			return fileId;
		}

		/**
		 * @param fileId the fileId to set
		 */
		public void setFileId(String fileId) {
			this.fileId = fileId;
		}

	}

	public static class PaperDetail {

		private BigDecimal totalScore; // 总分
		private BigDecimal rateA; // 优秀占分比
		private BigDecimal rateB; // 良好占分比
		private BigDecimal rateC; // 及格占分比
		private Boolean subjective; // 是否包含主观题
		private List<QuestionGroup> groups; // 大题列表

		public BigDecimal getTotalScore() {
			return totalScore;
		}

		public void setTotalScore(BigDecimal totalScore) {
			this.totalScore = totalScore;
		}

		public BigDecimal getRateA() {
			return rateA;
		}

		public void setRateA(BigDecimal rateA) {
			this.rateA = rateA;
		}

		public BigDecimal getRateB() {
			return rateB;
		}

		public void setRateB(BigDecimal rateB) {
			this.rateB = rateB;
		}

		public BigDecimal getRateC() {
			return rateC;
		}

		public void setRateC(BigDecimal rateC) {
			this.rateC = rateC;
		}

		public Boolean getSubjective() {
			return subjective;
		}

		public void setSubjective(Boolean subjective) {
			this.subjective = subjective;
		}

		public List<QuestionGroup> getGroups() {
			return groups;
		}

		public void setGroups(List<QuestionGroup> groups) {
			this.groups = groups;
		}
	}

	public static class QuestionGroup {
		private String groupTitle; // 大题标题
		private Integer ord; // 大题序号(从 1 开始)
		private String ordDesc; // 大题序号描述
		private BigDecimal score; // 大题总分
		private Boolean subjective; // 主观性
		private List<ScoredQuestion> questions; // 题目列表

		public String getGroupTitle() {
			return groupTitle;
		}

		public void setGroupTitle(String groupTitle) {
			this.groupTitle = groupTitle;
		}

		public Integer getOrd() {
			return ord;
		}

		public void setOrd(Integer ord) {
			this.ord = ord;
		}

		public String getOrdDesc() {
			return ordDesc;
		}

		public void setOrdDesc(String ordDesc) {
			this.ordDesc = ordDesc;
		}

		public BigDecimal getScore() {
			return score;
		}

		public void setScore(BigDecimal score) {
			this.score = score;
		}

		public Boolean getSubjective() {
			return subjective;
		}

		public void setSubjective(Boolean subjective) {
			this.subjective = subjective;
		}

		public List<ScoredQuestion> getQuestions() {
			return questions;
		}

		public void setQuestions(List<ScoredQuestion> questions) {
			this.questions = questions;
		}
	}

	public static class ScoredQuestion {

		private Integer ord; // 题目序号
		private Long questionId; // 题目ID
		private BigDecimal score; // 题目总分
		private Boolean hasSub; // 是否有子题
		private Boolean subjective; // 主观性
		private List<ScoredQuestion> subs; // 子题

		public Integer getOrd() {
			return ord;
		}

		public void setOrd(Integer ord) {
			this.ord = ord;
		}

		public Long getQuestionId() {
			return questionId;
		}

		public void setQuestionId(Long questionId) {
			this.questionId = questionId;
		}

		public BigDecimal getScore() {
			return score;
		}

		public void setScore(BigDecimal score) {
			this.score = score;
		}

		public Boolean getHasSub() {
			return hasSub;
		}

		public void setHasSub(Boolean hasSub) {
			this.hasSub = hasSub;
		}

		public Boolean getSubjective() {
			return subjective;
		}

		public void setSubjective(Boolean subjective) {
			this.subjective = subjective;
		}

		public List<ScoredQuestion> getSubs() {
			return subs;
		}

		public void setSubs(List<ScoredQuestion> subs) {
			this.subs = subs;
		}
	}

	public static JsonPaper mapper(PaperDTO paper) {
		String json = JsonUtils.toJSON(paper);
		return JsonUtils.fromJSON(json, JsonPaper.class);
	}

	public static List<JsonPaper> mapper(List<PaperDTO> papers) {
		String json = JsonUtils.toJSON(papers);
		return JsonUtils.readList(json, JsonPaper.class);
	}
}