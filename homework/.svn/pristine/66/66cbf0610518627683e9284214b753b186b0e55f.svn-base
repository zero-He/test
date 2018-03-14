package cn.strong.leke.homework.controller;

import cn.strong.leke.beike.model.MicrocourseDTO;
import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.beike.MicrocourseContext;
import cn.strong.leke.context.question.KnowledgeTreeContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.manage.FileDescService;
import cn.strong.leke.homework.manage.HolidayHwMicroService;
import cn.strong.leke.homework.model.VacationYearBean;
import cn.strong.leke.homework.model.mobile.FileDesc;
import cn.strong.leke.homework.model.mongo.HolidayHwMicro;
import cn.strong.leke.homework.model.mongo.HolidayMicrocourse;
import cn.strong.leke.homework.service.HomeworkConfigService;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.lesson.model.TeachSubj;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = { "/auth/*" })
public class HDVacationWorkControler {

	@Resource
	private IKlassRemoteService iKlassRemoteService;
	
	@Resource
	private HomeworkConfigService homeworkConfigService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private FileDescService fileDescService;
	@Resource
	private HolidayHwMicroService holidayHwMicroService;
	
	// 学生：作业和微课列表 寒假
	@RequestMapping(value = "hd/student/vacationWork/StudentWorkList")
	public String StudentWorkList() {
		if(!homeworkConfigService.HasConfig())
			return "/auth/hd/vacationWork/noConfig";
		return "/auth/hd/vacationWork/StuWorkList";
	}

	// 学生：学科-作业列表
	@RequestMapping(value = "hd/student/vacationWork/StuDecList")
	public String StuDecList(String workId,String title,Map<String, Object> map) {
		map.put("workId", workId);
		map.put("title",title);
		map.put("type", 1);
		return "/auth/hd/vacationWork/StuDecList";
	}

	// 学生：微课-作业列表
	@RequestMapping(value = "hd/student/vacationWork/StuWkList")
	public String StuWkList(String workId,String title,Long subjectId,String subjectName,Map<String, Object> map) {
		map.put("workId", workId);
		map.put("title",title);
		map.put("subjectId",subjectId);
		map.put("subjectName",subjectName);
		map.put("type", 2);
		return "/auth/hd/vacationWork/StuWkList";
	}
	
	// 学生：微课学习
		@RequestMapping(value = "hd/student/vacationWork/WkStudy")
		public String WkStudy(Model model,String id,Long microcourseId) throws UnsupportedEncodingException{
			MicrocourseDTO microcourse = MicrocourseContext.getMicrocourse(microcourseId);
			FileDesc fileDesc = this.fileDescService.convertToFileDesc(microcourse);
			Map<String, Object> Csts = Maps.newHashMap();
			HolidayHwMicro holidayHwMicro = this.holidayHwMicroService.getSingleHolidayHomework(id);
			HolidayMicrocourse holidayMicro = holidayHwMicro.getMicrocourses().stream().filter(v->v.getMicroId().equals(microcourseId)).findFirst().get();
			Integer index = 0;
			for (int i = 0; i< holidayHwMicro.getMicrocourses().size(); i++) {
				if (holidayHwMicro.getMicrocourses().get(i).getMicroId().equals(holidayMicro.getMicroId())) {
					index = i;
					break;
				}
			}
			if(index + 1 < holidayHwMicro.getTotal()){
				HolidayMicrocourse nexHolidayMicro = holidayHwMicro.getMicrocourses().get(index + 1);
				Csts.put("nexMicroId", nexHolidayMicro.getMicroId());
			}
			Csts.put("file", fileDesc);
			Csts.put("position", holidayMicro.getPosition() == null ?0:holidayMicro.getPosition());
			Csts.put("isPlayEnd", holidayMicro.getMicroState()==1);
			Csts.put("exerciseId", holidayMicro.getExerciseId());
			Csts.put("subjectId", holidayHwMicro.getSubjectId());
			Csts.put("subjectName", holidayHwMicro.getSubjectName());
			Csts.put("knowledgeId", holidayMicro.getKnowledgId());
			Csts.put("knowledgeName", KnowledgeTreeContext.get(holidayMicro.getKnowledgId()).getKnowledgeName());
			Csts.put("holidayId", id);
			Csts.put("microId", microcourseId);
			model.addAttribute("Csts", JsonUtils.toJSON(Csts));
			return "/auth/hd/vacationWork/WkStudy";
		}
	
	
	

