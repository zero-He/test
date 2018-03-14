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
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.core.nosql.CacheUtils;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.BaseCache;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.question.model.PublishedQuestionQuery;
import cn.strong.leke.question.service.QuestionManager;
import cn.strong.leke.question.service.QuestionService;

/**
 * 
 * 描述: 教研主管题目相关
 * 
 * @author liulb
 * @created 2014年5月22日 下午2:36:01
 * @since v1.0.0
 */
@Controller
@RequestMapping("/auth/admin/question")
public class AdminQuestionController {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionManager questionManager;
	/**
	 * 
	 * 描述: 正式题量统计明细
	 * 
	 * @author liulb
	 * @created 2014年5月23日 下午5:24:15
	 * @since v1.0.0
	 * @param query
	 * @param model
	 * @return void
	 */
	@RequestMapping(value = "pubQueList")
	public void pubQueListPage(PublishedQuestionQuery query, Model model) {
		model.addAttribute("query", query);
	}

	@RequestMapping(value = "pubQueListDetails")
	public void pubQueListDetails(PublishedQuestionQuery query, Page page,
			Model model) {
		List<QuestionDTO> questions = questionService.queryPublishedQuestions(
				query, page);
		model.addAttribute("query", query);
		model.addAttribute("questions", questions);
		model.addAttribute("page", page);
	}

	/**
	 * 
	 * 描述: 正式题量按知识点统计明细
	 * 
	 * @author liulb
	 * @created 2014年5月23日 下午5:24:32
	 * @since v1.0.0
	 * @param query
	 * @param model
	 * @return void
	 */
	@RequestMapping(value = "knowledgePubQueList")
	public void knowledgePubQueListPage(PublishedQuestionQuery query,
			Model model) {
		model.addAttribute("query", query);
	}

	@RequestMapping(value = "loadAll")
	@ResponseBody
	public JsonResult loadAll() {
		JsonResult result = new JsonResult();
		CacheUtils.delete(BaseCache.PREFIX_QUESTION);
		questionManager.batchCacheQuestions();
		return result;
	}
}
