/*
 * Copyright (c) 2015 Strong Group - 版权所有
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
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.duplication.model.DupQuestionQuery;
import cn.strong.leke.question.duplication.model.SimQueDTO;
import cn.strong.leke.question.duplication.service.DuplicationQuestionManager;
import cn.strong.leke.question.model.ResearcherQuestionQuery;

/**
 *
 *
 * @author  liulongbiao
 * @created 2015年1月19日 下午3:21:42
 * @since   v3.2.2
 */
@Controller
@RequestMapping("/auth/researcher/question/duplication")
public class DuplicationQuestionController {

	@Autowired
	private DuplicationQuestionManager duplicationQuestionManager;

	@RequestMapping("index")
	public void index(ResearcherQuestionQuery query, Model model) {
		model.addAttribute("query", query);
	}

	@RequestMapping("details")
	public void details(DupQuestionQuery query, Page page, Model model) {
		List<SimQueDTO> groups = duplicationQuestionManager
				.queryDupQuestions(query, page);
		model.addAttribute("groups", groups);
		model.addAttribute("page", page);
	}

	@RequestMapping("similarities")
	public void similarities(Long questionId, Page page, Model model) {
		Page similarities = duplicationQuestionManager.findSimilarities(
				questionId, page);
		model.addAttribute("similarities", similarities);
	}

	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(Long questionId) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		duplicationQuestionManager.deleteQuestion(questionId, user);
		return json;
	}

	@RequestMapping("disable")
	@ResponseBody
	public JsonResult disable(Long questionId) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		duplicationQuestionManager.disableQuestionWithTx(questionId, user);
		return json;
	}

	@RequestMapping("disableOthers")
	@ResponseBody
	public JsonResult disableOthers(Long dupGroupId, Long questionId) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		duplicationQuestionManager.disableOtherQuestionsWithTx(dupGroupId,
				questionId, user);
		return json;
	}

	@RequestMapping("notDup")
	@ResponseBody
	public JsonResult notDup(Long questionId) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		duplicationQuestionManager.updateNotDup(questionId, user);
		return json;
	}

	@RequestMapping("runDupJob")
	@ResponseBody
	public JsonResult runDupJob() {
		JsonResult json = new JsonResult();
		duplicationQuestionManager.runDupJob();
		return json;
	}
}
