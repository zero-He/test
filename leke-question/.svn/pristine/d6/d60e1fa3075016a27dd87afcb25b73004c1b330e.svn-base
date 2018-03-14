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

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.batch.BatchAction;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.user.ResearcherContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionRejection;
import cn.strong.leke.model.question.UserQuestionDTO;
import cn.strong.leke.model.user.Researcher;
import cn.strong.leke.question.model.BatchVerifyEvent;
import cn.strong.leke.question.model.CheckQuestionQuery;
import cn.strong.leke.question.model.ImportedQuestionQuery;
import cn.strong.leke.question.model.ResearcherQuestionQuery;
import cn.strong.leke.question.model.ReviewQuestionQuery;
import cn.strong.leke.question.model.TeacherShareQuestionQuery;
import cn.strong.leke.question.service.QuestionService;
import cn.strong.leke.question.service.ResearcherQuestionManager;

/**
 * 
 * 描述: 教研员题目相关
 * 
 * @author liulb
 * @created 2014年5月19日 上午10:51:12
 * @since v1.0.0
 */
@Controller
@RequestMapping("/auth/researcher/question")
public class ResearcherQuestionController {
	@Autowired
	private QuestionService questionService;
	@Autowired
	private ResearcherQuestionManager researcherQuestionManager;

	/**
	 * 
	 * 描述: 习题校对列表页面
	 * 
	 * @author liulb
	 * @created 2014年8月1日 下午3:25:22
	 * @since v1.1.0
	 * @param query
	 * @param model
	 */
	@RequestMapping("verify/index")
	public void verifyIndex(ResearcherQuestionQuery query, Model model) {
		model.addAttribute("query", query);
	}

	@RequestMapping("verify/details")
	public void verifyDetails(ResearcherQuestionQuery query, Page page,
			Model model) {
		query.setResearcherId(UserContext.user.getUserId());
		List<QuestionDTO> questions = questionService.queryResearcherQuestions(
				query, page);
		model.addAttribute("statusType", query.getStatusType());
		model.addAttribute("questions", questions);
		model.addAttribute("page", page);
	}

	@RequestMapping("verify/detail")
	public void verifyDetail(Long questionId, Integer statusType, Model model) {
		QuestionDTO question = questionService.getQuestionWithLog(questionId);
		model.addAttribute("que", question);
		model.addAttribute("statusType", statusType);
	}

	@RequestMapping("verify/edit")
	public void verifyEditPage(Long questionId, Model model) {
		QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
		model.addAttribute("initJson", JsonUtils.toJSON(question));
	}

	/**
	 * 
	 * 描述: 习题初审列表页面
	 * 
	 * @author liulb
	 * @created 2014年8月1日 下午3:26:53
	 * @since v1.1.0
	 * @param query
	 * @param model
	 */
	@RequestMapping("/review/index")
	public void reviewIndex(ReviewQuestionQuery query, Model model) {
		model.addAttribute("query", query);
	}

	@RequestMapping("/review/details")
	public void reviewDetails(ReviewQuestionQuery query, Page page, Model model) {
		if (query != null && query.getMaxInputDate() != null) {
			query.setMaxInputDate(DateUtils.addDays(query.getMaxInputDate(), 1));
		}
		List<QuestionDTO> questions = questionService.queryReviewQuestions(
				query, page);
		model.addAttribute("reviewed", query.getReviewed());
		model.addAttribute("questions", questions);
		model.addAttribute("page", page);
	}

	@RequestMapping("/review/detail")
	public void questionDetail(Long questionId, Boolean reviewed, Model model) {
		QuestionDTO question = questionService.getQuestionWithLog(questionId);
		model.addAttribute("que", question);
		model.addAttribute("reviewed", reviewed);
	}

	@RequestMapping("/review/edit")
	public void reviewEditPage(Long questionId, Model model) {
		QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
		model.addAttribute("initJson", JsonUtils.toJSON(question));
	}

	/**
	 * 
	 * 描述: 教师共享习题审核列表页面
	 * 
	 * @author liulb
	 * @created 2014年8月1日 下午3:27:05
	 * @since v1.1.0
	 * @param query
	 * @param model
	 */
	@RequestMapping("/teacherShare/index")
	public void teacherShareIndex(TeacherShareQuestionQuery query, Model model) {
		model.addAttribute("query", query);
	}

	@RequestMapping("/teacherShare/details")
	public void teacherShareDetails(TeacherShareQuestionQuery query, Page page,
			Model model) {
		if (query != null && query.getMaxInputDate() != null) {
			query.setMaxInputDate(DateUtils.addDays(query.getMaxInputDate(), 1));
		}
		List<UserQuestionDTO> questions = questionService
				.queryTeacherShareQuestions(query, page);
		model.addAttribute("questions", questions);
		model.addAttribute("page", page);
	}

	@RequestMapping("/check/index")
	public void checkIndex(CheckQuestionQuery query, Model model) {
		model.addAttribute("query", query);
	}

	@RequestMapping("/check/details")
	public void checkDetails(CheckQuestionQuery query, Page page, Model model) {
		List<QuestionDTO> questions = questionService.queryQuestionsForCheck(
				query, page);
		model.addAttribute("questions", questions);
		model.addAttribute("page", page);
		model.addAttribute("statusType", query.getStatusType());
	}

	@RequestMapping("/check/detail")
	public void checkDetail(Long questionId, Integer statusType, Model model) {
		QuestionDTO question = questionService.getQuestionWithLog(questionId);
		model.addAttribute("que", question);
		model.addAttribute("statusType", statusType);
	}

