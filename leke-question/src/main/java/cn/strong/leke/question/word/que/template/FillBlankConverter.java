/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.que.template;

import static cn.strong.leke.question.word.ParseCsts.CommandNames.ANALYSIS;
import static cn.strong.leke.question.word.ParseCsts.CommandNames.ANSWER;
import static cn.strong.leke.question.word.ParseCsts.CommandNames.STEM;
import static cn.strong.leke.question.word.ParseCsts.CommandNames.SUBJECTIVE;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.question.word.model.Command;
import cn.strong.leke.question.word.model.Doc;
import cn.strong.leke.question.word.model.JsArray;
import cn.strong.leke.question.word.model.QuestionPart;
import cn.strong.leke.question.word.que.BaseQuestionPartParser;

/**
 * 填空题模板
 * 
 * @author liulongbiao
 * @created 2014年12月11日 上午9:33:06
 * @since v3.2
 */
public class FillBlankConverter extends BaseTemplateConverter {

	@Override
	public void parseContent(QuestionDTO result, QuestionPart part,
			BaseQuestionPartParser parser) {
		parseFillBlankContent(result, part);
		parseAnalysis(part.getCommand(ANALYSIS), result, part);
		parseSubjective(part.getCommand(SUBJECTIVE), result);
	}

	public void parseFillBlankContent(QuestionDTO result, QuestionPart part) {
		JsArray<Doc> answers = readFillBlankAnswers(part);
		parseFillBlankStem(result, part, answers);
	}

	private void parseFillBlankStem(QuestionDTO result, QuestionPart part,
			JsArray<Doc> answers) {
		Command cmd = part.getCommand(STEM);
		Doc content = ensureReadContent(cmd,
				"que.word.part.fillblank.stem.missing");
		FillBlankStemParser parser = new FillBlankStemParser(result, answers);
		content.accept(parser);
	}

	private JsArray<Doc> readFillBlankAnswers(QuestionPart part) {
		Command answer = part.getCommand(ANSWER);
		Doc content = ensureReadContent(answer,
				"que.word.part.fillblank.answer.missing");
		FillBlankAnswerParser parser = new FillBlankAnswerParser();
		content.accept(parser);
		return parser.getAnswers();
	}

	private void parseSubjective(Command cmd, QuestionDTO result) {
		boolean subjective = true;
		if (cmd != null) {
			subjective = false;
			String value = cmd.getValue();
			if (value != null && "否".equals(value)) {
				subjective = true;
			}
		}
		result.setSubjective(subjective);
	}
}