	// 老师：作业和微课列表
	@RequestMapping(value = "hd/teacher/vacationWork/TeacherWorkList")
	public String TchWorkList() {

		if(!homeworkConfigService.HasConfig())
			return "/auth/hd/vacationWork/noConfig";
		return "/auth/hd/vacationWork/TchWorkList";
	}

	// 老师：学科-作业列表
	@RequestMapping(value = "hd/teacher/vacationWork/TchDecList")
	public String TchDecList(String workId,String title, Map<String, Object> map) {
		
		map.put("workId", workId);
		map.put("title",title);
		map.put("type", 1);
		return "/auth/hd/vacationWork/TchDecList";
	}

	// 老师：微课作业列表
	@RequestMapping(value = "hd/teacher/vacationWork/TchWkList")
	public String TchWkList(String workId,String title,Long subjectId,String subjectName, Map<String, Object> map) {
		
		map.put("workId", workId);
		map.put("title",title);
		map.put("subjectId",subjectId);
		map.put("subjectName",subjectName);
		map.put("type", 2);
		return "/auth/hd/vacationWork/TchWkList";
	}
	
	@RequestMapping(value = {"hd/teacher/vacationWork/tchSubjList"}, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult vacationHomeworkList(Long classId,Long subjectId) {
		JsonResult json =new JsonResult();
		User user = UserContext.user.get();
		Long userId = user.getId();
		Long schoolId = user.getCurrentSchool().getId();

		List<TeachSubj> teachSubj = iKlassRemoteService.findTeachSubjByUserId(userId,schoolId);
		teachSubj.sort(new Comparator<TeachSubj>(){
            public int compare(TeachSubj arg0, TeachSubj arg1) {
                return arg0.getSubjectId().compareTo(arg1.getSubjectId());
            }
        });
		
		if(teachSubj==null||teachSubj.size()<1){
			
			throw new ValidateException("请先绑定学科和班级。");
		}
		for(TeachSubj ts : teachSubj){
			List<Clazz> clazzList = ts.getClazzList();
			if(clazzList.size()==0){
				Clazz clazz = new Clazz();
				
				clazz.setClassId(-1l);
				clazz.setClassName("没有绑定学科班级");
				clazzList.add(clazz);
			}
			clazzList.sort(new Comparator<Clazz>(){
	            public int compare(Clazz arg0, Clazz arg1) {
	                return arg0.getClassId().compareTo(arg1.getClassId());
	            }
	        });
		}
		
		json.addDatas("dataList", teachSubj);
		return json;
	}

	/**
	 * 获取寒暑假list
	 * @return
	 */
	@RequestMapping(value = "hd/teacher/vacationWork/queryVacationList", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryVacationList() {
		JsonResult result = new JsonResult();
		int year = DateUtils.getCurrentYear();
		List<VacationYearBean> yearList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			String yearId = (year - i) + "";
			for (int j = 2; j > 0; j--) {
				//1:寒假，2：暑假
				VacationYearBean yearBean = new VacationYearBean();
				if (j == 2) {
					yearBean.setYearId(yearId);
					yearBean.setHoliday(2);
					yearBean.setYearName(yearId + "暑假");
				}
				if (j == 1) {
					yearBean.setYearId(yearId);
					yearBean.setHoliday(1);
					yearBean.setYearName(yearId + "寒假");
				}
				yearList.add(yearBean);
			}
		}
		result.addDatas("yearList", yearList);
		return result;
	}
	

}
