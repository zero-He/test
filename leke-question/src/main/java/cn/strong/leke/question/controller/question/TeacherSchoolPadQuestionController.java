package cn.strong.leke.question.controller.question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.ftl.util.FreeMarkerUtils;
import cn.strong.leke.model.question.querys.RepositoryQuestionQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.question.core.question.query.ISchoolQuestionQueryService;

@Controller
@RequestMapping("/auth/teacher/question/schoolPad")
public class TeacherSchoolPadQuestionController {

	@Resource
	private ISchoolQuestionQueryService schoolQuestionQueryService;

	@RequestMapping("list")
	public void list() {
		// nothing to do
	}

	@ResponseBody
	@RequestMapping("details")
	public JsonResult details(RepositoryQuestionQuery query, Page page, HttpServletRequest request) {
		JsonResult jResult = new JsonResult();
		query.setShareScope(ShareScopes.SCHOOL);
		query.setSchoolId(UserContext.user.getCurrentSchoolId());
		if (query.getSourceType() != null && query.getSourceType() == 1) {
			query.setAgencyUserId(UserContext.user.getUserId());
		}
		List<RepositoryRecord> records = schoolQuestionQueryService.querySchoolQuestions(query, page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ctx", request.getServletContext().getInitParameter("questionServerName"));
		map.put("records", records);
		map.put("shareScope", query.getShareScope());
		List<String> dataList = ListUtils.newArrayList();
		dataList.add(FreeMarkerUtils.replaceContent(map, "details.tpl"));
		page.setDataList(dataList);
		jResult.addDatas("page", page);
		return jResult;
	}

}
