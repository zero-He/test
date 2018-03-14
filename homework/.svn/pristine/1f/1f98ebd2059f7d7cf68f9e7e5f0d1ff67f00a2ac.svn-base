package cn.strong.leke.homework.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.model.SubjectHwStatistics;
import cn.strong.leke.homework.model.mobile.MPersonWorkCount;
import cn.strong.leke.homework.model.mobile.MPersonWorkDTO;
import cn.strong.leke.homework.model.mobile.MPersonWorkQuery;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;

@Controller
@RequestMapping("/auth/m/*")
public class MHomeworkPersonController {

	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private IUserRemoteService userRemoteService;

	/**
	 * 学生：作业管家
	 */
	@RequestMapping("student/homework/manager")
	public String studentManager(Model model) {
		Long userId = UserContext.user.getUserId();
		List<SubjectHwStatistics> subjects = homeworkDtlService.findStudentSubjectHwStatisticsList(userId, false);
		model.addAttribute("subjects", subjects);
		return "/auth/m/homework/person/student-manager";
	}

	/**
	 * 家长：作业管家页面
	 */
	@RequestMapping(value = "parent/homework/manager", method = RequestMethod.GET)
	public String parentManager(Model model) {
		List<User> childs = userRemoteService.findUserByParentId(UserContext.user.getUserId());
		Map<String, Object> csts = new HashMap<>();
		csts.put("childs", childs);
		model.addAttribute("_ts", System.currentTimeMillis());
		model.addAttribute("csts", JsonUtils.toJSON(csts));
		return "/auth/m/homework/person/parent-manager";
	}

	/**
	 * 家长：作业管家数据
	 */
	@ResponseBody
	@RequestMapping(value = "parent/homework/manager", method = RequestMethod.POST)
	public Object parentManager(Long userId, Model model) {
		List<SubjectHwStatistics> subjects = homeworkDtlService.findStudentSubjectHwStatisticsList(userId, false);
		return new JsonResult().addDatas("subjects", subjects);
	}

	/**
	 * 学生/家长：学科作业页面
	 */
	@RequestMapping(value = "person/homework/worklist", method = RequestMethod.GET)
	public String worklist(Long studentId, Long subjectId, Model model) {
		Map<String, Object> csts = new HashMap<>();
		csts.put("subjectId", subjectId);
		if (studentId == null) {
			csts.put("studentId", UserContext.user.getUserId());
			csts.put("isStudent", true);
		} else {
			csts.put("studentId", studentId);
			csts.put("isStudent", false);
		}
		SubjectRemote subject = SubjectContext.getSubject(subjectId);
		model.addAttribute("_ts", System.currentTimeMillis());
		model.addAttribute("subject", subject);
		model.addAttribute("csts", JsonUtils.toJSON(csts));
		return "/auth/m/homework/person/person-worklist";
	}

	/**
	 * 学生/家长：学科作业数据
	 */
	@ResponseBody
	@RequestMapping(value = "person/homework/worklist", method = RequestMethod.POST)
	public Object worklist(MPersonWorkQuery query) {
		JsonResult json = new JsonResult();
		if (query.getCounter()) {
			MPersonWorkCount count = this.homeworkDtlService.findMobilePersonWorkCount(query);
			json.addDatas("count", count);
		}
		List<MPersonWorkDTO> list = this.homeworkDtlService.findMobilePersonWorkList(query);
		json.addDatas("list", list);
		return json;
	}
}
