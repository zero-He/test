package cn.strong.leke.homework.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.thread.ThreadLocalHolder;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.user.StudentContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.business.incentive.DynamicHelper;
import cn.strong.leke.core.cas.SessionCst;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.model.Doubt;
import cn.strong.leke.homework.model.DoubtDtl;
import cn.strong.leke.homework.model.Explain;
import cn.strong.leke.homework.model.SubjectQuery;
import cn.strong.leke.homework.model.SubjectTeacher;
import cn.strong.leke.homework.service.DoubtService;
import cn.strong.leke.homework.service.ExplainService;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.base.Subject;
import cn.strong.leke.model.incentive.Award;
import cn.strong.leke.model.incentive.DynamicInfo;
import cn.strong.leke.model.incentive.DynamicTypes;
import cn.strong.leke.model.incentive.IncentiveTypes;
import cn.strong.leke.model.user.User;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.tutor.base.ISubjectRemoteService;

@Controller
@RequestMapping("/auth/m/*")
public class MDoubtController {
	
	protected static final Logger logger = LoggerFactory.getLogger(MDoubtController.class);

	@Resource
	private ISubjectRemoteService subjectRemoteService;
	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private DoubtService doubtServie;
	@Resource
	private ExplainService explainService;
	
	/**
	 * 列表页
	 * @param doubt
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"student/doubt/list","teacher/doubt/list"},method = RequestMethod.GET)
	public String list(Integer resolved,ModelMap model) {
		Long role = UserContext.user.getCurrentRoleId();
		Integer count = null;
		String word = null;
		if(RoleCst.TEACHER.equals(role)){
			resolved = resolved==null?0:resolved;
			word = "回答";
			count = doubtServie.getTeacherResolveDoubt(UserContext.user.getUserId());
		}else{
			resolved = resolved==null?1:resolved;
			word = "追问";
			count = doubtServie.getStudentResolveDoubt(UserContext.user.getUserId());
		}
		model.addAttribute("word", word);
		List<SubjectRemote> subjectList = UserContext.user.findSubjectsOfCurrentSchool();
		model.addAttribute("subjectList", subjectList);
		model.addAttribute("count", count==null?0:count>999?"+999":count);
		model.addAttribute("resolved", resolved);
		model.addAttribute("currentRoleId", role);
		return "/auth/m/doubt/list";
	}
	
	/**
	 * 列表页
	 * @param doubt
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"student/doubt/list_test","teacher/doubt/list_test"},method = RequestMethod.GET)
	public String list_test(Integer resolved,ModelMap model) {
		return "/auth/m/doubt/list_test";
	}
	
	
	/**
	 * 跳转到追问或回答页
	 */
	@RequestMapping(value = { "teacher/doubt/explain", "student/doubt/explain" },method=RequestMethod.GET)
	public String explain(Long doubtId,Integer resolved,ModelMap model) {
		Long role = UserContext.user.getCurrentRoleId();
		if(RoleCst.TEACHER.equals(role)){
			model.addAttribute("word", "回答");
			DoubtDtl doubt = new DoubtDtl();
			doubt.setDoubtId(doubtId);
			doubt = doubtServie.getDetail(doubt);
			model.addAttribute("doubt",doubt);
		}else{
			model.addAttribute("word", "追问");
		}
		model.addAttribute("doubtId", doubtId);
		model.addAttribute("resolved", resolved);
		model.addAttribute("currentRoleId", role);
		return "/auth/m/doubt/explain";
	}
	
	/**
	 * 提问页
	 * @param model
	 * @param resolved
	 * @param backStr 返回路径 不可空
	 * @param questionId 问题Id 可空
	 * @param teacherId 老师Id 可空
	 * @return
	 */
	@RequestMapping("student/doubt/ask")
	public String askDoubt(ModelMap model,Integer resolved,String backStr,Long questionId,Long teacherId) {
		Doubt doubt = new Doubt();
		doubt = doubtServie.getQuestionDtail(doubt, questionId);
		if(doubt.getSchoolStageId()==null){
			Clazz clazz = klassRemoteService.getDeptClazzByStudentId(UserContext.user.getUserId());
			if(clazz!=null){
				doubt.setSchoolStageId(clazz.getSchoolStageId());
			}
		}
		model.addAttribute("doubt", new Doubt());
		model.addAttribute("resolved", resolved==null?0:resolved);
		model.addAttribute("schoolStageId", doubt.getSchoolStageId());
		model.addAttribute("teacherId", teacherId);
		model.addAttribute("doubt", doubt);
		model.addAttribute("backStr", backStr);
		model.addAttribute("currentRoleId", UserContext.user.getCurrentRoleId());
		return "/auth/m/doubt/student/ask";
	}
	
