/* 
 * 包名: cn.strong.leke.question.dao.mybatis
 *
 * 文件名：IQuestionRejectionDao.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-7-29
 */
package cn.strong.leke.question.dao.mybatis;

import java.util.List;

import cn.strong.leke.model.question.QuestionRejection;


/**
 * 习题退回数据层接口
 * @author    lavender
 * @version   Avatar 
 */
public interface IQuestionRejectionDao {
	
	/**
	 * 将题目退回设置为已处理
	 * @param qr
	 * @return
	 * author：lavender
	 * 2014-7-29下午3:38:08
	 */
	public int processQuestionRejection(QuestionRejection qr);

	/**
	 * 查询退回信息
	 * @param questionId
	 * @return
	 * author：lavender
	 * 2014-7-29下午3:38:24
	 */
	public List<QuestionRejection> queryQueRejection(Long questionId);

	/**
	 * 
	 * 描述: 添加退回信息
	 * 
	 * @author liulb
	 * @created 2014年8月1日 下午5:07:38
	 * @since v1.1.0
	 * @param reject
	 * @return
	 */
	int insertQuestionRejection(QuestionRejection reject);
}
