package cn.strong.leke.homework.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.context.paper.PaperContext;
import cn.strong.leke.context.user.TeacherContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.cas.utils.UrlUtils;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.spring.MessageSource;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.model.ClassHwExcelTemplate;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.HomeworkDTO;
import cn.strong.leke.homework.model.HomeworkDtl;
import cn.strong.leke.homework.model.HomeworkDtlDTO;
import cn.strong.leke.homework.model.HomeworkQuery;
import cn.strong.leke.homework.model.HomeworkType;
import cn.strong.leke.homework.model.ResType;
import cn.strong.leke.homework.model.WorkRequest;
import cn.strong.leke.homework.model.query.ClassHomeworkQuery;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.homework.service.HomeworkMainService;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.homework.service.IJobService;
import cn.strong.leke.homework.service.impl.MessageService;
import cn.strong.leke.homework.util.HomeworkCst;
import cn.strong.leke.homework.util.HomeworkUtils;
import cn.strong.leke.homework.util.SubjectEnum;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.lesson.model.TeachSubj;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.paper.PaperDTO;
import cn.strong.leke.model.paper.PaperQuery;
import cn.strong.leke.model.user.Teacher;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.model.common.PageRemote;
import cn.strong.leke.remote.model.tukor.UserRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.paper.IPaperRemoteService;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;

