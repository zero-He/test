package cn.strong.leke.homework.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.model.user.ClazzGroupRemote;
import cn.strong.leke.remote.service.lesson.IStudentGroupRemoteService;

/**
 *
 * 描述: 代人布置作业
 *
 * @author raolei
 * @created 2016年3月11日 下午3:23:32
 * @since v1.0.0
 */
@Controller
@RequestMapping("/auth/teacher/batch/*")
public class HomeworkBatchController {

	@Resource
	private IStudentGroupRemoteService studentGroupRemoteService;

	/**
	 * 描述:选择分组
	 *
	 * @author raolei
	 * @created 2016年3月10日 下午3:50:22
	 * @since v1.0.0
	 * @param type  1 表示统一布置作业
	 * @return void
	 */
	@RequestMapping("popStudentGroup")
	public void popStudentGroup(String studentGroupIds, Integer type, Model model) {
		model.addAttribute("studentGroupIds", studentGroupIds);
		model.addAttribute("bsType", type);
	}

	@RequestMapping("loadStudentGroup")
	@ResponseBody
	public JsonResult loadStudentGroup(Integer type) {
		JsonResult jResult = new JsonResult();
		User user = UserContext.user.get();
		Long userId = user.getId();
		List<ClazzGroupRemote> groups = null;
		if (type != null && type.intValue() == 1) {
			groups = studentGroupRemoteService.findClazzGroupBySchoolId(user.getCurrentSchool().getId());
		} else {
			groups = studentGroupRemoteService.findClazzGroupByTeacherId(userId);
		}
		Map<String, List<ClazzGroupRemote>> grades = groups.stream()
				.filter(v -> StringUtils.isNotEmpty(v.getGradeName()))
				.collect(Collectors.groupingBy(ClazzGroupRemote::getGradeName));
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		grades.entrySet().stream().forEach(en -> {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("gradeName", en.getKey());
			map.put("grades", en.getValue());
			lst.add(map);
		});
		//没有年级的，默认是其他班级
		List<ClazzGroupRemote> noGrade = groups.stream().filter(v -> StringUtils.isEmpty(v.getGradeName()))
				.collect(Collectors.toList());
		if (CollectionUtils.isNotEmpty(noGrade)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("gradeName", "其他");
			map.put("grades", noGrade);
			lst.add(map);
		}
		jResult.addDatas("groups", lst);
		return jResult;
	}
}
