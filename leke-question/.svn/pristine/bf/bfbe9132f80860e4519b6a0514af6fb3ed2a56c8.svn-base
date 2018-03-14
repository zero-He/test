/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service;

import static cn.strong.leke.question.JsonHelper.printJson;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.strong.leke.model.question.Answer;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionStem;
import cn.strong.leke.model.question.StrengThenSelectRule;
import cn.strong.leke.question.AbstractIT;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年5月4日 下午6:41:10
 * @since   v1.0.0
 */
public class QuestionManagerIT extends AbstractIT {

	@Autowired
	private QuestionManager questionManager;

	/**
	 * Test method for
	 * {@link cn.strong.leke.question.service.QuestionManager#getQuestionDTO(java.lang.Long)}
	 * .
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@Test
	@Ignore
	public void testGetQuestionDTO() throws JsonGenerationException,
			JsonMappingException, IOException {
		Long questionId = 1L;
		QuestionDTO result = questionManager.getQuestionDTO(questionId);
		printJson(result);
	}

	@Test
	@Ignore
	public void testAddQuestionDTO() throws JsonGenerationException,
			JsonMappingException, IOException {
		QuestionDTO dto = getDTO();
		printJson(dto);

		questionManager.addQuestionDTO(dto);

		Long questionId = dto.getQuestionId();
		QuestionDTO result = questionManager.getQuestionDTO(questionId);
		System.out.println("~~~~~~~~~~~~~~~~~~");
		printJson(result);
	}

	private QuestionDTO getDTO() {
		QuestionDTO dto = new QuestionDTO();
		dto.setSchoolStageId(1L);
		dto.setGradeId(1L);
		dto.setSubjectId(1L);
		dto.setQuestionTypeId(1L);
		
		QuestionStem stem = new QuestionStem();
		stem.setStemContent("主题干");
		dto.setStem(stem);

		Answer op1 = new Answer();
		op1.setAnswerContent("对");
		op1.setOrd(0);
		//op1.setIsCorrect(0);

		Answer op2 = new Answer();
		op2.setAnswerContent("错");
		op2.setOrd(1);
		//op2.setIsCorrect(1);

		dto.setAnswers(Arrays.asList(op1, op2));

		return dto;
	}

	@Test
	public void testRandStrengThenByQuestionId() {
		StrengThenSelectRule rule = new StrengThenSelectRule();
		rule.setQuestionId(75436L);
		rule.setQuestionNum(3);
		rule = questionManager.randStrengThenByQuestionId(rule);
		printJson(rule);
	}
}
