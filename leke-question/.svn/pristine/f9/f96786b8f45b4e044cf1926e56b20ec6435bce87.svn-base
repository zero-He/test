/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.controller;

import static com.google.common.collect.Sets.newHashSet;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.UserPrefContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.context.user.model.UserDataAuthority;
import cn.strong.leke.context.user.model.UserPref;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.common.UserResGroupDTO;
import cn.strong.leke.model.question.Question;
import cn.strong.leke.model.question.QuestionComment;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionFeedbackDTO;
import cn.strong.leke.model.question.QuestionPraise;
import cn.strong.leke.model.question.UserQuestionDTO;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.question.SchoolQuestion;
import cn.strong.leke.question.model.question.TeacherQuestion;
import cn.strong.leke.question.service.IQuestionCommentService;
import cn.strong.leke.question.service.IQuestionFeedbackService;
import cn.strong.leke.question.service.IQuestionPraiseService;
import cn.strong.leke.question.service.IQuestionUserResGroupService;
import cn.strong.leke.question.service.QuestionManager;
import cn.strong.leke.question.service.SchoolQuestionService;
import cn.strong.leke.question.service.TeacherQuestionService;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年6月19日 下午3:29:29
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/common/userQue")
public class UserQueController {

	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private TeacherQuestionService teacherQuestionService;
	@Autowired
	private SchoolQuestionService schoolQuestionService;
	@Autowired
	private IQuestionFeedbackService questionFeedbackService;
	@Autowired
	private IQuestionPraiseService questionPraiseService;
	@Autowired
	private IQuestionCommentService questionCommentService;
	@Resource
	private IQuestionUserResGroupService questionUserResGroupService;

	@RequestMapping("get")
	@ResponseBody
	public JsonResult get(Long questionId) {
		JsonResult json = new JsonResult();
		QuestionDTO dto = questionManager.getQuestionDTO(questionId);
		json.addDatas("question", dto);
		return json;
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult add(String dataJson) {
		JsonResult json = new JsonResult();
		UserQuestionDTO dto = JsonUtils.fromJSON(dataJson,
				UserQuestionDTO.class);
		User user = UserContext.user.get();
		Long userId = user.getId();
		String userName = user.getUserName();
		School school = user.getCurrentSchool();
		Long roleId = user.getCurrentRole().getId();
		dto.setCreatedBy(userId);
		dto.setCreatorName(userName);
		dto.setModifiedBy(userId);
		dto.setOperatorName(userName);
		if (school != null) {
			dto.setSchoolId(school.getId());
			dto.setSchoolName(school.getName());
		}
		if (roleId.equals(RoleCst.SCHOOL_RESEARCHER)) {
			dto.setShareSchool(true);
			dto.setSharePlatform(false);
		}
		if (roleId.equals(RoleCst.RESEARCHER)) {
			dto.setShareSchool(false);
			dto.setSharePlatform(true);
		}
		questionManager.addUserQuestionDTO(dto);

		Long questionId = dto.getQuestionId();
		json.addDatas("questionId", questionId);
		return json;
	}

	@RequestMapping(value = "modify", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult modify(String dataJson) {
		JsonResult json = new JsonResult();
		UserQuestionDTO dto = JsonUtils.fromJSON(dataJson,
				UserQuestionDTO.class);
		User user = UserContext.user.get();
		School school = user.getCurrentSchool();
		dto.setModifiedBy(user.getId());
		dto.setOperatorName(user.getUserName());
		if (school != null) {
			dto.setSchoolId(school.getId());
			dto.setSchoolName(school.getName());
		}
		questionManager.updateUserQuestionDTO(dto);
		return json;
	}

	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(UserQuestionDTO dto) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		School school = user.getCurrentSchool();
		Long schoolId = school != null ? school.getId() : null;
		dto.setModifiedBy(user.getId());
		dto.setOperatorName(user.getUserName());
		dto.setSchoolId(schoolId);
		questionManager.deleteUserQuestionDTO(dto);
		return json;
	}

	@RequestMapping("addFav")
	@ResponseBody
	public JsonResult addFav(TeacherQuestion teacherQuestion) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		Long teacherId = user.getId();
		teacherQuestion.setTeacherId(teacherId);
		teacherQuestion.setCreatedBy(teacherId);
		teacherQuestion.setModifiedBy(teacherId);
		teacherQuestionService.addTeacherQuestion(teacherQuestion, user);
		return json;
	}

