/* 
 * 包名: cn.strong.leke.question.controller
 *
 * 文件名：UserController.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-5-12
 */
package cn.strong.leke.question.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.GradeContext;
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.GradeSubject;
import cn.strong.leke.model.base.SchoolStageSubject;
import cn.strong.leke.model.user.Checker;
import cn.strong.leke.model.user.Researcher;
import cn.strong.leke.question.service.IQuestionTaskService;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    lavender
 * @version   Avatar 
 */
@Controller
@RequestMapping("/auth/*")
public class UserController {
	@Resource
	private IUserRemoteService userService;
	@Resource
	private IQuestionTaskService questionTaskService;
	/**
	 * 设置题库审查范围页面
	 * author：lavender
	 * 2014-5-12下午2:51:37
	 */
	@RequestMapping("admin/user/setCheckRange")
	public void setCheckRange(ModelMap modle){
	}
	
	@RequestMapping("admin/user/setResearcherRange")
	public void setResearcherRange(ModelMap modle) {
		List<SchoolStageRemote> schoolStageList = SchoolStageContext.findSchoolStages();
		List<SubjectRemote> subjectList = SubjectContext.findSubjects();
		modle.addAttribute("schoolStageList", schoolStageList);
		modle.addAttribute("subjectList", subjectList);
	}

	@RequestMapping("admin/user/ajax/findQuestionUserList")
	@ResponseBody
	public JsonResult findQuestionUserList(String userName, Long schoolStageId,
			Long subjectId) {
		JsonResult json = new JsonResult();
		
		List<Researcher> userList = userService
				.findUserListForQuestionResearcher(userName, schoolStageId,
						subjectId);
		json.addDatas("userList", userList);
		
		return json;
	} 
	
	@RequestMapping("admin/user/ajax/findQuestionCheckerList")
	@ResponseBody
	public JsonResult findQuestionCheckerList(String userName, Long gradeId,
			Long subjectId) {
		JsonResult json = new JsonResult();

		List<Checker> userList = userService.findUserListForQuestionChecker(
				userName, gradeId, subjectId);
		json.addDatas("userList", userList);

		return json;
	}

	/**
	 * 设置学段科目页面
	 * author：lavender
	 * 2014-5-12下午2:51:37
	 */
	@RequestMapping("admin/user/setSchoolStageSubject")
	public void setSchoolStageSubject(Long id, ModelMap modle) {

		modle.addAttribute("id", id);
	}

	@RequestMapping("admin/user/ajax/addSchoolStageSubject")
	@ResponseBody
	public JsonResult addSchoolStageSubject(String jsonStr) {
		JsonResult json = new JsonResult();
		try {
			List<SchoolStageSubject> ssList = JsonUtils.readList(jsonStr, SchoolStageSubject.class);
			Long userId = UserContext.user.getUserId();
			if (CollectionUtils.isNotEmpty(ssList)) {
				Long id = ssList.get(0).getId();
				for (SchoolStageSubject gss : ssList) {
					gss.setId(id);
					gss.setCreatedBy(userId);
					gss.setModifiedBy(userId);
				}

				Integer flag = questionTaskService.checkFinished(id);
				if (flag > 0) {
					throw new ValidateException("que.user.info.taskExist");
				} else {
					//删除原有关系
					userService.deleteQuestionUserSubjectSchoolStage(id);
					//保存新关系
					userService.addQuestionUserSubjectSchoolStage(ssList);

				}
			}
			
		} catch (Exception e) {
			json.setErr(e.getMessage());
		}
		return json;
	}

	/**
	 * 设置学段科目页面
	 * author：lavender
	 * 2014-5-12下午2:51:37
	 */
	@RequestMapping("admin/user/setGradeSubject")
	public void setGradeSubject(Long id, ModelMap modle) {
		List<GradeRemote> gradeList = GradeContext.findGrades();
		List<SubjectRemote> subjectList = SubjectContext.findSubjects();
		modle.addAttribute("gradeList", gradeList);
		modle.addAttribute("subjectList", subjectList);
		modle.addAttribute("id", id);
	}

	@RequestMapping("admin/user/ajax/addGradeSubject")
	@ResponseBody
	public JsonResult addGradeSubject(String jsonStr, Long id) {
		JsonResult json = new JsonResult();
		try {
			List<GradeSubject> gsList = JsonUtils.readList(jsonStr, GradeSubject.class);
			Long userId = UserContext.user.getUserId();

			if (CollectionUtils.isNotEmpty(gsList)) {
				for (GradeSubject gs : gsList) {
					gs.setId(id);
					gs.setCreatedBy(userId);
					gs.setModifiedBy(userId);
				}
			}
			
			Integer flag = questionTaskService.checkFinished(id);
			if (flag > 0) {
				throw new ValidateException("que.user.info.taskExist");
			} else {
				//删除原有关系
				userService.deleteQuestionUserSubjectGrade(id);
				//保存新关系
				userService.addQuestionUserSubjectGrade(gsList);
				
			}
		} catch (Exception e) {
			json.setErr(e.getMessage());
		}
		return json;
	}
}
