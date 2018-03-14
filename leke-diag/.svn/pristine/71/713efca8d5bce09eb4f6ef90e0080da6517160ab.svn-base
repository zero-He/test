package cn.strong.leke.diag.manage;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.diag.dao.homework.mongo.WorkDetailDao;
import cn.strong.leke.diag.dao.homework.mongo.WorkStatsDao;
import cn.strong.leke.diag.model.WorkDetail;
import cn.strong.leke.diag.model.WorkStats;
import cn.strong.leke.diag.model.WorkStats.Named;

@Component
public class WorkStatsService {

	@Resource
	private WorkStatsDao workStatsDao;
	@Resource
	private WorkDetailDao workDetailDao;

	/**
	 * 查询作业的统计信息。
	 * @param homeworkId 作业ID
	 * @return
	 */
	public WorkStats getWorkStatsByHomeworkId(Long homeworkId) {
		return this.workStatsDao.getWorkStatsByHomeworkId(homeworkId);
	}

	/**
	 * 查询某个作业下题目的名单信息。
	 * @param homeworkId 作业ID
	 * @param questionId 题目ID
	 * @return
	 */
	public Named getNamedByHomeworkIdAndQuestionId(Long homeworkId, Long questionId) {
		return this.workStatsDao.getNamedByHomeworkIdAndQuestionId(homeworkId, questionId);
	}

	/**
	 * 查询作业学生作业信息
	 * @param homeworkId 老师作业ID
	 * @param studentId 学生ID
	 * @return
	 */
	public WorkDetail getWorkDetailByHomeworkIdAndStudentId(Long homeworkId, Long studentId) {
		return this.workDetailDao.getWorkDetailByHomeworkIdAndStudentId(homeworkId, studentId);
	}
}
