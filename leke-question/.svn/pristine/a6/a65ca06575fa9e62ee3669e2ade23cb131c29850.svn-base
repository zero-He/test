/* 
 * 包名: cn.strong.leke.question.controller
 *
 * 文件名：QuestionRejectionController.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-7-29
 */
package cn.strong.leke.question.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.QuestionRejection;
import cn.strong.leke.model.question.UserQuestionDTO;
import cn.strong.leke.question.service.IQuestionRejectionService;
import cn.strong.leke.question.service.QuestionManager;

/**
 * 习题退回控制器
 * @author    lavender
 * @version   Avatar 
 */
@Controller
@RequestMapping("/auth/*")
public class QuestionRejectionController {
	@Resource
	private IQuestionRejectionService questionRejectionService;
	@Resource
	private QuestionManager questionManager;
	
	/**
	 * 查询未处理的退回列表页面
	 * author：lavender
	 * 2014-7-29下午4:48:47
	 */
	@RequestMapping("teacher/questionRejection/userQuestionRejectionList")
	public void userQuestionRejectionList(){
	}
	
	
	/**
	 * 查询未处理的退回列表
	 * @param page
	 * @param model
	 * author：lavender
	 * 2014-7-29下午4:49:13
	 */
	@RequestMapping("teacher/questionRejection/userQuestionRejectionListDetails")
	public void userQuestionRejectionListDetails(Page page, Model model){
		UserContext user = UserContext.user;
		List<UserQuestionDTO> ql = questionManager.queryRejectionQuestionsByUser(user.getUserId(), page);
		model.addAttribute("questions", ql);
		model.addAttribute("page", page);
	}
	/**
	 * 忽略纠错信息
	 * @param dto
	 * @return
	 * author：lavender
	 * 2014-7-28下午2:45:13
	 */
	@RequestMapping("teacher/questionRejection/ignore")
	@ResponseBody
	public JsonResult ignore(Long questionId) {
		JsonResult json = new JsonResult();
		QuestionRejection qf = new QuestionRejection();
		qf.setModifiedBy(UserContext.user.getUserId());
		qf.setQuestionId(questionId);
		questionRejectionService.ignoreQuestionRejection(qf);
		return json;
	}
}
