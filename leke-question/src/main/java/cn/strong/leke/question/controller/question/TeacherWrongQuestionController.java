package cn.strong.leke.question.controller.question;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.lesson.model.TeachSubj;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.base.SchoolCst;
import cn.strong.leke.model.base.Subject;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.wrongquestion.WrongQuestion;
import cn.strong.leke.question.model.wrongquestion.WrongQuestionQuery;
import cn.strong.leke.question.model.wrongquestion.WrongSubjKnowQuery;
import cn.strong.leke.question.model.wrongquestion.WrongSubject;
import cn.strong.leke.question.model.wrongquestion.WrongSubjectKnowledge;
import cn.strong.leke.question.service.IWrongQuestionService;
import cn.strong.leke.remote.model.user.ClazzGroupRemote;
import cn.strong.leke.remote.service.homework.IHomeworkRemoteService;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.lesson.IStudentGroupRemoteService;

@Controller
@RequestMapping("/auth/teacher/question/wrong")
public class TeacherWrongQuestionController {

	@Resource
	private IWrongQuestionService wrongQuestionService;
	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private IStudentGroupRemoteService studentGroupRemoteService;
	@Resource 
	private IHomeworkRemoteService homeworkRemoteService;
	

	@RequestMapping("list")
	public void list(Model model) {
		Long userId = UserContext.user.getUserId();
		Long schoolId = UserContext.user.getCurrentSchoolId();
		List<Subject> subjects = getTacherSubject(userId, schoolId);
		model.addAttribute("subjects", JsonUtils.toJSON(subjects));
		List<Clazz> clazzs = findTeacherClazz(userId);
		model.addAttribute("clazzs", JsonUtils.toJSON(clazzs));
	}

	/**
	 * @param userId
	 * @param schoolId
	 * @return
	 */
	public List<Subject> getTacherSubject(Long userId, Long schoolId) {
		Integer curUserSchoolNature = SchoolContext.getSchoolBySchoolId(schoolId).getSchoolNature();
		List<Subject> subjects;
		if (SchoolCst.NATURE_UNIT == curUserSchoolNature) {
			List<Long> subjectIds = homeworkRemoteService.findSubjectIdFromHomework(userId, schoolId);
			if (CollectionUtils.isEmpty(subjectIds)) {
				throw new ValidateException("老师尚未布置作业，没有错题哟");
			}
			subjects = subjectIds.stream().map(subjectId -> {
				Subject subject = new Subject();
				subject.setSubjectId(subjectId);
				subject.setSubjectName(SubjectContext.getSubject(subjectId).getSubjectName());
				return subject;
			}).sorted((a, b) -> a.getSubjectId().compareTo(b.getSubjectId())).collect(Collectors.toList());
		} else {
			List<TeachSubj> teachSubjs = klassRemoteService.findTeachSubjByUserId(userId, schoolId);
			if (CollectionUtils.isEmpty(teachSubjs)) {
				throw new ValidateException("老师未设置任教班级~");
			}
			//老师任教的所有学科
			subjects = teachSubjs.stream().map(v -> v.getSubjectId()).distinct().map(subjectId -> {
				Subject subject = new Subject();
				subject.setSubjectId(subjectId);
				subject.setSubjectName(SubjectContext.getSubject(subjectId).getSubjectName());
				return subject;
			}).sorted((a, b) -> a.getSubjectId().compareTo(b.getSubjectId())).collect(Collectors.toList());
			return subjects;
		}
		return subjects;
	}

	/**
	 * @param userId
	 * @return
	 */
	public List<Clazz> findTeacherClazz(Long userId) {
		//行政班 + 选修班 + 分层班(学校分层和个人分层)
		List<ClazzGroupRemote> clazzList = studentGroupRemoteService.findClazzGroupByTeacherId(userId);
		List<Clazz> clazzs = clazzList.stream().map(v -> {
			Clazz clazz = new Clazz();
			clazz.setClassId(v.getClassId());
			clazz.setClassName(v.getClassName());
			clazz.setType(v.getType());
			return clazz;
		}).sorted((a, b) -> {
			int result = Integer.compare(a.getType(), b.getType());
			if (result == 0) {
				result = Long.compare(b.getClassId(), a.getClassId());
			}
			return result;
		}).collect(toList());
		return clazzs;
	}

	@RequestMapping("details")
	public void details(WrongQuestionQuery query, Page page, Model model) {
		if (query.getMinRate() == null) {
			query.setMinRate(BigDecimal.valueOf(0L));
		}
		if (query.getMaxRate() == null) {
			query.setMaxRate(BigDecimal.valueOf(100L));
		}
		User user = UserContext.user.get();
		query.setUserId(user.getId());
		query.setSchoolId(user.getCurrentSchool().getId());
		List<WrongQuestion> records = wrongQuestionService.findWrongQuestion(query, page);
		model.addAttribute("records", records);
		model.addAttribute("page", page);
	}

	@ResponseBody
	@RequestMapping("findWrongSubjectKnowledges")
	public JsonResult findWrongSubjectKnowledges(WrongSubjKnowQuery query) {
		JsonResult jResult = new JsonResult();
		User user = UserContext.user.get();
		query.setUserId(user.getId());
		query.setSchoolId(user.getCurrentSchool().getId());
		List<WrongSubjectKnowledge> wrongKlgs = wrongQuestionService.findWrongSubjectKnowledges(query);
		jResult.addDatas("wrongKlgs", wrongKlgs);
		return jResult;
	}

	@ResponseBody
	@RequestMapping("getWrongSubject")
	public JsonResult getWrongSubject(Long classId, Long subjectId) {
		JsonResult jResult = new JsonResult();
		User user = UserContext.user.get();
		WrongSubject wrongSubject = wrongQuestionService.getWrongSubject(user.getId(), classId, subjectId, user.getCurrentSchool().getId());
		jResult.addDatas("wrongSubject", wrongSubject);
		return jResult;
	}

	@ResponseBody
	@RequestMapping("delWrongQuestion")
	public JsonResult delWrongQuestion(WrongQuestion wrongQuestion) {
		JsonResult jResult = new JsonResult();
		User user = UserContext.user.get();
		wrongQuestion.setModifiedBy(user.getId());
		wrongQuestion.setSchoolId(user.getCurrentSchool().getId());
		wrongQuestionService.delWrongQuestion(wrongQuestion);
		return jResult;
	}

	@ResponseBody
	@RequestMapping("updateWrongSubjRate")
	public JsonResult updateWrongSubjRate(WrongSubject wrongSubject) {
		User user = UserContext.user.get();
		wrongSubject.setUserId(user.getId());
		wrongSubject.setSchoolId(user.getCurrentSchool().getId());
		wrongQuestionService.saveWrongSubjRate(wrongSubject);
		return new JsonResult();
	}
}
