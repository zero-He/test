package cn.strong.leke.question.controller.outline;

import static cn.strong.leke.core.business.web.JSONP.jsonp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.model.question.Outline;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.service.OutlineService;

import com.fasterxml.jackson.databind.util.JSONPObject;

/**
 *
 * 描述: 大纲jsonp
 *
 * @author raolei
 * @created 2016年6月14日 下午5:58:52
 * @since v1.0.0
 */
@Controller
@RequestMapping("/auth/common/outline/jsonp")
public class JsonpCommonOutlineController {

	@Resource
	private OutlineService outlineService;

	@RequestMapping("outlines")
	@ResponseBody
	public JSONPObject findOutlines(Long gradeId, Long subjectId, String callback) {
		return jsonp(callback, (json) -> {
			User user = UserContext.user.get();
			Long schoolId = user.getCurrentSchool().getId();
			List<Outline> records = outlineService.findOutlinesBySchIdGraIdSubId(schoolId, gradeId, subjectId);
			json.addDatas("records", records);
		});
	}

	@RequestMapping("outlinesByStaIdSubId")
	@ResponseBody
	public JSONPObject findOutlinesByStaIdSubId(Long schoolStageId, Long subjectId, String callback) {
		return jsonp(callback, (json) -> {
			User user = UserContext.user.get();
			Long schoolId = user.getCurrentSchool().getId();
			List<Outline> records = outlineService.findOutlinesBySchIdStaIdSubId(schoolId, schoolStageId, subjectId);
			json.addDatas("records", records);
		});
	}

}
