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
 * 换行符
 * 
 * @author liulongbiao
 * @created 2014年12月8日 下午4:42:52
 * @since v3.2
 */
public class Br implements Inline {

	public static final String HTML_BR = "<br/>"; // 换行符

	@Override
	public String html() {
		return HTML_BR;
	}

	@Override
	public void accept(DocVisitor visitor) {
		visitor.preVisit(this);
		visitor.postVisit(this);
	}
}
