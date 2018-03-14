/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.que.template;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.model.question.Answer;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.question.word.DocTextExtracter;
import cn.strong.leke.question.word.model.Command;
import cn.strong.leke.question.word.model.Doc;
import cn.strong.leke.question.word.model.QuestionPart;

/**
 * 判断题转换器
 * 
 * @author liulongbiao
 * @created 2014年12月11日 上午10:18:25
 * @since v3.2
 */
public class JudgementConverter extends BaseTemplateConverter {

	private static BidiMap pairs;

	static {
		pairs = new DualHashBidiMap();
		pairs.put("对", "错");
		pairs.put("是", "否");
		pairs.put("正确", "错误");
		pairs.put("√", "×");
	}

	@Override
	protected void parseAnswer(Command cmd, QuestionDTO result,
			QuestionPart part) {
		Doc content = ensureReadContent(cmd,
				"que.word.part.judgement.answer.missing");
		String answer = DocTextExtracter.extract(content).trim();
		if(pairs.containsKey(answer)) {
			addOptions(result, answer, (String) pairs.get(answer), true);
		} else if (pairs.containsValue(answer)) {
			addOptions(result, (String) pairs.getKey(answer), answer, false);
		} else {
			throw new LekeRuntimeException(
					"que.word.part.judgement.answer.unknown", answer);
		}
	}

	private void addOptions(QuestionDTO result, String rightText,
			String wrongText, boolean isRight) {
		Answer right = new Answer();
		right.setAnswerContent(rightText);
		right.setIsCorrect(isRight);
		Answer wrong = new Answer();
		wrong.setAnswerContent(wrongText);
		wrong.setIsCorrect(!isRight);
		List<Answer> answers = Arrays.asList(right, wrong);
		result.setAnswers(answers);
	}
}
