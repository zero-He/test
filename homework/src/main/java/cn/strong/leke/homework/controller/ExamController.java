package cn.strong.leke.homework.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.context.paper.PaperContext;
import cn.strong.leke.context.user.TeacherContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.pinyin.Pinyin;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.manage.WorkDetailService;
import cn.strong.leke.homework.model.ExamStutus;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.HomeworkDTO;
import cn.strong.leke.homework.model.HomeworkDtlDTO;
import cn.strong.leke.homework.model.HomeworkType;
import cn.strong.leke.homework.model.LayerAssign;
import cn.strong.leke.homework.model.LayerClazz;
import cn.strong.leke.homework.model.MyHomeworkDTO;
import cn.strong.leke.homework.model.QuestionProgress;
import cn.strong.leke.homework.model.ResType;
import cn.strong.leke.homework.model.query.ExamStuQuery;
import cn.strong.leke.homework.model.query.ExamTeaQuery;
import cn.strong.leke.homework.service.HomeworkAssignService;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.homework.service.IExamService;
import cn.strong.leke.homework.util.HomeworkCst;
import cn.strong.leke.homework.util.HomeworkUtils;
import cn.strong.leke.homework.util.SubjectEnum;
import cn.strong.leke.lesson.model.TeachSubj;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.paper.PaperDTO;
import cn.strong.leke.model.user.Teacher;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.model.user.ClazzGroupRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.lesson.IStudentGroupRemoteService;

/**
 * 在线考试控制器
 * @Author LIU.SHITING
 * @Version 2.7
 * @Date 2017-05-18 13:53:15
 */
@Controller
@RequestMapping("/auth/*")
public class ExamController {

	protected static final Logger logger = LoggerFactory.getLogger(ExamController.class);

	@Resource
	private IExamService examService;
	@Resource
	private HomeworkAssignService homeworkAssignService;
	@Resource
	private HomeworkService homeworkService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private WorkDetailService workDetailService;
	@Resource
	private IStudentGroupRemoteService studentGroupRemoteService;

	/**
	 * 老师在线考试列表页
	 * @return void
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/19 10:05
	 * @Param [query, model]
	 */
	@RequestMapping(value = "teacher/exam/teaOnlineExamList", method = RequestMethod.GET)
	public String teaOnlineExamList(ExamTeaQuery query, Model model) throws UnsupportedEncodingException {
		boolean isOfflineSheet = false;
		Long schoolId = UserContext.user.getCurrentSchoolId();
		if (schoolId != null) {
			School school = SchoolContext.getSchoolBySchoolId(schoolId);
			if (school != null && school.getIsOfflineSheet() != null) {
				isOfflineSheet = school.getIsOfflineSheet();
			}
		}
		model.addAttribute("isOfflineSheet", isOfflineSheet);

		List<TeachSubj> teachSubjByUserId = klassRemoteService.findTeachSubjByUserId(UserContext.user.getUserId(), UserContext.user.getCurrentSchoolId());
		if (CollectionUtils.isNotEmpty(teachSubjByUserId)) {
			for (TeachSubj teachSubj : teachSubjByUserId) {
				if (SubjectEnum.isEnglishSubject(teachSubj.getSubjectId())) {
					model.addAttribute("isEnglish", true);
					break;
				}
			}
		}
		return "/auth/exam/teaOnlineExamList";
	}

	/**
	 * 老师在线考试列表页数据拉取
	 * @return cn.strong.leke.framework.web.JsonResult
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/19 10:04
	 * @Param [examQuery, page]
	 */
	@ResponseBody
	@RequestMapping(value = "teacher/exam/queryTeaOnlineExamListData", method = RequestMethod.POST)
	public JsonResult queryTeaOnlineExamListData(ExamTeaQuery examQuery, Page page) {
		if (examQuery != null && examQuery.getHomeworkName() != null) {
			examQuery.setHomeworkName(StringUtils.trimWhitespace(examQuery.getHomeworkName()));
		}
		Teacher teacher = TeacherContext.teacher.get();
		examQuery.setTeacherId(teacher.getId());
		examQuery.setSchoolId(teacher.getCurrentSchool().getId());
		List<HomeworkDTO> homeworkList = examService.queryTeacherExamList(examQuery, page);
		page.setDataList(homeworkList);

		JsonResult result = new JsonResult();
		result.addDatas("page", page);
		return result;
	}

