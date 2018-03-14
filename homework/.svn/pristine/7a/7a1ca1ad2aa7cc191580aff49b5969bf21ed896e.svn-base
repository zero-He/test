package cn.strong.leke.homework.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.service.ActivityService;

@Controller
@RequestMapping("/auth/student/**")
public class TestController {

	@Resource
	private ActivityService activityService;
	
	@RequestMapping("/test1")
	@ResponseBody
	public JsonResult testActivity () {
		Long userId = UserContext.user.getUserId();
		JsonResult jsonResult = new JsonResult();
		jsonResult.addDatas("bugfix", this.activityService.getBugFixInfo(userId, 7));
		jsonResult.addDatas("monthHw", this.activityService.findMonthHw(userId, 7));;
		jsonResult.addDatas("exercise", this.activityService.findTodayExercis(userId));
		return jsonResult;
	}
}
