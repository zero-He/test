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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.model.question.Answer;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionStem;
import cn.strong.leke.question.word.Doc2HtmlConverter;
import cn.strong.leke.question.word.model.BaseDocVisitor;
import cn.strong.leke.question.word.model.Br;
import cn.strong.leke.question.word.model.Doc;
import cn.strong.leke.question.word.model.DocCtx;
import cn.strong.leke.question.word.model.Img;
import cn.strong.leke.question.word.model.JsArray;
import cn.strong.leke.question.word.model.Paragraph;
import cn.strong.leke.question.word.model.Text;

/**
 * 
 * 
 * @author liulongbiao
 * @created 2014年12月11日 下午4:22:34
 * @since v3.2
 */
public class FillBlankStemParser extends BaseDocVisitor {

	private static final Pattern PAT_PLACEHOLDER = Pattern.compile("_+");

	private final QuestionDTO question;
	private List<Answer> answers;
	private final JsArray<Doc> adocs;
	private DocCtx ctx = new DocCtx();
	private int answerCount = 0;

	public FillBlankStemParser(QuestionDTO question, JsArray<Doc> adocs) {
		this.question = question;
		this.adocs = adocs;
		answers = new ArrayList<Answer>();
	}

	@Override
	public void preVisit(Paragraph p) {
		ctx.paragraphStarted();
	}

	@Override
	public void preVisit(Text text) {
		String content = StringUtils.trim(text.getContent());
		if (StringUtils.isEmpty(content)) {
			return;
		}
		Matcher mat = PAT_PLACEHOLDER.matcher(content);
		if (mat.find()) {
			int start = mat.start();
			int end = mat.end();
			if (start > 0) {
				Text pre = text.copy();
				pre.setContent(content.substring(0, start));
				ctx.addInline(pre);
			}

			Answer answer = new Answer();
			Doc adoc = adocs.get(answerCount);
			if (adoc == null || adoc.isEmpty()) {
				throw new LekeRuntimeException(
						"que.word.part.fillblank.blank.answer.missing",
						(answerCount + 1));
			}
			answer.setAnswerContent(Doc2HtmlConverter.convert(adoc));
			answer.setIsCorrect(true);
			answers.add(answer);
			ctx.addInline(new Text("##" + (end - start) + "##"));
			answerCount++;

			text.setContent(content.substring(end));
			preVisit(text);
		}
	}

	@Override
	public void postVisit(Text text) {
		String content = StringUtils.trim(text.getContent());
		if (StringUtils.isNotEmpty(content)) {
			ctx.addInline(text);
		}
	}

	@Override
	public void postVisit(Br br) {
		ctx.addInline(br);
	}

	@Override
	public void postVisit(Img img) {
		ctx.addInline(img);
	}

	@Override
	public void postVisit(Doc doc) {
		if (CollectionUtils.isEmpty(answers)) {
			throw new LekeRuntimeException(
					"que.word.part.fillblank.blanks.min.one");
		}
		QuestionStem stem = new QuestionStem();
		stem.setStemContent(Doc2HtmlConverter.convert(ctx.getDoc()));
		question.setStem(stem);
		question.setAnswers(answers);
	}
}
