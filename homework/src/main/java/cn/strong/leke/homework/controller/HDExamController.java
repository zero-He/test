package cn.strong.leke.homework.controller;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.homework.model.LayerClazz;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.model.user.ClazzGroupRemote;
import cn.strong.leke.remote.service.lesson.IStudentGroupRemoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @Author LIU.SHITING
 * @Version 2.7
 * @Date 2017-05-31 14:41:07
 */
@Controller
@RequestMapping("/auth/hd/**")
public class HDExamController {

	@Resource
	private IStudentGroupRemoteService studentGroupRemoteService;

	/**
	 * 发布考试
	 * @return java.lang.String
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/31 15:03
	 * @Param [model]
	 */
	@RequestMapping("teacher/exam/examHomework")
	public String assignHomework(Model model) {
		User user = UserContext.user.get();
		List<ClazzGroupRemote> clazzList = studentGroupRemoteService.findClazzGroupByTeacherId(user.getId());
		List<LayerClazz> classes = clazzList.stream().map(v -> {
			LayerClazz clazz = new LayerClazz();
			clazz.setClassId(v.getClassId());
			clazz.setClassName(v.getClassName());
			clazz.setClassType(v.getType());
			if (CollectionUtils.isNotEmpty(v.getStudentGroupList())) {
				clazz.setGroups(v.getStudentGroupList().stream().map(g -> {
					LayerClazz.Group group = new LayerClazz.Group();
					group.setGroupId(g.getGroupId());
					group.setGroupName(g.getGroupName());
					return group;
				}).collect(toList()));
			}
			return clazz;
		}).sorted((a, b) -> {
			int result = Integer.compare(a.getClassType(), b.getClassType());
			if (result == 0) {
				result = Long.compare(b.getClassId(), a.getClassId());
			}
			return result;
		}).collect(toList());
		model.addAttribute("classes", classes);
		return "auth/hd/exam/examHomework";
	}


}
