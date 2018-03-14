package cn.strong.leke.homework.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.business.incentive.DynamicHelper;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.model.Doubt;
import cn.strong.leke.homework.model.Explain;
import cn.strong.leke.homework.service.DoubtService;
import cn.strong.leke.homework.service.ExplainService;
import cn.strong.leke.lesson.model.LessonCatalog;
import cn.strong.leke.lesson.model.LessonVM;
import cn.strong.leke.model.base.SchoolCst;
import cn.strong.leke.model.incentive.DynamicInfo;
import cn.strong.leke.model.incentive.DynamicTypes;
import cn.strong.leke.model.incentive.IncentiveTypes;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.service.lesson.ILessonRemoteService;

@Controller
@RequestMapping("/api/n/*")
public class DoubtNAPIController {

	@Resource
	private DoubtService doubtService;
	@Resource
	private ExplainService explainService;
	@Resource
	private ILessonRemoteService lessonRemoteService;
	
	/**
	 * 保存提问
	 *	_s：homework
	 *	_m: saveDoubt
	 *	type: n
	 * @param data
	 * @return
	 */
	@RequestMapping("saveDoubt")
	@ResponseBody
	public JsonResult saveDoubt(String data) {
		Doubt doubt = JsonUtils.fromJSON(data, Doubt.class);
		UserBase user = UserBaseContext.getUserBaseByUserId(doubt.getCreatedBy());
		doubt.setUserName(user.getUserName());
		doubt.setDoubtType(1);
		doubt.setTargetType(1L);
		doubt.setResolved(Boolean.FALSE);
		doubt.setDeleted(Boolean.FALSE);
		doubt.setSource(Doubt.SOURCE_LESSON);
		if(StringUtils.isNotEmpty(doubt.getDoubtAudio())){
			doubt.setDoubtAudio(doubt.getDoubtAudio().replaceFirst("https://file.leke.cn/", ""));
		}
		if(doubt.getLessonId()!=null){
			LessonVM lesson = lessonRemoteService.getLessonVMByLessonId(doubt.getLessonId());
			doubt.setDoubtTitle(lesson.getCourseSingleName());
			doubt.setSubjectId(lesson.getSubjectId());
			doubt.setSchoolStageId(lesson.getSchoolStageId());
			List<LessonCatalog> list = lessonRemoteService.findLessonCatalogsByLessonId(doubt.getLessonId());
			if(CollectionUtils.isNotEmpty(list)){
				doubt.setMaterialNodeId(list.get(0).getNodeId());
				doubt.setMaterialPathName(list.get(0).getPath());
			}
		}
		doubtService.saveDoubt(doubt);
		//发送激励
		DynamicInfo dynamicInfo = new DynamicInfo();
		dynamicInfo.setUserId(user.getId());
		dynamicInfo.setUserName(user.getUserName());
		dynamicInfo.setRoleId(RoleCst.STUDENT);
		dynamicInfo.setSchoolId(SchoolCst.LEKE);
		dynamicInfo.setDynamicType(DynamicTypes.HW_SPONSOR_DOUBT);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("doubtId", doubt.getDoubtId());
		dynamicInfo.setParams(params);
		dynamicInfo.setTitle(doubt.getDoubtTitle());
		dynamicInfo.setTypeId(IncentiveTypes.STUDENT.HW_STUDENT_DOUBT);
		DynamicHelper.publish(dynamicInfo);
		JsonResult result = new JsonResult();
		result.addDatas("doubtId", doubt.getDoubtId());
		return result;
	}
	
	/**
	 * 保存回答
	 *	_s：homework
	 *	_m: saveExplain
	 *	type: n
	 * @param data
	 * @return
	 */
	@RequestMapping("saveExplain")
	@ResponseBody
	public JsonResult saveExplain(String data) {
		Explain explain = JsonUtils.fromJSON(data, Explain.class);
		UserBase user = UserBaseContext.getUserBaseByUserId(explain.getCreatedBy());
		explain.setUserName(user.getUserName());
		explain.setDeleted(Boolean.FALSE);
		if(StringUtils.isNotEmpty(explain.getExpainAudio())){
			explain.setExpainAudio(explain.getExpainAudio().replaceFirst("https://file.leke.cn/", ""));
		}
		explainService.saveExplain(explain,explain.getRoleId());
		if(RoleCst.TEACHER.equals(explain.getRoleId())){
			DynamicInfo dynamicInfo = new DynamicInfo();
			dynamicInfo.setUserId(explain.getCreatedBy());
			dynamicInfo.setUserName(user.getUserName());
			dynamicInfo.setRoleId(RoleCst.TEACHER);
			dynamicInfo.setSchoolId(SchoolCst.LEKE);
			dynamicInfo.setDynamicType(DynamicTypes.HW_SLOVE_DOUBT);
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("doubtId", explain.getDoubtId());
			dynamicInfo.setParams(params);
			dynamicInfo.setTitle("老师答疑");
			dynamicInfo.setTypeId(IncentiveTypes.TEACHER.HW_STUDENT_DOUBT);
			DynamicHelper.publish(dynamicInfo);
		}
		return new JsonResult();
	}

}