/**
 * 
 *
 * 描述:教师布置作业controller
 *
 * @author  basil
 * @created 2014-6-21 下午1:41:04
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/*")
public class HomeworkController {

	protected static final Logger logger = LoggerFactory.getLogger(HomeworkController.class);

	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private HomeworkService homeworkService;
	@Resource
	private IPaperRemoteService paperRemoteService;
	@Resource
	private IUserRemoteService userRemoteService;
	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private HomeworkMainService homeworkMainService;
	@Resource
	private MessageService messageService;

	@Resource
	private IJobService jobService ;
	@RequestMapping("teacher/job")
	@ResponseBody
	public void jobWeek(){
		jobService.excuteWeekHomework();
	}
	
	/**
	 * 描述:加载布置作业中,选择试卷弹出层
	 * @param model
	 * @return  void
	 */
	@RequestMapping("teacher/distribute/popPapers")
	public void popPapers(ModelMap model, String paperIds) {
		model.addAttribute("shareScopeList", PaperQuery.ShareScope.values());
		model.addAttribute("paperIds", paperIds);
	}

	/**
	 * 描述:布置作业中 加载试卷信息
	 * @author  andy
	 * @created 2014年7月1日 上午10:23:05
	 * @since   v1.0.0 
	 * @param paperQuery
	 * @param page
	 * @return
	 * @return  JsonResult
	 */
	@ResponseBody
	@RequestMapping("teacher/distribute/loadPapers")
	public JsonResult loadPapers(PaperQuery paperQuery, PageRemote<PaperDTO> page,Boolean isPage) {
		Teacher teacher = TeacherContext.teacher.get();
		paperQuery.setSchoolId(teacher.getCurrentSchool().getId());
		paperQuery.setUserId(teacher.getId());
		if (paperQuery.getShareScope() >= 10) {
			// 共享范围大于10的为联盟共享，转换字段值
			paperQuery.setLeagueId(paperQuery.getShareScope().longValue());
			// 联盟共享使用4为标识
			paperQuery.setShareScope(4);
		}
		if(isPage != null && isPage.booleanValue()){
			page = paperRemoteService.queryVisiblePapers(paperQuery, page); //分页
		}else{
			page = paperRemoteService.queryVisiblePapers(paperQuery, null); //取消分页
			if(page != null && page.getDataList().size()>0){
				page.setTotalSize(1); //标示用于前台页面空数据显示
			}
		}
		JsonResult jsonResult = new JsonResult();
		jsonResult.addDatas("page", page);
		return jsonResult;
	}
	
	/**
	 * 老师：按照班级分组，查询待批改作业数
	 */
	@RequestMapping("teacher/homework/classHomeworks")
	public void classHomeworks(Model model){
		boolean isOfflineSheet = false;
		Long schoolId = UserContext.user.getCurrentSchoolId();
		if (schoolId != null) {
			School school = SchoolContext.getSchoolBySchoolId(schoolId);
			if (school != null && school.getIsOfflineSheet() != null) {
				isOfflineSheet = school.getIsOfflineSheet();
			}
		}
		model.addAttribute("isOfflineSheet", isOfflineSheet);
	}
	
	@RequestMapping("teacher/homework/getClassHomeworksData")
	@ResponseBody
	public JsonResult getClassHomeworksData(){
		JsonResult jsonResult = new JsonResult();
		List<Map<String, Object>> datas = homeworkService.getCorrectHomeworks(UserContext.user.getUserId(),null);
		jsonResult.addDatas("dataList", datas);
		return jsonResult;
	}
	
	
	@RequestMapping("teacher/homework/homeworkList")
	public void homeworkList(Integer ord,HomeworkQuery query,Model model) throws UnsupportedEncodingException {
		model.addAttribute("ord", ord);
		model.addAttribute("classId", query.getClassId());
		if (StringUtils.hasText(query.getClassName())) {
			model.addAttribute("className", URLDecoder.decode(query.getClassName(), "UTF-8"));
		}
		boolean isOfflineSheet = false;
		Long schoolId = UserContext.user.getCurrentSchoolId();
		if (schoolId != null) {
			School school = SchoolContext.getSchoolBySchoolId(schoolId);
			if (school != null && school.getIsOfflineSheet() != null) {
				isOfflineSheet = school.getIsOfflineSheet();
			}
		}
		model.addAttribute("isOfflineSheet", isOfflineSheet);
		//查找复批作业数
		query.setTeacherId(UserContext.user.getUserId());
		Integer reCorrectNum = homeworkService.getReCorrectNum(query);
		if (reCorrectNum == null) {
			model.addAttribute("reCorrectNum", 0);
		} else if (reCorrectNum > 99) {
			model.addAttribute("reCorrectNum", "99+");
		} else {
			model.addAttribute("reCorrectNum", reCorrectNum);
		}
        List<TeachSubj> teachSubjByUserId = klassRemoteService.findTeachSubjByUserId(UserContext.user.getUserId(), UserContext.user.getCurrentSchoolId());
        if (CollectionUtils.isNotEmpty(teachSubjByUserId)) {
            for (TeachSubj teachSubj : teachSubjByUserId) {
                if (SubjectEnum.isEnglishSubject(teachSubj.getSubjectId())) {
                    model.addAttribute("isEnglish", true);
                    break;
                }
            }
        }
    }

	@RequestMapping("teacher/homework/loadHomeworkList")
	@ResponseBody
	public JsonResult loadHomeworkList(HomeworkQuery homework, Page page) {
		if (homework != null && homework.getHomeworkName() != null) {
			homework.setHomeworkName(StringUtils.trimWhitespace(homework.getHomeworkName()));
		}
		Teacher teacher = TeacherContext.teacher.get();
		homework.setTeacherId(teacher.getId());
		homework.setSchoolId(teacher.getCurrentSchool().getId());
		List<HomeworkDTO> homeworkList = homeworkService.findHomeworkList(homework, page);
		page.setDataList(homeworkList);

		JsonResult json = new JsonResult();
		json.addDatas("page", page);
		return json;
	}
	
	/*老师：已作废作业列表*/
	@RequestMapping(value = { "teacher/homework/dumpedHomeworkList" })
	public void dumpedHomeworkList() {
	}
	
	@RequestMapping("teacher/homework/loadDumpedHomeworkList")
	@ResponseBody
	public JsonResult loadDumpedHomeworkList(HomeworkQuery query, Page page) {
		Teacher teacher = TeacherContext.teacher.get();
		query.setTeacherId(teacher.getId());
		query.setSchoolId(teacher.getCurrentSchool().getId());
		List<HomeworkDTO> homeworkList = homeworkService.findDumpedHomeworkList(query, page);
		page.setDataList(homeworkList);
		
		JsonResult json = new JsonResult();
		json.addDatas("page", page);
		return json;
	}
	/**
	 * 恢复作废作业
	 * @param ids
	 * @return
	 */
	@RequestMapping("teacher/homework/recover")
	@ResponseBody
	public JsonResult recover(@RequestParam("ids[]")List<Long> ids) {
		JsonResult result = new JsonResult();
		Boolean b = this.homeworkService.recoverHomework(ids, UserContext.user.getUserId());
		if(b){
			result.setMessage("该试卷不能恢复！！");
			result.setSuccess(false);
			return result;
		}
		return result;
	}
	/**
	 * 永久删除作业
	 * @param ids
	 * @return
	 */
	@RequestMapping("teacher/homework/del")
	@ResponseBody
	public JsonResult del(@RequestParam("ids[]") List<Long> ids) {
		this.homeworkService.delHomework(ids, UserContext.user.getUserId());
		return new JsonResult();
	}

	/**
	 * 测试是否可以生成分析。
	 * @param homeworkId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("teacher/homework/tryGenerate")
	public JsonResult tryGenerate(Long homeworkId) {
		JsonResult result = new JsonResult();
		Homework homeworkDTO = this.homeworkService.getHomeworkById(homeworkId);
		if (homeworkDTO.getStatsStatus().equals(2)) {
			result.addDatas("genFlag", true);
			return result;
		}
		if (homeworkDTO.getFinishNum() <= 0) {
			result.setErr("该作业仍未有学生提交，无法生成分析！");
			return result;
		}
		if (homeworkDTO.getStatsStatus() == 1) {
			result.setErr("该作业正在统计中，请稍后再查看分析！");
			return result;
		}
		// 是否全部批改
		result.addDatas("isAllCorrect", homeworkDTO.getCorrectNum() >= homeworkDTO.getFinishNum());
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/teacher/homework/validateAnalysis")
	public JsonResult validateAnalysis(Long homeworkId) {
		JsonResult jsonResult = new JsonResult();
		Homework homework = homeworkService.getHomeworkById(homeworkId);
		Integer status = homework.getStatsStatus();
		if (status != null && HomeworkCst.HOMEWORK_STATS_STATUS_FINISH != status) {
			jsonResult.setErr("当前分析仍未生成，请等待老师生成作业分析");
		}
		return jsonResult;
	}

	/**
	 * 描述:作业明细
	 * @author  DuanYanming
	 * @created 2014年11月5日 下午2:01:27
	 * @since   v1.0.0 
	 * @param modelMap
	 * @param homeworkId
	 * @return  void
	 */
	@RequestMapping(value = { "teacher/homework/homeworkDetail", "classTeacher/homework/homeworkDetail",
			"provost/homework/homeworkDetail", "seller/homework/homeworkDetail",
			"technicalSupport/homework/homeworkDetail" })
	public String homeworkDetail(ModelMap modelMap, Long homeworkId) {
		Homework homework = homeworkService.getHomeworkById(homeworkId);
		modelMap.addAttribute("homework", homework);
		modelMap.addAttribute("homeworkTypeStr", HomeworkUtils.fmtHomeworkTypeStr(homework.getHomeworkType()));
		modelMap.addAttribute("user", UserContext.user.get());
		if (!homework.getHomeworkType().equals(HomeworkType.AUTO.value)
				&& homework.getResType().equals(HomeworkCst.HOMEWORK_RES_PAPER)) {
			PaperDTO paper = PaperContext.getPaperDTO(homework.getPaperId());
			modelMap.addAttribute("questionNum", paper.getDetail().getQuestionNum());
		}
		
		Integer resType = homework.getResType();
		if (resType != null && resType == ResType.COURSEWARE.value) {
			return "/auth/homework/coursewareDetail";
		} else if (resType != null && resType == ResType.MICROCOURSE.value) {
			return "/auth/homework/microDetail";
		} else {
			User user = UserContext.user.get();
			if(homework.getTeacherId().equals(user.getId()) && RoleCst.TEACHER.equals(user.getCurrentRole().getId())){
				return "/auth/homework/homeworkDetail";
			}else{
				return "/auth/homework/homeworkDetail_View";
			}
		}
	}
	
	/*老师：复批学生作业列表*/
	@RequestMapping(value = { "teacher/homework/reCorrectHomeworkDetail" })
	public String reCorrectHomeworkDetail(ModelMap modelMap, Long homeworkId, Boolean isExam) {
		Homework homework = homeworkService.getHomeworkById(homeworkId);
		modelMap.addAttribute("homework", homework);
		modelMap.addAttribute("homeworkTypeStr", HomeworkUtils.fmtHomeworkTypeStr(homework.getHomeworkType()));
		modelMap.addAttribute("user", UserContext.user.get());
		modelMap.addAttribute("isExam", isExam);
		return "/auth/homework/reCorrectHomeworkDetail";
	}

	
	@RequestMapping(value = { "teacher/homework/getReCorrectHomeworkDetail" })
	@ResponseBody
	public JsonResult getReCorrectHomeworkDetail(Long homeworkId,String studentName) {
		JsonResult json = new JsonResult();
		List<HomeworkDtlDTO> homeworkDtlDTOList = homeworkDtlService.findHomeworkDtlList(homeworkId,studentName);
		json.addDatas("list", homeworkDtlDTOList);
		return json;
	}
	/*复批作业：全部通过复批
	 * 
	 */
	@RequestMapping(value = { "teacher/homework/reviewBatchSumitHomeworks" })
	@ResponseBody
	public JsonResult reviewBatchSumitHomeworks(Long homeworkId) {
		JsonResult jsonResult = new JsonResult();
		WorkRequest workRequest = new WorkRequest();
		workRequest.setHomeworkId(homeworkId);
		workRequest.setUserId(UserContext.user.getUserId());
		homeworkMainService.saveReviewSubmitWithBatch(workRequest);
		return jsonResult;
	}

	/**
	 * 作业试卷的跳转
	 * @param homeworkId
	 * @param redirectAttributes
	 * @param request
	 * @return
	 */
	@RequestMapping("teacher/homework/homeworkPaper")
	public String homeworkPaper(Long homeworkId, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		Homework homework = homeworkService.getHomeworkById(homeworkId);
		if (homework == null) {
			throw new ValidateException("非法的请求参数");
		}
		redirectAttributes.addAttribute("paperId", homework.getPaperId());
		return "redirect:" + UrlUtils.getServerName(request, "paperServerName") + "/auth/common/paper/view.htm";
	}

	/**
	 * 描述:加载作业明细
	 * @author  DuanYanming
	 * @created 2014年11月5日 下午2:02:02
	 * @since   v1.0.0 
	 * @param homeworkId
	 * @return
	 * @return  JsonResult
	 */
	@ResponseBody
	@RequestMapping(value = { "teacher/homework/loadHomeworkDtlList", "classTeacher/homework/loadHomeworkDtlList",
			"provost/homework/loadHomeworkDtlList","seller/homework/loadHomeworkDtlList","technicalSupport/homework/loadHomeworkDtlList" })
	public JsonResult loadHomeworkList(Long homeworkId, String studentName, Integer questionNum, Integer sort) {
		JsonResult json = new JsonResult();
		Homework homework = this.homeworkService.getHomeworkById(homeworkId);
		List<HomeworkDtlDTO> homeworkDtlDTOList = homeworkDtlService.findHomeworkDtlList(homeworkId, studentName);
		homeworkDtlDTOList.forEach(h -> h.setUsedTime(HomeworkUtils.convertUsedTime(h.getUsedTime())));
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
		}
		json.addDatas("list", homeworkDtlDTOList);
		return json;
	}
	
	private BigDecimal formatScore(BigDecimal score, Integer sort) {
		if (score == null) {
			if (sort == 1){
				return BigDecimal.valueOf(99999);
			}
			else {
				return BigDecimal.valueOf(0);
			}
		} else {
			return score;
		}
	}

	/**
	 * 描述:更新作业发布/截至时间
	 * @author  DuanYanming
	 * @created 2015年1月13日 下午4:02:32
	 * @since   v1.0.0 
	 * @param homeworkId
	 * @param startTime
	 * @param closeTime
	 * @return  void
	 */
	@RequestMapping("teacher/homework/modifyHomeworkDate")
	@ResponseBody
	public JsonResult modifyHomeworkDate(Long homeworkId, Date startTime, Date closeTime, Date oldStartTime,
			Date oldCloseTime) {
		JsonResult json = new JsonResult();
		Date date = new Date();
		if (oldCloseTime.before(date)) {
			json.setErr(MessageSource.getMessage("homework.homework.controller.edmessage"));
			return json;
		}
		if (oldStartTime.before(date)) {
			startTime = null;
		}
		json.setMessage(MessageSource.getMessage("homework.homework.controller.modifysuc"));
		homeworkService.updateHomeworkDate(homeworkId, startTime, closeTime);
		//截止时间修改发送消息提醒
		if (closeTime != null && new Date().after(oldStartTime) && oldCloseTime.compareTo(closeTime) != 0) {
			Homework homework = homeworkService.getHomeworkById(homeworkId);
			List<Long> userIds = homeworkDtlService.findHomeworkDtlListByHomeworkId(homeworkId).stream()
					.map(s -> s.getStudentId()).collect(Collectors.toList());
			messageService.sendModifyHwCloseTimeLetterMessage(userIds, homework.getHomeworkName(), closeTime);
		}

		return json;
	}

	/**
	 * 描述:作业作废
	 * @author  DuanYanming
	 * @created 2015年1月14日 上午8:57:39
	 * @since   v1.0.0 
	 * @param homeworkId
	 * @return  void
	 */
	@RequestMapping("teacher/homework/homeworkInvalid")
	@ResponseBody
	public JsonResult homeworkInvalid(Long homeworkId) {
		Long teacherId = UserContext.user.getUserId();
		homeworkService.updateHomeworkInvalid(homeworkId, teacherId);
		return new JsonResult();
	}

	/**
	 *	
	 * 描述:返回教务作业列表页面
	 *
	 * @author  DuanYanming
	 * @created 2014年7月25日 下午4:37:40
	 * @since   v1.0.0 
	 * @return  void
	 */
	@RequestMapping("provost/homework/homeworkList")
	public void provostHomeworkList() {
	}

	/**
	 *	
	 * 描述:教务作业列表
	 *
	 * @author  DuanYanming
	 * @created 2014年7月25日 下午2:38:15
	 * @since   v1.0.0 
	 * @param page
	 * @return
	 * @return  JsonResult
	 */
	@RequestMapping({"provost/homework/loadHomeworkList","classTeacher/homework/loadHomeworkList"})
	@ResponseBody
	public JsonResult loadProvostHomeworkList(ClassHomeworkQuery query, Page page) {
		if (query != null) {
			if (query.getTeacherName() != null) {
				query.setTeacherName(StringUtils.trimWhitespace(query.getTeacherName()));
			}
			if (query.getHomeworkName() != null) {
				query.setHomeworkName(StringUtils.trimWhitespace(query.getHomeworkName()));
			}
		}
		JsonResult json = new JsonResult();
		query.setSchoolId(UserContext.user.getCurrentSchoolId());
		if(query.getCloseTime() != null){
			query.setCloseTime(DateUtils.addDays(query.getCloseTime(), 1));
		}
		setQueryClassId(query);
		List<HomeworkDTO> homeworkList = new ArrayList<HomeworkDTO>();
		if(!(query.getClassId() == null && query.getGradeId() != null) ){
			homeworkList = homeworkService.findProvostHomeworkList(query, page);
		}
		page.setDataList(homeworkList);
		json.addDatas("page", page);
		return json;
	}
	
	@RequestMapping(value = { "provost/homework/exportClassHomeWork","classTeacher/homework/exportClassHomeWork" })
	@ResponseBody
	public void exportClassHomeWork(ClassHomeworkQuery query,HttpServletRequest request,
			HttpServletResponse response) {
		try {
			User user = UserContext.user.get();
			query.setSchoolId(user.getCurrentSchool().getId());
			if(query.getCloseTime() != null){
				query.setCloseTime(DateUtils.addDays(query.getCloseTime(), 1));
			}
			setQueryClassId(query);
			List<HomeworkDTO> homeworkList = new ArrayList<HomeworkDTO>();
			if (!(query.getClassId() == null && query.getGradeId() != null)) {
				homeworkList = homeworkService.findProvostHomeworkList(query, null);
			}
			String[] headers = { "老师", "学科", "布置班级", "开始/截止时间", "作业标题", "完成情况", "批改情况", "平均分" };
			String[] fieldNames = { "teacherName", "subjectName", "className", "startFinishTime", "homeworkName","finishInfo",
					"correctInfo", "avgScore" };
			//装载成导出excle的对象
			List<ClassHwExcelTemplate> excelDataList = new ArrayList<ClassHwExcelTemplate>();
			for (HomeworkDTO hw : homeworkList) {
				ClassHwExcelTemplate item = new ClassHwExcelTemplate();
				item.setTeacherName(hw.getTeacherName());
				item.setClassName(hw.getClassName());
				item.setSubjectName(hw.getSubjectName());
				item.setHomeworkName("[" + hw.getHomeworkTypeStr() + "]" + hw.getHomeworkName());
				item.setFinishInfo(hw.getFinishNumStr() + "/" + hw.getTotalNum());
				item.setCorrectInfo(hw.getCorrectNumStr() + "/" + hw.getFinishNumStr());
				item.setAvgScore(hw.getAvgScore() == null ? "--" : hw.getAvgScore().toString());
				item.setStartFinishTime(DateUtils.formatTime(hw.getStartTime()) + " / "
						+ DateUtils.formatTime(hw.getCloseTime()));
				excelDataList.add(item);
			}

			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream;charset=UTF-8");
			String agent = request.getHeader("user-agent");
			String fileName = "班级作业";
			if(RoleCst.TEACHER_HEADER.equals(user.getCurrentRole().getId())){
				fileName = request.getParameter("fileName").toString();
			}
			fileName += "-" + DateUtils.format(new Date(), DateUtils.LONG_DATE_PATTERN_NOSPLIT);
			fileName = FileUtils.getEncodingFileName(fileName + ".xls", agent);
			StringBuffer sb = new StringBuffer();
			sb.append("attachment;").append(fileName);
			response.setHeader("Content-disposition", sb.toString());
			new ExportExcelForTable<ClassHwExcelTemplate>().exportExcel("班级作业", headers, fieldNames, excelDataList,
					response.getOutputStream(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据年级设置班级Id集合
	 * @param query
	 */
	private void setQueryClassId(ClassHomeworkQuery query) {
		if (query.getClassId() != null && query.getClassId().size() > 0) {
			return;
		} else {
			Long gradeId = query.getGradeId();
			if (gradeId == null) {
				query.setClassId(null);
			} else {
				ClazzQuery clazzQuery = new ClazzQuery();
				clazzQuery.setGradeId(query.getGradeId());
				clazzQuery.setType(query.getClassType());
				List<Clazz> clazzs = getGradeClazz(clazzQuery);
				if(clazzs != null && clazzs.size()>0){
					query.setClassId(clazzs.stream().map(c -> c.getClassId()).collect(Collectors.toList()));
				}else{
					query.setClassId(null);
				}
			}
		}
	}

	@ResponseBody
	@RequestMapping("provost/homework/teacherList")
	public List<UserRemote> teacherList(Long subjectId) {
		Long schoolId = UserContext.user.getCurrentSchoolId();
		List<UserRemote> teacherList = this.userRemoteService
				.findTeacherListBySchoolIdAndSubjectId(schoolId, subjectId);
		return teacherList;
	}

	@RequestMapping("provost/homework/getClasses")
	@ResponseBody
	public JsonResult getClasses(ClazzQuery query) {
		return new JsonResult().addDatas("classes", getGradeClazz(query));
	}
	
	private List<Clazz> getGradeClazz(ClazzQuery query){
		User user = UserContext.user.get();
		query.setUserId(user.getId());
		query.setRoleId(user.getCurrentRole().getId());
		query.setSchoolId(user.getCurrentSchool().getId());
		return this.klassRemoteService.findClazzByQuery(query);
	}
	
	/*操作公布答案*/
	@RequestMapping("teacher/homework/openAnswer")
	@ResponseBody
	public JsonResult openAnswer(Long homeworkId) {
		JsonResult jsonResult = new JsonResult();
		Integer result = homeworkService.saveOpenAnswer(homeworkId, UserContext.user.getUserId());
		if (result < 0) {
			jsonResult.setErr("操作失败");
		}
		return jsonResult;
	}

	/*操作自行校对*/
	@RequestMapping("teacher/homework/selfCheck")
	@ResponseBody
	public JsonResult selfCheck(Long homeworkId) {
		JsonResult jsonResult = new JsonResult();
		Long teacherId = UserContext.user.getUserId();
		Integer result = homeworkService.saveSelfCheck(homeworkId, teacherId);
		if (result < 0) {
			jsonResult.setErr("操作失败");
		}
		return jsonResult;
	}
	
	/*复批列表页面 page*/
	@RequestMapping("teacher/homework/reCorrectHomeworkList")
	public void  reCorrectHomeworkList(Long classId,String className,Model model) throws UnsupportedEncodingException{
		model.addAttribute("classId", classId);
		if(StringUtils.hasText(className)){
			model.addAttribute("className", URLDecoder.decode(className, "UTF-8"));
		}
	}
	
	@RequestMapping("teacher/homework/queryReCorrectHomeworkList")
	@ResponseBody
	public JsonResult queryReCorrectHomeworkList(HomeworkQuery query,Page page){
		JsonResult jsonResult = new JsonResult();
		query.setTeacherId(UserContext.user.getUserId());
		List<Map<String, Object>>  datas= homeworkService.queryReCorrectHomeworkList(query,page);
		datas.forEach(map->{
			map.put("homeworkTypeStr",HomeworkUtils.fmtHomeworkTypeStr(Integer.parseInt(map.get("homeworkType").toString())));
		});
		page.setDataList(datas);
		jsonResult.addDatas("page", page);
		return jsonResult;
	}
	
	@RequestMapping("teacher/homework/cuijiao")
	@ResponseBody
	public JsonResult cuijiao(Long homeworkId) {
		JsonResult json = new JsonResult();
		Homework homework = this.homeworkService.getHomeworkById(homeworkId);
		List<HomeworkDtl> homeworkDtls = this.homeworkDtlService.findHomeworkDtlListByHomeworkId(homeworkId);
		json.addDatas("past", System.currentTimeMillis() - homework.getCloseTime().getTime() > 0);
		if(CollectionUtils.isEmpty(homeworkDtls)){
			json.setErr("该作业没有学生~");
		}else{
			homeworkDtls = homeworkDtls.stream().filter(v->v.getSubmitTime() == null).collect(Collectors.toList());
			if(CollectionUtils.isEmpty(homeworkDtls)){
				json.setErr("所有学生已提交~");
			}else{
				json.addDatas("stuCount", homeworkDtls.size());
				json.addDatas("time", HomeworkUtils.fmtDiffTime(homework.getCloseTime().getTime()));
			}
		}
		return json;
	}
	
	@RequestMapping("teacher/homework/doCuijiao")
	@ResponseBody
	public JsonResult doCuijiao(Long homeworkId) {
		this.homeworkMainService.cuijiaoHomework(homeworkId);
		return new JsonResult();
	}

}
