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
import cn.strong.leke.common.utils.batch.BatchAction;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.question.Question;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionKnowledge;
import cn.strong.leke.model.question.QuestionOfficialTag;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.BatchCheckEvent;
import cn.strong.leke.question.model.BatchReviewEvent;
import cn.strong.leke.question.model.BatchVerifyEvent;
import cn.strong.leke.question.model.QuestionReview;
import cn.strong.leke.question.model.StatisQuestionQuery;
import cn.strong.leke.question.service.QuestionKnowledgeService;
import cn.strong.leke.question.service.QuestionManager;
import cn.strong.leke.question.service.QuestionOfficialTagService;
import cn.strong.leke.question.service.QuestionReviewService;
import cn.strong.leke.question.service.QuestionService;
import cn.strong.leke.question.util.QuestionIndexContext;
import cn.strong.leke.repository.common.cmd.IRepoSaveHandler;
import cn.strong.leke.repository.common.model.RepoSaveContext;
import cn.strong.leke.repository.common.model.RepoScope;

import com.fasterxml.jackson.databind.util.JSONPObject;

/**
 * 
 * 描述: 题目管理API
 * 
 * @author liulb
 * @created 2014年5月4日 下午7:55:49
 * @since v1.0.0
 */
@Controller
@RequestMapping("/auth/common/question")
public class QuestionController {

	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionKnowledgeService questionKnowledgeService;
	@Autowired
	private QuestionOfficialTagService questionOfficialTagService;
	@Autowired
	private QuestionReviewService questionReviewService;
	@Resource(name = "questionSaveHandler")
	private IRepoSaveHandler<QuestionDTO> questionSaveHandler;

	@RequestMapping("view")
	public void view(Long questionId, Model model) {
		model.addAttribute("questionId", questionId);
		model.addAttribute("questionJson", JsonUtils.toJSON(QuestionContext.getQuestionDTO(questionId)));
	}

	/**
	 * 
	 * 描述: 获取题目完整信息
	 * 
	 * @author liulb
	 * @created 2014年5月18日 下午2:33:52
	 * @since v1.0.0
	 * @param questionId
	 * @return JsonResult
	 */
	@RequestMapping("get")
	@ResponseBody
	public JsonResult get(Long questionId) {
		JsonResult json = new JsonResult();
		QuestionDTO dto = questionManager.getQuestionDTO(questionId);
		json.addDatas("question", dto);
		return json;
	}

	/**
	 * 保存习题
	 * 
	 * @param dataJson
	 * @param scope
	 * @param action
	 * @return
	 */
	@RequestMapping("doSave")
	@ResponseBody
	public JsonResult doSave(String dataJson, RepoScope scope, String action) {
		QuestionDTO data = JsonUtils.fromJSON(dataJson, QuestionDTO.class);
		Validation.notNull(data, "que.question.info.incomplete");
		User user = UserContext.user.get();

		RepoSaveContext<QuestionDTO> ctx = new RepoSaveContext<>();
		data.setWorkbookNodeId(scope.getCurWbnodeId());
		ctx.setData(data);
		ctx.setScope(scope);
		ctx.setUser(user);
		ctx.setOldId(data.getQuestionId());
		ctx.setAction(action);

		JsonResult json = new JsonResult();
		questionSaveHandler.save(ctx);
		json.addDatas("questionId", ctx.getNewId() == null ? ctx.getOldId() : ctx.getNewId());
		QuestionIndexContext.sendUpdateIndex(ctx.getNewId());
		return json;
	}

