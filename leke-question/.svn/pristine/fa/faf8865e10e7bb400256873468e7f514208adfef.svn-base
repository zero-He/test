package cn.strong.leke.question.model.question.dto;

import java.util.List;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.model.question.QuestionDTO;

/**
 * 用于组卷的习题信息
 * 
 * @author liulongbiao
 *
 */
public class PapQue {

	/**
	 * 将习题信息提取为组卷相关习题信息
	 * 
	 * @param que
	 * @return
	 */
	public static PapQue toPapQue(QuestionDTO que) {
		if (que == null) {
			return null;
		}
		PapQue result = new PapQue();
		result.setQuestionId(que.getQuestionId());
		result.setQuestionTypeId(que.getQuestionTypeId());
		result.setHasSub(que.getHasSub());
		result.setSubjective(que.getSubjective());
		result.setHandwritten(que.getHandwritten());
		result.setSubs(ListUtils.map(que.getSubs(), PapQue::toPapQue));
		return result;
	}

	private Long questionId;
	private Long questionTypeId;
	private Boolean hasSub;
	private Boolean subjective;
	private Boolean handwritten;
	private List<PapQue> subs;

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

	public Boolean getHandwritten() {
		return handwritten;
	}

	public void setHandwritten(Boolean handwritten) {
		this.handwritten = handwritten;
	}

	public List<PapQue> getSubs() {
		return subs;
	}

	public void setSubs(List<PapQue> subs) {
		this.subs = subs;
	}

}