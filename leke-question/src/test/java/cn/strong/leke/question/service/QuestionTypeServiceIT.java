/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service;

import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.strong.leke.model.question.QuestionType;
import cn.strong.leke.question.AbstractIT;
import cn.strong.leke.question.JsonHelper;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年6月25日 下午3:24:36
 * @since   v1.0.0
 */
public class QuestionTypeServiceIT extends AbstractIT {

	@Autowired
	private QuestionTypeService questionTypeService;

	@Test
	@Ignore
	public void test() {
		Map<String, List<QuestionType>> subjectTypeMap = questionTypeService
				.findAllSubjectQuestionTypeMap();
		JsonHelper.printJson(subjectTypeMap);
	}

}
