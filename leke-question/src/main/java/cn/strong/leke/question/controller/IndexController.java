/* 
 * 包名: cn.strong.leke.question.controller
 *
 * 文件名：QuestionController.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-5-5
 */
package cn.strong.leke.question.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.InputStatisDTO;
import cn.strong.leke.question.service.IQuestionLogService;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    lavender
 * @version   Avatar 
 */
@Controller
@RequestMapping("/auth/*")
public class IndexController {
	@Resource
	private IQuestionLogService questionLogService;
	//--------------------------------------admin---------------------------------------------//
	/**
	 * 管理员主页页面
	 * author：lavender
	 * 2014-5-5上午9:23:09
	 */
	@RequestMapping("admin/adminIndex")
	public void adminIndex(){
		
	}
	
	/**
	 * 管理员主页
	 * author：lavender
	 * 2014-5-5上午9:23:09
	 */
	@RequestMapping("admin/ajax/findAdminIndex")
	@ResponseBody
	public JsonResult findAdminIndex(){
		JsonResult json = new JsonResult();
		UserContext user = UserContext.user;
		User user1 = user.get();
		user1.setPhoto(FileUtils.getDomain() + "/" + user1.getPhoto());
		
		InputStatisDTO inputStatis = questionLogService.findDraftAndFormalTotalAmount();
		json.addDatas("inputStatis",inputStatis);
		json.addDatas("user", user1);
		json.addDatas("userName", user.getUserName());
		return json;
	}
	
	//--------------------------------------checker---------------------------------------------//
	/**
	 * 审核员主页页面
	 * author：lavender
	 * 2014-5-5上午9:23:09
	 */
	@RequestMapping("checker/checkerIndex")
	public void checkerIndex(){
		
	}
	
	/**
	 * 审核员主页
	 * author：lavender
	 * 2014-5-5上午9:23:09
	 */
	@RequestMapping("checker/ajax/findCheckerIndex")
	@ResponseBody
	public JsonResult findCheckerIndex(ModelMap model){
		JsonResult json = new JsonResult();
		UserContext user = UserContext.user;
		User user1 = user.get();
		user1.setPhoto(FileUtils.getDomain() + "/" +user.get().getPhoto());
		
		InputStatisDTO inputStatis = questionLogService.findCheckAmountByUserId(user.getUserId());

		json.addDatas("inputStatis",inputStatis);
		json.addDatas("user", user1);
		return json;
	}
	
	//--------------------------------------inputer---------------------------------------------//
	/**
	 * 录入员主页页面
	 * author：lavender
	 * 2014-5-5上午9:23:09
	 */
	@RequestMapping("inputer/inputerIndex")
	public void inputerIndex(){

	}
	
	/**
	 * 录入员主页
	 * author：lavender
	 * 2014-5-5上午9:23:09
	 */
	@RequestMapping("inputer/ajax/findInputerIndex")
	@ResponseBody
	public JsonResult findInputerIndex(ModelMap model){
		JsonResult json = new JsonResult();
		UserContext user = UserContext.user;
		User user1 = user.get();
		user1.setPhoto(FileUtils.getDomain() + "/" +user.get().getPhoto());
		
		InputStatisDTO inputStatis = questionLogService.findInputAmountByUserId(user.getUserId());

		json.addDatas("inputStatis",inputStatis);
		json.addDatas("user", user1);
		return json;
	}
	
	//--------------------------------------researcher---------------------------------------------//
	/**
	 * 教研员主页页面
	 * author：lavender
	 * 2014-5-5上午9:23:09
	 */
	@RequestMapping("researcher/researcherIndex")
	public void researcherIndex(){
		
	}
	
	/**
	 * 教研员主页
	 * author：lavender
	 * 2014-5-5上午9:23:09
	 */
	@RequestMapping("researcher/ajax/findResearcherIndex")
	@ResponseBody
	public JsonResult findResearcherIndex(ModelMap model){
		JsonResult json = new JsonResult();
		UserContext user = UserContext.user;
		User user1 = user.get();
		user1.setPhoto(FileUtils.getDomain() + "/" +user.get().getPhoto());
		
		InputStatisDTO inputStatis = questionLogService.findProofreadingAmountByUserId(user.getUserId());

		json.addDatas("inputStatis",inputStatis);
		json.addDatas("user", user1);
		return json;
	}
	
}