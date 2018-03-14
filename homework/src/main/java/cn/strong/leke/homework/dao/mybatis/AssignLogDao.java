package cn.strong.leke.homework.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.model.AssignLogDO;
import cn.strong.leke.homework.model.query.AssignLogQueryDTO;

public interface AssignLogDao {
	/**
	 * 新增一条记录
	 * @param assignLogDO
	 */
	void addAssignLog(@Param("assignLogDOList") List<AssignLogDO> assignLogDOList);

	/**
	 * 修改截止时间
	 * @param assignLog
	 */
	void modifyCloseTime(AssignLogDO assignLog);

	/**
	 * 作废作业状态
	 * @param assignLog
	 */
	void updateStatus(AssignLogDO assignLog);

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
