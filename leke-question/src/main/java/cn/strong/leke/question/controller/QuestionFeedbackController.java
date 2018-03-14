/* 
 * 包名: cn.strong.leke.question.controller
 *
 * 文件名：QuestionFeedbackController.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-6-16
 */
package cn.strong.leke.question.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.base.ParametersContext;
import cn.strong.leke.context.user.ResearcherContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.business.notice.NoticeHelper;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.SchoolStageSubject;
import cn.strong.leke.model.question.QuestionFeedback;
import cn.strong.leke.model.question.QuestionFeedbackDTO;
import cn.strong.leke.model.question.UserQuestionDTO;
import cn.strong.leke.model.user.Researcher;
import cn.strong.leke.model.user.User;
import cn.strong.leke.notice.model.LetterMessage;
import cn.strong.leke.question.service.IQuestionFeedbackService;
import cn.strong.leke.question.service.QuestionManager;

/**
 * 题目反馈控制器
 * @author    lavender
 * @version   Avatar 
 */
@Controller
@RequestMapping("/auth/*")
public class QuestionFeedbackController {
	@Resource
	private IQuestionFeedbackService questionFeedbackService;
	@Resource
	private QuestionManager questionManager;


	/**
	 * 教研员查询未处理的题目反馈列表
	 * author：lavender
	 * 2014-6-16下午2:52:52
	 */
	@RequestMapping("researcher/questionFeedback/questionFeedbackList")
	public void questionFeedbackList(){
	}
	
	/**
	 * 教研员查询纠错列表
	 * @param page
	 * @return
	 * author：lavender
	 * 2014-6-17下午2:51:26
	 */
	@RequestMapping("researcher/questionFeedback/questionFeedbackListDetails")
	public void questionFeedbackListDetails(QuestionFeedbackDTO questionFeedbackDTO, Page page, Model model){
		Researcher researcher = ResearcherContext.researcher.get();
		if (questionFeedbackDTO.getSchoolStageId() != null && questionFeedbackDTO.getSubjectId() != null) {
			List<SchoolStageSubject> schoolStageSubjects = ListUtils.newArrayList();
			SchoolStageSubject schoolStageSubject = new SchoolStageSubject();
			schoolStageSubject.setSchoolStageId(questionFeedbackDTO.getSchoolStageId());
			schoolStageSubject.setSubjectId(questionFeedbackDTO.getSubjectId());
			schoolStageSubjects.add(schoolStageSubject);
			researcher.setSchoolStageSubjects(schoolStageSubjects);
		}
		List<UserQuestionDTO> ql = questionManager.queryFeedbackQuestions(questionFeedbackDTO, researcher, page);
		model.addAttribute("questions", ql);
		model.addAttribute("page", page);
	}

	/**
	 * 教师查询未处理的纠错反馈列表页面
	 * author：lavender
	 * 2014-7-28下午4:02:36
	 */
	@RequestMapping("teacher/questionFeedback/userQuestionFeedbackList")
	public void userQuestionFeedbackList(){
	}
	
	/**
	 * 教师查询未处理的纠错反馈列表
	 * @param page
	 * @param model
	 * author：lavender
	 * 2014-7-28下午4:03:04
	 */
	@RequestMapping("teacher/questionFeedback/userQuestionFeedbackListDetails")
	public void userQuestionFeedbackListDetails(Page page, Model model){
		UserContext user = UserContext.user;
		List<UserQuestionDTO> ql = questionManager.queryFeedbackQuestionsByUser(user.getUserId(), page);
		model.addAttribute("questions", ql);
		model.addAttribute("page", page);
	}
	
	
	/**
	 * 教研员将题目设置为已纠错
	 * @param qf
	 * @return
	 * author：lavender
	 * 2014-6-17下午2:51:39
	 */
	@RequestMapping("researcher/questionFeedback/ajax/addQuestionFeedback")
	@ResponseBody
	public JsonResult processQuestionFeedback(QuestionFeedback qf){
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		Long userId = user.getId();
		qf.setModifiedBy(userId);
		qf.setUserId(userId);
		qf.setUserName(user.getUserName());
		questionFeedbackService.processQuestionFeedback(qf);
		return json;
	}
	
	/**
	 * 教师添加纠错修改意见
	 * @param qf
	 * @return
	 * author：lavender
	 * 2014-6-17下午2:55:05
	 */
	@RequestMapping("common/questionFeedback/ajax/addFeedbackContent")
	@ResponseBody
	public JsonResult addFeedbackContent(QuestionFeedbackDTO qf){
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		questionFeedbackService.addQuestionFeedback(qf, user);
		return json;
	}

	/**
	 * 忽略纠错信息
	 * @param dto
	 * @return
	 * author：lavender
	 * 2014-7-28下午2:45:13
	 */
	@RequestMapping("common/questionFeedback/ignore")
	@ResponseBody
	public JsonResult ignore(Long questionId, Long questionFeedbackId) {
		JsonResult json = new JsonResult();
		QuestionFeedback qf = new QuestionFeedback();
		qf.setModifiedBy(UserContext.user.getUserId());
		qf.setQuestionId(questionId);
		qf.setQuestionFeedbackId(questionFeedbackId);
		
		List<QuestionFeedbackDTO> questionFeedbackList = questionFeedbackService.findQuestionFeedback(questionId);

		for(QuestionFeedback qF : questionFeedbackList){
			if (questionFeedbackId != null && !qF.getQuestionFeedbackId().equals(questionFeedbackId)) {
				continue;
			}
			LetterMessage message = new LetterMessage(String.valueOf(qF.getUserId()), "纠错处理提醒");
			message.setContent(ParametersContext.getString("FEEDBACK_IGNORE_TEMPLATE"));
			message.addVariable("name", "习题");
			NoticeHelper.send(message);
		}
		
		questionFeedbackService.ignoreQuestionFeedback(qf);
		return json;
	}
}
