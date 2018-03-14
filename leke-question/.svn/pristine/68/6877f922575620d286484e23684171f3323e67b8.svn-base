/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.thread.ThreadLocalHolder;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.cas.SessionCst;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.question.model.InputerQuestionQuery;
import cn.strong.leke.question.service.QuestionService;

/**
 * 
 * 描述: 录入人员题目相关
 * 
 * @author liulb
 * @created 2014年5月13日 下午3:09:47
 * @since v1.0.0
 */
@Controller
@RequestMapping("/auth/inputer/question")
public class InputerQuestionController {

	@Autowired
	private QuestionService questionService;

	@RequestMapping(value = "input/questionAdd")
	public String questionAddPage(Model model) {
		model.addAttribute(SessionCst.TICKET,
				ThreadLocalHolder.getResource(SessionCst.TICKET));
		return "/auth/inputer/question/questionAdd";
	}

	@RequestMapping(value = "list/questionList")
	public String questionListPage(InputerQuestionQuery query, Model model) {
		model.addAttribute("query", query);
		return "/auth/inputer/question/questionList";
	}

	@RequestMapping(value = "questionListDetails")
	public void questionListDetails(InputerQuestionQuery query, Page page,
			Model model) {
		query.setInputerId(UserContext.user.getUserId());
		List<QuestionDTO> questions = questionService.queryInputerQuestions(
				query, page);
		model.addAttribute("questions", questions);
		model.addAttribute("page", page);
	}

	@RequestMapping(value = "questionDetail")
	public void questionDetail(Long questionId, Model model) {
		QuestionDTO question = questionService.getQuestionWithLog(questionId);
		model.addAttribute("que", question);
	}

	@RequestMapping(value = "questionEdit")
	public void questionEditPage(Long questionId, Model model) {
		QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
		model.addAttribute("initJson", JsonUtils.toJSON(question));
	}

	@RequestMapping(value = "fixType/typeMismatch")
	public String typeMismatchQuestions(InputerQuestionQuery query, Model model) {
		model.addAttribute("query", query);
		return "/auth/inputer/question/typeMismatch";
	}

	@RequestMapping(value = "typeMismatchDetails")
	public void typeMismatchDetails(InputerQuestionQuery query,
			Page page, Model model) {
		query.setInputerId(UserContext.user.getUserId());
		List<QuestionDTO> questions = questionService
				.queryTypeMismatchInputerQuestions(query, page);
		model.addAttribute("questions", questions);
		model.addAttribute("page", page);
	}
}
