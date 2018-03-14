package cn.strong.leke.diag.controller.studentMonitor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.NumberUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.core.nosql.CacheUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.diag.model.studentMonitor.StudentAttend;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.service.studentMonitor.StudentAttendAnalyseService;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;

@Controller
@RequestMapping("/auth/provost/studentMonitor/studentAttend/")
public class StudentAttendAnalyseController {
	
	protected static final Logger logger = LoggerFactory.getLogger(StudentAttendAnalyseController.class);
	
	@Resource
	private CommService commService;
	
	@Resource
	private StudentAttendAnalyseService studentAttendAnalyseService;
	
	@RequestMapping("toShowStudentAttend")
	public String toShowStudentAttend(RequestVo vo){
		String queryType = (StringUtils.hasText(vo.getQueryType()) ? vo.getQueryType() : StudentAttend.LESSON);
		String uri = null;
		if(StudentAttend.LESSON.equalsIgnoreCase(queryType))
			uri = "/auth/studentMonitor/studentAttendLesson";
		if(StudentAttend.STUDENT.equalsIgnoreCase(queryType))
			uri = "/auth/studentMonitor/studentAttendStudent";
		if(StudentAttend.TEACHER.equalsIgnoreCase(queryType))
			uri = "/auth/studentMonitor/studentAttendTeacher";
		if(StudentAttend.CLASS.equalsIgnoreCase(queryType))
			uri = "/auth/studentMonitor/studentAttendClass";
		if(StudentAttend.SUBJECT.equalsIgnoreCase(queryType))
			uri = "/auth/studentMonitor/studentAttendSubject";
		if(StudentAttend.DAY.equalsIgnoreCase(queryType))
			uri = "/auth/studentMonitor/studentAttendDay";
		return uri;
	}
	
	@RequestMapping("toShowStudentAttendDtlByLesson")
	public String toShowStudentAttendDtlByLesson(RequestVo vo, String duration, Model model){
		model.addAttribute("startTime", vo.getStartDate());
		model.addAttribute("teacherName", vo.getTeacherName());
		model.addAttribute("subjectName", vo.getSubjectName()); 
		model.addAttribute("className", vo.getClassName()); 
		model.addAttribute("csAttenId", vo.getCsAttenId()); 
		model.addAttribute("duration", duration); 
		return "/auth/studentMonitor/studentAttendLessonDtl";
	}
	
	@RequestMapping("toShowStudentAttendDtlByClass")
	public String toShowStudentAttendDtlByClass(RequestVo vo, String duration, Model model){
		model.addAttribute("teacherName", vo.getTeacherName());
		model.addAttribute("classId", vo.getClassId()); 
		model.addAttribute("className", vo.getClassName()); 
		model.addAttribute("startDate", vo.getStartDate()); 
		model.addAttribute("endDate", vo.getEndDate()); 
		return "/auth/studentMonitor/studentAttendClassDtl";
	}
	
	/**
	 * 查询学生考勤明细
	 * @param vo
	 * @param page
	 * @return
	 */
	@RequestMapping("findStudentAttendDtl")
	@ResponseBody
	public JsonResult findStudentAttendDtl(RequestVo vo, Page page){
		JsonResult json = new JsonResult();
		commService.setCommPropToRequestVo(vo);
		
		List<StudentAttend> attendList = null;
		String queryType = (StringUtils.hasText(vo.getQueryType()) ? vo.getQueryType() : StudentAttend.LESSON);
		if(StudentAttend.LESSON_DTL.equalsIgnoreCase(queryType))
			attendList = studentAttendAnalyseService.findStudentAttendDtlByLesson(vo, page);
		if(StudentAttend.CLASS_DTL.equalsIgnoreCase(queryType))
			attendList = studentAttendAnalyseService.findStudentAttendDtlByClass(vo, page);
		
		int index = 0;
		for(StudentAttend attend : attendList){
			++index;
			attend.setIndex((page.getCurPage() - 1) * page.getPageSize() + index);
		}
		
		page.setDataList(attendList);
		json.addDatas("page", page);
		return json;
	}
	