	@RequestMapping("/check/edit")
	public void checkEditUncheckedPage(Long questionId, Model model) {
		QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
		model.addAttribute("initJson", JsonUtils.toJSON(question));
	}

	@RequestMapping("/check/editChecked")
	public void checkEditCheckedPage(Long questionId, Model model) {
		QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
		model.addAttribute("initJson", JsonUtils.toJSON(question));
	}

	@RequestMapping("/imported/index")
	public void importedIndex() {
	}

	@RequestMapping("/imported/details")
	public void importedDetails(ImportedQuestionQuery query, Page page,
			Model model) {
		query.setUserId(UserContext.user.getUserId());
		List<QuestionDTO> questions = researcherQuestionManager
				.queryImportedQuestions(query, page);
		model.addAttribute("questions", questions);
		model.addAttribute("page", page);
	}

	@RequestMapping("/imported/edit")
	public void editImportedPage(Long questionId, Model model) {
		QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
		model.addAttribute("initJson", JsonUtils.toJSON(question));
	}

	/**
	 *	
	 * 描述:教研员统计列表页面
	 *
	 * @author  lavender
	 * @created 2015年1月5日 上午11:09:12
	 * @since   v1.0.0 
	 * @param query
	 * @param model
	 * @return  void
	 */
	@RequestMapping("/statistic/index")
	public void statisticIndex(CheckQuestionQuery query, Model model) {
		model.addAttribute("query", query);
	}

	/**
	 *	
	 * 描述:习题详细信息列表
	 *
	 * @author  lavender
	 * @created 2015年1月5日 上午11:09:26
	 * @since   v1.0.0 
	 * @param query
	 * @param page
	 * @param model
	 * @return  void
	 */
	@RequestMapping("/statistic/details")
	public void statisticDetails(ResearcherQuestionQuery query, Page page, Model model) {
		List<QuestionDTO> questions = questionService.queryResearcherQuestionList(query, page);
		model.addAttribute("questions", questions);
		model.addAttribute("page", page);
		model.addAttribute("statusType", query.getStatusType());
	}

	/**
	 *	
	 * 描述:批量导入习题退回列表编辑
	 *
	 * @author  lavender
	 * @created 2015年1月5日 上午11:09:45
	 * @since   v1.0.0 
	 * @param questionId
	 * @param model
	 * @return  void
	 */
	@RequestMapping("/statistic/rejectEdit")
	public void rejectEditPage(Long questionId, Model model) {
		QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
		model.addAttribute("initJson", JsonUtils.toJSON(question));
	}

	/**
	 *	
	 * 描述:未校对习题列表编辑
	 *
	 * @author  lavender
	 * @created 2015年1月5日 上午11:10:06
	 * @since   v1.0.0 
	 * @param questionId
	 * @param model
	 * @return  void
	 */
	@RequestMapping("/statistic/editChecked")
	public void editCheckedPage(Long questionId, Model model) {
		QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
		model.addAttribute("initJson", JsonUtils.toJSON(question));
	}

	/**
	 * 习题批量审核通过
	 * 
	 * @author liulongbiao
	 * @created 2015年1月14日 下午2:34:16
	 * @since v3.2.2
	 * @param dataJson
	 * @return
	 */
	@RequestMapping(value = "/batchCheckPass", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult batchCheckPass(String dataJson) {
		JsonResult json = new JsonResult();
		final BatchVerifyEvent event = JsonUtils.fromJSON(dataJson, BatchVerifyEvent.class);
		final Researcher researcher = ResearcherContext.researcher.get();
		String msg = event.execute(new BatchAction<Long>() {

			@Override
			public void execute(Long questionId) {
				researcherQuestionManager.updateChecked(questionId, researcher);
			}
		});
		json.addDatas("msg", msg);
		return json;
	}

	/**
	 * 批量审核通过教师共享习题
	 * 
	 * @author liulongbiao
	 * @created 2015年1月14日 下午2:43:43
	 * @since v3.2.2
	 * @param dataJson
	 * @return
	 */
	@RequestMapping(value = "batchCheckPassTeacherShare", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult batchCheckPassTeacherShare(String dataJson) {
		JsonResult json = new JsonResult();
		final BatchVerifyEvent event = JsonUtils.fromJSON(dataJson,
				BatchVerifyEvent.class);
		final Researcher researcher = ResearcherContext.researcher.get();
		String msg = event.execute(new BatchAction<Long>() {

			@Override
			public void execute(Long questionId) {
				researcherQuestionManager.updateCheckedTeacherShare(questionId,
						researcher);
			}
		});
		json.addDatas("msg", msg);
		return json;
	}

	@RequestMapping(value = "checkPassTeacherShare", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult checkPassTeacherShare(Long questionId) {
		JsonResult json = new JsonResult();
		Researcher researcher = ResearcherContext.researcher.get();
		researcherQuestionManager.updateCheckedTeacherShare(questionId,
				researcher);
		return json;
	}

	@RequestMapping(value = "rejectTeacherShare", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult rejectTeacherShare(QuestionRejection rejection) {
		JsonResult json = new JsonResult();
		Researcher researcher = ResearcherContext.researcher.get();
		researcherQuestionManager.updateRejectedTeacherShare(rejection,
				researcher);
		return json;
	}

	/**
	 *
	 * 描述: 平台教研员试卷列表
	 *
	 * @author raolei
	 */
	@RequestMapping("personal/list")
	public void list() {
	}
}
