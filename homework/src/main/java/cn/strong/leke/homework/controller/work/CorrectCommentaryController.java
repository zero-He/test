package cn.strong.leke.homework.controller.work;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.manage.HwQueCommentaryService;
import cn.strong.leke.homework.model.mongo.HwQueCommentary;
import cn.strong.leke.homework.service.HomeworkDtlService;

/**
 * 批改微课批注
 * @author Zhang Fujun
 * @date 2017年12月8日
 */
@Controller
@RequestMapping({ "/auth/*" })
public class CorrectCommentaryController {
	@Resource
	private HomeworkDtlService homeworkDtlService;

	@Resource
	private HwQueCommentaryService hwQueCommentaryService;

	/**
	 * 保存微课批注
	 * @param commentary
	 * @return
	 */
	@RequestMapping({ "teacher/commentary/shareCommentary" })
	@ResponseBody
	public JsonResult shareCommentary(String dataJson, Long homeworkDtlId) {
		Long stuId = homeworkDtlService.getHomeworkDtlInfoById(homeworkDtlId).getStudentId();
		List<HwQueCommentary> commentaries = JsonUtils.readList(dataJson, HwQueCommentary.class);
		commentaries.forEach(v -> {
			v.setType(HwQueCommentary.WAY_PERSONAL);
			v.setIncludeUserIds(new HashSet<>(Arrays.asList(stuId)));
			hwQueCommentaryService.saveCommentary(v);
		});
		return new JsonResult();
	}
	
	/**
	 * 移除微课批注
	 * @param commentary
	 * @return
	 */
	@RequestMapping({ "teacher/commentary/removeCommentary" })
	@ResponseBody
	public JsonResult removeCommentary(Long homeworkId, Long questionId, Long resId, Long homeworkDtlId) {
		Long stuId = homeworkDtlService.getHomeworkDtlInfoById(homeworkDtlId).getStudentId();
		hwQueCommentaryService.removeCommentaryForStu(homeworkId, questionId, resId, stuId,
				UserContext.user.getUserId());
		return new JsonResult();
	}
	
	/**获取作业某个题目的批注信息
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("teacher/commentary/findSignleQueComment")
	@ResponseBody
	public JsonResult findSignleQueComment (Long homeworkId,Long questionId, Long studentId) {
		List<HwQueCommentary> commentaries = hwQueCommentaryService.findCommentaries(homeworkId, questionId, studentId );
		List<Map<String, Object>> list = commentaries.stream().map(v->{
			Map<String, Object> item = new HashMap<>();
			item.put("type", v.getType());
			item.put("resId", v.getResId());
			item.put("resName", v.getResName());
			item.put("link", "http://beike.leke.cn/auth/common/microcourse/preview.htm?microcourseId=" + v.getResId());
			return item;
		}).collect(toList());
		return new JsonResult().addDatas("list", list);
	}
}
