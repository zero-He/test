package cn.strong.leke.homework.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.strong.leke.context.paper.PaperContext;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.model.paper.PaperDTO;
import cn.strong.leke.model.paper.PaperDetail;
import cn.strong.leke.model.paper.ScoredQuestion;
import cn.strong.leke.model.question.QuestionDTO;

public class PaperVisitor {

	private PaperDTO paperDTO;
	private List<Long> questionIds;
	private Map<Long, QuestionDTO> questionMap;
	private Map<Long, ScoredQuestion> questionScoredMap;

	public static PaperVisitor build(PaperDTO paperDTO) {
		PaperVisitor paper = new PaperVisitor();
		paper.initFillData(paperDTO);
		return paper;
	}
	
	public static PaperVisitor build(Long paperId) {
		if(paperId == null){
			return null;
		}
		PaperVisitor paper = new PaperVisitor();
		paper.initFillData(PaperContext.getPaperDTO(paperId));
		return paper;
	}

	private PaperVisitor() {
	}

	private void initFillData(PaperDTO paperDto) {
		this.paperDTO = paperDto;
		this.questionIds = new ArrayList<Long>();
		this.questionMap = new HashMap<Long, QuestionDTO>();
		this.questionScoredMap = new HashMap<Long, ScoredQuestion>();

		this.paperDTO.getDetail().getGroups().stream().forEach(questionGroup -> {
			questionGroup.getQuestions().forEach(scoredQuestion -> {
				this.questionIds.add(scoredQuestion.getQuestionId());
				this.questionScoredMap.put(scoredQuestion.getQuestionId(), scoredQuestion);
				if (scoredQuestion.getHasSub()) {
					scoredQuestion.getSubs().forEach(childScoredQuestion -> {
						this.questionScoredMap.put(childScoredQuestion.getQuestionId(), childScoredQuestion);
					});
				}
			});
		});

		List<QuestionDTO> questionList = QuestionContext.findQuestions(questionIds);
		this.questionMap = questionList.stream().collect(Collectors.toMap(v -> v.getQuestionId(), v -> v));
		questionList.stream().filter(v -> v.getHasSub()).forEach(question -> {
			question.getSubs().forEach(v -> this.questionMap.put(v.getQuestionId(), v));
		});
	}

	public Integer getPaperType() {
		return this.paperDTO.getPaperType();
	}

	public Boolean getSubjective() {
		return this.paperDTO.getSubjective();
	}

	public BigDecimal getTotalScore() {
		return this.paperDTO.getDetail().getTotalScore();
	}
	
	public Integer getQuestionNum() {
		return this.paperDTO.getDetail().getQuestionNum();
	}

	public PaperDetail getPaperDetail() {
		return this.paperDTO.getDetail();
	}

	public QuestionDTO getQuestion(Long questionId) {
		return this.questionMap.get(questionId);
	}

	public ScoredQuestion getQuestionScored(Long questionId) {
		return this.questionScoredMap.get(questionId);
	}
}
