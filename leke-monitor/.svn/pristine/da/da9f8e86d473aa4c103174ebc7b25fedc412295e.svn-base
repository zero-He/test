package cn.strong.leke.monitor.controller.crm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.monitor.core.model.AttendanceQuery;
import cn.strong.leke.monitor.core.model.AttendanceStudentDTO;
import cn.strong.leke.monitor.core.model.AttendanceTeacherDTO;
import cn.strong.leke.monitor.core.model.AttendanceTeacherDtlDTO;
import cn.strong.leke.monitor.core.service.IAttendanceService;
import cn.strong.leke.monitor.core.utils.ExportExcelForTable;
import cn.strong.leke.monitor.util.FileUtils;

/**
 * crm考勤统计
 * 
 * @Description
 * @author Deo
 * @createdate 2017年3月22日 下午1:23:14
 */
@Controller
@RequestMapping("/auth/common/crm/attendance/")
public class AttendanceStatisticsController
{

	@Resource
	private IAttendanceService attendanceService;

	/**
	 * 考勤统计（学段）
	 * 
	 * @param requestQuery
	 * @param model
	 */
	@RequestMapping("/list")
	public void list(AttendanceQuery requestQuery, Model model)
	{
		model.addAttribute("query", requestQuery);
		model.addAttribute("queryJson", JsonUtils.toJSON(requestQuery));
	}

	/**
	 * 考勤统计（班级）
	 * 
	 * @param requestQuery
	 * @param model
	 */
	@RequestMapping("/classlist")
	public void classlist(AttendanceQuery requestQuery, Model model)
	{
		model.addAttribute("query", requestQuery);
		model.addAttribute("queryJson", JsonUtils.toJSON(requestQuery));
	}

	/**
	 * 学生课堂考勤统计（明细）
	 * 
	 * @param requestQuery
	 * @param model
	 */
	@RequestMapping("/dtlslist")
	public void dtlslist(AttendanceQuery requestQuery, Model model)
	{
		model.addAttribute("query", requestQuery);
		model.addAttribute("queryJson", JsonUtils.toJSON(requestQuery));
	}

	/**
	 * 班主任课堂考勤统计（明细）
	 * 
	 * @param requestQuery
	 * @param model
	 */
	@RequestMapping("/teacherLessonlist")
	public void teacherLessonlist(AttendanceQuery requestQuery, Model model)
	{
		model.addAttribute("query", requestQuery);
		model.addAttribute("queryJson", JsonUtils.toJSON(requestQuery));
	}

	/**
	 * 加载数据列表
	 * 
	 * @param Query
	 * @return
	 */
	@RequestMapping("/getAttendanceList")
	@ResponseBody
	public JsonResult getAttendanceList(AttendanceQuery query, Page page)
	{
		JsonResult json = new JsonResult();
		try
		{
			json.addDatas("record", attendanceService.getList(query, page));
		} catch (Exception ex)
		{
			json.setErr(ex.getMessage());
		}
		json.addDatas("page", page);

		return json;
	}

	/**
	 * 加载班级考勤数据列表(学生)
	 * 
	 * @param Query
	 * @return
	 */
	@RequestMapping("/getStudentAttendanceClassList")
	@ResponseBody
	public JsonResult getStudentAttendanceClassList(AttendanceQuery query, Page page)
	{
		JsonResult json = new JsonResult();
		try
		{
			json.addDatas("record", attendanceService.getStudentAttendanceClassList(query, page));
		} catch (Exception ex)
		{
			json.setErr(ex.getMessage());
		}
		json.addDatas("page", page);

		return json;
	}

	/**
	 * 加载班级考勤数据列表(班主任)
	 * 
	 * @param Query
	 * @return
	 */
	@RequestMapping("/getTeacherAttendanceClassList")
	@ResponseBody
	public JsonResult getTeacherAttendanceClassList(AttendanceQuery query, Page page)
	{
		JsonResult json = new JsonResult();
		try
		{
			json.addDatas("record", attendanceService.getTeacherAttendanceClassList(query, page));
		} catch (Exception ex)
		{
			json.setErr(ex.getMessage());
		}
		json.addDatas("page", page);

		return json;
	}

	/**
	 * 加载课堂考勤数据列表(学生)
	 * 
	 * @param Query
	 * @return
	 */
	@RequestMapping("/getStudentAttendanceLessonList")
	@ResponseBody
	public JsonResult getStudentAttendanceLessonList(AttendanceQuery query, Page page)
	{
		JsonResult json = new JsonResult();
		try
		{
			json.addDatas("record", attendanceService.getStudentAttendanceLessonList(query, page));
		} catch (Exception ex)
		{
			json.setErr(ex.getMessage());
		}
		json.addDatas("page", page);

		return json;
	}

