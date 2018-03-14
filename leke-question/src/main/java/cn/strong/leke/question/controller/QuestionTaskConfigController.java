/* 
 * 包名: cn.strong.leke.question.controller
 *
 * 文件名：QuestionTaskConfigController.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-6-10
 */
package cn.strong.leke.question.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.question.model.QueTaskConfig;
import cn.strong.leke.question.service.IQuestionTaskConfigService;

/**
 * 习题领取设置控制器
 * @author    lavender
 * @version   Avatar 
 */
@Controller
@RequestMapping("/auth/admin/questionTaskConfig/*")
public class QuestionTaskConfigController {
	@Resource
	private IQuestionTaskConfigService questionTaskConfigService;
	
	/**
	 * 习题领取设置页面
	 * author：lavender
	 * 2014-6-10下午3:28:54
	 */
	@RequestMapping("taskConfig")
	public void taskConfig(){
		
	}
	

	/**
	 * 习题领取设置
	 * @return
	 * author：lavender
	 * 2014-6-10下午3:29:37
	 */
	@RequestMapping("ajax/setTaskConfig")
	@ResponseBody
	public JsonResult setTaskConfig(QueTaskConfig qtc){
		JsonResult json = new JsonResult();
		UserContext user = UserContext.user;
		Long userId = user.getUserId();
		qtc.setCreatedBy(userId);
		qtc.setModifiedBy(userId);
		Integer result = questionTaskConfigService.addQueTaskConfig(qtc);
		json.addDatas("result",result);
		return json;
	}
}
