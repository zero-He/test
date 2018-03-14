package cn.strong.leke.homework.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.model.LessonResInfo;
import cn.strong.leke.homework.service.HomeworkService;

@Controller
@RequestMapping("/auth/student/*")
public class GlobalController {

	@Resource
	private HomeworkService homeworkService;
	
	
	@ResponseBody
	@RequestMapping("findLessonResFinishInfo")
	public JsonResult findLessonResFinishInfo(String dataJson) {
		LessonResFinishInfo info = JsonUtils.fromJSON(dataJson, LessonResFinishInfo.class);
		List<LessonResInfo> resInfos = homeworkService.findLessonResInfos(UserContext.user.getUserId(), info.getLessonIds(), info.usePhase);
		return new JsonResult().addDatas("list", resInfos);
	}
	
	public static class LessonResFinishInfo implements Serializable {
		/**
		 * Description:
		 */
		private static final long serialVersionUID = -5059253931821445397L;
		private List<Long> lessonIds;
		private Integer usePhase;

		public List<Long> getLessonIds() {
			return lessonIds;
		}

		public void setLessonIds(List<Long> lessonIds) {
			this.lessonIds = lessonIds;
		}

		public Integer getUsePhase() {
			return usePhase;
		}

		public void setUsePhase(Integer usePhase) {
			this.usePhase = usePhase;
		}

	}
}


	