	/**
	 * 老师点击批改或查看跳转的页面
	 * @return java.lang.String
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/18 20:38
	 * @Param [modelMap, homeworkId]
	 */
	@RequestMapping(value = "teacher/exam/teaOnlineExamDetail", method = RequestMethod.GET)
	public String teaOnlineExamDetail(ModelMap modelMap, Long homeworkId) {
		Homework homework = homeworkService.getHomeworkById(homeworkId);
		modelMap.addAttribute("homework", homework);
		modelMap.addAttribute("closeTime", homework.getCloseTime().getTime());
		modelMap.addAttribute("homeworkTypeStr", HomeworkUtils.fmtHomeworkTypeStr(homework.getHomeworkType()));
		modelMap.addAttribute("user", UserContext.user.get());
		if (homework.getHomeworkType().equals(HomeworkType.Exam.value) && homework.getResType().equals(HomeworkCst.HOMEWORK_RES_PAPER)) {
			PaperDTO paper = PaperContext.getPaperDTO(homework.getPaperId());
			modelMap.addAttribute("questionNum", paper.getDetail().getQuestionNum());
		}
		return "/auth/exam/teaOnlineExamDetail";
	}

	/**
	 * 加载老师在线考试明细
	 * @return cn.strong.leke.framework.web.JsonResult
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/18 20:53
	 * @Param [homeworkId, studentName, questionNum, sort]
	 */
	@ResponseBody
	@RequestMapping(value = "teacher/exam/queryTeaOnlineExamDetailData", method = RequestMethod.POST)
	public JsonResult queryTeaOnlineExamDetailData(Long homeworkId, String studentName, Integer questionNum, Integer sort) {
		List<HomeworkDtlDTO> homeworkDtlDTOList = homeworkDtlService.findHomeworkDtlList(homeworkId, studentName);
		homeworkDtlDTOList.forEach(h -> h.setUsedTime(HomeworkUtils.convertUsedTime(h.getUsedTime())));
		Homework homework = this.homeworkService.getHomeworkById(homeworkId);
		if (homework.getResType() == ResType.PAPER.value) {
			if (questionNum != null) {
				homeworkDtlDTOList.forEach(h -> h.setPaperQuestionNum(questionNum));
			} else {
				List<String> paperIds = homeworkDtlDTOList.stream().map(h -> h.getPaperId()).collect(Collectors.toList());
				Map<String, Integer> questionNumMap = homeworkDtlService.findPaperQuestionNums(paperIds);
				homeworkDtlDTOList.forEach(v -> v.setPaperQuestionNum(questionNumMap.get(v.getPaperId())));
			}
		}
		//排序
		if (sort != null) {
			if (sort == 1) {
				//按照得分正序
				homeworkDtlDTOList.sort((a, b) -> {
					return formatScore(a.getScore(), sort).compareTo(formatScore(b.getScore(), sort));
				});

			} else {
				//按照得分倒序
				homeworkDtlDTOList.sort((a, b) -> {
					return formatScore(b.getScore(), sort).compareTo(formatScore(a.getScore(), sort));
				});
			}
		} else {
			homeworkDtlDTOList.sort((a, b) -> {
				return Pinyin.toPinyinAbbr(a.getStudentName()).compareTo(Pinyin.toPinyinAbbr(b.getStudentName()));
			});
		}
		JsonResult result = new JsonResult();
		result.addDatas("list", homeworkDtlDTOList);
		return result;
	}

	private BigDecimal formatScore(BigDecimal score, Integer sort) {
		if (score == null) {
			if (sort == 1) {
				return BigDecimal.valueOf(99999);
			} else {
				return BigDecimal.valueOf(0);
			}
		} else {
			return score;
		}
	}

	/**
	 * 老师按题批改
	 * @return java.lang.String
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/6/3 10:11
	 * @Param [homeworkId, model]
	 */
	@RequestMapping(value = "teacher/exam/batchByQuestions", method = RequestMethod.GET)
	public String batchByQuestions(Long homeworkId, Model model) {
		Homework homework = homeworkService.getHomeworkById(homeworkId);
		PaperDTO paperDto = PaperContext.getPaperDTO(homework.getPaperId());
		List<QuestionProgress> questionProgresses = workDetailService.findBatchProgressByHomeworkId(homeworkId);
		Map<String, Object> progressMaps = questionProgresses.stream().collect(Collectors.toMap(v -> v.getQuestionId().toString(), v -> v.getCorrectNum() == null ? 0 : v.getCorrectNum()));
		model.addAttribute("homework", homework);
		model.addAttribute("homeworkId", homeworkId);
		model.addAttribute("progressMaps", progressMaps);
		model.addAttribute("groups", paperDto.getDetail().getGroups());
		model.addAttribute("paperAttachement", paperDto.getAttachment());
		model.addAttribute("finishNum", homework.getFinishNum());
		model.addAttribute("homeworkTypeStr", HomeworkUtils.fmtHomeworkTypeStr(homework.getHomeworkType()));
		model.addAttribute("paperType", paperDto.getPaperType());
		return "/auth/exam/batchByQuestions";
	}


