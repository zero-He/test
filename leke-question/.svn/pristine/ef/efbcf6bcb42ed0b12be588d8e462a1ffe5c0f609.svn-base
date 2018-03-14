/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionTemplates;
import cn.strong.leke.model.question.QuestionType;
import cn.strong.leke.question.JsonHelper;
import cn.strong.leke.question.word.command.CommandParser;
import cn.strong.leke.question.word.doc.DocParser;
import cn.strong.leke.question.word.doc.WordImgConverter;
import cn.strong.leke.question.word.model.Command;
import cn.strong.leke.question.word.model.Doc;
import cn.strong.leke.question.word.model.JsArray;
import cn.strong.leke.question.word.model.QuestionPart;
import cn.strong.leke.question.word.model.WordQuestionInfo;
import cn.strong.leke.question.word.que.BaseQuestionPartParser;
import cn.strong.leke.question.word.que.template.BaseTemplateConverter;
import cn.strong.leke.question.word.que.template.ChoiceConverter;
import cn.strong.leke.question.word.que.template.DelayedChoiceConverter;
import cn.strong.leke.question.word.que.template.FillBlankConverter;
import cn.strong.leke.question.word.que.template.JudgementConverter;
import cn.strong.leke.question.word.que.template.OpenendConverter;

/**
 * 
 *
 * @author  liulongbiao
 * @created 2014年12月11日 上午10:19:16
 * @since   v3.2
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Configuration
public class QuestionParseIT {

	@Configuration
	public static class Config {
		@Bean
		public QuestionPartParser questionParser() {
			BaseQuestionPartParser qp = new BaseQuestionPartParser();

			qp.regist("问答题", new OpenendConverter());
			qp.regist("单选题", new ChoiceConverter());
			qp.regist("多选题", new ChoiceConverter());
			qp.regist("不定项选择", new ChoiceConverter());
			qp.regist("选择题", new DelayedChoiceConverter());
			qp.regist("判断题", new JudgementConverter());
			qp.regist("填空题", new FillBlankConverter());
			qp.regist("计算题", new OpenendConverter());
			qp.regist("简答题", new BaseTemplateConverter());
			qp.regist("完型填空", new BaseTemplateConverter());
			qp.regist("阅读理解", new BaseTemplateConverter());

			QuestionType single = new QuestionType();
			single.setQuestionTypeId(1L);
			single.setQuestionTypeName("单选题");
			single.setSubjective(false);
			single.setHasSub(false);
			single.setTemplate(QuestionTemplates.SINGLE_CHOICE);
			qp.registType("单选题", single);

			QuestionType multi = new QuestionType();
			multi.setQuestionTypeId(2L);
			multi.setQuestionTypeName("多选题");
			multi.setSubjective(false);
			multi.setHasSub(false);
			multi.setTemplate(QuestionTemplates.MULTI_CHOICE);
			qp.registType("多选题", multi);

			QuestionType judgement = new QuestionType();
			judgement.setQuestionTypeId(4L);
			judgement.setQuestionTypeName("判断题");
			judgement.setSubjective(false);
			judgement.setHasSub(false);
			judgement.setTemplate(QuestionTemplates.JUDGEMENT);
			qp.registType("判断题", judgement);

			QuestionType fillblank = new QuestionType();
			fillblank.setQuestionTypeId(4L);
			fillblank.setQuestionTypeName("填空题");
			fillblank.setSubjective(false);
			fillblank.setHasSub(false);
			fillblank.setTemplate(QuestionTemplates.FILL_BLANK);
			qp.registType("填空题", fillblank);

			QuestionType qa = new QuestionType();
			qa.setQuestionTypeId(7L);
			qa.setQuestionTypeName("问答题");
			qa.setSubjective(true);
			qa.setHasSub(false);
			qa.setTemplate(QuestionTemplates.OPENEND);
			qp.registType("问答题", qa);

			QuestionType jisuan = new QuestionType();
			jisuan.setQuestionTypeId(8L);
			jisuan.setQuestionTypeName("计算题");
			jisuan.setSubjective(true);
			jisuan.setHasSub(false);
			jisuan.setTemplate(QuestionTemplates.OPENEND);
			qp.registType("计算题", jisuan);

			QuestionType cloze = new QuestionType();
			cloze.setQuestionTypeId(8L);
			cloze.setQuestionTypeName("完型填空");
			cloze.setSubjective(false);
			cloze.setHasSub(true);
			cloze.setTemplate(QuestionTemplates.CLOZE);
			qp.registType("完型填空", cloze);

			QuestionType read = new QuestionType();
			read.setQuestionTypeId(9L);
			read.setQuestionTypeName("阅读理解");
			read.setSubjective(false);
			read.setHasSub(true);
			read.setTemplate(QuestionTemplates.READING);
			qp.registType("阅读理解", read);

			QuestionType jieda = new QuestionType();
			jieda.setQuestionTypeId(26L);
			jieda.setQuestionTypeName("简答题");
			jieda.setSubjective(true);
			jieda.setHasSub(true);
			jieda.setTemplate(QuestionTemplates.BIG_QUE);
			qp.registType("简答题", jieda);

			return qp;
		}

		@Bean
		public DocParser docParser() {
			DocParser wp = new DocParser();
			WordImgConverter converter = new FakeWordImgConverter();
			wp.setWordImgConverter(converter);
			return wp;
		}

		@Bean
		public CommandParser commandParser() {
			return new CommandParser();
		}
	}

	@Autowired
	private DocParser dp;
	@Autowired
	private CommandParser cp;
	@Autowired
	private QuestionPartParser qp;

	public void testParse(String path) {
		Resource resource = new ClassPathResource(path);
		InputStream is = null;
		Doc doc = null;
		try {
			is = resource.getInputStream();
			doc = dp.convert(is);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}
		if (doc == null) {
			throw new RuntimeException("convert word file to Doc error.");
		}
		List<QuestionPart> questions = cp.parse(doc);

		WordQuestionInfo info = new WordQuestionInfo();
		info.setSubjectId(1L);
		info.setNote("test note");

		for (QuestionPart part : questions) {
			try {
				printQuestionPart(part);
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println(part.getType().getValue());

				QuestionDTO dto = qp.parse(part, info);
				JsonHelper.printJson(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void printQuestionPart(QuestionPart part) {
		if (part == null) {
			return;
		}
		for (Command cmd : part.getCommands().values()) {
			System.out.println("【" + cmd.getName()
					+ (cmd.getValue() != null ? (" : " + cmd.getValue()) : "")
					+ "】");
			Doc content = cmd.getContent();
			if (!content.isEmpty()) {
				System.out.println(Doc2HtmlConverter.convert(content));
			}
		}

		JsArray<QuestionPart> children = part.getChildren();
		int i = 1;
		for (QuestionPart child : children) {
			System.out.println("【小题" + i + "】");
			printQuestionPart(child);
			i++;
		}
	}

	@Test
	@Ignore
	public void test() {
		testParse("word/test.docx");
	}

	@Test
	@Ignore
	public void testJudgement() {
		testParse("word/judgement.docx");
	}

	@Test
	@Ignore
	public void testFillBlank() {
		testParse("word/fillblank.docx");
	}

	@Test
	@Ignore
	public void testBigQue() {
		testParse("word/bigque.docx");
	}

	@Test

	public void testCloze() {
		testParse("word/cloze.docx");
	}

	@Test
	@Ignore
	public void english() {
		testParse("word/english.docx");
	}

	@Test
	@Ignore
	public void math() {
		testParse("word/math.docx");
	}
}
