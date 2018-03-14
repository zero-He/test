package cn.strong.leke.homework.service.impl;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.base.ParametersContext;
import cn.strong.leke.core.business.notice.NoticeHelper;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.dao.mybatis.ExamDao;
import cn.strong.leke.homework.dao.mybatis.HomeworkDao;
import cn.strong.leke.homework.model.*;
import cn.strong.leke.homework.model.query.ApiStuExamQuery;
import cn.strong.leke.homework.model.query.ApiTeaExamQuery;
import cn.strong.leke.homework.model.query.ExamStuQuery;
import cn.strong.leke.homework.model.query.ExamTeaQuery;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.homework.service.IExamService;
import cn.strong.leke.homework.util.HomeworkCst;
import cn.strong.leke.notice.model.LetterMessage;
import cn.strong.leke.notice.model.MessageBusinessTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 在线考试接口实现层
 * @Author LIU.SHITING
 * @Version 2.7
 * @Date 2017-05-18 13:56:30
 */
@Service
public class ExamServiceImpl implements IExamService {

	protected static final Logger logger = LoggerFactory.getLogger(ExamServiceImpl.class);

	@Resource
	private ExamDao examDao;
	@Resource
	private HomeworkDao homeworkDao;
	@Resource
	private HomeworkDtlService homeworkDtlService;

	/**
	 * 查询老师在线考试列表数据
	 * @return java.util.List<cn.strong.leke.homework.model.HomeworkDTO>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/18 14:40
	 * @Param [query, page]
	 */
	public List<HomeworkDTO> queryTeacherExamList(ExamTeaQuery query, Page page) {
		return examDao.selectTeacherExamList(query, page);
	}

	/**
	 * 查询学生在线考试列表数据
	 * @return java.util.List<cn.strong.leke.homework.model.MyHomeworkDTO>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/27 14:03
	 * @Param [query, page]
	 */
	public List<MyHomeworkDTO> queryStuOnlineExamListData(ExamStuQuery query, Page page) {
		return examDao.selectStuOnlineExamListData(query, page);
	}

	/**
	 * 老师根据homeworkId修改考试开始时间
	 * @return void
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/18 15:51
	 * @Param [homeworkId, startTime]
	 */
	public void updateTeacherExamDate(Long homeworkId, Date startTime) {
		if (homeworkId == null || startTime == null) {
			return;
		}
		//如果修改后的考试开始时间，在5分钟内，则马上就要发消息通知参与考试的学生
		List<HomeworkDtl> homeworkDtl = homeworkDtlService.findHomeworkDtlListByHomeworkId(homeworkId);
		List<String> to = homeworkDtl.stream().map(x -> x.getStudentId().toString()).collect(Collectors.toList());
		Homework homework = homeworkDao.getHomeworkById(homeworkId);
		//发送在线考试提醒消息
		sendOnlineExamRemindMessage(to, startTime, homework.getTeacherName());
		//考试时长
		long diffTime = homework.getCloseTime().getTime() - homework.getStartTime().getTime();
		//考试截止时间
		Date closeTime = new Date(startTime.getTime() + diffTime);
		//根据homeworkId修改考试开始和截止时间
		examDao.updateTeacherExamDate(homeworkId, startTime, closeTime);

	}

	/**
	 * 发送在线考试提醒消息
	 * @return void
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/23 11:14
	 * @Param [users, homework]
	 */
	private void sendOnlineExamRemindMessage(List<String> to, Date startTime, String teacherName) {
		long diffSec = startTime.getTime() - System.currentTimeMillis();
		if (diffSec <= 0) {
			/*throw new ValidateException("考试开始时间不能在当前时间之前，请重新选择！");*/
			throw new ValidateException("请重新选择考试开始时间！");
		}
		String subject = "在线考试";
		LetterMessage message = new LetterMessage();
		message.setBusinessType(MessageBusinessTypes.HOMEWORK);
		message.addTo(to);
		message.setSubject(subject);
		message.addVariable("teacher", teacherName);
		if (diffSec / 1000 < 5 * 60) {
			String content = ParametersContext.getString(HomeworkCst.AUTO_ONLINE_EXAM_REMIND);
			message.setContent(content);
			message.addVariable("minute", diffSec / 1000 / 60);
		} else {
			String content = ParametersContext.getString(HomeworkCst.ONLINE_EXAM_REMIND);
			message.setContent(content);
			message.addVariable("time", DateUtils.format(startTime, DateUtils.MINITE_DATE_PATTERN));
		}
		NoticeHelper.send(message);
	}

	/**
	 * 在线考试定时任务提醒
	 * @return void
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/19 17:03
	 * @Param []
	 */
	public void excuteOnlineExam() {
		//扫描出所有用于在线考试的试卷（isExam=1）
		List<ExamDTO> examDTO = examDao.selectTeaExamPaperList();
		examDTO.forEach((x) -> {
			//根据homeworkId查所有考试的学生
			List<String> to = examDao.selectStuExamPaperList(x.getHomeworkId());
			//发送在线考试提醒消息
			sendOnlineExamMessage(to, x.getTeacherName());
		});
	}

	/**
	 * 发送在线考试提醒消息
	 * @return void
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/22 11:29
	 * @Param [to, teacherName]
	 */
	private void sendOnlineExamMessage(List<String> to, String teacherName) {
		String subject = "在线考试";
		String content = ParametersContext.getString(HomeworkCst.AUTO_ONLINE_EXAM_REMIND);
		LetterMessage message = new LetterMessage();
		message.setBusinessType(MessageBusinessTypes.HOMEWORK);
		message.addTo(to);
		message.setSubject(subject);
		message.setContent(content);
		message.addVariable("teacher", teacherName);
		message.addVariable("minute", 5);
		NoticeHelper.send(message);
	}


	/**
	 * 老师在线考试列表（API）
	 * @return java.util.List<cn.strong.leke.homework.model.Homework>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/24 14:47
	 * @Param [query]
	 */
	public List<Homework> queryTeaOnlineExamList(ApiTeaExamQuery query) {
		return examDao.selectTeaOnlineExamList(query);
	}

	/**
	 * 老师在线考试列表总数（API）
	 * @return java.util.Map<java.lang.String,java.lang.Long>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/24 15:00
	 * @Param [userId]
	 */
	public Map<String, Long> queryTeaOnlineExamTotal(Long teacherId) {
		return examDao.selectTeaOnlineExamTotal(teacherId);
	}

	/**
	 * 学生在线考试列表（API）
	 * @return java.util.List<cn.strong.leke.homework.model.ApiStudentHomeworkListDTO>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/24 17:14
	 * @Param [query]
	 */
	public List<ApiStudentHomeworkListDTO> queryStuOnlineExamList(ApiStuExamQuery query) {
		return examDao.selectStuOnlineExamList(query);
	}

	/**
	 * 学生在线考试列表总数（API）
	 * @return java.util.Map<java.lang.String,java.lang.Long>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/24 17:14
	 * @Param [studentId]
	 */
	public Map<String, Long> queryStuOnlineExamTotal(Long studentId) {
		return examDao.selectStuOnlineExamTotal(studentId);
	}
}
