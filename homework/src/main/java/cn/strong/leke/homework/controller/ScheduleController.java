package cn.strong.leke.homework.controller;

import java.sql.Date;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.strong.leke.framework.spring.SpringContextHolder;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.manage.ExerciseService;
import cn.strong.leke.homework.manage.HolidayHwMicroService;
import cn.strong.leke.homework.manage.SheetTaskService;
import cn.strong.leke.homework.model.HolidayHwMQ;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.homework.service.HomeworkForWrongService;
import cn.strong.leke.homework.service.HomeworkMainService;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.homework.service.IJobService;

@RestController
@RequestMapping("/auth/platformAdmin/*")
public class ScheduleController {

	@Resource
	private SheetTaskService sheetTaskService;
	@Resource
	private HomeworkMainService homeworkMainService;

	@Resource
	private HolidayHwMicroService holidayHwMicroService;
	@Resource
	private HomeworkForWrongService homeworkForWrongService;

	@Resource
	private HomeworkDtlService homeworkDtlService;

	@ResponseBody
	@RequestMapping(value = "/task", method = RequestMethod.GET)
	public JsonResult task(String id, @RequestParam(required = false, defaultValue = "false") Boolean force) {
		this.sheetTaskService.executeSheetTask(id, force);
		return new JsonResult();
	}

	@ResponseBody
	@RequestMapping(value = "/rewrite", method = RequestMethod.GET)
	public JsonResult rewrite(String id) {
		this.sheetTaskService.executeNormalReWrite(id);
		return new JsonResult();
	}

	@ResponseBody
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public JsonResult write(String id) {
		this.homeworkMainService.handleSheetSubmitMessageWithTx(id);
		return new JsonResult();
	}

	@RequestMapping("exercise/weekRank")
	@ResponseBody
	public JsonResult weekRank(Integer year, Integer week) {
		ExerciseService service = SpringContextHolder.getBean(ExerciseService.class);
		//生成当前周的 自主练习排名
		service.saveWeekRank(year, week);
		return new JsonResult();
	}

	@RequestMapping("weekHomework")
	@ResponseBody
	public JsonResult weekHomework(){
		IJobService jobService = SpringContextHolder.getBean(IJobService.class);
		jobService.excuteWeekHomework();
		return new JsonResult();
	}

	@RequestMapping("monthHomework")
	@ResponseBody
	public JsonResult monthHomework(){
		IJobService jobService = SpringContextHolder.getBean(IJobService.class);
		jobService.excuteMonthHomework();
		return new JsonResult();
	}

	@RequestMapping("stuHwIncentive")
	@ResponseBody
	public JsonResult stuHwIncentive(){
		HomeworkService service = SpringContextHolder.getBean(HomeworkService.class);
		service.execStudentHwIncentvie();
		return new JsonResult();
	}

	@RequestMapping("teaHwIncentive")
	@ResponseBody
	public JsonResult teaHwIncentive(){
		HomeworkService service = SpringContextHolder.getBean(HomeworkService.class);
		service.execTeacherHwIncentvie();
		return new JsonResult();
	}

	@RequestMapping("executeHolidayMic")
	public void executeHolidayMic(HolidayHwMQ holidayMQ, Long schoolId){
		this.holidayHwMicroService.executeHolidayForMic_school(holidayMQ, schoolId);

	}

	@RequestMapping("homework/teacherWrongHw")
	@ResponseBody
	public void wrongQuestion(){
		homeworkForWrongService.excuteWrontQuestion();
	}

	@RequestMapping("first/teacherWrongHw")
	public void executeWrongQueFoTeacher(Date start,Date end){
		this.homeworkForWrongService.executeWrongQuestion(start, end);
	}

	@RequestMapping("excuteCallVacationHomework")
	public JsonResult excuteCallVacationHomework(){
		JsonResult result = new JsonResult();
		holidayHwMicroService.excuteCallVacationHomework_2();
		result.setCode("200");
		result.setMessage("催交成功！！！");
		return result;
	}



}
