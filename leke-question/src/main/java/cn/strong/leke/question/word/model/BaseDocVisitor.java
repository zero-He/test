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
 * DocVisitor 适配器
 * 
 * @author liulongbiao
 * @created 2014年12月8日 下午6:16:25
 * @since v3.2
 */
public class BaseDocVisitor implements DocVisitor {

	@Override
	public void preVisit(Doc doc) {
		// adopt
	}

	@Override
	public void preVisit(Paragraph p) {
		// adopt
	}

	@Override
	public void preVisit(Text text) {
		// adopt
	}

	@Override
	public void preVisit(Img img) {
		// adopt
	}

	@Override
	public void preVisit(Br br) {
		// adopt
	}

	@Override
	public void postVisit(Doc doc) {
		// adopt
	}

	@Override
	public void postVisit(Paragraph p) {
		// adopt
	}

	@Override
	public void postVisit(Text text) {
		// adopt
	}

	@Override
	public void postVisit(Img img) {
		// adopt
	}

	@Override
	public void postVisit(Br br) {
		// adopt
	}

}