	/**
	 * 老师根据homeworkId修改考试开始时间
	 * @return cn.strong.leke.framework.web.JsonResult
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/19 10:15
	 * @Param [homeworkId, startTime]
	 */
	@ResponseBody
	@RequestMapping(value = "teacher/exam/updateOnlineExamDate", method = RequestMethod.POST)
	public JsonResult updateOnlineExamDate(Long homeworkId, Date startTime) {
		examService.updateTeacherExamDate(homeworkId, startTime);
		return new JsonResult();
	}

	/**
	 * 学生在线考试列表页
	 * @return java.lang.String
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/19 11:00
	 * @Param [model]
	 */
	@RequestMapping(value = "student/exam/stuOnlineExamList", method = RequestMethod.GET)
	public String stuOnlineExamList(Model model) {
		return "/auth/exam/stuOnlineExamList";
	}

	/**
	 * 获取学生在线考试列表页数据
	 * @return cn.strong.leke.framework.web.JsonResult
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/19 11:02
	 * @Param [homeworkDtlQuery, page]
	 */
	@ResponseBody
	@RequestMapping(value = "student/exam/queryStuOnlineExamListData", method = RequestMethod.POST)
	public JsonResult queryStuOnlineExamListData(ExamStuQuery query, Page page) {
		User user = UserContext.user.get();
		if (RoleCst.STUDENT.equals(user.getCurrentRole().getId())) {
			query.setStudentId(user.getId());
		}
		List<MyHomeworkDTO> homeworkList = examService.queryStuOnlineExamListData(query, page);
		homeworkList.forEach((x) -> {
			if (new Date().before(x.getStartTime())) {
				x.setExamStatus(ExamStutus.NOTSTART.key);
			} else if (x.getCloseTime().before(new Date())) {
				x.setExamStatus(ExamStutus.ISOVER.key);
			} else {
				x.setExamStatus(ExamStutus.EXAMING.key);
			}
		});
		page.setDataList(homeworkList);
		return new JsonResult().addDatas("page", page);
	}

	/**
	 * 在线考试试卷发布跳转页
	 * @return void
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/23 9:56
	 * @Param [model]
	 */
	@RequestMapping(value = "teacher/exam/assign/index", method = RequestMethod.GET)
	public String index(Model model) {
		List<LayerAssign.AssignResource> resources = Collections.emptyList();
		model.addAttribute("resJson", JsonUtils.toJSON(resources));

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
				}).collect(Collectors.toList()));
			}
			return clazz;
		}).sorted((a, b) -> {
			int result = Integer.compare(a.getClassType(), b.getClassType());
			if (result == 0) {
				result = Long.compare(b.getClassId(), a.getClassId());
			}
			return result;
		}).collect(Collectors.toList());
		model.addAttribute("classes", JsonUtils.toJSON(classes));
		return "auth/exam/assign/index";
	}

	/**
	 * 在线考试试卷发布保存
	 * @param dataJson
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "teacher/exam/assign/saveAssign", method = RequestMethod.POST)
	public JsonResult saveAssign(String dataJson) {
		LayerAssign assign = JsonUtils.fromJSON(dataJson, LayerAssign.class);
		User user = UserContext.user.get(); 
		assign.setTeacherId(user.getId());
		assign.setTeacherName(user.getUserName());
		assign.setSchoolId(user.getCurrentSchool().getId());
		assign.setIsExam(true);
		Validation.isTrue(assign.getStartTime().getTime() > System.currentTimeMillis(), "请重新选择考试开始时间！");
		for (LayerAssign.Section section : assign.getSections()) {
			Validation.isTrue(section.getResources().size() == 1, "只能选择一份试卷用于考试，请重新选择！");
		}
		List<Long> homeworkIds = this.homeworkAssignService.saveAssign(assign);
		Long homeworkId = homeworkIds.size() == 1 ? homeworkIds.get(0) : null;
		return new JsonResult().addDatas("homeworkId", homeworkId);
	}

	/**
	 * 在线考试试卷发布成功跳转页
	 * @param homeworkId
	 * @param model
	 */
	@RequestMapping("teacher/exam/assign/success")
	public String success(Long homeworkId, Model model) {
		if (homeworkId != null) {
			Homework homework = this.homeworkService.getHomeworkById(homeworkId);
			model.addAttribute("homeworkId", homeworkId);
			model.addAttribute("paperId", homework.getPaperId());
			model.addAttribute("resType", homework.getResType());
		}
		return "auth/exam/assign/success";
	}

}
