package cn.strong.leke.diag.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.cas.utils.UrlUtils;
import cn.strong.leke.diag.manage.SchoolStatsService;
import cn.strong.leke.diag.model.GradeStatsDto;
import cn.strong.leke.diag.model.SubjStatsDto;
import cn.strong.leke.diag.util.DiagHelp;
import cn.strong.leke.remote.service.homework.IHomeworkRemoteService;

@Controller
@RequestMapping("/auth/m/*")
public class MHomeworkController {
	
	@Resource
	private SchoolStatsService schoolStatsService;
	@Resource
	private IHomeworkRemoteService homeworkRemoteService;
	/**
	 * 教务/校长：作业管家
	 */
	@RequestMapping("provost/homework/manager")
	public String provostManager(Model model){
		Date startDate = DiagHelp.getSemesterStarDate();
		model.addAttribute("startDate", DateUtils.formatDate(startDate));
		model.addAttribute("finishDate", DateUtils.formatDate(new Date()));
		List<GradeStatsDto> list = this.schoolStatsService.findSchoolDiligent(UserContext.user.getCurrentSchoolId());
		if(CollectionUtils.isEmpty(list)){
			model.addAttribute("message", "暂无年级");
			return "/tips/m-nodata";
		}
		model.addAttribute("dataList", list);
		return "/auth/m/homework/manager/provost";
	}
	
	/**
	 * 学生：作业管家情况
	 */
	@RequestMapping("student/homework/subjectManager")
	public String studentSubjectManager(Long subjectId,Model model,HttpServletRequest request){
		try{
			Long studentId = UserContext.user.getUserId();
			List<SubjStatsDto>  dataList = schoolStatsService.findStudentSubjDillgent(studentId);
			SubjStatsDto subjStats = dataList.stream().filter(v->{return v.getSubjectId().equals(subjectId);}).findFirst().get();
			Date startDate = DiagHelp.getSemesterStarDate();
			Map<String, Integer> map = homeworkRemoteService.findStuSubjResByStudentId(studentId, subjectId);
			model.addAttribute("startDate", DateUtils.formatDate(startDate));
			model.addAttribute("finishDate", DateUtils.formatDate(new Date()));
			model.addAttribute("subjStats", subjStats);
			model.addAttribute("subjectId", subjectId);
			model.addAllAttributes(map);
		}catch(Exception e){
			return "redirect:"+UrlUtils.getServerName(request, "homeworkServerName")+"/auth/m/student/homework/manager/detail.htm?subjectId="+subjectId;
		}
		return "/auth/m/homework/manager/student";
	}
	
	/**
	 * 家长：作业管家情况
	 */
	@RequestMapping("parent/homework/subjectManager")
	public String parentSubjectManager(Long subjectId,Long studentId,Model model,HttpServletRequest request){
		try{
			List<SubjStatsDto>  dataList = schoolStatsService.findStudentSubjDillgent(studentId);
			SubjStatsDto subjStats = dataList.stream().filter(v->{return v.getSubjectId().equals(subjectId);}).findFirst().get();
			Date startDate = DiagHelp.getSemesterStarDate();
			Map<String, Integer> map = homeworkRemoteService.findStuSubjResByStudentId(studentId, subjectId);
			model.addAttribute("startDate", DateUtils.formatDate(startDate));
			model.addAttribute("finishDate", DateUtils.formatDate(new Date()));
			model.addAttribute("subjStats", subjStats);
			model.addAttribute("subjectId", subjectId);
			model.addAttribute("studentId", studentId);
			model.addAllAttributes(map);
		}catch(Exception e){
			return "redirect:"+UrlUtils.getServerName(request, "homeworkServerName")+"/auth/m/parent/homework/manager/detail.htm?subjectId="+subjectId+"&studentId="+studentId;
		}
		return "/auth/m/homework/manager/parent";
	}
	
}
