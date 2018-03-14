package cn.strong.leke.homework.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.homework.dao.mybatis.HomeworkDao;
import cn.strong.leke.homework.dao.mybatis.HomeworkDtlDao;
import cn.strong.leke.homework.job.ExerciseWeekRankJob;
import cn.strong.leke.homework.manage.HwWrongStudentService;
import cn.strong.leke.homework.manage.WorkDetailService;
import cn.strong.leke.homework.model.HwWrongStudent;
import cn.strong.leke.homework.model.mongo.WorkStateSingleQuestion;
import cn.strong.leke.homework.service.HomeworkForWrongService;
import cn.strong.leke.model.wrong.HomeworkForWrong;
import cn.strong.leke.model.wrong.HomeworkForWrongMQ;
import cn.strong.leke.remote.service.question.IWrongQuestionRemoteService;

@Service
public class HomeworkForWrongServiceImpl implements HomeworkForWrongService {

	@Resource
	private MessageSender homeworkForWrongSender;
	@Resource
	private HomeworkDtlDao HomeworkDtlDao;
	@Resource
	private HomeworkDao homeworkDao;
	@Resource
	private IWrongQuestionRemoteService wrongQuestionRemoteService;
	@Resource
	private WorkDetailService workDetailService;
	@Resource
	private HwWrongStudentService hwWrongStudentService;

	private static Logger logger = LoggerFactory.getLogger(ExerciseWeekRankJob.class);
	
	@Override
	public void excuteWrontQuestion() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtils.parseDate(DateUtils.format(DateUtils.SHORT_DATE_PATTERN)));
		calendar.add(Calendar.DAY_OF_MONTH, -2);
		Date start = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date end = calendar.getTime();
		execute(start, end);
	}
	
	/**
	 * @param start
	 * @param end
	 */
	private void execute(Date start, Date end) {
		logger.info("start excuteWrontQuestion task……");
		List<HomeworkForWrong> homeworkForWrongs = homeworkDao.findWrongHwInfo(start, end);
		for (HomeworkForWrong item : homeworkForWrongs) {
			BigDecimal singleQueRate = this.getRate(item.getTeacherId(), item.getClassId(),
					item.getSubjectId(),item.getSchoolId());
			List<Long> correctedHwDtlIds = HomeworkDtlDao.findCorrectedHwDtlId(item.getHomeworkId());
			List<WorkStateSingleQuestion> list = workDetailService.findWorkStateSingleQuestion(correctedHwDtlIds);
			list = list.stream().filter(v -> BigDecimal.valueOf(v.getScoreRate()*100).compareTo(singleQueRate) <= 0)
					.collect(Collectors.toList());
			for (WorkStateSingleQuestion workStateSingleQuestion : list) {
				HomeworkForWrongMQ mq = new HomeworkForWrongMQ();
				mq.setHomework(item);
				Long questionId =workStateSingleQuestion.getQuestionId();
				mq.setQuestionId(questionId);
				mq.setSingleQueRate( BigDecimal.valueOf(workStateSingleQuestion.getScoreRate()*100).setScale(2, RoundingMode.HALF_UP));
				Integer wrongStuTotal = insetHwWrongStudent(item.getHomeworkId(), questionId);
				mq.setWrongStuTotal(wrongStuTotal);
				homeworkForWrongSender.send(mq);
			}
		}
		logger.info("end excuteWrontQuestion task ");
	}

	/**
	 * 记录作业某个题目错题人数
	 * @param homeworkId
	 * @param questionId
	 */
	private Integer insetHwWrongStudent(Long homeworkId, Long questionId) {
		HwWrongStudent hwWrongStudent = new HwWrongStudent();
		hwWrongStudent.setId(new ObjectId().toString());
		hwWrongStudent.setHomeworkId(homeworkId);
		hwWrongStudent.setQuestionId(questionId);
		List<Long> students = this.workDetailService.findHwWrongStudent(questionId, homeworkId);
		hwWrongStudent.setStudents(students);
		this.hwWrongStudentService.insertHwWrongStudent(hwWrongStudent);
		return students.size();
	}
	
	private BigDecimal getRate(Long teacherId,Long classId,Long subjectId,Long schoolId){
		BigDecimal singleQueRate = wrongQuestionRemoteService.getRate(teacherId, classId,
				subjectId,schoolId);
		if (singleQueRate == null) {
			singleQueRate = BigDecimal.valueOf(50);
		}
		return singleQueRate;
	}
	@Override
	public void executeWrongQuestion(Date start, Date end) {
		this.execute(start, end);
	}

}
