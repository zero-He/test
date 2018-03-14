package cn.strong.leke.scs.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.scs.model.query.StuAttendQuery;
import cn.strong.leke.scs.model.query.TeachAttendQuery;
import cn.strong.leke.scs.model.view.StudentAttend;
import cn.strong.leke.scs.model.view.TeachAttend;
import cn.strong.leke.scs.service.AttendanceService;

/**
 * 考勤统计。
 * @author  andy
 * @created 2015年6月17日 上午8:41:26
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/scs/attendance")
public class AttendanceController {

	@Resource
	private AttendanceService attendanceService;

	/**
	 * 老师考勤列表数据(单课为最小单位)
	 * @param query 条件
	 * @param page 分页
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/teacher", method = RequestMethod.POST)
	public JsonResult aa(TeachAttendQuery query, Page page) {
		List<TeachAttend> dataList = this.attendanceService.findTeachAttendListByQuery(query, page);
		page.setDataList(dataList);
		JsonResult json = new JsonResult();
		json.addDatas("page", page);
		return json;
	}

	/**
	 * 销售使用的学生考勤查看页面（按单课显示）。
	 * @param model
	 */
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public void student(Long csAttendId, Model model) {
		TeachAttend teachAttend = this.attendanceService.getTeachAttendByAttendId(csAttendId);
		model.addAttribute("teachAttend", teachAttend);
	}

	/**
	 * 销售使用的学生考勤查看数据（按单课显示）。
	 * @param query 条件
	 * @param page 分页
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/student", method = RequestMethod.POST)
	public JsonResult student(StuAttendQuery query, Page page) {
		List<StudentAttend> dataList = this.attendanceService.findStuAttendListByQuery(query, page);
		page.setDataList(dataList);
		JsonResult json = new JsonResult();
		json.addDatas("page", page);
		return json;
	}

}
