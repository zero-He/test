package cn.strong.leke.homework.controller;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.model.HDStuHomeworkListDTO;
import cn.strong.leke.homework.model.StuSubjRes;
import cn.strong.leke.homework.model.StudentHomeworkQuery;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * H5学生作业Controller
 * @Author LIU.SHITING
 * @Version 1.0.0
 * @Date 2017-04-10 10:34:36
 */
@Controller
@RequestMapping("auth/hd/*")
public class HDStudentHomeworkController {

	@Autowired
	private HomeworkDtlService homeworkDtlService;
	@Autowired
	private HomeworkService homeworkService;

	/**
	 * 跳转到学生学科列表页
	 * @Author LIU.SHITING
	 * @Version 1.0.0
	 * @Date 2017/4/10 16:07
	 */
	@RequestMapping("queryStudentSubjectList")
	public String queryStudentSubjectList() {
		return "/auth/hd/homework/student/subjectList";
	}

	/**
	 * 请求学生学科列表页数据
	 * @Author LIU.SHITING
	 * @Version 1.0.0
	 * @Date 2017/4/10 16:07
	 */
	@RequestMapping(value = "queryStudentSubjectListData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryStudentSubjectListData() {
		JsonResult result = new JsonResult();
		User user = UserContext.user.get();
		Long studentId = user.getId();
		List<StuSubjRes> subjectList = this.homeworkDtlService.findStuSubjResByStudentId(studentId, null, null);
		result.addDatas("subjectList", subjectList);
		return result;
	}


	/**
	 * 跳转到学生作业列表页
	 * @return java.lang.String
	 * @Author LIU.SHITING
	 * @Version 1.0.0
	 * @Date 2017/4/10 17:07
	 * @Param [request]
	 */
	@RequestMapping("queryStudentHomeworkList")
	public String queryStudentHomeworkList(Model model, String subjectId) {
		User user = UserContext.user.get();
		Long studentId = user.getId();
		Map<String, Integer> totalMap = homeworkService.findStuHomeworkInfoTotal(studentId, Long.parseLong(subjectId));
		model.addAttribute("subjectId", subjectId);

		if (totalMap == null) {
			model.addAttribute("total", 0);
			model.addAttribute("doingTotal", 0);
			model.addAttribute("bugfixTotal", 0);
		} else {
			int doingTotal = Integer.parseInt(String.valueOf(totalMap.get("doingTotal")));
			int bugfixTotal = Integer.parseInt(String.valueOf(totalMap.get("bugfixTotal")));
			if (doingTotal > 99 && bugfixTotal > 99) {
				model.addAttribute("total", totalMap.get("total"));
				model.addAttribute("doingTotal", "99+");
				model.addAttribute("bugfixTotal", "99+");
			} else if (doingTotal > 99 && bugfixTotal <= 99) {
				model.addAttribute("total", totalMap.get("total"));
				model.addAttribute("doingTotal", "99+");
				model.addAttribute("bugfixTotal", bugfixTotal);
			} else if (doingTotal <= 99 && bugfixTotal > 99) {
				model.addAttribute("total", totalMap.get("total"));
				model.addAttribute("doingTotal", doingTotal);
				model.addAttribute("bugfixTotal", "99+");
			} else {
				model.addAttribute("total", totalMap.get("total"));
				model.addAttribute("doingTotal", doingTotal);
				model.addAttribute("bugfixTotal", bugfixTotal);
			}
		}
		return "/auth/hd/homework/student/studentHomeworkList";
	}

	/**
	 * 请求学生作业列表页数据
	 * @return cn.strong.leke.framework.web.JsonResult
	 * @Author LIU.SHITING
	 * @Version 1.0.0
	 * @Date 2017/4/11 10:45
	 * @Param [homeworkQuery, page]
	 */
	@RequestMapping(value = "queryStuHomeworkListData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryStuHomeworkListData(StudentHomeworkQuery homeworkQuery, Page page) {
		JsonResult result = new JsonResult();
		User user = UserContext.user.get();
		Long studentId = user.getId();
		homeworkQuery.setStudentId(studentId);

		if (result.isSuccess()) {
			List<HDStuHomeworkListDTO> stuHomeworkList = homeworkDtlService.queryStuHomeworkListData(homeworkQuery, page);
 			page.setDataList(stuHomeworkList);
			result.addDatas("page", page);
		}

		return result;
	}

}

















