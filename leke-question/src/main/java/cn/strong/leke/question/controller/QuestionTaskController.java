/* 
 * 包名: cn.strong.leke.question.controller
 *
 * 文件名：QuestionTaskController.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-5-7
 */
package cn.strong.leke.question.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.CheckerContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.user.Checker;
import cn.strong.leke.question.model.QuestionTask;
import cn.strong.leke.question.service.IQuestionTaskService;

/**
 * 习题领取控制器
 * @author    lavender
 * @version   Avatar 
 */
@Controller
@RequestMapping("/auth/*")
public class QuestionTaskController {

	@Resource
	private IQuestionTaskService questionTaskService;
	
	//--------------------------------------checker---------------------------------------------//
	
	/**
	 * 审核员习题领取页面
	 * author：lavender
	 * 2014-5-8下午1:58:45
	 */
	@RequestMapping("checker/questionTask/questionTask")
	public void questionTask() {
	}
	
	/**
	 * 验证是否已经全部审核完已领取题目
	 * @return
	 * author：lavender
	 * 2014-5-8下午1:58:53
	 */
	@RequestMapping("checker/questionTask/ajax/checkFinished")
	@ResponseBody
	public JsonResult checkFinished() {
		JsonResult json = new JsonResult();
		int result = questionTaskService.checkFinished(UserContext.user.getUserId());
		if (result > 0) {
			json.addDatas("result", 1);
		} else {
			json.addDatas("result", 0);
		}
		
		return json;
	}
	
	/**
	 * 添加题目领取
	 * @param count
	 * @param gradeId
	 * @param subjectId
	 * @return
	 * author：lavender
	 * 2014-5-8下午1:58:57
	 */
	@RequestMapping("checker/questionTask/ajax/addQuestionTask")
	@ResponseBody
	public JsonResult addQuestionTask(QuestionTask task) {
		JsonResult json = new JsonResult();
		try {
			task.setUserId(UserContext.user.getUserId());
			questionTaskService.addQuestionTask(task);
		} catch (Exception e) {
			json.setErr(e.getMessage());
		}
		return json;
	}
	
	/**
	 * 查询用户历史领取数量及未完成数量，年级科目排序
	 * @return
	 * author：lavender
	 * 2014-5-8下午1:59:01
	 */
	@RequestMapping("checker/questionTask/ajax/findQuestionTaskByUserId")
	@ResponseBody
	public JsonResult findQuestionTaskByUserId() {
		JsonResult json = new JsonResult();
		Checker checker = CheckerContext.checker.get();
		List<QuestionTask> qtList = questionTaskService.findQuestionTaskByUserId(checker);
		json.addDatas("qtList", qtList);
		
		return json;
	}
	
	//--------------------------------------admin---------------------------------------------//
	
	/**
	 * 管理员习题收回页面
	 * author：lavender
	 * 2014-5-8下午1:59:05
	 */
	@RequestMapping("admin/questionTask/recycleQuestionTask")
	public void recycleQuestionTask() {
	}
	
	/**
	 * 查询领取习题剩余数量列表，根据用户排序
	 * @return
	 * author：lavender
	 * 2014-5-8下午1:59:09
	 */
	@RequestMapping("admin/questionTask/ajax/findQuestionTaskSurplusGroupByUserId")
	@ResponseBody
	public JsonResult findQuestionTaskSurplusGroupByUserId(String userName) {
		JsonResult json = new JsonResult();
		List<QuestionTask> qtList = questionTaskService.findQuestionTaskSurplusGroupByUserId(userName);
		json.addDatas("qtList", qtList);
		
		return json;
	}
	
	/**
	 * 管理员回收对应用户的已领取习题
	 * @param userId
	 * @return
	 * author：lavender
	 * 2014-5-8下午1:59:12
	 */
	@RequestMapping("admin/questionTask/ajax/recycleQuestionTask")
	@ResponseBody
	public JsonResult recycleQuestionTask(Long userId) {
		JsonResult json = new JsonResult();
		int result = questionTaskService.recycleQuestionTask(userId);
		if(result > 0) {
			json.addDatas("result", 1);
		} else {
			json.addDatas("result", 0);
		}
		
		return json;
	}
	
}