	/**
	 * 
	 * 描述: 添加题目
	 * 
	 * @author liulb
	 * @created 2014年5月18日 下午2:34:07
	 * @since v1.0.0
	 * @param dataJson
	 * @return JsonResult
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	@Deprecated
	public JsonResult add(String dataJson) {
		JsonResult json = new JsonResult();
		QuestionDTO dto = JsonUtils.fromJSON(dataJson, QuestionDTO.class);
		fillCreateInfo(dto, UserContext.user.get());
		questionManager.addQuestionDTO(dto);
		json.addDatas("questionId", dto.getQuestionId());
		return json;
	}

	/**
	 * 
	 * 描述: 修改题目
	 * 
	 * @author liulb
	 * @created 2014年5月18日 下午2:34:20
	 * @since v1.0.0
	 * @param dataJson
	 * @return JsonResult
	 */
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	@ResponseBody
	@Deprecated
	public JsonResult modify(String dataJson) {
		JsonResult json = new JsonResult();
		QuestionDTO dto = JsonUtils.fromJSON(dataJson, QuestionDTO.class);
		User user = UserContext.user.get();
		fillUpdateInfo(dto, user);
		questionManager.updateQuestionDTO(dto, user);
		return json;
	}

	/**
	 * 
	 * 描述: 删除题目
	 * 
	 * @author liulb
	 * @created 2014年5月18日 下午2:34:31
	 * @since v1.0.0
	 * @param dto
	 * @return JsonResult
	 */
	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(QuestionDTO dto) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		dto.setModifiedBy(user.getId());
		dto.setOperatorName(user.getUserName());
		questionManager.deleteQuestionDTO(dto);
		return json;
	}
	
	/**
	 * 
	 * 描述: 设置难度值
	 * 
	 * @author liulb
	 * @created 2014年5月18日 下午2:34:41
	 * @since v1.0.0
	 * @param question
	 * @return JsonResult
	 */
	@RequestMapping("setDifficulty")
	@ResponseBody
	public JsonResult setDifficulty(Question question) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		question.setModifiedBy(user.getId());
		questionManager.updateDifficulty(question);
		return json;
	}

	/**
	 * 
	 * 描述: 添加关联知识点，内部去重
	 * 
	 * @author liulb
	 * @created 2014年5月18日 下午2:34:55
	 * @since v1.0.0
	 * @param dataJson
	 * @return JsonResult
	 */
	@RequestMapping("attachKnowledges")
	@ResponseBody
	public JsonResult attachKnowledges(String dataJson) {
		JsonResult json = new JsonResult();
		QuestionDTO dto = JsonUtils.fromJSON(dataJson, QuestionDTO.class);
		User user = UserContext.user.get();
		Long userId = user.getId();
		dto.setCreatedBy(userId);
		dto.setModifiedBy(userId);
		questionManager.addQuestionKnowledges(dto);

		List<QuestionKnowledge> knowledges = questionKnowledgeService
				.findQuestionKnowledgeByQuestionId(dto.getQuestionId());
		json.addDatas("knowledges", knowledges);
		return json;
	}

	/**
	 * 
	 * 描述: 移除关联知识点
	 * 
	 * @author liulb
	 * @created 2014年5月18日 下午2:47:51
	 * @since v1.0.0
	 * @param assoc
	 * @return JsonResult
	 */
	@RequestMapping("detachKnowledge")
	@ResponseBody
	public JsonResult detachKnowledge(QuestionKnowledge assoc) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		assoc.setModifiedBy(user.getId());
		questionManager.deleteQuestionKnowledge(assoc);
		return json;
	}

	/**
	 *
	 * 描述: 添加习题关联教材
	 *
	 * @author raolei
	 * @created 2016年8月3日 下午4:08:00
	 * @since v1.0.0
	 * @param dataJson
	 * @return
	 * @return JsonResult
	 */
	@RequestMapping("attachSections")
	@ResponseBody
	public JsonResult attachSections(String dataJson) {
		JsonResult json = new JsonResult();
		QuestionDTO dto = JsonUtils.fromJSON(dataJson, QuestionDTO.class);
		User user = UserContext.user.get();
		Long userId = user.getId();
		dto.setCreatedBy(userId);
		dto.setModifiedBy(userId);
		questionManager.addQuestionSections(dto, user);
		return json;
	}

	@RequestMapping("detachSection")
	@ResponseBody
	public JsonResult detachSection(final Long quesSectionId) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		questionManager.deleteQuestionSection(quesSectionId, user);
		return json;
	}

	/**
	 * 
	 * 描述: 添加关联题库标签，内部去重
	 * 
	 * @author liulb
	 * @created 2014年5月18日 下午2:35:34
	 * @since v1.0.0
	 * @param dataJson
	 * @return JsonResult
	 */
	@RequestMapping("attachTags")
	@ResponseBody
	public JsonResult attachTags(String dataJson) {
		JsonResult json = new JsonResult();
		QuestionDTO dto = JsonUtils.fromJSON(dataJson, QuestionDTO.class);
		User user = UserContext.user.get();
		Long userId = user.getId();
		dto.setCreatedBy(userId);
		dto.setModifiedBy(userId);
		questionManager.addQuestionOfficialTags(dto);

		List<QuestionOfficialTag> tags = questionOfficialTagService
				.findQuestionOfficialTagByQuestionId(dto.getQuestionId());
		json.addDatas("tags", tags);
		return json;
	}

	/**
	 * 
	 * 描述: 移除关联题库标签
	 * 
	 * @author liulb
	 * @created 2014年5月18日 下午2:48:50
	 * @since v1.0.0
	 * @param assoc
	 * @return JsonResult
	 */
	@RequestMapping("detachTag")
	@ResponseBody
	public JsonResult detachTag(QuestionOfficialTag assoc) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		assoc.setModifiedBy(user.getId());
		questionManager.deleteQuestionOfficialTag(assoc);
		return json;
	}

	/**
	 * 
	 * 描述: 审核通过
	 * 
	 * @author liulb
	 * @created 2014年7月31日 下午5:00:08
	 * @since v1.1.0
	 * @param dataJson
	 * @return
	 */
	@RequestMapping(value = "check", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult check(String dataJson) {
		JsonResult json = new JsonResult();
		final BatchCheckEvent event = JsonUtils.fromJSON(dataJson,
				BatchCheckEvent.class);
		final User user = UserContext.user.get();
		String msg = event.execute(new BatchAction<Long>() {

			@Override
			public void execute(Long questionId) {
				QuestionDTO dto = new QuestionDTO();
				dto.setQuestionId(questionId);
				dto.setModifiedBy(user.getId());
				dto.setOperatorName(user.getUserName());
				questionManager.updateCheck(dto);
			}
		});
		json.addDatas("msg", msg);
		return json;
	}

	/**
	 * 
	 * 描述: 退回录入
	 * 
	 * @author liulb
	 * @created 2014年7月31日 下午4:59:27
	 * @since v1.1.0
	 * @param dto
	 * @param rejectReason
	 * @return
	 */
	@RequestMapping("reject")
	@ResponseBody
	public JsonResult reject(QuestionDTO dto, String rejectReason) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		dto.setModifiedBy(user.getId());
		dto.setOperatorName(user.getUserName());
		questionManager.updateReject(dto, rejectReason);
		return json;
	}

	/**
	 * 
	 * 描述: 设置纠错
	 * 
	 * @author liulb
	 * @created 2014年7月31日 下午4:59:18
	 * @since v1.1.0
	 * @param dto
	 * @return
	 */
	@RequestMapping("correct")
	@ResponseBody
	public JsonResult correct(QuestionDTO dto) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		dto.setModifiedBy(user.getId());
		dto.setOperatorName(user.getUserName());
		questionManager.updateCorrect(dto);
		return json;
	}

	/**
	 * 
	 * 描述: 校对通过
	 * 
	 * @author liulb
	 * @created 2014年7月31日 下午4:59:03
	 * @since v1.1.0
	 * @param dataJson
	 * @return
	 */
	@RequestMapping(value = "verify", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult verify(String dataJson) {
		JsonResult json = new JsonResult();
		final BatchVerifyEvent event = JsonUtils.fromJSON(dataJson,
				BatchVerifyEvent.class);
		final User user = UserContext.user.get();
		String msg = event.execute(new BatchAction<Long>() {

			@Override
			public void execute(Long questionId) {
				QuestionDTO dto = new QuestionDTO();
				dto.setQuestionId(questionId);
				dto.setModifiedBy(user.getId());
				dto.setOperatorName(user.getUserName());
				questionManager.updateVerify(dto);
			}
		});
		json.addDatas("msg", msg);
		return json;
	}

	@RequestMapping(value = "statisQueListDetails")
	public void statisQueListDetails(StatisQuestionQuery query, Page page,
			Model model) {
		List<QuestionDTO> questions = questionService.queryStatisQuestions(
				query, page);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("questions", questions);
	}

	/**
	 * 
	 * 描述: 修改题型
	 * 
	 * @author liulb
	 * @created 2014年7月31日 下午4:58:51
	 * @since v1.1.0
	 * @param question
	 * @return
	 */
	@RequestMapping("setQuestionType")
	@ResponseBody
	public JsonResult setQuestionType(Question question) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		question.setModifiedBy(user.getId());
		questionService.updateQuestionType(question);
		return json;
	}

	/**
	 * 
	 * 描述: 初审通过
	 * 
	 * @author liulb
	 * @created 2014年7月31日 下午4:58:34
	 * @since v1.1.0
	 * @param dataJson
	 * @return
	 */
	@RequestMapping(value = "review", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult review(String dataJson) {
		JsonResult json = new JsonResult();
		final BatchReviewEvent event = JsonUtils.fromJSON(dataJson,
				BatchReviewEvent.class);
		final User user = UserContext.user.get();
		String msg = event.execute(new BatchAction<Long>() {

			@Override
			public void execute(Long questionId) {
				QuestionReview review = new QuestionReview();
				review.setQuestionId(questionId);
				review.setCreatedBy(user.getId());
				questionReviewService.addQuestionReview(review);
			}
		});
		json.addDatas("msg", msg);
		return json;
	}

	/**
	 * 
	 * 描述: 校对编辑
	 * 
	 * @author liulb
	 * @created 2014年7月31日 下午4:58:19
	 * @since v1.1.0
	 * @param dataJson
	 * @return
	 */
	@RequestMapping(value = "verifyEdit", method = RequestMethod.POST)
	@ResponseBody
	@Deprecated
	public JsonResult verifyEdit(String dataJson) {
		JsonResult json = new JsonResult();
		QuestionDTO dto = JsonUtils.fromJSON(dataJson, QuestionDTO.class);
		fillUpdateInfo(dto, UserContext.user.get());
		Long newId = questionManager.updateQuestionOnVerify(dto);
		json.addDatas("newId", newId);
		return json;
	}

	/**
	 * 
	 * 描述: 禁用习题
	 * 
	 * @author liulb
	 * @created 2014年7月31日 下午4:58:08
	 * @since v1.1.0
	 * @param dto
	 * @return
	 */
	@RequestMapping("disable")
	@ResponseBody
	public JsonResult disable(QuestionDTO dto) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		dto.setModifiedBy(user.getId());
		dto.setOperatorName(user.getUserName());
		questionManager.disableQuestion(dto, user);
		return json;
	}

	/**
	 *	
	 * 描述:查询习题数量，用于平台管理员统计习题数量
	 *
	 * @author  lavender
	 * @created 2014年8月28日 上午9:56:29
	 * @since   v1.0.0 
	 * @param jsoncallback
	 * @return
	 * @return  JSONPObject
	 */
	@RequestMapping("ajax/findQuestionCount")
	@ResponseBody
	public JSONPObject findQuestionCount(String jsoncallback) {
		JsonResult json = new JsonResult();
		Long questionCount = questionService.findQuestionCount();
		json.addDatas("questionCount", questionCount);
		return new JSONPObject(jsoncallback, json);
	}

	@RequestMapping(value = "editChecked", method = RequestMethod.POST)
	@ResponseBody
	@Deprecated
	public JsonResult editChecked(String dataJson) {
		JsonResult json = new JsonResult();
		QuestionDTO dto = JsonUtils.fromJSON(dataJson, QuestionDTO.class);
		fillUpdateInfo(dto, UserContext.user.get());
		Long newId = questionManager.updateQuestionOnEditChecked(dto);
		json.addDatas("newId", newId);
		return json;
	}

	@RequestMapping(value = "addChecked", method = RequestMethod.POST)
	@ResponseBody
	@Deprecated
	public JsonResult addChecked(String dataJson) {
		JsonResult json = new JsonResult();
		QuestionDTO dto = JsonUtils.fromJSON(dataJson, QuestionDTO.class);
		fillCreateInfo(dto, UserContext.user.get());
		Long questionId = questionManager.addCheckedQuestionDTO(dto);
		json.addDatas("questionId", questionId);
		return json;
	}

	@RequestMapping(value = "editImported", method = RequestMethod.POST)
	@ResponseBody
	@Deprecated
	public JsonResult editImported(String dataJson) {
		JsonResult json = new JsonResult();
		QuestionDTO dto = JsonUtils.fromJSON(dataJson, QuestionDTO.class);
		User user = UserContext.user.get();
		fillUpdateInfo(dto, user);
		questionManager.updateImportedQuestion(dto, user);
		return json;
	}

	/**
	 *	
	 * 描述:批量导题退回习题编辑
	 *
	 * @author  lavender
	 * @created 2015年1月5日 上午10:58:24
	 * @since   v1.0.0 
	 * @param dataJson
	 * @return
	 * @return  JsonResult
	 */
	@RequestMapping(value = "rejectEdit", method = RequestMethod.POST)
	@ResponseBody
	@Deprecated
	public JsonResult rejectEdit(String dataJson) {
		JsonResult json = new JsonResult();
		QuestionDTO dto = JsonUtils.fromJSON(dataJson, QuestionDTO.class);
		User user = UserContext.user.get();
		fillUpdateInfo(dto, user);
		questionManager.updateQuestionDTO(dto, user);
		return json;
	}

	private void fillCreateInfo(QuestionDTO dto, User user) {
		fillUpdateInfo(dto, user);
		dto.setCreatedBy(user.getId());
		dto.setCreatorName(user.getUserName());
		School school = user.getCurrentSchool();
		if (school != null) {
			dto.setSchoolId(school.getId());
			dto.setSchoolName(school.getName());
		}
	}

	private void fillUpdateInfo(QuestionDTO dto, User user) {
		if (dto.getSharePlatform() == null) {
			dto.setSharePlatform(true);
		}
		dto.setModifiedBy(user.getId());
		dto.setOperatorName(user.getUserName());
	}
	
	public static class QueCartType {
		private List<Long> questionIds;
		private Long questionTypeId;

		public Long getQuestionTypeId() {
			return questionTypeId;
		}

		public void setQuestionTypeId(Long questionTypeId) {
			this.questionTypeId = questionTypeId;
		}

		public List<Long> getQuestionIds() {
			return questionIds;
		}

		public void setQuestionIds(List<Long> questionIds) {
			this.questionIds = questionIds;
		}
	}
	
	
	@RequestMapping(value = "queryQuestionsByType")
	@ResponseBody
	public JsonResult queryQuestionsByType(String dataJson) {
		QueCartType options = JsonUtils.fromJSON(dataJson, QueCartType.class);
		List<Long> questionList = new ArrayList<Long>();
		List<QuestionDTO> questions = QuestionContext.findQuestions(options.getQuestionIds());
		for(QuestionDTO question : questions){
			if(question.getQuestionTypeId() == options.getQuestionTypeId()){
				questionList.add(question.getQuestionId());
			}
		}
		JsonResult json = new JsonResult();
		json.addDatas("questionList", questionList);
		return json;
	}
}