	@RequestMapping("delFav")
	@ResponseBody
	public JsonResult delFav(TeacherQuestion teacherQuestion) {
		JsonResult json = new JsonResult();
		Long teacherId = UserContext.user.getUserId();
		teacherQuestion.setTeacherId(teacherId);
		teacherQuestion.setModifiedBy(teacherId);
		teacherQuestionService.deleteTeacherQuestion(teacherQuestion);
		return json;
	}

	@RequestMapping("addSchFav")
	@ResponseBody
	public JsonResult addSchFav(SchoolQuestion schoolQuestion) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		Long userId = user.getId();
		School school = user.getCurrentSchool();
		Long schoolId = school != null ? school.getId() : null;
		schoolQuestion.setSchoolId(schoolId);
		schoolQuestion.setCreatedBy(userId);
		schoolQuestion.setModifiedBy(userId);
		schoolQuestionService.addSchoolQuestion(schoolQuestion);
		return json;
	}

	@RequestMapping("delSchFav")
	@ResponseBody
	public JsonResult delSchFav(SchoolQuestion schoolQuestion) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		Long userId = user.getId();
		School school = user.getCurrentSchool();
		Long schoolId = school != null ? school.getId() : null;
		schoolQuestion.setSchoolId(schoolId);
		schoolQuestion.setModifiedBy(userId);
		schoolQuestionService.deleteSchoolQuestion(schoolQuestion);
		return json;
	}

	/**
	 * 
	 * 描述: 纠错反馈
	 * 
	 * @author liulb
	 * @created 2014年6月23日 下午2:36:11
	 * @since v1.0.0
	 * @param teacherQuestion
	 * @return JsonResult
	 */
	@RequestMapping("feedback")
	@ResponseBody
	public JsonResult feedback(QuestionFeedbackDTO feedback) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		questionFeedbackService.addQuestionFeedback(feedback, user);
		return json;
	}

	/**
	 * 
	 * 描述: 点赞
	 * 
	 * @author liulb
	 * @created 2014年6月23日 下午3:52:28
	 * @since v1.0.0
	 * @param praise
	 * @return JsonResult
	 */
	@RequestMapping("praise")
	@ResponseBody
	public JsonResult praise(QuestionPraise praise) {
		JsonResult json = new JsonResult();
		Long teacherId = UserContext.user.getUserId();
		praise.setCreatedBy(teacherId);
		questionPraiseService.addQuestionPraise(praise);
		return json;
	}

	/**
	 * 
	 * 描述: 评论
	 * 
	 * @author liulb
	 * @created 2014年6月23日 下午3:52:41
	 * @since v1.0.0
	 * @param queComment
	 * @return JsonResult
	 */
	@RequestMapping("comment")
	@ResponseBody
	public JsonResult comment(QuestionComment queComment) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		Long userId = user.getId();
		String userName = user.getUserName();
		School school = user.getCurrentSchool();
		Long schoolId = school != null ? school.getId() : null;
		String schoolName = school != null ? school.getName() : null;
		queComment.setCreatedBy(userId);
		queComment.setModifiedBy(userId);
		queComment.setUserName(userName);
		queComment.setSchoolId(schoolId);
		queComment.setSchoolName(schoolName);
		questionCommentService.addQuestionComment(queComment);
		return json;
	}

	/**
	 * 
	 * 描述: 评论列表
	 * 
	 * @author liulb
	 * @created 2014年6月24日 上午11:24:25
	 * @since v1.0.0
	 * @param questionId
	 * @param page
	 * @return void
	 */
	@RequestMapping("comments")
	public void comments(Long questionId, Page page, Model model) {
		List<QuestionComment> comments = questionCommentService
				.queryCommentsByQuestionId(questionId, page);
		model.addAttribute("comments", comments);
		model.addAttribute("page", page);
	}

	/**
	 * 禁用题目
	 * @param dto
	 * @return
	 * author：lavender
	 * 2014-7-28上午10:25:25
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

	@RequestMapping(value = "replaceEdit", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult replaceEdit(String dataJson) {
		JsonResult json = new JsonResult();
		QuestionDTO dto = JsonUtils.fromJSON(dataJson, QuestionDTO.class);
		User user = UserContext.user.get();
		Long userId = user.getId();
		School school = user.getCurrentSchool();
		dto.setModifiedBy(userId);
		dto.setOperatorName(user.getUserName());
		Long schoolId = null;
		if (school != null) {
			schoolId = school.getId();
			dto.setSchoolId(schoolId);
			dto.setSchoolName(school.getName());
		}
		Long newId = questionManager.updateReplaceQuestion(dto, user);
		questionManager.updateStatus(newId, Question.QUE_STATUS_TEACHER_INPUT);

		json.addDatas("newId", newId);
		return json;
	}

	@ResponseBody
	@RequestMapping("addBatchQuestionUserGroup")
	public JsonResult addBatchQuestionUserGroup(Long userResGroupId,
			@RequestParam("questionIds[]") Long[] questionIds) {
		User user = UserContext.user.get();
		questionUserResGroupService.addBatchQuestionUserResGroup(userResGroupId,
				newHashSet(questionIds), user);
		return new JsonResult();
	}

	@ResponseBody
	@RequestMapping("moveBatchQuestionUserGroup")
	public JsonResult moveBatchQuestionUserGroup(String dataJson,
			@RequestParam("questionIds[]") Long[] questionIds) {
		UserResGroupDTO userResGroup = JsonUtils.fromJSON(dataJson, UserResGroupDTO.class);
		User user = UserContext.user.get();
		questionUserResGroupService.saveMoveBatchQUserResGroup(userResGroup,
				newHashSet(questionIds), user);
		return new JsonResult();
	}

	@ResponseBody
	@RequestMapping("delBatchQuestionUserResGroup")
	public JsonResult delBatchQuestionUserResGroup(Long userResGroupId,
			@RequestParam("questionIds[]") Long[] questionIds) {
		User user = UserContext.user.get();
		questionUserResGroupService.deleteBatchQuestionUserResGroup(userResGroupId,
				newHashSet(questionIds),
				user);
		return new JsonResult();
	}

	/**
	 *
	 * 描述: 习题的分组删除
	 *
	 * @author raolei
	 * @created 2016年4月14日 下午5:45:45
	 */
	@RequestMapping("delUserGroup")
	@ResponseBody
	public JsonResult delUserGroup(Long userResGroupId) {
		User user = UserContext.user.get();
		questionUserResGroupService.deleteQURGroupByUserResGroupId(userResGroupId, user);
		return new JsonResult();
	}

	/**
	 *
	 * 描述: 当前用户是否可以编辑习题
	 *
	 * @author raolei
	 */
	@RequestMapping("canEdit")
	@ResponseBody
	public JsonResult canEdit(Long questionId) {
		QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
		boolean canEdit = false;
		if (question == null) {
			throw new ValidateException("que.question.not.exist");
		}
		Long schoolStageId = question.getSchoolStageId();
		Long subjectId = question.getSubjectId();
		UserPref userPref =  UserPrefContext.getUserPref();
		if (userPref != null) {
			UserDataAuthority userDataAuthority = userPref.getAuthority();
			if (userDataAuthority != null && CollectionUtils.isNotEmpty(userDataAuthority.getSchoolStages())) {
				for (SchoolStageRemote schoolStage : userDataAuthority.getSchoolStages()) {
					if (canEdit) {
						break;
					}
					if (schoolStage.getSchoolStageId().equals(schoolStageId)) {
						for (SubjectRemote subject : schoolStage.getSubjects()) {
							if (subject.getSubjectId().equals(subjectId)) {
								canEdit = true;
								break;
							}
						}
					}
				}
			}
		}
		return new JsonResult().addDatas("canEdit", canEdit);
	}
}