	/**
	 * 学生详细页
	 * @param model
	 * @param doubt
	 * @return
	 */
	@RequestMapping("student/doubt/detail")
	public String getDoubtDetail(ModelMap model, DoubtDtl doubt,Integer resolved,HttpServletRequest request) {
		model.addAllAttributes(doubtServie.getDoubt_Explain_Question(doubt,request));
		model.addAttribute("word", "追问");
		model.addAttribute("resolved", resolved);
		Long role = UserContext.user.getCurrentRoleId();
		model.addAttribute("currentRoleId", role);
		return "/auth/m/doubt/doubtDetail";
	}
	
	/**
	 * 老师详情页
	 */
	@RequestMapping("teacher/doubt/detail")
	public String getDoubtDetailForExplain(ModelMap model, DoubtDtl doubt,Integer resolved,HttpServletRequest request) {
		model.addAllAttributes(doubtServie.getDoubt_Explain_Question(doubt,request));
		model.addAttribute("teacherName", UserContext.user.getUserName());
		model.addAttribute(SessionCst.TICKET, ThreadLocalHolder.getResource(SessionCst.TICKET));
		model.addAttribute("word", "回答");
		model.addAttribute("resolved", resolved);
		model.addAttribute("currentRoleId", UserContext.user.getCurrentRoleId());
		return "/auth/m/doubt/doubtDetail";
	}

	/**
	 * 保存
	 * @param doubt
	 * @param needIncentive
	 * @return
	 */
	@RequestMapping("student/doubt/save")
	@ResponseBody
	public JsonResult saveDoubt(Doubt doubt) {
		JsonResult result = new JsonResult();
		User user = UserContext.user.get();
		doubt.setCreatedBy(user.getId());
		doubt.setSchoolId(user.getCurrentSchool().getId());
		doubt.setSchoolName(user.getCurrentSchool().getName());
		doubt.setUserName(user.getUserName());
		try{
			doubt.setSource(Doubt.SOURCE_MINE);
			doubtServie.saveDoubt(doubt);
			DynamicInfo dynamicInfo = new DynamicInfo();
			dynamicInfo.setUserId(user.getId());
			dynamicInfo.setUserName(user.getUserName());
			dynamicInfo.setRoleId(RoleCst.STUDENT);
			dynamicInfo.setSchoolId(user.getCurrentSchool().getId());
			dynamicInfo.setDynamicType(DynamicTypes.HW_SPONSOR_DOUBT);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("doubtId", doubt.getDoubtId());
			dynamicInfo.setParams(params);
			dynamicInfo.setTitle(doubt.getDoubtTitle());
			dynamicInfo.setTypeId(IncentiveTypes.STUDENT.HW_STUDENT_DOUBT);
			Award award = DynamicHelper.publish(dynamicInfo);
			if (award.getSucc() && award.getHave()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("leke", award.getLekeVal());
				map.put("exp", award.getExpVal());
				map.put("type", dynamicInfo.getTypeId());
				result.addDatas("tips", map);
			} else {
				result.addDatas("tips", null);
			}
		}catch(Exception e){
			logger.info("可能。。。特殊字符集插入报错，使得页面奔溃",e);
			//可能。。。特殊字符集插入报错，使得页面奔溃
			//不做任何处理
		}
		return result;
	}

	/**
	 * 列表数据
	 * @param doubt
	 * @param page
	 * @return
	 */
	@RequestMapping("student/doubt/getDoubtList")
	@ResponseBody
	public JsonResult getDoubtList(DoubtDtl doubt, Page page) {
		JsonResult jsonresult = new JsonResult();
		doubt.setCreatedBy(UserContext.user.getUserId());
		List<DoubtDtl> list = doubtServie.doubtList(doubt, page,true);
		page.setDataList(list);
		jsonresult.addDatas("page", page);
		return jsonresult;
	}

	/**
	 * 列表数据
	 * @param doubt
	 * @param page
	 * @return
	 */
	@RequestMapping("teacher/doubt/getDoubtList")
	@ResponseBody
	public JsonResult getTeacherDoubtList(DoubtDtl doubt, Page page) {
		JsonResult json = new JsonResult();
		List<DoubtDtl> doubts = doubtServie.getTeacherDoubtl(doubt, page);
		page.setDataList(doubts);
		json.addDatas("page", page);
		return json;
	}

