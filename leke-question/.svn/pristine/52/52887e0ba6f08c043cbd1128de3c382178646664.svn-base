/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word;

import cn.strong.leke.question.word.model.BaseDocVisitor;
import cn.strong.leke.question.word.model.Br;
import cn.strong.leke.question.word.model.Doc;
import cn.strong.leke.question.word.model.Paragraph;
import cn.strong.leke.question.word.model.Text;

/**
 * 文本抽取
 * 
 * @author liulongbiao
 * @created 2014年12月10日 下午7:17:36
 * @since v3.2
 */
public class DocTextExtracter extends BaseDocVisitor {

	private static final char BR = '\n';

	public static String extract(Doc doc) {
		DocTextExtracter ext = new DocTextExtracter();
		doc.accept(ext);
		return ext.text();
	}

	public String text() {
		return sb.toString();
	}

	private StringBuilder sb = new StringBuilder();

	@Override
	public void postVisit(Paragraph p) {
		sb.append(BR);
	}

	@Override
	public void preVisit(Text text) {
		sb.append(text.getContent());
	}

	@Override
	public void preVisit(Br br) {
		sb.append(BR);
	}
}
