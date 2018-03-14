/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.que.template;

import static cn.strong.leke.question.word.ParseCsts.CommandNames.ANSWER;
import cn.strong.leke.model.question.Analysis;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.question.word.Doc2HtmlConverter;
import cn.strong.leke.question.word.model.Command;
import cn.strong.leke.question.word.model.Doc;
import cn.strong.leke.question.word.model.QuestionPart;

/**
 * 开放题型转换器
 * 
 * @author liulongbiao
 * @created 2014年12月10日 下午7:57:48
 * @since v3.2
 */
public class OpenendConverter extends BaseTemplateConverter {

	@Override
	protected void parseAnalysis(Command cmd, QuestionDTO result,
			QuestionPart part) {
		Doc doc = new Doc();
		// 开放题将答案区和解析区合并，作为习题的解析存储
		Command answer = part.getCommand(ANSWER);
		if (answer != null) {
			doc.appendDoc(answer.getContent());
		}
		if (cmd != null) {
			doc.appendDoc(cmd.getContent());
		}
		if (!doc.isEmpty()) {
			Analysis analysis = new Analysis();
			String analysisContent = Doc2HtmlConverter.convert(doc);
			analysis.setAnalysisContent(analysisContent);
			result.setAnalysis(analysis);
		}
	}
}
