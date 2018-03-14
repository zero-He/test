/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.que.template;

import org.apache.commons.lang.StringUtils;

import cn.strong.leke.question.word.model.BaseDocVisitor;
import cn.strong.leke.question.word.model.Br;
import cn.strong.leke.question.word.model.Doc;
import cn.strong.leke.question.word.model.DocCtx;
import cn.strong.leke.question.word.model.Img;
import cn.strong.leke.question.word.model.JsArray;
import cn.strong.leke.question.word.model.Paragraph;
import cn.strong.leke.question.word.model.Text;

/**
 * 填空题答案解析
 *
 * @author  liulongbiao
 * @created 2014年12月11日 下午3:28:11
 * @since   v3.2
 */
public class FillBlankAnswerParser extends BaseDocVisitor {
	
	private static final String SEP_ANSWER = "##";

	public JsArray<DocCtx> ctxs;

	public FillBlankAnswerParser() {
		ctxs = new JsArray<DocCtx>();
	}

	/**
	 * 获取答案内容
	 * 
	 * @author liulongbiao
	 * @created 2014年12月11日 下午4:12:16
	 * @since v3.2
	 * @return
	 */
	public JsArray<Doc> getAnswers() {
		JsArray<Doc> docs = new JsArray<Doc>();
		for (DocCtx dc : ctxs) {
			docs.push(dc.getDoc());
		}
		return docs;
	}
	
	@Override
	public void preVisit(Doc doc) {
		if (!doc.isEmpty()) {
			ctxs.push(new DocCtx());
		}
	}

	private DocCtx ctx() {
		return ctxs.peek();
	}

	@Override
	public void preVisit(Paragraph p) {
		ctx().paragraphStarted();
	}

	@Override
	public void preVisit(Text text) {
		String content = StringUtils.trim(text.getContent());
		if (StringUtils.isEmpty(content)) {
			return;
		}
		int idx = content.indexOf(SEP_ANSWER);
		if (idx != -1) {
			if (idx > 0) {
				Text pre = text.copy();
				pre.setContent(content.substring(0, idx));
				ctx().addInline(pre);
			}

			ctxs.push(new DocCtx());

			text.setContent(content.substring(idx + 2));
			preVisit(text);
		}
	}
	
	@Override
	public void postVisit(Text text) {
		String content = StringUtils.trim(text.getContent());
		if (StringUtils.isNotEmpty(content)) {
			ctx().addInline(text);
		}
	}

	@Override
	public void postVisit(Br br) {
		ctx().addInline(br);
	}
	
	@Override
	public void postVisit(Img img) {
		ctx().addInline(img);
	}
}
