/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.model;


/**
 * 行内元素
 * 
 * @author liulongbiao
 * @created 2014年12月8日 下午2:42:42
 * @since v3.2
 */
public interface Inline extends DocElement {

	/**
	 * 获取 HTML 表示
	 *
	 * @author  liulongbiao
	 * @created 2014年12月8日 下午6:32:11
	 * @since   v3.2
	 * @return
	 */
	String html();

}
