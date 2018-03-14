package cn.strong.leke.homework.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.datetime.Week;
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.manage.ExerciseService;
import cn.strong.leke.homework.model.ExerciseType;
import cn.strong.leke.homework.model.mongo.Exercise;
import cn.strong.leke.homework.model.mongo.ExerciseRank;
import cn.strong.leke.homework.model.mongo.ExerciseRankUser;
import cn.strong.leke.homework.model.mongo.ExerciseReport;
import cn.strong.leke.homework.model.query.ExerciseListQuery;
import cn.strong.leke.homework.util.ExerciseUtils;
import cn.strong.leke.homework.util.PhotoUtils;
import cn.strong.leke.homework.util.ScoreUtils;
import cn.strong.leke.model.user.RoleSchool;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.service.learn.ILearnRemoteService;

/**
 * 平板端页面
 * 自主练习业务
 * @author Zhang Fujun
 * @date 2016年11月24日
 */
@Controller
@RequestMapping("/auth/hd/student/exercise/*")
public class HDExerciseController {

	@Resource
	private ExerciseService exerciseService;
	@Resource
	private ILearnRemoteService learnRemoteService;
	/**
	 * 练习报告
	 */
	@RequestMapping("report")
	public String report(@RequestParam("id") String exerciseId, Model model) {
		Exercise exercise = exerciseService.getExerciseById(exerciseId);
		exercise.setAccuracy(new BigDecimal(ScoreUtils.toPercent(exercise.getAccuracy())));
		List<ExerciseReport> directReport = null;
		if (exercise.getExerciseType() == ExerciseType.KNOWLEDGE.value) {
			directReport = exercise.getReport().stream().filter(v -> v.getType().equals(ExerciseReport.KNOWLEDGE))
					.collect(Collectors.toList());
		}
		if(CollectionUtils.isNotEmpty(directReport)){
			directReport.forEach(v->v.setAccuracy(new BigDecimal(ScoreUtils.toPercent(v.getAccuracy()))));
		}
		model.addAttribute("directReport", directReport);
		List<ExerciseReport> relationReport = exercise.getReport().stream()
				.filter(v -> v.getType().equals(ExerciseReport.RELATION_KNOWLEDGE)).collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(relationReport)){
			relationReport.forEach(v->v.setAccuracy(new BigDecimal(ScoreUtils.toPercent(v.getAccuracy()))));
		}
		model.addAttribute("relationReport", relationReport);
		model.addAttribute("exerciseTitle", ExerciseUtils.getExerciseTitle(exercise.getExerciseName(), exercise.getExerciseType()));
		model.addAttribute("exercise", exercise);
		Exercise firstExercise = exerciseService.getFirstStuExercise(UserContext.user.getUserId());
		if(firstExercise != null && firstExercise.getExerciseId().equals(exerciseId)){
			model.addAttribute("firstExercise", true);
		}else{
			model.addAttribute("firstExercise", false);
		}
		return "auth/hd/exercise/report";
	}
	
	/**
	 * 返回同步练习列表
	 * @param model
	 * @return
	 */
	@RequestMapping("chapterTree")
	public String chapterTree(Model model) {
		model.addAttribute("flag", ExerciseType.CHAPTER.value);
		model.addAttribute("schoolId", UserContext.user.getCurrentSchoolId());
		return "/auth/hd/exercise/chapterTree";
	}

	/**
	 * 知识点检测列表
	 * @param model
	 * @return
	 */
	@RequestMapping("knowledgeTree")
	public String knowledgeTree(Model model) {
		model.addAttribute("flag", ExerciseType.KNOWLEDGE.value);
		model.addAttribute("schoolId", UserContext.user.getCurrentSchoolId());
		return "/auth/hd/exercise/knowledgeTree";
	}
	
	@RequestMapping("rank")
	public String rank(Model model) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		model.addAttribute("weekOfYear", weekOfYear);
		return "/auth/hd/exercise/rank";
	}
	@RequestMapping("list")
	public String list(Model model) {
		return "/auth/hd/exercise/list";
	}

	@RequestMapping("index")
	public String index(Model model) {
		return "/auth/hd/exercise/index";
	}

	
	@RequestMapping("ajax/loadList")
	@ResponseBody
	public JsonResult loadExerciseList(ExerciseListQuery query, Integer timeRangeDays, Page page) {
		if(timeRangeDays != null){
			query.setEndTime(new Date());
			query.setStartTime(DateUtils.addDays(new Date(), -timeRangeDays));
		}
		if (query.getEndTime() != null) {
			// 结束日期后移一天
			query.setEndTime(DateUtils.addDays(query.getEndTime(), 1));
		}
		query.setStudentId(UserContext.user.getUserId());
		List<Exercise> datas = exerciseService.findStuExerciseList(query, page);
		datas.forEach(v->{
			if(v.getAccuracy() != null){
				v.setAccuracy(new BigDecimal(ScoreUtils.toPercent(v.getAccuracy())));
			}
		});
		page.setDataList(datas);
		return new JsonResult().addDatas("page", page);
	}
	
	@RequestMapping("ajax/loadWeekRank")
	@ResponseBody
	public JsonResult loadWeekRank(Integer week, HttpServletRequest request) {
		JsonResult json = new JsonResult();
		Integer year = DateUtils.getCurrentYear();
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		Week wk = new Week(calendar.getTime());
		calendar.setTime(DateUtils.addDays(wk.getStartDate(), -1));
		ExerciseRank rank = new ExerciseRank();
		if(week == 0){
			week = calendar.get(Calendar.WEEK_OF_YEAR);
			year = calendar.get(Calendar.YEAR);
			rank = exerciseService.findWeekRank(year, week);
		}else{
			rank = exerciseService.findWeekRank(year, week);
		}

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		ArrayList<Long> userIds = new ArrayList<>();
		userIds.add(UserContext.user.getUserId());
		if (rank != null && rank.getList().size() > 0) {
			rank.getList().forEach(v -> {
				userIds.add(v.getId());	
			});
		}
		if (rank != null && rank.getList().size() > 0) {
			rank.getList().forEach(
					v -> {
						Map<String, Object> itemMap = new HashMap<>();
						UserBase userBase = UserBaseContext.getUserBaseByUserId(v.getId());
						itemMap.put("userId", v.getId());
						itemMap.put("userName", userBase.getUserName());
						String photo = PhotoUtils.getAbsoluteResizePhoto(userBase.getPhoto(), userBase.getSex(), request);
						itemMap.put("photo", photo);
						itemMap.put("total", v.getTotal());
						itemMap.put("rank", v.getRank());
						Optional<RoleSchool> firstOptional = userBase.getRoleSchoolList().stream()
								.filter(s -> s.getRoleId().equals(RoleCst.STUDENT)).findFirst();
						if (firstOptional.isPresent()) {
							String schoolName = firstOptional.get().getSchoolName();
							itemMap.put("schoolName", schoolName);
						}
						dataList.add(itemMap);
					});
			//查询当前用户是否在该排名中
			Long userId = UserContext.user.getUserId();
			Optional<ExerciseRankUser> userRank = rank.getList().stream().filter(v -> v.getId().equals(userId))
					.findFirst();
			if (userRank.isPresent()) {
				json.addDatas("myRank", userRank.get());
			} else {
				Map<String, Object> myNoRank = new HashMap<String, Object>();
				Long total = exerciseService.getRightQCountByWeek(userId,year, week,rank.getCreatedOn());
				myNoRank.put("total", total);
				myNoRank.put("rank", null);
				if(total != null){
					myNoRank.put("difference", rank.getList().get(rank.getList().size() - 1).getTotal() - total);
				}
				json.addDatas("myRank", myNoRank);
			}
		}
		return json.addDatas("dataList", dataList);
	}

	@RequestMapping("ajax/loadTodayRank")
	@ResponseBody
	public JsonResult loadTodayRank(HttpServletRequest request) {
		List<Exercise> list = exerciseService.findTodayRank();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		list.stream().forEach(v -> {
			Map<String, Object> itemMap = new HashMap<>();
			UserBase userBase = UserBaseContext.getUserBaseByUserId(v.getStudentId());
			itemMap.put("userId", v.getStudentId());
			itemMap.put("userName", userBase.getUserName());
			String photo = PhotoUtils.getAbsoluteResizePhoto(userBase.getPhoto(), userBase.getSex(), request);
			itemMap.put("photo", photo);
			itemMap.put("subjectId", v.getSubjectId());
			itemMap.put("subjectName", v.getSubjectName());
			itemMap.put("rightNum", v.getRightNum());
			itemMap.put("submitTime", DateUtils.format(new Date(v.getSubmitTime()), "HH:mm"));
			itemMap.put("schoolName", SchoolContext.getSchoolBySchoolId(v.getSchoolId()).getName());
			dataList.add(itemMap);
		});
		return new JsonResult().addDatas("dataList", dataList);
	}
	
}
