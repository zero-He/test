package cn.strong.leke.question.controller.question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.ftl.util.FreeMarkerUtils;
import cn.strong.leke.model.question.querys.RepositoryQuestionQuery;
import cn.strong.leke.model.repository.QuestionRepositoryRecord;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.question.model.question.query.QuestionShareLogQuery;
import cn.strong.leke.question.service.IQuestionShareLogService;
import cn.strong.leke.question.service.QuestionService;

@Controller
@RequestMapping("/auth/teacher/question/personalPad/*")
public class TeacherPersonalPadQuestionController {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private IQuestionShareLogService questionShareLogService;

	@RequestMapping("list")
	public void list() {
		// nothing to do
	}

	@ResponseBody
	@RequestMapping("details")
	public JsonResult details(RepositoryQuestionQuery query, Page page, HttpServletRequest request) {
		JsonResult jResult = new JsonResult();
		Map<String, Object> map = new HashMap<String, Object>();
		List<RepositoryRecord> records = new ArrayList<>();
		Long userId = UserContext.user.getUserId();
		query.setUserId(userId);
		if (query.getShareScope() == ShareScopes.PERSONAL) {
			records = questionService.queryPersonalQuestions(query, page);
			map.put("records", records);
		} else if (query.getShareScope() == ShareScopes.FAVORITE) {
			records = questionService.queryFavoriteQuestions(query, page);
			map.put("records", records);
		} else if ((query.getShareScope() == 10)) {
			QuestionShareLogQuery q = new QuestionShareLogQuery();
			q.setUserId(userId);
			List<QuestionRepositoryRecord> records2 = questionShareLogService.queryQuestionShareInfo(q, page);
			map.put("records", records2);
		} else {
			records = Collections.emptyList();
			map.put("records", records);
		}
		map.put("ctx", request.getServletContext().getInitParameter("questionServerName"));
		map.put("shareScope", query.getShareScope());
		List<String> dataList = ListUtils.newArrayList();
		dataList.add(FreeMarkerUtils.replaceContent(map, "details.tpl"));
		page.setDataList(dataList);
		jResult.addDatas("page", page);
		return jResult;
	}

}
