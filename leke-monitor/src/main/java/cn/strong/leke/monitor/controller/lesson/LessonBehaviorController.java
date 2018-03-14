package cn.strong.leke.monitor.controller.lesson;

import java.util.ArrayList;
import java.util.List;

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
import cn.strong.leke.monitor.core.model.lesson.SchoolLessonDto;
import cn.strong.leke.monitor.core.model.lesson.TeacherLessonDto;
import cn.strong.leke.monitor.core.service.ILessonBehaviorService;
import cn.strong.leke.monitor.core.utils.ExportExcelForTable;
import cn.strong.leke.monitor.util.FileUtils;
/**
 * @Description: 课堂行为功能统计控制器层接口
 * @author hedan
 *
 */
@Controller
@RequestMapping("/auth/common/lessonhavior/*")
public class LessonBehaviorController {
	
	@Resource
	private ILessonBehaviorService lessonBehaviorService;
		

	@RequestMapping("wholeNetworkStatisView")
	public void wholeNetworkStatisView(){
		
	}
	@RequestMapping("getWholeNetworkData")
	@ResponseBody
	public JsonResult getWholeNetworkData(SchoolLessonDto query){
		List<TeacherLessonDto> list = lessonBehaviorService.queryWholeNetwork(query);
		JsonResult jsonResult = new JsonResult();
		jsonResult.addDatas("record", list);
		return jsonResult;
	}
	
	@RequestMapping("schoolStatisView")
	public void schoolStatisView(SchoolLessonDto query,Model model){
		model.addAttribute("query", query);
	}
	
	@RequestMapping("getSchoolHaviorData")
	@ResponseBody
	public JsonResult getSchoolHaviorData(TeacherLessonDto query, Page page){
		List<TeacherLessonDto> schoolLessonStatistics = lessonBehaviorService.schoolLessonStatistics(query,page);
		page.setTotalSize(schoolLessonStatistics.size());
		List<TeacherLessonDto> list = new ArrayList<>();
		for (int i = 0; i < page.getPageSize() && i< schoolLessonStatistics.size() - page.getOffset(); i++) {
			list.add(schoolLessonStatistics.get(page.getOffset() + i));
		}
		JsonResult jsonResult = new JsonResult();
		jsonResult.addDatas("record", list);
		jsonResult.addDatas("page", page);
		return jsonResult;
	}
	
	@RequestMapping("schoolLessondetail")
	public void schoolLessondetail(TeacherLessonDto query,Model model){
		model.addAttribute("query", query);
	}
	
	
	@RequestMapping("getSchoolLessondetailData")
	@ResponseBody
	public JsonResult getSchoolLessondetailData(TeacherLessonDto query){
		List<TeacherLessonDto> list = lessonBehaviorService.schoolLessondetail(query);
		JsonResult jsonResult = new JsonResult();
		jsonResult.addDatas("record", list);
		return jsonResult;
	}
	
	@RequestMapping("teacherLessonStatistics")
	public void teacherLessonStatistics(TeacherLessonDto query,Model model){
		List<TeacherLessonDto> gradeNames = lessonBehaviorService.getGradeNameBySchool(query);
		List<TeacherLessonDto> subjectNames = lessonBehaviorService.getSubjectNameBySchool(query);
		model.addAttribute("gradeNames", gradeNames);
		model.addAttribute("subjectNames", subjectNames);
		model.addAttribute("query", query);
	}
	
	@RequestMapping("getTeacherLessonData")
	@ResponseBody
	public JsonResult getTeacherLessonData(TeacherLessonDto query,Page page){
		List<TeacherLessonDto> teacherLessonDtos = lessonBehaviorService.teacherLessonStatistics(query, page);
		page.setTotalSize(teacherLessonDtos.size());
		List<TeacherLessonDto> list = new ArrayList<>();
		for (int i = 0; i < page.getPageSize() && i < teacherLessonDtos.size() - page.getOffset(); i++) {
			list.add(teacherLessonDtos.get(page.getOffset() + i));
		}
		JsonResult jsonResult = new JsonResult();
		jsonResult.addDatas("record", list);
		jsonResult.addDatas("page", page);
		return jsonResult;
	}
	
	@RequestMapping("teacherLessonDetail")
	public void teacherLessonDetail(TeacherLessonDto query,Model model){
		model.addAttribute("query", query);
	}
	