	@RequestMapping(value = { "teacher/doubt/explain", "student/doubt/explain" },method=RequestMethod.POST)
	public String explainDoubt(Explain explain, DoubtDtl doubt,Integer resolved) {
		boolean hasDone = explainService.firstTime(doubt.getDoubtId());
		User user = UserContext.user.get();
		if (RoleCst.STUDENT.equals(UserContext.user.getCurrentRoleId())) {
			explainService.updateAgainDoubt(doubt.getDoubtId());
		}else if(doubt.getMaterialNodeId()!=null){
			doubtServie.updateSection(doubt);
		}
		explain.setUserName(UserContext.user.getUserName());
		explain.setSchoolId(UserContext.user.getCurrentSchoolId());
		explain.setCreatedBy(UserContext.user.getUserId());
		try{
			explainService.saveExplain(explain,UserContext.user.getCurrentRoleId());
			//添加激励
			if (hasDone && RoleCst.TEACHER.equals(UserContext.user.getCurrentRoleId())) {
				DynamicInfo dynamicInfo = new DynamicInfo();
				dynamicInfo.setUserId(user.getId());
				dynamicInfo.setUserName(user.getUserName());
				dynamicInfo.setRoleId(RoleCst.TEACHER);
				dynamicInfo.setSchoolId(user.getCurrentSchool().getId());
				doubt = doubtServie.getDetail(doubt);
				dynamicInfo.setTitle(doubt.getDoubtTitle());
				dynamicInfo.setDynamicType(DynamicTypes.HW_SLOVE_DOUBT);
				dynamicInfo.setTypeId(IncentiveTypes.TEACHER.HW_STUDENT_DOUBT);
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("doubtId", doubt.getDoubtId());
				params.put("stuName", doubt.getUserName());
				dynamicInfo.setParams(params);
				DynamicHelper.publish(dynamicInfo);
			}
		}catch(Exception e){
			logger.info("可能。。。特殊字符集插入报错，使得页面奔溃",e);
			//可能。。。特殊字符集插入报错，使得页面奔溃
			//不做任何处理
		}
		return "redirect:detail.htm?doubtId="+doubt.getDoubtId()+"&resolved="+resolved;
	}

	@RequestMapping("/student/doubt/getTeachers")
	@ResponseBody
	public JsonResult queryTeacherForDoubt(String data) {
		SubjectQuery query = JsonUtils.fromJSON(data, SubjectQuery.class);
		Long userId = StudentContext.student.getUserId();
		List<SubjectTeacher> list = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(query.getSubjectList())){
			for(Subject subject:query.getSubjectList()){
				List<Long> teacherIds = this.klassRemoteService.findTeacherIdsByStudentId(userId,subject.getSubjectId());
				List<UserBase> teacherList = UserBaseContext.findUserBaseByUserId(teacherIds.toArray(new Long[0]));
				if(CollectionUtils.isNotEmpty(teacherList)){
					List<SubjectTeacher> subjectTeacherList = new ArrayList<>();
					for(UserBase v : teacherList){
						if(v!=null){
							SubjectTeacher subjectTeacher = new SubjectTeacher();
							subjectTeacher.setSubjectId(subject.getSubjectId());
							subjectTeacher.setSubjectName(subject.getSubjectName());
							subjectTeacher.setTeacherId(v.getUserId());
							subjectTeacher.setTeacherName(v.getUserName());
							subjectTeacherList.add(subjectTeacher);
						}
					}
					list.addAll(subjectTeacherList);
				}
			}
		}
		JsonResult jsonresult = new JsonResult();
		jsonresult.addDatas("resultList", list);
		return jsonresult;
	}
	
	/**
	 * 删除自己的问答
	 */
	@RequestMapping(value={"teacher/doubt/deleteMySelfDoubt","student/doubt/deleteMySelfDoubt"},method=RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteMySelfDoubt(Long[] doubtIds) {
		doubtServie.deleteMySelfDoubt(doubtIds, UserContext.user.getCurrentRoleId());
		return new JsonResult();
	}
	
	/**
	 * 收藏问答
	 */
	@RequestMapping(value={"teacher/doubt/markMySelfDoubt","student/doubt/markMySelfDoubt"},method=RequestMethod.POST)
	@ResponseBody
	public JsonResult markMySelfDoubt(Long doubtId) {
		doubtServie.markMySelfDoubt(doubtId, UserContext.user.getCurrentRoleId());
		return new JsonResult();
	}
	
}
