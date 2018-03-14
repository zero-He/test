/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.model.question.QuestionDTO;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年6月19日 下午2:25:48
 * @since   v1.0.0
 */
@Controller("oldTeacherQuestionController")
@RequestMapping("/auth/teacher/question")
public class TeacherQuestionController {

	@RequestMapping("/add/index")
	public void questionAddIndex() {

	}

	@RequestMapping(value = "questionEdit")
	public void questionEditPage(Boolean copy, Long questionId, Model model) {
		QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
		if (copy == null) {
			copy = false;
		}
		model.addAttribute("initJson", JsonUtils.toJSON(question));
		model.addAttribute("copy", copy);
	}

	@RequestMapping(value = "replaceEdit")
	public void replaceEditPage(Long questionId, Model model) {
		QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
		model.addAttribute("initJson", JsonUtils.toJSON(question));
	}

	@RequestMapping("remoteAdd")
	public void remoteAddPage(QuestionDTO question, Model model) {
		Long questionId = question.getQuestionId();
		if (questionId != null) {
			question = QuestionContext.getQuestionDTO(questionId);
		}
		model.addAttribute("initJson", JsonUtils.toJSON(question));
	}
}
