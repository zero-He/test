package cn.strong.leke.homework.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.model.HolidayHwMQ;
import cn.strong.leke.homework.model.HomeworkConfig;
import cn.strong.leke.homework.service.HomeworkConfigService;


@Controller
@RequestMapping("/auth/platformAdmin/homeworkConfig/*")
public class HomeworkConfigControler {
	
	
	@Resource
	private HomeworkConfigService homeworkConfigService;
	@Resource
	private MessageSender holidayHwSender;
	
	@RequestMapping(value = "HomeworkConfigList")
	public String ToHomeworkConfigList() {
		
		//List<HomeworkConfig> lst = homeworkConfigService.GetHomeworkConfigList(null,page);
		//model.addAttribute("lst", lst);
	   	return "/auth/platformAdmin/homeworkConfig/HomeworkConfigList";
		
	}
	@RequestMapping(value = "HomeworkConfigList",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult HomeworkConfigList(
			Page page) {
		
		JsonResult json =new JsonResult();
		List<HomeworkConfig> lst = homeworkConfigService.GetHomeworkConfigList(null,page);
		page.setDataList(lst);
		json.addDatas("page", page);
		return json;

		
	}

	@RequestMapping(value = "AddHomeworkConfig",method = RequestMethod.GET)
   public String ToAddHomeworkConfig(){
	   
	   return "/auth/platformAdmin/homeworkConfig/AddHomeworkConfig";
   }
	
	@RequestMapping(value = "AddConfig",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult AddConfig(HomeworkConfig homeworkConfig) {
		
		JsonResult json =new JsonResult();
		/*HomeworkConfig hc =new HomeworkConfig();
		hc.setYear(homeworkConfig.getYear());
		hc.setType(homeworkConfig.getType());
		List<HomeworkConfig> lst =homeworkConfigService.GetHomeworkConfigList(hc,null);
		if(lst!=null&&lst.size()>0){
			json.setErr("已存在相同年份,相同类型的配置");
			//json.setSuccess(false);
			return json;
		}*/
		
		homeworkConfig.setCreatedBy(UserContext.user.getUserId());
		homeworkConfig.setModifiedBy(UserContext.user.getUserId());
		homeworkConfigService.AddHomeworkConfig(homeworkConfig);
		//发送生成寒暑假作业的消息
		HolidayHwMQ mq = new HolidayHwMQ(homeworkConfig.getYear(), homeworkConfig.getType(),
				homeworkConfig.getWork_begintime(), homeworkConfig.getWork_endtime(),
				homeworkConfig.getCycle_begintime(), homeworkConfig.getCycle_endtime());

		holidayHwSender.send(mq);
		return json;
	}
	
	@RequestMapping(value = "HasConfig",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult HasConfig() {

		JsonResult json =new JsonResult();
	    Page page=new Page();
	    page.setCurPage(1);
	    page.setPageSize(1);
		List<HomeworkConfig> lst = homeworkConfigService.GetHomeworkConfigList(null,page);
		json.addDatas("hasConfig",(lst!=null&&lst.size()>0)?1:0);
		return json;

	}
}
