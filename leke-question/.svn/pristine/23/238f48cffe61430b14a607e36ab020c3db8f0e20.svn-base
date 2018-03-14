/**
 * 
 */
package cn.strong.leke.question.util;

import static cn.strong.leke.framework.exceptions.Validation.isTrue;
import static cn.strong.leke.framework.exceptions.Validation.notNull;

import java.util.ArrayList;
import java.util.List;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.question.Analysis;
import cn.strong.leke.model.question.Answer;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionStem;

/**
 * 习题内容对比类
 * 
 * @author liulongbiao
 *
 */
public class QueContentDiff {

	public final List<QuestionStem> stems = new ArrayList<>();
	public final List<Analysis> analysises = new ArrayList<>();
	public final List<Answer> answers = new ArrayList<>();

	private QueContentDiff() {
		// 私有构造函数
	}

	public void add(QuestionStem stem) {
		stems.add(stem);
	}

	public void add(Analysis analysis) {
		analysises.add(analysis);
	}

	public void add(Answer answer) {
		answers.add(answer);
	}

	/**
	 * 对比两道习题，获取内容变更
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	public static QueContentDiff diff(QuestionDTO source, QuestionDTO target) {
		QueContentDiff result = new QueContentDiff();
		diffQueContent(source, target, result, "");
		return result;
	}

	private static void diffQueContent(QuestionDTO source, QuestionDTO target,
			QueContentDiff result,
			String prefix) {
		notNull(source, prefix + "对比来源习题不能为 null");
		notNull(target, prefix + "对比目标习题不能为 null");
		isTrue(source.getTemplate().equals(target.getTemplate()), prefix + "待对比的习题题型不一致");
		diffStem(source.getStem(), target.getStem(), result, prefix);
		diffAnswers(source.getAnswers(), target.getAnswers(), result, prefix);
		diffAnalysis(source.getAnalysis(), target.getAnalysis(), result, prefix);
		diffSubs(source.getSubs(), target.getSubs(), result, prefix);
	}

	public static QueContentDiff diff2(QuestionDTO source, QuestionDTO target) {
		QueContentDiff result = new QueContentDiff();
		diffQueContent2(source, target, result, "");
		return result;
	}

	private static void diffQueContent2(QuestionDTO source, QuestionDTO target, QueContentDiff result, String prefix) {
		notNull(source, prefix + "对比来源习题不能为 null");
		notNull(target, prefix + "对比目标习题不能为 null");
		isTrue(source.getTemplate().equals(target.getTemplate()), prefix + "待对比的习题题型不一致");
		diffStem(source.getStem(), target.getStem(), result, prefix);
		diffAnalysis(source.getAnalysis(), target.getAnalysis(), result, prefix);
		diffSubs2(source.getSubs(), target.getSubs(), result, prefix);
	}

	private static void diffStem(QuestionStem stem, QuestionStem stem2, QueContentDiff result,
			String prefix) {
		if (stem == null) {
			// ignore source stem missing
			return;
		}
		String content = "";
		if (stem2 != null && stem2.getStemContent() != null) {
			content = stem2.getStemContent();
		}
		boolean contNotEqual = !content.equals(stem.getStemContent());
		Boolean isFbUnord = stem2.getIsFbUnord();
		boolean fbUnordNotEqual = isFbUnord != null && !isFbUnord.equals(stem.getIsFbUnord());
		if (contNotEqual || fbUnordNotEqual) {
			stem.setStemContent(content);
			stem.setIsFbUnord(isFbUnord);
			result.add(stem);
		}
	}

	private static void diffAnswers(List<Answer> answers, List<Answer> answers2,
			QueContentDiff result, String prefix) {
		if (CollectionUtils.isEmpty(answers)) {
			isTrue(CollectionUtils.isEmpty(answers2), prefix + "答案个数不一致");
			return;
		}
		Validation.notEmpty(answers2, prefix + "答案个数不一致");
		isTrue(answers.size() == answers2.size(), prefix + "答案个数不一致");
		int size = answers.size();
		for (int i = 0; i < size; i++) {
			diffAnswer(answers.get(i), answers2.get(i), result, prefix + "第" + (i + 1) + "个答案");
		}
	}

	private static void diffAnswer(Answer answer, Answer answer2, QueContentDiff result,
			String prefix) {
		notNull(answer, prefix + "对比来源答案不能为 null");
		notNull(answer2, prefix + "对比目标答案不能为 null");
		isTrue(answer.getIsCorrect().equals(answer2.getIsCorrect()), prefix + "答案正确性不一致");

		String content = "";
		if (answer2 != null && answer2.getAnswerContent() != null) {
			content = answer2.getAnswerContent();
		}
		if (!content.equals(answer.getAnswerContent())) {
			answer.setAnswerContent(content);
			result.add(answer);
		}
	}

	private static void diffAnalysis(Analysis analysis, Analysis analysis2, QueContentDiff result,
			String prefix) {
		if (analysis == null) {
			// ignore source analysis missing
			return;
		}
		String content = "";
		if (analysis2 != null && analysis2.getAnalysisContent() != null) {
			content = analysis2.getAnalysisContent();
		}
		if (!content.equals(analysis.getAnalysisContent())) {
			analysis.setAnalysisContent(content);
			result.add(analysis);
		}
	}

	private static void diffSubs2(List<QuestionDTO> subs, List<QuestionDTO> subs2, QueContentDiff result, String prefix) {
		if (CollectionUtils.isEmpty(subs)) {
			isTrue(CollectionUtils.isEmpty(subs2), prefix + "小题个数不一致");
			return;
		}
		Validation.notEmpty(subs2, prefix + "小题个数不一致");
		isTrue(subs.size() == subs2.size(), prefix + "小题个数不一致");
		int size = subs.size();
		for (int i = 0; i < size; i++) {
			diffQueContent2(subs.get(i), subs2.get(i), result, prefix + "第" + (i + 1) + "小题");
		}
	}

	private static void diffSubs(List<QuestionDTO> subs, List<QuestionDTO> subs2,
			QueContentDiff result, String prefix) {
		if (CollectionUtils.isEmpty(subs)) {
			isTrue(CollectionUtils.isEmpty(subs2), prefix + "小题个数不一致");
			return;
		}
		Validation.notEmpty(subs2, prefix + "小题个数不一致");
		isTrue(subs.size() == subs2.size(), prefix + "小题个数不一致");
		int size = subs.size();
		for (int i = 0; i < size; i++) {
			diffQueContent(subs.get(i), subs2.get(i), result, prefix + "第" + (i + 1) + "小题");
		}
	}

}
