/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.model;

import java.util.ArrayList;
import java.util.List;

import cn.strong.leke.common.utils.CollectionUtils;

/**
 * 段落
 * 
 * @author liulongbiao
 * @created 2014年12月8日 下午2:58:52
 * @since v3.2
 */
public class Paragraph implements DocElement {

	private List<Inline> contents = new ArrayList<Inline>();

	public List<Inline> getContents() {
		return contents;
	}

	/**
	 * 添加内容
	 * 
	 * @author liulongbiao
	 * @created 2014年12月8日 下午3:17:20
	 * @since v3.2
	 * @param inline
	 */
	public void addContent(Inline inline) {
		if (inline instanceof Br) {
			if (CollectionUtils.isEmpty(contents)
					|| contents.get(contents.size() - 1) instanceof Br) {
				// already has line break
				return;
			}
		}
		contents.add(inline);
	}

	public boolean isEmpty() {
		return contents.isEmpty();
	}

	@Override
	public void accept(DocVisitor visitor) {
		visitor.preVisit(this);
		if (CollectionUtils.isNotEmpty(contents)) {
			for (Inline inline : contents) {
				inline.accept(visitor);
			}
		}
		visitor.postVisit(this);
	}
}
