/* 
 * 包名: cn.strong.leke.question.service
 *
 * 文件名：IQuestionFeedbackService.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-6-16
 */
package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.model.question.QuestionFeedback;
import cn.strong.leke.model.question.QuestionFeedbackDTO;
import cn.strong.leke.model.user.User;

/**
 * 题目反馈服务层接口
 * @author    lavender
 * @version   Avatar 
 */
public interface IQuestionFeedbackService {
	/**
	 * 添加题目反馈
	 * @param qf
	 * @return
	 * author：lavender
	 * 2014-6-16下午2:23:03
	 */
	public void addQuestionFeedback(QuestionFeedbackDTO qf, User user);
	/**
	 * 教研员将题目反馈设置为已处理
	 * @param qf
	 * @return
	 * author：lavender
	 * 2014-6-16下午2:23:13
	 */
	public int processQuestionFeedback(QuestionFeedback qf);
	/**
	 * 查找题目反馈
	 * @return
	 * author：lavender
	 * 2014-6-16下午2:23:44
	 */
	public List<QuestionFeedbackDTO> findQuestionFeedback(Long questionId);
	
	/**
	 * 忽略纠错信息
	 * @param qf
	 * @return
	 * author：lavender
	 * 2014-7-28下午2:47:23
	 */
	public int ignoreQuestionFeedback(QuestionFeedback qf);
	
	/**
	 * 
	 *
	 * 描述:  查询纠错类型
	 *
	 * @author qw
	 * @created 2017年4月27日
	 * @since v1.0.0
	 * @param questionId
	 * @return
	 * @return List<QuestionFeedbackDTO>
	 */
	public List<Integer> findQuestionFeedbackType(Long questionFeedbackId);
	
	/**
	 * 
	 *
	 * 描述: 查询所有纠错
	 *
	 * @author qw
	 * @created 2017年5月17日
	 * @since v1.0.0
	 * @param questionId
	 * @return
	 * @return List<QuestionFeedbackDTO>
	 */
	public List<QuestionFeedbackDTO> findAllQuestionFeedback(Long questionId);
}
