package cn.strong.leke.homework.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.pinyin.Pinyin;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.model.CorrectHomeworkDtlInfo;
import cn.strong.leke.homework.model.HomeworkDtl;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.homework.service.MutualCorrectionService;
import cn.strong.leke.remote.service.lesson.IDiscussionGroupRemoteService;

/**
 * 作业互批
 * @author Zhang Fujun
 * @date 2017年5月22日
 */
@Controller
@RequestMapping("/auth/*")
public class MutualCorrectController {

	@Resource
	private MutualCorrectionService mutualCorrectionService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private IDiscussionGroupRemoteService discussionGroupServiceRemote;

	@RequestMapping("teacher/validateMutualCorrect")
	@ResponseBody
	public JsonResult validateMutualCorrect(Long homeworkId) {
		JsonResult jsonResult = new JsonResult();
		try {
			mutualCorrectionService.validateMutualCorrection(homeworkId, UserContext.user.getUserId());
		} catch (Exception ex) {
			jsonResult.setErr(ex.getMessage());
		}
		return jsonResult;
	}


	@ResponseBody
	@RequestMapping("teacher/changeCorrectUser")
	public JsonResult changeCorrectUser(Long homeworkDtlId, Long userId) {
		JsonResult jsonResult = new JsonResult();
		try {
			mutualCorrectionService.changeCorrectUserWithTx(homeworkDtlId, UserContext.user.getUserId(), userId);
		} catch (Exception ex) {
			jsonResult.setMessage(ex.getMessage());
		}
		return jsonResult;
	}

	@ResponseBody
	@RequestMapping("teacher/saveCorrection")
	public JsonResult saveCorrectUsers(Long homeworkId, @RequestParam(name="userIds[]",required = false) Long[] userIds) {
		JsonResult jsonResult = new JsonResult();
		try {
			Long userId = UserContext.user.getUserId();
			if(userIds != null && userIds.length > 0){
				mutualCorrectionService.saveCorrectUsers(homeworkId, userId, Arrays.asList(userIds));
			} else {
				mutualCorrectionService.saveCorrectEachOther(homeworkId, userId);
			}
		} catch (Exception ex) {
			jsonResult.setMessage(ex.getMessage());
		}
		return jsonResult;
	}
	
	@ResponseBody
	@RequestMapping("teacher/findClassGroups")
	public JsonResult findClassGroups(Long homeworkId, Long classId, Long excludeId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Long> groupLeaders = discussionGroupServiceRemote.findGroupLeaer(classId, UserContext.user.getUserId());
		List<HomeworkDtl> homeworkDtls = homeworkDtlService.findHomeworkDtlListByHomeworkId(homeworkId);
		homeworkDtls.removeIf(v->v.getStudentId().equals(excludeId));
		homeworkDtls = homeworkDtls.stream().limit(100).collect(Collectors.toList());
		homeworkDtls.forEach(v->{
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("userId", v.getStudentId());
			item.put("userName", v.getStudentName());
			item.put("isLeader", groupLeaders.contains(v.getStudentId()));
			item.put("sort", groupLeaders.contains(v.getStudentId()) == true ? 1 : 0);
			list.add(item);
		});
		list.sort((a, b) -> {
			if ((int) a.get("sort") == 1 || (int) b.get("sort") == 1) {
				return Integer.compare((int) b.get("sort"), (int) a.get("sort"));
			} else {
				return Pinyin.toPinyinAbbr(String.valueOf(a.get("userName"))).compareTo(Pinyin.toPinyinAbbr(String.valueOf(b.get("userName"))));
			}
		});
		return new JsonResult().addDatas("list", list);
	}


	@ResponseBody
	@RequestMapping("student/loadCorrectHomework")
	public JsonResult loadCorrectHomework(Page page) {
		List<CorrectHomeworkDtlInfo> datas = mutualCorrectionService.findHomeworkDtlByCorrectUser(UserContext.user.getUserId());
		for (int i = 0; i < datas.size(); i++) {
			datas.get(i).setStudentName(i+1+"");
		}
		page.setDataList(datas);
		if(datas.size() > 0) {
			page.setTotalSize(datas.size());
		}
		return new JsonResult().addDatas("page", page);
	}
	
	

	
	/**
	 * 学生批改作业列表
	 * @param homeworkId
	 * @return
	 */
	@RequestMapping("student/homework/correctHomeworkList")
	public String correctHomework(Model model, Long homeworkId) {
		model.addAttribute("homeworkId", homeworkId);
		return "auth/student/homework/correctHomeworkList";
	}
	

}