	@RequestMapping("getTeacherLessonDetail")
	@ResponseBody
	public JsonResult getTeacherLessonDetail(TeacherLessonDto query){
		List<TeacherLessonDto> teacherLessonDetail = lessonBehaviorService.teacherLessonDetail(query);
		JsonResult jsonResult = new JsonResult();
		jsonResult.addDatas("record", teacherLessonDetail);
		return jsonResult;
	}
	
	@RequestMapping("exportWholeNetWorkData")
	@ResponseBody
	public void exportWholeNetWorkData(String dataJson,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		SchoolLessonDto query = JsonUtils.fromJSON(dataJson,
				SchoolLessonDto.class);
		List<TeacherLessonDto> list = lessonBehaviorService.queryWholeNetwork(query);
		String[] headers = { "事件", "上课课堂数", "发起次数", "平均发起次数/课", "发起率"};
		String[] fieldNames = { "type", "lessonNum", "sponsorNum",
				"average", "sponsorRate"};
		exportExcelData(request, response, list, headers, fieldNames);
	}
	
	@RequestMapping("exportSchoolStatisViewData")
	@ResponseBody
	public void exportSchoolStatisViewData(String dataJson,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		TeacherLessonDto query = JsonUtils.fromJSON(dataJson,
				TeacherLessonDto.class);
		List<TeacherLessonDto> list = lessonBehaviorService.schoolLessonStatistics(query,null);
		String[] headers = { "学校", "学段","上课课堂数", "发起次数", "平均发起次数/课", "发起率"};
		String[] fieldNames = { "schoolName","schoolStageName", "lessonNum", "sponsorNum",
				"average", "sponsorRate"};
		exportExcelData(request, response, list, headers, fieldNames);
	}
	
	@RequestMapping("exportSchoolLessondetail")
	@ResponseBody
	public void exportSchoolLessondetail(String dataJson,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		TeacherLessonDto query = JsonUtils.fromJSON(dataJson,
				TeacherLessonDto.class);
		List<TeacherLessonDto> list = lessonBehaviorService.schoolLessondetail(query);
		String[] headers = { "事件", "上课课堂数", "发起次数", "平均发起次数/课", "发起率"};
		String[] fieldNames = { "type", "lessonNum", "sponsorNum",
				"average", "sponsorRate"};
		exportExcelData(request, response, list, headers, fieldNames);
	}
	
	@RequestMapping("exportTeacherLessonStatistics")
	@ResponseBody
	public void exportTeacherLessonStatistics(String dataJson,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		TeacherLessonDto query = JsonUtils.fromJSON(dataJson,
				TeacherLessonDto.class);
		List<TeacherLessonDto> list = lessonBehaviorService.teacherLessonStatistics(query,null);
		String[] headers = { "乐号", "姓名","年级","学科","上课课堂数", "发起次数", "平均发起次数/课", "发起率"};
		String[] fieldNames = { "loginName","teacherName","gradeName","subjectName", 
				"lessonNum", "sponsorNum","average", "sponsorRate"};
		exportExcelData(request, response, list, headers, fieldNames);
	}
	
	@RequestMapping("exportTeacherLessondetail")
	@ResponseBody
	public void exportTeacherLessondetail(String dataJson,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		TeacherLessonDto query = JsonUtils.fromJSON(dataJson,
				TeacherLessonDto.class);
		List<TeacherLessonDto> list = lessonBehaviorService.teacherLessonDetail(query);
		String[] headers = { "事件", "上课课堂数", "发起次数", "平均发起次数/课", "发起率"};
		String[] fieldNames = { "type", "lessonNum", "sponsorNum","average", "sponsorRate"};
		exportExcelData(request, response, list, headers, fieldNames);
	}
	
	
	/**
	 * 导出数据到excel中
	 * @param request
	 * @param response
	 * @param list
	 * @param headers
	 * @param fieldNames
	 * @throws Exception
	 */
	public void exportExcelData(HttpServletRequest request, HttpServletResponse response,
			List<TeacherLessonDto> list,String[] headers,String[] fieldNames)
			throws Exception {
		String pattern = "yyyy-MM-dd HH:mm";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String agent = request.getHeader("user-agent");
		StringBuffer titleSingle = new StringBuffer("");
		titleSingle.append("课堂功能统计");
		String fileName = FileUtils.getEncodingFileName(titleSingle.toString()
				+ ".xls", agent);
		StringBuffer sb = new StringBuffer();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		new ExportExcelForTable<TeacherLessonDto>().exportExcel(
				titleSingle.toString(), headers, fieldNames, list,
				response.getOutputStream(), pattern);
	}
	
	
}
