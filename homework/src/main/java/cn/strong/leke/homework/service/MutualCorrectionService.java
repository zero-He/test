package cn.strong.leke.homework.service;

import java.util.List;

import cn.strong.leke.homework.model.CorrectHomeworkDtlInfo;

/**
 *  互批作业服务
 * @author Zhang Fujun
 * @date 2017年5月18日
 */
public interface MutualCorrectionService {

	/*
	 * 1,校验是否允许互批（不做批改的）
	 * 2,查找未批改(含未提交)的学生作业
	 * 3,随机批改人，保存数据
	 * 
	 * */
	void saveCorrectEachOther(Long homeworkId,Long teacherId);
	
	
	/*
	 * 1,校验是否允许互批（不做批改的）
	 * 2,查找未批改(含未提交)的学生作业
	 * 3,指定批改人，保存数据
	 * */
	void saveCorrectUsers(Long homeworkId,Long teacherId,List<Long> userIds);
	
	/**
	 * 1，校验是否允许换人
	 * 3，变更批改人，保存数据
	 * 
	 * 换人批改
	 * @param HomeworkDtlId
	 * @param teacherId
	 * @param correctUserId
	 */
	void changeCorrectUserWithTx(Long homeworkDtlId, Long teacherId,Long correctUserId);


	/**
	 * 校验互批操作是否允许
	 * @param homeworkId
	 * @param teacherId
	 */
	void validateMutualCorrection(Long homeworkId, Long teacherId);
	
	/**
	 * 查询分配的配改作业列表
	 * @param correctUserId
	 * @param page
	 * @return
	 */
	List<CorrectHomeworkDtlInfo> findHomeworkDtlByCorrectUser(Long correctUserId);
	
	List<CorrectHomeworkDtlInfo> findSubmitByHwIds(Long studentId,List<Long> homeworkIds);
	
	/**
	 * 待批改作业总数
	 * @param correctUserId
	 * @return
	 */
	Long getCorrectTotal(Long correctUserId);
}
