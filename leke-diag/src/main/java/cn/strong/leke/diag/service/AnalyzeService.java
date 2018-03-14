package cn.strong.leke.diag.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.strong.leke.context.paper.PaperContext;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.diag.dao.homework.mongo.WorkDetailDao;
import cn.strong.leke.diag.dao.homework.mongo.WorkStatsDao;
import cn.strong.leke.diag.dao.homework.mybatis.HomeworkDao;
import cn.strong.leke.diag.model.Homework;
import cn.strong.leke.diag.model.StudentAnswer;
import cn.strong.leke.diag.model.WorkStats;
import cn.strong.leke.diag.report.stat.QuestionStatAdapter;
import cn.strong.leke.diag.util.DiagCst;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.paper.PaperDTO;
import cn.strong.leke.model.paper.ScoredQuestion;
import cn.strong.leke.model.question.QuestionDTO;

@Repository
public class AnalyzeService {

	@Resource
	private WorkDetailDao workDetailDao;
	@Resource
	private HomeworkDao homeworkDao;
	@Resource
	private WorkStatsDao workStatsDao;
	@Resource
	private QuestionStatAdapter questionStatAdapter;

	/**
	 * 生成作业分析数据
	 * @param homeworkId
	 * @return
	 */
	public WorkStats generateHomeworkAnalyze(Long homeworkId) {
		Homework homework = this.homeworkDao.getHomeworkByHomeworkId(homeworkId);
		Validation.notNull(homework, "作业信息不存在，无法生成作业统计数据。");
		Validation.isFalse(homework.getFinishNum() == null || homework.getFinishNum() <= 0, "作业没有人完成，无法生成作业统计数据。");
		PaperDTO paperDTO = PaperContext.getPaperDTO(homework.getPaperId());
		List<Long> questionIds = paperDTO.getDetail().getGroups().stream().flatMap(v -> v.getQuestions().stream())
				.map(ScoredQuestion::getQuestionId).collect(Collectors.toList());
		List<WorkStats.Sum> sums = this.workDetailDao.findSumsByHomeworkId(homeworkId);
		sums.forEach(v -> v.setQuestionNo(questionIds.indexOf(v.getQuestionId()) + 1));
		sums.sort((sum1, sum2) -> Integer.compare(sum1.getQuestionNo(), sum2.getQuestionNo()));
		WorkStats workStats = new WorkStats();
		workStats.setHomeworkId(homeworkId);
		workStats.setSums(sums);
		sums.forEach(v -> fillAnalyze(v.getQuestionId(), homeworkId, workStats));
		return workStats;
	}

	/**
	 * 根据作业ID，生成作业统计信息
	 * @param homeworkId 作业ID
	 */
	public void generateHomeworkAnalyzeWithTx(Long homeworkId) {
		WorkStats workStats = this.generateHomeworkAnalyze(homeworkId);
		this.homeworkDao.updateHomeworkStatsStatus(homeworkId, DiagCst.HOMEWORK_STATS_STATUS_FINISH);
		this.workStatsDao.saveOrUpdateWorkStats(workStats);
	}

	/**
	 * 填充分析对象中的缺失信息。
	 * @param questionId 题目ID
	 * @param homeworkId 作业ID
	 */
	private void fillAnalyze(Long questionId, Long homeworkId, WorkStats workStats) {
		QuestionDTO questionDTO = QuestionContext.getQuestionDTO(questionId);
		List<StudentAnswer> stuAnswerList = this.workDetailDao.findStudentAnswersByQuestionId(homeworkId, questionId);
		if (questionDTO.getHasSub()) {
			int i = 0;
			for (QuestionDTO subQuestionDTO : questionDTO.getSubs()) {
				final int index = i;
				List<StudentAnswer> stuSubAnswerList = stuAnswerList.stream().map(v -> {
					StudentAnswer sub = new StudentAnswer();
					sub.setStudentId(v.getStudentId());
					sub.setQuestionResult(v.getQuestionResult().getSubs().get(index));
					return sub;
				}).filter(v -> v.getQuestionResult().getTotalIsRight() != null).collect(Collectors.toList());
				questionStatAdapter.analyze(subQuestionDTO, stuSubAnswerList, workStats);
				i++;
			}
		} else {
			stuAnswerList = stuAnswerList.stream().filter(v -> v.getQuestionResult().getTotalIsRight() != null)
					.collect(Collectors.toList());
			questionStatAdapter.analyze(questionDTO, stuAnswerList, workStats);
		}
	}
}
