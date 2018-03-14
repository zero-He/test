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

import cn.strong.leke.question.word.ParseCsts;
import cn.strong.leke.question.word.model.BaseDocVisitor;
import cn.strong.leke.question.word.model.Br;
import cn.strong.leke.question.word.model.ChoiceStem;
import cn.strong.leke.question.word.model.Doc;
import cn.strong.leke.question.word.model.DocCtx;
import cn.strong.leke.question.word.model.Img;
import cn.strong.leke.question.word.model.Paragraph;
import cn.strong.leke.question.word.model.Text;

/**
 * 选择题题干解析器
 * 
 * @author liulongbiao
 * @created 2014年12月10日 下午4:41:00
 * @since v3.2
 */
public class ChoiceStemParser extends BaseDocVisitor {

	private boolean lineBegin = true;
	private boolean optionsStarted = false;
	private boolean optionLabelAccepted = false;
	private int optionCount = 0;

	private ChoiceStem stem;
	private DocCtx ctx;

	@Override
	public void preVisit(Doc doc) {
		stem = new ChoiceStem();
		ctx = stem.getStem();
	}
	
	public ChoiceStem getStem() {
		return stem;
	}

	@Override
	public void preVisit(Paragraph p) {
		ctx.paragraphStarted();
		lineBegin = true;
	}

	@Override
	public void preVisit(Text text) {
		String content = StringUtils.trim(text.getContent());
		if (StringUtils.isEmpty(content)) {
			return;
		}
		if (optionLabelAccepted) {
			char first = content.charAt(0);
			if (first == '.' || first == '．') {
				ctx = new DocCtx();
				stem.setOption(optionCount, ctx);
				optionCount++;
				optionLabelAccepted = false;
				optionsStarted = true;

				text.setContent(content.substring(1));
				preVisit(text);
			} else {
				char ch = (char) (ParseCsts.CHAR_CODE_A + optionCount);
				Text label = text.copy();
				label.setContent(String.valueOf(ch));
				ctx.addInline(label);

				optionLabelAccepted = false;
				preVisit(text);
			}
		} else if (optionsStarted) {
			int idx = indexOfOptLabel(content);
			if (idx != -1) {
				if (idx > 0) {
					Text pre = text.copy();
					pre.setContent(content.substring(0, idx));
					ctx.addInline(pre);
				}

				optionLabelAccepted = true;
				text.setContent(content.substring(idx + 1));
				preVisit(text);
			}
		} else {
			if (lineBegin && indexOfOptLabel(content) == 0) {
				optionLabelAccepted = true;
				text.setContent(content.substring(1));
				preVisit(text);
			}
		}
	}

	private int indexOfOptLabel(String content) {
		return content.indexOf(ParseCsts.CHAR_CODE_A + optionCount);
	}

	@Override
	public void postVisit(Text text) {
		String content = text.getContent();
		if (StringUtils.isNotEmpty(content)) {
			ctx.addInline(text);
			lineBegin = false;
		}
	}

	@Override
	public void postVisit(Br br) {
		ctx.addInline(br);
		lineBegin = true;
	}

	@Override
	public void postVisit(Img img) {
		ctx.addInline(img);
		lineBegin = false;
	}

}
