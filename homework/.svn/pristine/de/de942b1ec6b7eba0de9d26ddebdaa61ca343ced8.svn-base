/* 
 * 包名: cn.strong.leke.homework.controller
 *
 * 文件名：DoubtController.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: luf
 *
 * 日期：2014-6-18
 */
package cn.strong.leke.homework.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.thread.ThreadLocalHolder;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.user.StudentContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.business.incentive.DynamicHelper;
import cn.strong.leke.core.cas.SessionCst;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.model.Doubt;
import cn.strong.leke.homework.model.DoubtDtl;
import cn.strong.leke.homework.model.Explain;
import cn.strong.leke.homework.service.DoubtService;
import cn.strong.leke.homework.service.ExplainService;
import cn.strong.leke.homework.service.impl.HomeworkDtlServiceImpl;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.incentive.Award;
import cn.strong.leke.model.incentive.DynamicInfo;
import cn.strong.leke.model.incentive.DynamicTypes;
import cn.strong.leke.model.incentive.IncentiveTypes;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.user.User;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.tutor.base.ISubjectRemoteService;


/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    luf
 * @version   Avatar 
 */
@Controller
@RequestMapping("/auth/*")
public class DoubtController {

	protected static final Logger logger = LoggerFactory.getLogger(HomeworkDtlServiceImpl.class);

	@Resource
	private ISubjectRemoteService subjectRemoteService;
	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private DoubtService doubtService;
	@Resource
	private ExplainService explainService;

	/**
	 * 描述:跳转到学生我 要提问页面
	 * @author  DuanYanming
	 * @created 2014年8月28日 下午3:20:37
	 * @since   v1.0.0 
	 * @param model
	 * @return
	 * @return  String
	 */
	@RequestMapping("student/myDoubt/ask/doubt")
	public String askDoubt(ModelMap model, Long teacherId) {
		if(teacherId != null){
			UserBase userBase = UserBaseContext.getUserBaseByUserId(teacherId);
			model.addAttribute("teacherId", teacherId);
			model.addAttribute("teacherName", userBase != null ? userBase.getUserName() : "");
		}
		Clazz clazz = klassRemoteService.getDeptClazzByStudentId(UserContext.user.getUserId());
		if(clazz!=null){
			model.addAttribute("clazz", clazz);
		}
		model.addAttribute("comeFromExam", false);
		return "/auth/student/doubt/doubt";
	}

	@RequestMapping("student/myDoubt/doubtList/doubt")
	public String doubt(ModelMap model,Long teacherId) {
		model.addAttribute("teacherId", teacherId);
		return "/auth/student/doubt/doubt";
	}

	/**
	 * 通过题目弹出窗口提问
	 * @param questionId 题目ID
	 * @param subjectId 学科ID
	 * @param crossDomain 是否跨域
	 * @param model
	 * @return
	 */
	@RequestMapping("student/myDoubt/doubtList/doubtForQuestion")
	public String doubtForQuestion(Model model,Doubt doubt,Integer cross) {
		Long userId = StudentContext.student.getUserId();
		List<Long> teacherIds;
		if (doubt.getTeacherId() == null) {
			teacherIds = this.klassRemoteService.findTeacherIdsByStudentId(userId, doubt.getSubjectId());
			List<UserBase> teacherList = UserBaseContext.findUserBaseByUserId(teacherIds.toArray(new Long[0]));
			model.addAttribute("teacherList", teacherList);
		} else {
			UserBase userBase = UserBaseContext.getUserBaseByUserId(doubt.getTeacherId());
			model.addAttribute("teacherId", doubt.getTeacherId());
			model.addAttribute("teacherName", userBase != null ? userBase.getUserName() : "");
		}
		QuestionDTO questionDto = QuestionContext.getQuestionDTO(doubt.getQuestionId());
		if(questionDto != null) {
			model.addAttribute("subjective", questionDto.getSubjective());
		}
		model.addAttribute("subjectId", doubt.getSubjectId());
		model.addAttribute("homeworkDtlId", doubt.getHomeworkDtlId());
		model.addAttribute("questionId", doubt.getQuestionId());
		model.addAttribute("sourceId", doubt.getSourceId());
		model.addAttribute("sourceType", doubt.getSourceType());
		model.addAttribute("sourceName", doubt.getSourceName());
		model.addAttribute("cross", cross);
		return "/auth/student/doubt/doubtForPaper";
	}

