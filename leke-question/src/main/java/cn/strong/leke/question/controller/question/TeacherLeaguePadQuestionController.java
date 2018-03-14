package cn.strong.leke.question.controller.question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.base.LeagueContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.ftl.util.FreeMarkerUtils;
import cn.strong.leke.model.question.querys.RepositoryQuestionQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.model.user.league.League;
import cn.strong.leke.question.service.QuestionService;

@Controller
@RequestMapping("/auth/teacher/question/leaguePad")
public class TeacherLeaguePadQuestionController {

	@Autowired
	private QuestionService questionService;

	@RequestMapping("list")
	public void list(Long leagueId, Model model) {
		Long schoolId = UserContext.user.getCurrentSchoolId();
		List<League> leagues = LeagueContext.findJoinedLeagues(schoolId);
		model.addAttribute("leagueId", leagueId);
		for (League league : leagues) {
			if (league.getLeagueId().equals(leagueId)) {
				model.addAttribute("leagueName", league.getLeagueName());
			}
		}
		model.addAttribute("leagues", leagues);
	}

	@ResponseBody
	@RequestMapping("details")
	public JsonResult details(RepositoryQuestionQuery query, Page page, HttpServletRequest request) {
		JsonResult jResult = new JsonResult();
		query.setShareScope(ShareScopes.LEAGUE);
		query.setSchoolId(UserContext.user.getCurrentSchoolId());
		List<RepositoryRecord> records = questionService.queryLeagueQuestions(query, page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ctx", request.getServletContext().getInitParameter("questionServerName"));
		map.put("records", records);
		map.put("shareScope", query.getShareScope());
		List<String> dataList = ListUtils.newArrayList();
		dataList.add(FreeMarkerUtils.replaceContent(map, "details.tpl"));
		page.setDataList(dataList);
		return jResult.addDatas("page", page);
	}

}
