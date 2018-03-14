/* 
 * 包名: cn.strong.leke.question.service
 *
 * 文件名：IQuestionTaskConfigService.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-6-10
 */
package cn.strong.leke.question.service;

import cn.strong.leke.question.model.QueTaskConfig;

/**
 * 习题领取设置服务层接口
 * @author    lavender
 * @version   Avatar 
 */
public interface IQuestionTaskConfigService {
	/**
	 * 设置习题领取
	 * @param qtc
	 * @return
	 * author：lavender
	 * 2014-6-10下午3:21:19
	 */
	public Integer addQueTaskConfig(QueTaskConfig qtc);
	/**
	 * 获取习题领取量
	 * @return
	 * author：lavender
	 * 2014-6-10下午5:20:10
	 */
	public Integer findTaskCount();
}
