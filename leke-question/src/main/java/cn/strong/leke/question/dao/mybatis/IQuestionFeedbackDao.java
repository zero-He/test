/* 
 * 包名: cn.strong.leke.question.dao.mybatis
 *
 * 文件名：IQueFeedbackDao.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-6-16
 */
package cn.strong.leke.question.dao.mybatis;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.model.question.QuestionFeedback;
import cn.strong.leke.model.question.QuestionFeedbackDTO;

/**
 * 题目反馈数据层接口
 * @author    lavender
 * @version   Avatar 
 */
public interface IQuestionFeedbackDao {
	/**
	 * 保存题目反馈信息
	 * @param qf
	 * @return
	 * author：lavender
	 * 2014-6-16下午1:54:16
	 */
	public int saveQuestionFeedback(QuestionFeedback qf);

	/**
	 *
	 * 描述: 纠错
	 *
	 * @author raolei
	 * @created 2016年11月15日 下午2:33:21
	 * @since v1.0.0
	 * @param questionId
	 * @param userId
	 * @return
	 * @return int
	 */
	public int doCorrect(@Param("questionId") Long questionId, @Param("userId") Long userId);
	
	/**
	 * 教研员将题目反馈设置为忽略
	 * 
	 * @param qf
	 * @return author：lavender 2014-6-16下午1:54:52
	 */
	public int processQuestionFeedback(QuestionFeedback qf);

	/**
	 *
	 * 描述: 查找该习题提纠错审核通过的人
	 *
	 * @author raolei
	 * @created 2016年11月15日 下午3:57:49
	 * @since v1.0.0
	 * @param questionId
	 * @return
	 * @return List<Long>
	 */
	public List<Long> findCorrectUserIds(@Param("questionId") Long questionId);
	/**
	 * 查询题目反馈
	 * @return
	 * author：lavender
	 * 2014-6-16下午1:55:16
	 */
	public List<QuestionFeedbackDTO> queryQueFeedback(Long questionId);

	/**
	 * 
	 *
	 * 描述: 查找该习题提纠提交人
	 *
	 * @author qw
	 * @created 2017年2月16日
	 * @since v1.0.0
	 * @param questionId
	 * @return
	 * @return List<Long>
	 */
	public List<Long> findCorrectUserIdsFeedback(@Param("questionId") Long questionId);

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
	public List<QuestionFeedbackDTO> queryAllQueFeedback(Long questionId);
}
