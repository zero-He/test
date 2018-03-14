package cn.strong.leke.diag.manage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.diag.dao.homework.mongo.WorkDetailDao;
import cn.strong.leke.diag.model.StuKnoRate;
import cn.strong.leke.diag.model.StuQueStat;
import cn.strong.leke.diag.model.WorkDetail;

@Component
public class WorkDetailService {

	@Resource
	private WorkDetailDao workDetailDao;

	/**
	 * 根据老师作业ID，查询学生答题信息列表
	 * @param homeworkId
	 * @return
	 */
	public List<WorkDetail> findWorkDetailByHomeworkId(Long homeworkId) {
		return this.workDetailDao.findWorkDetailByHomeworkId(homeworkId);
	}

	/**
	 * 统计作业中的题量和错题数（只记一个人）。
	 * @param homeworkDtlIds
	 * @return
	 */
	public StuQueStat getStuQueStatByHomeworkDtlIds(List<Long> homeworkDtlIds) {
		return this.workDetailDao.getStuQueStatByHomeworkDtlIds(homeworkDtlIds);
	}
	
	/**
	 * 获取一批学生的学科完成情况（分析用）
	 * @param homeworkDtlIds
	 * @return
	 */
	public List<StuKnoRate> findStuKnoRates(List<Long> homeworkDtlIds) {
		return this.workDetailDao.findStuKnoRates(homeworkDtlIds);
	}
}
