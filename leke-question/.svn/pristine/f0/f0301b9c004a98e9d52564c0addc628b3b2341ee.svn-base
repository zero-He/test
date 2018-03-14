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
 * 解析上下文
 * 
 * @author liulongbiao
 * @created 2014年12月8日 上午9:30:47
 * @since v3.2
 */
public class DocCtx {

	private Doc doc; // 文档对象
	private Paragraph currentP; // 当前段落
	private Text prevText; // 前一个文本

	public DocCtx() {
		this.doc = new Doc();
	}

	public Doc getDoc() {
		doc.removeEmptyParagraphs();
		return doc;
	}

	/**
	 * 段落开始
	 * 
	 * @author liulongbiao
	 * @created 2014年12月8日 下午3:21:08
	 * @since v3.2
	 */
	public void paragraphStarted() {
		currentP = new Paragraph();
		doc.addParagraph(currentP);
		prevText = null;
	}

	/**
	 * 添加行内元素
	 * 
	 * @author liulongbiao
	 * @created 2014年12月8日 下午3:52:52
	 * @since v3.2
	 * @param inline
	 */
	public void addInline(Inline inline) {
		if (inline == null) {
			return;
		}
		if(currentP == null) {
			paragraphStarted();
		}
		if (inline instanceof Text) {
			Text newText = (Text) inline;
			if (prevText != null && prevText.hasSameStyle(newText)) {
				prevText.append(newText.getContent());
			} else {
				currentP.addContent(newText);
				prevText = newText;
			}
		} else {
			currentP.addContent(inline);
			prevText = null;
		}
	}
}
