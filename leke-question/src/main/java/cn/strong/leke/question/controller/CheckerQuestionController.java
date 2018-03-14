/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.BatchCheckEvent;
import cn.strong.leke.question.model.CheckerQuestionQuery;
import cn.strong.leke.question.service.QuestionService;
import cn.strong.leke.repository.common.cmd.ISchoolRepoCheckHandler;

/**
 * 
 * 描述: 审核员题目相关
 * 
 * @author liulb
 * @created 2014年5月16日 下午2:30:23
 * @since v1.0.0
 */
@Controller
@RequestMapping("/auth/checker/question")
public class CheckerQuestionController {

	@Autowired
	private QuestionService questionService;
	@Resource(name = "schoolQuestionCheckHandler")
	private ISchoolRepoCheckHandler schoolQuestionCheckHandler;

	@RequestMapping(value = "questionList")
	public void questionListPage(CheckerQuestionQuery query, Model model) {
		model.addAttribute("query", query);
	}

	@RequestMapping(value = "questionListDetails")
	public void questionListDetails(CheckerQuestionQuery query, Page page,
			Model model) {
		query.setCheckerId(UserContext.user.getUserId());
		List<QuestionDTO> questions = questionService.queryCheckerQuestions(
				query, page);
		model.addAttribute("statusType", query.getStatusType());
		model.addAttribute("questions", questions);
		model.addAttribute("page", page);
	}

	/**
	 *	
	 * 描述:批量导入退回
	 *
	 * @author  lavender
	 * @created 2014年12月30日 下午3:13:42
	 * @since   v1.0.0 
	 * @param dto
	 * @param rejectReason
	 * @return
	 * @return  JsonResult
	 */
	@RequestMapping(value = "importReject")
	@ResponseBody
	public JsonResult importReject(QuestionDTO dto, String rejectReason) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		Long schoolId = UserContext.user.getCurrentSchoolId();
		Long questionId = dto.getQuestionId();
		List<Long> resIds = new ArrayList<Long>();
		resIds.add(questionId);
		schoolQuestionCheckHandler.checkReject(resIds, schoolId, rejectReason, user);
		return json;
	}

	/**
	 *	
	 * 描述:审核通过
	 *
	 * @author  lavender
	 * @created 2014年12月30日 下午3:13:58
	 * @since   v1.0.0 
	 * @param dataJson
	 * @return
	 * @return  JsonResult
	 */
	@RequestMapping(value = "importCheck", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult importCheck(String dataJson) {
		JsonResult json = new JsonResult();
		final BatchCheckEvent event = JsonUtils.fromJSON(dataJson, BatchCheckEvent.class);
		User user = UserContext.user.get();
		Long schoolId = user.getCurrentSchool().getId();
		schoolQuestionCheckHandler.checkPass(event.getQuestionIds(), schoolId, user);
		return json;
	}
}
