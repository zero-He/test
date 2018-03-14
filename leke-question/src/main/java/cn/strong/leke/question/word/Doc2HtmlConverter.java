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
import cn.strong.leke.question.word.model.Img;
import cn.strong.leke.question.word.model.Paragraph;
import cn.strong.leke.question.word.model.Text;

/**
 * HTML 内容转换器
 * 
 * @author liulongbiao
 * @created 2014年12月8日 下午6:15:05
 * @since v3.2
 */
public class Doc2HtmlConverter extends BaseDocVisitor {

	/**
	 * 将 Doc 对象转换成 HTML
	 * 
	 * @author liulongbiao
	 * @created 2014年12月9日 下午7:45:31
	 * @since v3.2
	 * @param doc
	 * @return
	 */
	public static String convert(Doc doc) {
		Doc2HtmlConverter hc = new Doc2HtmlConverter();
		doc.accept(hc);
		return hc.html();
	}

	private StringBuilder sb = new StringBuilder();
	private boolean singlePara;

	@Override
	public void preVisit(Doc doc) {
		singlePara = doc.isSingleParagraph();
	}

	@Override
	public void preVisit(Paragraph p) {
		if (!singlePara && !p.isEmpty()) {
			sb.append("<p>");
		}
	}

	@Override
	public void postVisit(Paragraph p) {
		if (!singlePara && !p.isEmpty()) {
			sb.append("</p>");
		}
	}

	@Override
	public void preVisit(Text text) {
		sb.append(text.html());
	}

	@Override
	public void preVisit(Img img) {
		sb.append(img.html());
	}

	@Override
	public void preVisit(Br br) {
		sb.append(br.html());
	}

	public String html() {
		return sb.toString();
	}

}
