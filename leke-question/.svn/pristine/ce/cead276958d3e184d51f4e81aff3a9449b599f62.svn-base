/* 
 * 包名: cn.strong.leke.question.service
 *
 * 文件名：IQuestionTaskService.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-5-7
 */
package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.model.user.Checker;
import cn.strong.leke.question.model.InputStatisDTO;
import cn.strong.leke.question.model.InputStatisQuery;
import cn.strong.leke.question.model.QuestionTask;
import cn.strong.leke.question.model.QuestionTaskItem;

/**
 * 领取题量服务层接口
 * @author    lavender
 * @version   Avatar 
 */
public interface IQuestionTaskService {
	
	/**
	 * 验证是否已经全部审核完已领取题目
	 * @param questionTask
	 * @return
	 * author：lavender
	 * 2014-5-7上午10:57:05
	 */
	public Integer checkFinished(Long userId);
	
	/**
	 * 添加题目领取
	 * @param questionTask
	 * @return
	 * author：lavender
	 * 2014-5-7上午10:58:08
	 */
	public Integer addQuestionTask(QuestionTask questionTask);
	
	/**
	 *  查询用户历史领取数量及未完成数量，年级科目排序
	 * @param questionTask
	 * @return
	 * author：lavender
	 * 2014-5-7上午11:06:32
	 */
	public List<QuestionTask> findQuestionTaskByUserId(Checker checker);
	
	/**
	 * 查询领取习题剩余数量列表
	 * @return
	 * author：lavender
	 * 2014-5-7上午11:29:06
	 */
	public List<QuestionTask> findQuestionTaskSurplusGroupByUserId(String userName);
	
	/**
	 * 根据用户id，查询每天领取量，按时间排序
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 * author：lavender
	 * 2014-4-23下午4:01:45
	 */
	public List<InputStatisDTO> findTaskAmountListByUserIdOrderByDate(InputStatisQuery query);
	
	/**
	 *	
	 * 描述:查询用户领取量列表
	 *
	 * @author  lavender
	 * @created 2014年12月31日 下午1:58:43
	 * @since   v1.0.0 
	 * @param inputStatisDTO
	 * @return
	 * @return  List<InputStatisDTO>
	 */
	public List<InputStatisDTO> findTaskAmountListOrderByUserId(InputStatisQuery query);

	/**
	 * 管理员回收对应用户的已领取习题
	 * @param questionTask
	 * @return
	 * author：lavender
	 * 2014-5-7上午11:26:38
	 */
	public Integer recycleQuestionTask(Long userId);

	/**
	 * 
	 * 描述: 根据题目ID删除明细项
	 * 
	 * @author liulb
	 * @created 2014年5月18日 下午6:56:18
	 * @since v1.0.0
	 * @param questionTaskItem
	 * @return
	 */
	void deleteQuestionTaskItemByQuestionId(QuestionTaskItem questionTaskItem);
}
