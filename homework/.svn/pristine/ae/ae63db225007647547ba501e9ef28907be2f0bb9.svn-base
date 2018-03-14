package cn.strong.leke.homework.controller.work;

import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Maps;

import cn.strong.leke.beike.model.CoursewareDTO;
import cn.strong.leke.beike.model.MicrocourseDTO;
import cn.strong.leke.beike.model.Types;
import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.beike.CoursewareContext;
import cn.strong.leke.context.beike.MicrocourseContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.homework.manage.FileDescService;
import cn.strong.leke.homework.manage.HwQueCommentaryService;
import cn.strong.leke.homework.manage.WorkDetailService;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.HomeworkDtl;
import cn.strong.leke.homework.model.WebWorkModel;
import cn.strong.leke.homework.model.WorkDetail;
import cn.strong.leke.homework.model.mobile.FileDesc;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.homework.service.HomeworkMainService;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.homework.util.HomeworkCst;
import cn.strong.leke.model.question.QuestionResult;
import cn.strong.leke.model.user.User;
import cn.strong.leke.tags.answer.ResDataModel;
import cn.strong.leke.tags.question.prepare.PrepareHandler;
import cn.strong.leke.tags.question.prepare.PrepareHandlerAdapter;

/**
 * 学生查看作业
 * @author  andy
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/student/homework/*")
public class ViewStudentController {

	protected static final Logger logger = LoggerFactory.getLogger(ViewStudentController.class);

	@Resource
	private HomeworkService homeworkService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private HomeworkMainService homeworkMainService;
	@Resource
	private WorkDetailService workDetailService;
	@Resource
	private FileDescService fileDescService;
	@Resource
	private HwQueCommentaryService hwQueCommentaryService;

	/**
	 * 查看试卷信息。<br>
	 * 注意：如果学生已经提交但还未批改，只显示学生答题信息，如果学生已经提交并已批改，显示学生答案、正确答案、试题解析。<br>
	 * 权限：学生和老师都可以访问，根据当前用户判断是否有访问权限。
	 * @param homeworkDtlId 学生试卷标识
	 * @param model
	 */
	@RequestMapping("viewWork")
	public String viewWork(Long homeworkDtlId, Long questionId, String type, Model model, HttpServletRequest request) {
		// 作业信息
		User user = UserContext.user.get();
		HomeworkDtl homeworkDtl = this.homeworkDtlService.getHomeworkDtlById(homeworkDtlId);
		Validation.isPageNotFound(homeworkDtl == null);
		Validation.isPageNotFound(RoleCst.STUDENT.equals(user.getCurrentRole().getId())
				&& !user.getId().equals(homeworkDtl.getStudentId()));
		Homework homework = this.homeworkService.getHomeworkById(homeworkDtl.getHomeworkId());
		if (homework.getExam()) {
			//对考试时间的逻辑判断
			if (homework.getStartTime().getTime() > System.currentTimeMillis()) {
				throw new ValidateException("考试时间还未开始！");
			}
			if (homework.getStartTime().getTime() < System.currentTimeMillis() && homework.getCloseTime().getTime() > System.currentTimeMillis() && homeworkDtl.getSubmitTime() == null) {
				throw new ValidateException("请移步至平板端进行考试！");
			}
		}

		model.addAttribute("globalNoteParams", WorkSupport.buildGlobalNoteParams(homework, homeworkDtl));

		Integer resType = homework.getResType();
		if (resType == HomeworkCst.HOMEWORK_RES_PAPER) {//试卷
			ResDataModel respond = WorkSupport.buildResDataModel(user, ResDataModel.DO_VIEWER, homework, homeworkDtl);
			// 答题信息
			if (homeworkDtl.getModifiedOn() != null) {
				// 已保存的试卷答题及批改信息
				WorkDetail workDetail = this.workDetailService.getWorkDetailByHomeworkDtlId(homeworkDtlId);
				if (workDetail != null) {
					//合并新存储的微课批注信息
					hwQueCommentaryService.mergeWorkDetailForVodMic(workDetail);
					PrepareHandlerAdapter.doPrepare(PrepareHandler.Mode.View, workDetail.getQuestions());

					Map<Long, QuestionResult> questionResultMap = workDetail.getQuestions().stream()
							.collect(Collectors.toMap(v -> v.getQuestionId(), v -> v));
					model.addAttribute("sheetBookId", workDetail.getSheetBookId());
					respond.setQuestionResultMap(questionResultMap);
				}
			}
			WebWorkModel webWorkModel = WorkSupport.buildWebWorkModel(respond, homework, homeworkDtl);
			model.addAttribute("paper", WorkSupport.getOrBuildPaperDTO(homework, homeworkDtl));
			model.addAttribute("respond", respond);
			model.addAttribute("workModel", JsonUtils.toJSON(webWorkModel));
			// 批改前不显示正确答案和试题解析，批改后显示
			if (questionId != null && type.equals("error")) {
				//答题卡错题补充题干
				model.addAttribute("questionId", questionId);
				model.addAttribute("questionStem", "1");
			}
			return "/auth/work/doViewerWork";
		}

		model.addAttribute("homework", homework);
		model.addAttribute("homeworkDtl", homeworkDtl);

		// 非试卷作业：每次做题时，更新查看时间
		this.homeworkMainService.saveStartWork(homeworkDtlId, HomeworkCst.HOMEWORK_DATA_SOURCE_WEBSITE);
		Integer position = this.workDetailService.getPosition(homeworkDtlId);
		if (resType == HomeworkCst.HOMEWORK_RES_MICROCOURSE) {//微课
			MicrocourseDTO microcourse = MicrocourseContext.getMicrocourse(homework.getPaperId());
			if (microcourse.getMicrocourseType() == 1) {
				StringBuilder builder = new StringBuilder();
				builder.append("redirect:");
				builder.append(request.getServletContext().getInitParameter("onlineServerName"));
				builder.append("/view/service.php?action=recordStart&mode=2");
				builder.append("&coursewareId=").append(microcourse.getCrcwId());
				builder.append("&recordId=").append(microcourse.getRecordId());
				return builder.toString();
			} else if (microcourse.getMicrocourseType() == 3) {
				Map<String, Object> Csts = Maps.newHashMap();
				Csts.put("position", position);
				Csts.put("homeworkId", homeworkDtl.getHomeworkId());
				Csts.put("homeworkDtlId", homeworkDtl.getHomeworkDtlId());
				Csts.put("status", homeworkDtl.getSubmitStatus());
				model.addAttribute("microcourse", microcourse);
				model.addAttribute("Csts", JsonUtils.toJSON(Csts));
				return "/auth/student/homework/doMicroWorkForNewVod";
			} else {
				FileDesc fileDesc = this.fileDescService.convertToFileDesc(microcourse);
				Map<String, Object> Csts = Maps.newHashMap();
				Csts.put("file", fileDesc);
				Csts.put("position", position);
				Csts.put("isSubmit", homeworkDtl.getSubmitTime() != null);
				Csts.put("homeworkId", homeworkDtl.getHomeworkId());
				Csts.put("homeworkDtlId", homeworkDtl.getHomeworkDtlId());
				model.addAttribute("microcourse", microcourse);
				model.addAttribute("type", Types.valueOf(microcourse.getMicrocourseFile().getType()));
				model.addAttribute("Csts", JsonUtils.toJSON(Csts));
				return "/auth/student/homework/doMicroWork";
			}
		} else {//课件
			CoursewareDTO courseware = CoursewareContext.getCourseware(homework.getPaperId());
			FileDesc fileDesc = this.fileDescService.convertToFileDesc(courseware);
			Map<String, Object> Csts = Maps.newHashMap();
			Csts.put("file", fileDesc);
			Csts.put("position", position);
			Csts.put("isSubmit", homeworkDtl.getSubmitTime() != null);
			Csts.put("homeworkId", homeworkDtl.getHomeworkId());
			Csts.put("homeworkDtlId", homeworkDtl.getHomeworkDtlId());
			model.addAttribute("courseware", courseware);
			model.addAttribute("type", Types.valueOf(courseware.getType()));
			model.addAttribute("Csts", JsonUtils.toJSON(Csts));
			return "/auth/student/homework/doCourseWork";
		}
	}

	/**
	 * 学生作业跳转
	 * @param homeworkId 老师作业ID
	 * @return
	 */
	@RequestMapping("redirectViewWork")
	public String redirectViewWork(@RequestParam("homeworkId") Long homeworkId) {
		Long studentId = UserContext.user.getUserId();
		HomeworkDtl homeworkDtl = this.homeworkDtlService.getHomeworkDtlByHomeworkIdAndStudentId(homeworkId, studentId);
		Validation.isPageNotFound(homeworkDtl == null);
		return "redirect:/auth/student/homework/viewWork.htm?homeworkDtlId=" + homeworkDtl.getHomeworkDtlId();
	}
}
