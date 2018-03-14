/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.controller.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.QuestionShareEvent;
import cn.strong.leke.question.service.QuestionManager;

/**
 * 教师习题控制器
 * 
 * @author liulongbiao
 */
@Controller
@RequestMapping("/auth/teacher/question")
public class TeacherQuestionController {
	@Autowired
	private QuestionManager questionManager;

	@RequestMapping("share")
	public void share(Long questionId, Model model) {
		User user = UserContext.user.get();
		QuestionShareEvent backend = questionManager.getQuestionShareInfo(questionId, user);
		model.addAttribute("backend", JsonUtils.toJSON(backend));
	}

	@RequestMapping("doShare")
	@ResponseBody
	public JsonResult doShare(String dataJson) {
		JsonResult json = new JsonResult();
		QuestionShareEvent event = JsonUtils.fromJSON(dataJson,
				QuestionShareEvent.class);
		Long questionId = event.getQuestionId();
		User user = UserContext.user.get();
		Long userId = user.getId();
		checkIsQuestionCreator(questionId, userId);
		questionManager.updateQuestionShare(event, user);
		return json;
	}

	private void checkIsQuestionCreator(Long questionId, Long userId) {
		QuestionDTO old = QuestionContext.getQuestionDTO(questionId);
		if (old == null) {
			throw new ValidateException("que.question.not.exist");
		}
		if (!userId.equals(old.getCreatedBy())) {
			throw new ValidateException("que.warn.permission.denied");
		}
	}

	@RequestMapping("doDisable")
	@ResponseBody
	public JsonResult doDisable(Long questionId) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		Long userId = user.getId();
		checkIsQuestionCreator(questionId, userId);
		QuestionDTO dto = new QuestionDTO();
		dto.setQuestionId(questionId);
		dto.setModifiedBy(user.getId());
		dto.setOperatorName(user.getUserName());
		questionManager.disableQuestion(dto, user);
		return json;
	}
}