	@RequestMapping("student/myDoubt/saveDoubt")
	@ResponseBody
	public JsonResult saveDoubt(Doubt doubt,
			@RequestParam(value = "needIncentive", defaultValue = "false") Boolean needIncentive) {
		if(StringUtils.isEmpty(doubt.getDoubtContent())){
			throw new ValidateException("请上传内容");
		}
		JsonResult jsonresult = new JsonResult();
		User user = UserContext.user.get();
		doubt.setCreatedBy(user.getId());
		doubt.setSchoolId(user.getCurrentSchool().getId());
		doubt.setSchoolName(user.getCurrentSchool().getName());
		doubt.setUserName(user.getUserName());
		if(doubt.getSource()==null){
			doubt.setSource(Doubt.SOURCE_MINE);
		}
		doubtService.saveDoubt(doubt);

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
			jsonresult.addDatas("tips", map);
		} else {
			jsonresult.addDatas("tips", null);
		}
		jsonresult.setSuccess(true);
		return jsonresult;
	}

	/**
	 * 描述:返回我的提问列表页面
	 * @author  DuanYanming
	 * @created 2014年8月28日 下午3:20:56
	 * @since   v1.0.0 
	 * @param doubt
	 * @param page
	 * @param model
	 * @return
	 * @return  String
	 */
	@RequestMapping("student/myDoubt/doubtList/enterDoubtList")
	public String enterDoubtList(ModelMap model) {
		Integer count = doubtService.getStudentResolveDoubt(UserContext.user.getUserId());
		model.addAttribute("count", count==null?0:count>999?"+999":count);
		return "/auth/student/doubt/doubtList";
	}

	@RequestMapping("student/myDoubt/getDoubtList")
	@ResponseBody
	public JsonResult getDoubtList(DoubtDtl doubt, Page page) {
		JsonResult jsonresult = new JsonResult();
		doubt.setCreatedBy(UserContext.user.getUserId());
		if(doubt.getEndTime()!=null){
			doubt.setEndTime(DateUtils.addDays(doubt.getEndTime(), 1));
		}
		List<DoubtDtl> list = doubtService.doubtList(doubt, page,true);
		page.setDataList(list);
		jsonresult.addDatas("page", page);
		return jsonresult;
	}
	
	@RequestMapping("student/doubt/doubtListFormSource")
	@ResponseBody
	public JsonResult doubtListFormSource(Integer source,Long sourceId,Integer sourceType, Page page) {
		JsonResult result = new JsonResult();
		Long userId = UserContext.user.getUserId();
		DoubtDtl doubt = new DoubtDtl();
		doubt.setCreatedBy(userId);
		doubt.setSource(source);
		doubt.setSourceId(sourceId);
		doubt.setSourceType(sourceType);
		List<DoubtDtl> list = doubtService.doubtList(doubt, page,false);
		if(CollectionUtils.isNotEmpty(list)){
			list.forEach(v->{
				Explain explain = new Explain();
				explain.setDoubtId(v.getDoubtId());
				explain.setCreatedBy(userId);
				v.setExplains(explainService.getExplainList(explain));
			});
		}
		page.setDataList(list);
		result.addDatas("page", page);
		return result;
	}

	@RequestMapping("student/doubt/doubtList_tpl")
	@ResponseBody
	public JsonResult getDoubtListTpl(DoubtDtl doubt, Page page) {
		JsonResult json = new JsonResult();
		doubt.setCreatedBy(UserContext.user.getUserId());
		if(doubt.getEndTime()!=null){
			doubt.setEndTime(DateUtils.addDays(doubt.getEndTime(), 1));
		}
		List<DoubtDtl> list = doubtService.doubtList(doubt, page,true);
		page.setDataList(list);
		json.addDatas("page", page);
		return json;
	}

	/**
	 * 描述:我的提问详细
	 * @author  DuanYanming
	 * @created 2014年8月28日 下午3:21:14
	 * @since   v1.0.0 
	 * @param model
	 * @param doubt
	 * @return
	 * @return  String
	 */
	@RequestMapping("student/myDoubt/doubtList/getDoubtDetail")
	public String getDoubtDetail(ModelMap model, DoubtDtl doubt,HttpServletRequest request) {
		model.addAllAttributes(doubtService.getDoubt_Explain_Question(doubt,request));
		return "/auth/student/doubt/newDoubtDetail";
	}
	
	/**
	 * 描述:试题答疑查看
	 * @author  DuanYanming
	 * @created 2014年8月28日 下午3:19:58
	 * @since   v1.0.0 
	 * @param model
	 * @param doubt
	 * @return
	 * @return  String
	 */
	@RequestMapping("teacher/myDoubt/getDoubtDetail")
	public String getDoubtDetailForExplain(ModelMap model, DoubtDtl doubt,HttpServletRequest request) {
		model.addAllAttributes(doubtService.getDoubt_Explain_Question(doubt,request));
		model.addAttribute("teacherName", UserContext.user.getUserName());
		model.addAttribute(SessionCst.TICKET, ThreadLocalHolder.getResource(SessionCst.TICKET));
		return "/auth/teacher/doubt/newDoubtDetail";
	}

	/**
	 * 描述: 跳转到试题答疑页面
	 * @author  DuanYanming
	 * @created 2014年8月28日 下午3:19:23
	 * @since   v1.0.0 
	 * @return
	 * @return  String
	 */
	@RequestMapping("teacher/myDoubt/enterTeacherDoubtList")
	public String enterTeacerDoubtList(Model model) {
		Integer count = doubtService.getTeacherResolveDoubt(UserContext.user.getUserId());
		model.addAttribute("count", count==null?0:count>999?"+999":count);
		return "/auth/teacher/doubt/doubtList";
	}
	
	/**
	 * 删除自己的问答
	 */
	@RequestMapping(value={"teacher/myDoubt/deleteMySelfDoubt","student/myDoubt/doubtList/deleteMySelfDoubt"},method=RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteMySelfDoubt(Long[] doubtIds) {
		doubtService.deleteMySelfDoubt(doubtIds, UserContext.user.getCurrentRoleId());
		return new JsonResult();
	}
	
	/**
	 * 收藏问答
	 */
	@RequestMapping(value={"teacher/myDoubt/markMySelfDoubt","student/myDoubt/doubtList/markMySelfDoubt"},method=RequestMethod.POST)
	@ResponseBody
	public JsonResult markMySelfDoubt(Long doubtId) {
		doubtService.markMySelfDoubt(doubtId, UserContext.user.getCurrentRoleId());
		return new JsonResult();
	}

	@RequestMapping("teacher/doubt/teacherDoubtList_tpl")
	@ResponseBody
	public JsonResult getTeacherDoubtList(DoubtDtl doubt, Page page) {
		JsonResult json = new JsonResult();
		if(doubt.getEndTime()!=null){
			doubt.setEndTime(DateUtils.addDays(doubt.getEndTime(), 1));
		}
		List<DoubtDtl> doubts = doubtService.getTeacherDoubtl(doubt, page);
		page.setDataList(doubts);
		json.addDatas("page", page);
		return json;
	}


	@RequestMapping("/student/myDoubt/getTeachers")
	@ResponseBody
	public JsonResult queryTeacherForDoubt(Long subjectId) {
		Long userId = StudentContext.student.getUserId();
		List<Long> teacherIds = this.klassRemoteService.findTeacherIdsByStudentId(userId, subjectId);
		List<UserBase> teacherList = UserBaseContext.findUserBaseByUserId(teacherIds.toArray(new Long[0]));

		JsonResult jsonresult = new JsonResult();
		jsonresult.addDatas("resultList", teacherList);
		return jsonresult;
	}
	
	/**
	 * 修改问答中教材章节信息
	 * @param doubt
	 * @return
	 */
	@RequestMapping("/teacher/doubt/updateSection")
	@ResponseBody
	public JsonResult updateSection(Doubt doubt) {
		doubtService.updateSection(doubt);
		return new JsonResult();
	}
	
	/**
	 * 通过题目弹出窗口提问
	 * @param questionId 题目ID
	 * @param subjectId 学科ID
	 * @param crossDomain 是否跨域
	 * @param model
	 * @return
	 */
	@RequestMapping("student/newToolbar/doubt")
	public String toolbarDoubt(Model model,Doubt doubt,Integer cross) {
		model.addAttribute("cross", cross);
		return "/auth/student/toolbar/doubt";
	}
	
	@ResponseBody
	@RequestMapping("student/newToolbar/teachers")
	public JsonResult teachers(Long subjectId) {
		Long userId = StudentContext.student.getUserId();
		List<Long> teacherIds = this.klassRemoteService.findTeacherIdsByStudentId(userId, subjectId);
		List<UserBase> teachers = UserBaseContext.findUserBaseByUserId(teacherIds.toArray(new Long[0]));
		JsonResult json = new JsonResult();
		json.addDatas("teachers", teachers);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("student/newToolbar/saveDoubt")
	public JsonResult doubt(Doubt doubt) {
		User user = UserContext.user.get();
		doubt.setCreatedBy(user.getId());
		doubt.setSchoolId(user.getCurrentSchool().getId());
		doubt.setSchoolName(user.getCurrentSchool().getName());
		doubt.setUserName(user.getUserName());
		doubt.setSource(Doubt.SOURCE_MINE);
		doubtService.saveDoubt(doubt);
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

		JsonResult json = new JsonResult();
		if (award.getSucc() && award.getHave()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("leke", award.getLekeVal());
			map.put("exp", award.getExpVal());
			map.put("type", dynamicInfo.getTypeId());
			json.addDatas("tips", map);
		} else {
			json.addDatas("tips", null);
		}
		return json;
	}

}
