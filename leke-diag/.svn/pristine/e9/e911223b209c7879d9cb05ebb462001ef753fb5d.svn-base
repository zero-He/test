package cn.strong.leke.diag.controller.teachingMonitor;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.nosql.CacheUtils;
import cn.strong.leke.diag.model.teachingMonitor.HomePageDynamic;
import cn.strong.leke.diag.model.teachingMonitor.HomePageLessonSchedule;
import cn.strong.leke.diag.model.teachingMonitor.HomePageSchedule;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.diag.service.teachingMonitor.HomePageService;
import cn.strong.leke.diag.service.teachingMonitor.LessonBeikeInfoService;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.user.Role;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;

@Controller
@RequestMapping("/auth/provost/teachingMonitor/homepage/")
public class HomePageController {
	
	protected static final Logger logger = LoggerFactory.getLogger(HomePageController.class);
	
	@Resource
	private CommService commService;
	
	@Resource
	private HomePageService homePageService;
	
	@Resource
	private IUserRemoteService userRemoteService;
	
	@Resource
	private LessonBeikeInfoService lessonBeikeInfoService;
	
	@RequestMapping("toShowHomePage")
	public String toShowHomePage(){
		return "/auth/teachingMonitor/homePage";
	}
	
	/**
	 * 处理首页动态数据
	 * @param vo
	 * @return
	 */
	public HomePageDynamic handleHomePageDynamic(RequestVo vo){
		commService.setCommPropToRequestVo(vo);
		HomePageDynamic dynamic = homePageService.findTeacherLessonAndBeike(vo);
		
		HomePageDynamic teacherDoubt = homePageService.findTeacherDoubt(vo);
		HomePageDynamic studentDoubt = homePageService.findStudentDoubt(vo);
		HomePageDynamic studentAttend = homePageService.findStudentAttendent(vo);
		
		dynamic.setResolveDoubt(teacherDoubt.getResolveDoubt());
		dynamic.setNotResolveDoubt(teacherDoubt.getNotResolveDoubt());
		dynamic.setDoubt(studentDoubt.getDoubt());
		dynamic.setDoubtStu(studentDoubt.getDoubtStu());
		dynamic.setEarlyStu(studentAttend.getEarlyStu());
		dynamic.setLateStu(studentAttend.getLateStu());
		dynamic.setNotAttendStu(studentAttend.getNotAttendStu());
		dynamic.setAssignHW(homePageService.findTeacherAssignHW(vo).getAssignHW());
		dynamic.setAwaitingCorrectHW(homePageService.findTeacherAwaitingCorrectHW(vo).getAwaitingCorrectHW());
		dynamic.setCorrectHW(homePageService.findTeacherCorrectHW(vo).getCorrectHW());
		
		List<Long> studentIds = userRemoteService.findUserIdsBySchoolIdAndRoleId(vo.getSchoolId(), RoleCst.STUDENT);
		Date tsDate = DateUtils.addMinutes(new Date(), -15);
		if(studentIds.isEmpty()){
			dynamic.setOnlineStudent(0L);
			dynamic.setOnlineParent(0L);
			dynamic.setTotalStudent(0L);
			dynamic.setTotalParent(0L);
		}else{
			List<Long> parentIds = userRemoteService.findParentsByStuIds(studentIds);
			dynamic.setOnlineStudent(homePageService.findOnlineUserCount(vo.getSchoolId(), studentIds, tsDate, RoleCst.STUDENT));
			dynamic.setOnlineParent(homePageService.findOnlineUserCount(0L, parentIds, tsDate, RoleCst.PARENT));
			dynamic.setTotalStudent(Long.valueOf(studentIds.size()));
			dynamic.setTotalParent(Long.valueOf(parentIds.size()));
		}
		List<Long> teacherIds = userRemoteService.findUserIdsBySchoolIdAndRoleId(vo.getSchoolId(), RoleCst.TEACHER);
		dynamic.setOnlineTeacher(homePageService.findOnlineUserCount(vo.getSchoolId(), teacherIds, tsDate, RoleCst.TEACHER));
		dynamic.setTotalTeacher(Long.valueOf(teacherIds.size()));
		
		return dynamic;
	}
	
	/**
	 * 查询首页动态数据
	 * @param vo
	 * @return
	 */
	@RequestMapping("findHomePageDynamic")
	@ResponseBody
	public JsonResult findHomePageDynamic(RequestVo vo){
		JsonResult json = new JsonResult();
		HomePageDynamic dynamic = handleHomePageDynamic(vo);
		json.addDatas("dynamic", dynamic);
		return json;
	}
	
	/**
	 * 处理首页日课表数据
	 * @param vo
	 * @return
	 */
	public List<HomePageLessonSchedule> handleHomePageSchedule(RequestVo vo){
		commService.setCommPropToRequestVo(vo);
		List<HomePageLessonSchedule> scheduleList = homePageService.findHomePageSchedule(vo);
		
		int idx = 0;
		for(HomePageLessonSchedule lesson : scheduleList){
			++idx;
			lesson.setLessonIndex("第"+idx+"节");
			lesson.getLessonList().stream().forEach(v->{
				if(lessonBeikeInfoService.hasLessonPlanForLesson(v.getCourseSingleId())){
					v.setLessonPlan(1);
				}else{
					v.setLessonPlan(0);
				}
			});
		}
		
		return scheduleList;
	}
	
	/**
	 * 查询首页日课表
	 * @param vo
	 * @return
	 */
	@RequestMapping("findHomePageSchedule")
	@ResponseBody
	public JsonResult findHomePageSchedule(RequestVo vo){
		JsonResult json = new JsonResult();
		List<HomePageLessonSchedule> scheduleList = handleHomePageSchedule(vo);
		json.addDatas("scheduleList", scheduleList);
		return json;
	}

}
