/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.que.template;

import static cn.strong.leke.model.question.QuestionTemplates.BIG_QUE;
import static cn.strong.leke.model.question.QuestionTemplates.CLOZE;
import static cn.strong.leke.model.question.QuestionTemplates.FILL_BLANK;
import static cn.strong.leke.model.question.QuestionTemplates.JUDGEMENT;
import static cn.strong.leke.model.question.QuestionTemplates.Listening;
import static cn.strong.leke.model.question.QuestionTemplates.MULTI_CHOICE;
import static cn.strong.leke.model.question.QuestionTemplates.OPENEND;
import static cn.strong.leke.model.question.QuestionTemplates.ORAL;
import static cn.strong.leke.model.question.QuestionTemplates.READING;
import static cn.strong.leke.model.question.QuestionTemplates.SINGLE_CHOICE;

import java.util.HashMap;
import java.util.Map;

import cn.strong.leke.question.word.que.TemplateConverter;

/**
 * 
 *
 * @author  liulongbiao
 * @created 2014年12月11日 下午7:46:38
 * @since   v3.2
 */
public class TemplateConverterRegistry {

	private static Map<String, TemplateConverter> map;

	static {
		map = new HashMap<String, TemplateConverter>();
		map.put(SINGLE_CHOICE, new ChoiceConverter());
		map.put(MULTI_CHOICE, new ChoiceConverter());
		map.put(FILL_BLANK, new FillBlankConverter());
		map.put(JUDGEMENT, new JudgementConverter());
		map.put(CLOZE, new BaseTemplateConverter());
		map.put(READING, new BaseTemplateConverter());
		map.put(OPENEND, new OpenendConverter());
		map.put(Listening, new BaseTemplateConverter());
		map.put(BIG_QUE, new BaseTemplateConverter());
		// map.put(PUNCTUATE, new JudgementConverter());
		// map.put(HANDWRITE, new JudgementConverter());
		map.put(ORAL, new BaseTemplateConverter());
	}

	public static TemplateConverter get(String template) {
		return map.get(template);
	}

}
