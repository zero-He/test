package cn.strong.leke.homework.service;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.model.AssignLogDO;
import cn.strong.leke.homework.model.AssignLogDTO;
import cn.strong.leke.homework.model.query.AssignLogQueryDTO;

public interface IAssignLogService {

	/**
	 * 新增一条记录
	 * @param assignLogDTO
	 */
	void addAssignLog(AssignLogDTO assignLogDTO);
	
	/**
	 * 修改截止时间
	 * @param assignLog
	 */
	void modifyCloseTime(AssignLogDO assignLog);

	/**
	 * 作废
	 * @param assignLog
	 */
	void invalid(AssignLogDO assignLog);

	/**
	 * 获取批量布置作业列表
	 * @param query
	 * @param page
	 * @return
	 */
	List<AssignLogDO> findAssignLogList(AssignLogQueryDTO query, Page page);
	
	/**获取assignLog
	 * @param assignId
	 * @return
	 */
	AssignLogDO getAssignLog(Long assignId);
}