	/**
	 * 加载课堂考勤数据列表(班主任)
	 * 
	 * @param Query
	 * @return
	 */
	@RequestMapping("/getTeacherAttendanceLessonList")
	@ResponseBody
	public JsonResult getTeacherAttendanceLessonList(AttendanceQuery query, Page page)
	{
		JsonResult json = new JsonResult();
		try
		{
			json.addDatas("record", attendanceService.getTeacherAttendanceLessonList(query, page));
		} catch (Exception ex)
		{
			json.setErr(ex.getMessage());
		}
		json.addDatas("page", page);

		return json;
	}

	/**
	 * 导出数据
	 * 
	 * @param <T>
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("exportExcelData")
	@ResponseBody
	public void exportExcelData(String action, String dataJson, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (action.equals("classTeacher"))
		{
			List<AttendanceTeacherDTO> list = new ArrayList<AttendanceTeacherDTO>();
			export(action, dataJson, request, response, list);
		} else if (action.equals("lessonTeacher"))
		{
			List<AttendanceTeacherDtlDTO> list = new ArrayList<AttendanceTeacherDtlDTO>();
			export(action, dataJson, request, response, list);
		} else
		{
			List<AttendanceStudentDTO> list = new ArrayList<AttendanceStudentDTO>();
			export(action, dataJson, request, response, list);
		}
	}

	private <T> void export(String action, String dataJson, HttpServletRequest request, HttpServletResponse response, List<T> list) throws Exception
	{
		Map<Object, String> map = new HashMap<Object, String>();
		getExcelData(action, dataJson, map, list);
		String headerList = map.get("headerList");
		String fieldList = map.get("fieldList");
		String title = map.get("title");
		String[] tempHeads = headerList.split(",");
		String[] tempFiled = fieldList.split(",");
		String[] headers = new String[tempHeads.length];
		String[] fieldNames = new String[tempFiled.length];
		for (int j = 0; j < headers.length; j++)
		{
			headers[j] = tempHeads[j];
		}
		for (int j = 0; j < fieldNames.length; j++)
		{
			fieldNames[j] = tempFiled[j];
		}
		String pattern = "yyyy-MM-dd HH:mm";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String agent = request.getHeader("user-agent");
		StringBuffer titleSingle = new StringBuffer("");
		titleSingle.append(title);
		String fileName = FileUtils.getEncodingFileName(titleSingle.toString() + ".xls", agent);
		StringBuffer sb = new StringBuffer();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		new ExportExcelForTable<T>().exportExcel(titleSingle.toString(), headers, fieldNames, list, response.getOutputStream(), pattern);
	}

	@SuppressWarnings("unchecked")
	private <T> void getExcelData(String action, String dataJson, Map<Object, String> map, List<T> list)
	{
		AttendanceQuery query = JsonUtils.fromJSON(dataJson, AttendanceQuery.class);
		switch (action)
		{
		case "stage":
			map.put("headerList", "学段,应上课人数,应上课人次,实上课人次,到课率");
			map.put("fieldList", "schoolStageName,mustClassNum,mustClassTimes,actualClassTimes,attendanceRate");
			map.put("title", "考勤统计");
			list.addAll((Collection<? extends T>) attendanceService.getList(query, null));
			break;
		case "classStudent":
			map.put("headerList", "班级,应上课人数,应上课人次,实上课人次,到课率");
			map.put("fieldList", "className,mustClassNum,mustClassTimes,actualClassTimes,attendanceRate");
			map.put("title", "学生考勤统计(班级)");
			list.addAll((Collection<? extends T>) attendanceService.getStudentAttendanceClassList(query, null));
			break;
		case "classTeacher":
			map.put("headerList", "班级,班主任,计划旁听课堂数,实际旁听课堂数,旁听率,旁听总时长,旁听次数");
			map.put("fieldList", "className,headTeacherName,mustAttendLessonNum,actualLessonTimes,attendanceRate,attendHours,attendTimes");
			map.put("title", "老师考勤统计(班级)");
			list.addAll((Collection<? extends T>) attendanceService.getTeacherAttendanceClassList(query, null));
			break;
		case "lessonStudent":
			map.put("headerList", "计划上课时间,课堂名称,应上课人数,实上课人数,到课率");
			map.put("fieldList", "lessonTimeStr,lessonName,mustClassTimes,actualClassTimes,attendanceRate");
			map.put("title", "课堂考勤统计明细");
			list.addAll((Collection<? extends T>) attendanceService.getStudentAttendanceLessonList(query, null));
			break;
		case "lessonTeacher":
			map.put("headerList", "计划上课时间,计划上课时长（min）,课堂名称,实际旁听时间,旁听时长（min）");
			map.put("fieldList", "lessonTimeStr,lessonMinutes,lessonName,attendTime,attendHours");
			map.put("title", "课堂考勤统计明细");
			list.addAll((Collection<? extends T>) attendanceService.getTeacherAttendanceLessonList(query, null));
			break;
		default:
			break;
		}
	}
}
