/* 
 * 包名: cn.strong.leke.question.dao.mybatis
 *
 * 文件名：IQuestionTask.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-5-7
 */
package cn.strong.leke.question.dao.mybatis;

import java.util.List;

import cn.strong.leke.question.model.InputStatisDTO;
import cn.strong.leke.question.model.InputStatisQuery;
import cn.strong.leke.question.model.QuestionTask;
import cn.strong.leke.question.model.QuestionTaskItem;

/**
 * 习题领取数据层接口
 * @author    lavender
 * @version   Avatar 
 */
public interface IQuestionTaskDao {
	/**
	 * 查询最大剩余量
	 * @return
	 * author：lavender
	 * 2014-5-7上午10:56:53
	 */
	public Integer maxTaskCount(QuestionTask questionTask);
	
	/**
	 * 验证是否已经全部审核完已领取题目
	 * @param questionTask
	 * @return
	 * author：lavender
	 * 2014-5-7上午10:57:05
	 */
	public Integer checkFinished(QuestionTask questionTask);
	
	/**
	 * 添加题目领取
	 * @param questionTask
	 * @return
	 * author：lavender
	 * 2014-5-7上午10:58:08
	 */
	public Integer saveQuestionTask(QuestionTask questionTask);
	
	/**
	 * 获取未被领取过的题目id
	 * @param count
	 * @return
	 * author：lavender
	 * 2014-5-7下午2:45:01
	 */
	public List<Long> queryUnTaskedQuestionIdList(QuestionTask questionTask);
	
	/**
	 * 添加题目领取详情
	 * @param questionTaskItem
	 * @return
	 * author：lavender
	 * 2014-5-7上午10:58:53
	 */
	public Integer saveQuestionTaskItem(QuestionTaskItem questionTaskItem);
	
	/**
	 * 查询用户各年级科目历史领取数量
	 * @param questionTask
	 * @return
	 * author：lavender
	 * 2014-5-7上午11:06:32
	 */
	public List<QuestionTask> queryQuestionTaskByUserId(QuestionTask questionTask);
	
	/**
	 * 查询用户各年级科目剩余数量
	 * @param questionTask
	 * @return
	 * author：lavender
	 * 2014-5-7上午11:06:32
	 */
	public List<QuestionTask> querySurplusQuestionTaskByUserId(QuestionTask questionTask);
	
	/**
	 * 查询领取习题剩余数量列表
	 * @return
	 * author：lavender
	 * 2014-5-7上午11:29:06
	 */
	public List<QuestionTask> queryQuestionTaskSurplusGroupByUserId(QuestionTask questionTask);
	/**
	 * 根据用户id，查询每天领取量，按时间排序
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 * author：lavender
	 * 2014-4-23下午4:01:45
	 */
	public List<InputStatisDTO> queryTaskAmountListByUserIdOrderByDate(InputStatisQuery query);
	
	/**
	 *	
	 * 描述:查询用户领取量
	 *
	 * @author  lavender
	 * @created 2014年12月31日 下午1:54:50
	 * @since   v1.0.0 
	 * @param inputStatisDTO
	 * @return
	 * @return  List<InputStatisDTO>
	 */
	public List<InputStatisDTO> queryTaskAmountListOrderByUserId(InputStatisQuery query);

	/**
	 * 管理员回收对应用户的已领取习题详情
	 * @param questionTask
	 * @return
	 * author：lavender
	 * 2014-5-7上午11:26:38
	 */
	public Integer recycleQuestionTaskItem(QuestionTask questionTask);

	/**
	 * 
	 * 描述: 根据题目ID删除明细项
	 * 
	 * @author liulb
	 * @created 2014年5月18日 下午6:56:18
	 * @since v1.0.0
	 * @param questionTaskItem
	 * @return int
	 */
	int deleteQuestionTaskItemByQuestionId(QuestionTaskItem questionTaskItem);
}
