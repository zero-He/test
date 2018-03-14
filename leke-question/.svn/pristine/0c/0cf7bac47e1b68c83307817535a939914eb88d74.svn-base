/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.que.template;

import java.util.ArrayList;
import java.util.List;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.model.question.Answer;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionStem;
import cn.strong.leke.model.question.QuestionTemplates;
import cn.strong.leke.question.word.Doc2HtmlConverter;
import cn.strong.leke.question.word.DocTextExtracter;
import cn.strong.leke.question.word.ParseCsts;
import cn.strong.leke.question.word.model.ChoiceStem;
import cn.strong.leke.question.word.model.Command;
import cn.strong.leke.question.word.model.Doc;
import cn.strong.leke.question.word.model.DocCtx;
import cn.strong.leke.question.word.model.JsArray;
import cn.strong.leke.question.word.model.QuestionPart;
import cn.strong.leke.question.word.que.BaseQuestionPartParser;

/**
 * 选择题模板
 * 
 * @author liulongbiao
 * @created 2014年12月10日 上午10:59:57
 * @since v3.2
 */
public class ChoiceConverter extends BaseTemplateConverter {

	@Override
	public void parseStem(Command cmd, QuestionDTO result, QuestionPart part) {
		Doc content = ensureReadContent(cmd,
				"que.word.part.choice.stem.missing");
		ChoiceStemParser parser = new ChoiceStemParser();
		content.accept(parser);
		ChoiceStem cs = parser.getStem();
		Doc stemDoc = cs.getStem().getDoc();
		if (!stemDoc.isEmpty()) {
			QuestionStem stem = new QuestionStem();
			stem.setStemContent(Doc2HtmlConverter.convert(stemDoc));
			result.setStem(stem);
		}

		List<Answer> answers = new ArrayList<Answer>();
		JsArray<DocCtx> options = cs.getOptions();
		int idx = 0;
		for (DocCtx op : options) {
			if (op == null) {
				throw new LekeRuntimeException(
						"que.word.part.choice.option.missing",
						((char) ParseCsts.CHAR_CODE_A + idx));
			}
			Doc opDoc = op.getDoc();
			Answer answer = new Answer();
			answer.setAnswerContent(Doc2HtmlConverter.convert(opDoc));
			answer.setIsCorrect(false);
			answers.add(answer);
			idx++;
		}
		result.setAnswers(answers);
	}

	@Override
	public void parseAnswer(Command cmd, QuestionDTO result, QuestionPart part) {
		Doc content = ensureReadContent(cmd,
				"que.word.part.choice.options.missing");
		List<Answer> answers = result.getAnswers();
		if (CollectionUtils.isEmpty(answers)) {
			throw new LekeRuntimeException(
					"que.word.part.choice.options.missing");
		}
		int size = answers.size();
		String text = DocTextExtracter.extract(content).trim();
		for (int i = 0, len = text.length(); i < len; i++) {
			char ch = text.charAt(i);
			int idx = text.codePointAt(i) - ParseCsts.CHAR_CODE_A;
			if (idx >= 0 && idx < 26) {
				if (idx >= size) {
					throw new LekeRuntimeException(
							"que.word.part.choice.option.missing", ch);
				}
				Answer answer = answers.get(idx);
				if (answer == null) {
					throw new LekeRuntimeException(
							"que.word.part.choice.option.missing", ch);
				}
				answer.setIsCorrect(true);
			}
		}
	}

	@Override
	public void postProcess(QuestionDTO result, QuestionPart part,
			BaseQuestionPartParser parser) {
		List<Answer> answers = result.getAnswers();
		if (answers == null || answers.size() < 2) {
			throw new LekeRuntimeException("que.word.part.choice.options.min.two");
		}

		int correctCount = getCorrectOptionCount(answers);
		String template = result.getTemplate();
		if (StringUtils.isEmpty(template)) {
			template = QuestionTemplates.MULTI_CHOICE;
			result.setTemplate(template);
		}
		if (QuestionTemplates.SINGLE_CHOICE.equals(template)) {
			if (correctCount != 1) {
				throw new LekeRuntimeException(
						"que.word.part.choice.options.correct.single");
			}
		} else {
			if (correctCount < 1) {
				throw new LekeRuntimeException(
						"que.word.part.choice.options.correct.min.one");
			}
		}
	}

	public int getCorrectOptionCount(List<Answer> answers) {
		int correctCount = 0;
		for (Answer answer : answers) {
			if (answer.getIsCorrect().equals(true)) {
				correctCount++;
			}
		}
		return correctCount;
	}
}
