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
 * 
 *
 * @author  liulongbiao
 * @created 2014年12月8日 下午5:52:14
 * @since   v3.2
 */
public interface DocVisitor {

	void preVisit(Doc doc);

	void preVisit(Paragraph p);

	void preVisit(Text text);

	void preVisit(Img img);

	void preVisit(Br br);

	void postVisit(Doc doc);

	void postVisit(Paragraph p);

	void postVisit(Text text);

	void postVisit(Img img);

	void postVisit(Br br);
}
