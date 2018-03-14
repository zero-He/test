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
 * 选择题题干
 * 
 * @author liulongbiao
 * @created 2014年12月10日 下午6:15:00
 * @since v3.2
 */
public class ChoiceStem {

	private DocCtx stem;
	private JsArray<DocCtx> options;

	public ChoiceStem() {
		stem = new DocCtx();
		options = new JsArray<DocCtx>();
	}

	public DocCtx getStem() {
		return stem;
	}

	public void setStem(DocCtx stem) {
		this.stem = stem;
	}

	public JsArray<DocCtx> getOptions() {
		return options;
	}

	public void setOption(int i, DocCtx ctx) {
		options.set(i, ctx);
	}

}