	/**
	 * 处理学生考勤
	 * @param vo
	 * @param page
	 * @param sessionId
	 * @return
	 */
	public List<StudentAttend> handleStudentAttend(RequestVo vo, Page page, String sessionId){
		commService.setCommPropToRequestVo(vo);
		String queryType = (StringUtils.hasText(vo.getQueryType()) ? vo.getQueryType() : StudentAttend.LESSON);
		List<StudentAttend> attendList = null;
		
		try {
			if(StringUtils.hasText(vo.getStudentName())){
				vo.setStudentName(URLDecoder.decode(vo.getStudentName(), "utf-8"));
			}
			if(StringUtils.hasText(vo.getTeacherName())){
				vo.setTeacherName(URLDecoder.decode(vo.getTeacherName(), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("老师或学生姓名转码失败", e.getMessage());
		}
			
		
		if(null == page){// 不分页，供导出用
			
			if(StudentAttend.LESSON.equalsIgnoreCase(queryType))
				attendList = studentAttendAnalyseService.findStudentAttendByLesson(vo, null);
			if(StudentAttend.STUDENT.equalsIgnoreCase(queryType))
				attendList = studentAttendAnalyseService.findStudentAttendByStudent(vo, null);
			if(StudentAttend.TEACHER.equalsIgnoreCase(queryType))
				attendList = studentAttendAnalyseService.findStudentAttendByTeacher(vo, null);
			if(StudentAttend.CLASS.equalsIgnoreCase(queryType))
				attendList = studentAttendAnalyseService.findStudentAttendByClass(vo, null);
			if(StudentAttend.SUBJECT.equalsIgnoreCase(queryType))
				attendList = studentAttendAnalyseService.findStudentAttendBySubject(vo, null);
			if(StudentAttend.DAY.equalsIgnoreCase(queryType))
				attendList = studentAttendAnalyseService.findStudentAttendByDay(vo, null);
			if(StudentAttend.LESSON_DTL.equalsIgnoreCase(queryType))
				attendList = studentAttendAnalyseService.findStudentAttendDtlByLesson(vo, null);
			if(StudentAttend.CLASS_DTL.equalsIgnoreCase(queryType))
				attendList = studentAttendAnalyseService.findStudentAttendDtlByClass(vo, null);
			
		}else{// 分页查询，且将合计放在每页的第一条
			
			String attendKey = sessionId + "_" + UserContext.user.get().getId() + "_" +queryType;
			StudentAttend studentAttend = null;
			if(page.getCurPage() == 1){
				if(StudentAttend.LESSON.equalsIgnoreCase(queryType))
					studentAttend = studentAttendAnalyseService.findStudentAttendAggregateByLesson(vo);
				if(StudentAttend.STUDENT.equalsIgnoreCase(queryType))
					studentAttend = studentAttendAnalyseService.findStudentAttendAggregateByStudent(vo);
				if(StudentAttend.TEACHER.equalsIgnoreCase(queryType))
					studentAttend = studentAttendAnalyseService.findStudentAttendAggregateByTeacher(vo);
				if(StudentAttend.CLASS.equalsIgnoreCase(queryType))
					studentAttend = studentAttendAnalyseService.findStudentAttendAggregateByClass(vo);
				if(StudentAttend.SUBJECT.equalsIgnoreCase(queryType))
					studentAttend = studentAttendAnalyseService.findStudentAttendAggregateBySubject(vo);
				
				if(null != studentAttend){
					CacheUtils.set(attendKey, RequestVo.EXPIRED_TIME, studentAttend);
				}else{
					CacheUtils.delete(attendKey);
				}
			}
			
			if(StudentAttend.LESSON.equalsIgnoreCase(queryType))
				attendList = studentAttendAnalyseService.findStudentAttendByLesson(vo, page);
			if(StudentAttend.STUDENT.equalsIgnoreCase(queryType))
				attendList = studentAttendAnalyseService.findStudentAttendByStudent(vo, page);
			if(StudentAttend.TEACHER.equalsIgnoreCase(queryType))
				attendList = studentAttendAnalyseService.findStudentAttendByTeacher(vo, page);
			if(StudentAttend.CLASS.equalsIgnoreCase(queryType))
				attendList = studentAttendAnalyseService.findStudentAttendByClass(vo, page);
			if(StudentAttend.SUBJECT.equalsIgnoreCase(queryType))
				attendList = studentAttendAnalyseService.findStudentAttendBySubject(vo, page);
			if(StudentAttend.DAY.equalsIgnoreCase(queryType))
				attendList = studentAttendAnalyseService.findStudentAttendByDay(vo, page);
			
			if(CacheUtils.exists(attendKey)){
				attendList.add(0, CacheUtils.get(attendKey));
			}
		}
		
		attendList.stream().forEach(v->{
			if(null != v.getAttendCountRate())
				v.setAttendCountRate(NumberUtils.formatScore(Double.valueOf(v.getAttendCountRate())));
			if(null != v.getNormalCountRate())
				v.setNormalCountRate(NumberUtils.formatScore(Double.valueOf(v.getNormalCountRate())));
			if(null != v.getLateCountRate())
				v.setLateCountRate(NumberUtils.formatScore(Double.valueOf(v.getLateCountRate())));
			if(null != v.getEarlyCountRate())
				v.setEarlyCountRate(NumberUtils.formatScore(Double.valueOf(v.getEarlyCountRate())));
			if(null != v.getLateAndEarlyCountRate())
				v.setLateAndEarlyCountRate(NumberUtils.formatScore(Double.valueOf(v.getLateAndEarlyCountRate())));
			if(null != v.getAbsentCountRate())
				v.setAbsentCountRate(NumberUtils.formatScore(Double.valueOf(v.getAbsentCountRate())));
		});
		
		return attendList;
	}
	
	/**
	 * 查询学生考勤
	 * @param vo
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping("findStudentAttend")
	@ResponseBody
	public JsonResult findStudentAttend(RequestVo vo, Page page, HttpSession session){
		JsonResult json = new JsonResult();
		List<StudentAttend> attendList = handleStudentAttend(vo, page, session.getId());
		int index = StudentAttend.DAY.equalsIgnoreCase(vo.getQueryType())? 0 : -1;
		for(StudentAttend attend : attendList){
			++index;
			attend.setIndex((page.getCurPage() - 1) * page.getPageSize() + index);
		}
		page.setDataList(attendList);
		json.addDatas("page", page);
		return json;
	}
	
	/**
	 * 导出学生考勤
	 * @param vo
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("exportStudentAttendData")
	public void exportStudentAttendByLessonData(RequestVo vo, HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		
		String agent = request.getHeader("user-agent");
		String[] titleArr = commService.handleExcelTitlePro(vo);
		String title = null;
		String[] headers = null;
		String[] fieldNames = null;
		List<StudentAttend> excelList = null;
		String queryType = (StringUtils.hasText(vo.getQueryType()) ? vo.getQueryType() : StudentAttend.LESSON);
		
		if(StudentAttend.LESSON.equalsIgnoreCase(queryType)){
			title = titleArr[0] + "学生考勤-课堂" + titleArr[1];
			headers = new String[]{"上课时间", "课堂时长", "班级", "学科", "上课老师", "实到人数", "应到人数", "到课率", "全勤", "迟到", "早退", "迟到且早退", "缺勤"};
			fieldNames = new String[]{"startTime", "duration", "className", "subjectName", "teacherName", "realCount", "totalCount", "attendCountRate", "normalCount", "lateCount", "earlyCount", "lateAndEarlyCount", "absentCount"};
		}else if(StudentAttend.STUDENT.equalsIgnoreCase(queryType)){
			title = titleArr[0] + "学生考勤-学生" + titleArr[1];
			headers = new String[]{"学生姓名", "班级", "到课率", "实到课堂", "应到课堂", "全勤", "全勤比率", "迟到", "迟到比率", "早退", "早退比率", "迟到且早退", "迟到且早退比率", "缺勤", "缺勤比率"};
			fieldNames = new String[]{"studentName", "className", "attendCountRate", "attendCount", "lessonCount", "normalCount", "normalCountRate", "lateCount", "lateCountRate", "earlyCount", "earlyCountRate", "lateAndEarlyCount", "lateAndEarlyCountRate", "absentCount", "absentCountRate"};
		}else if(StudentAttend.TEACHER.equalsIgnoreCase(queryType)){
			title = titleArr[0] + "学生考勤-老师" + titleArr[1];
			headers = new String[]{"上课老师", "任教学科", "上课堂数", "到课率", "实到人次", "应到人次", "全勤人次", "全勤比率", "迟到人次", "迟到比率", "早退人次", "早退比率", "迟到且早退人次", "迟到且早退比率", "缺勤人次", "缺勤比率"};
			fieldNames = new String[]{"teacherName", "subjectName", "lessonCount", "attendCountRate", "realCount", "totalCount", "normalCount", "normalCountRate", "lateCount", "lateCountRate", "earlyCount", "earlyCountRate", "lateAndEarlyCount", "lateAndEarlyCountRate", "absentCount", "absentCountRate"};
		}else if(StudentAttend.CLASS.equalsIgnoreCase(queryType)){
			title = titleArr[0] + "学生考勤-班级" + titleArr[1];
			headers = new String[]{"班级", "班主任", "班级人数", "上课堂数", "到课率", "实到人次", "应到人次", "全勤人次", "全勤比率", "迟到人次", "迟到比率", "早退人次", "早退比率", "迟到且早退人次", "迟到且早退比率", "缺勤人次", "缺勤比率"};
			fieldNames = new String[]{"className", "teacherName", "studentCount", "lessonCount", "attendCountRate", "realCount", "totalCount", "normalCount", "normalCountRate", "lateCount", "lateCountRate", "earlyCount", "earlyCountRate", "lateAndEarlyCount", "lateAndEarlyCountRate", "absentCount", "absentCountRate"};
		}else if(StudentAttend.SUBJECT.equalsIgnoreCase(queryType)){
			title = titleArr[0] + "学生考勤-学科" + titleArr[1];
			headers = new String[]{"学科", "老师人数", "上课堂数", "到课率", "实到人次", "应到人次", "全勤人次", "全勤比率", "迟到人次", "迟到比率", "早退人次", "早退比率", "迟到且早退人次", "迟到且早退比率", "缺勤人次", "缺勤比率"};
			fieldNames = new String[]{"subjectName", "teacherCount", "lessonCount", "attendCountRate", "realCount", "totalCount", "normalCount", "normalCountRate", "lateCount", "lateCountRate", "earlyCount", "earlyCountRate", "lateAndEarlyCount", "lateAndEarlyCountRate", "absentCount", "absentCountRate"};
		}else if(StudentAttend.DAY.equalsIgnoreCase(queryType)){
			title = titleArr[0] + "学生考勤-当日缺勤" + titleArr[1];
			headers = new String[]{"班级", "学生姓名", "当日缺勤次数"};
			fieldNames = new String[]{"className", "studentName", "absentCount"};
		}else if(StudentAttend.CLASS_DTL.equalsIgnoreCase(queryType)){
			title = titleArr[0] + "学生考勤-班级明细" + titleArr[1];
			headers = new String[]{"学生姓名", "到课率", "实上课堂", "应上课堂", "全勤", "全勤比率", "迟到", "迟到比率", "早退", "早退比率", "迟到且早退", "迟到且早退比率", "缺勤", "缺勤比率"};
			fieldNames = new String[]{"studentName", "attendCountRate", "attendCount", "lessonCount", "normalCount", "normalCountRate", "lateCount", "lateCountRate", "earlyCount", "earlyCountRate", "lateAndEarlyCount", "lateAndEarlyCountRate", "absentCount", "absentCountRate"};
		}else if(StudentAttend.LESSON_DTL.equalsIgnoreCase(queryType)){
			title = titleArr[0] + "学生考勤-课堂明细" + titleArr[1];
			headers = new String[]{"学生姓名", "班级", "学校", "首次进入—最后退出时间", "在线时长（分钟）", "考勤状态", "实点到次数", "应点到次数"};
			fieldNames = new String[]{"studentName", "className", "schoolName", "startTime", "duration", "attendState", "calledNum", "totalCalled"};
		}
		
		excelList = handleStudentAttend(vo, null, null);
		StringBuilder sb = new StringBuilder();
		String fileName = FileUtils.getEncodingFileName(title + ".xls", agent);
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		
		new ExportExcelForTable<StudentAttend>().exportExcel(title, headers, fieldNames, excelList,
				response.getOutputStream(),null);
		
	}

}
