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
import java.util.Collection;
import java.util.List;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.common.utils.function.Predicate;

/**
 * 文档
 * 
 * @author liulongbiao
 * @created 2014年12月8日 下午3:00:59
 * @since v3.2
 */
public class Doc implements DocElement {

	/**
	 * 合并多个文档
	 * 
	 * @author liulongbiao
	 * @created 2014年12月10日 下午8:03:31
	 * @since v3.2
	 * @param docs
	 * @return
	 */
	public static Doc merge(Doc... docs) {
		Doc result = new Doc();
		for (Doc doc : docs) {
			result.appendDoc(doc);
		}
		return result;
	}

	private List<Paragraph> paragraphs = new ArrayList<Paragraph>();

	public List<Paragraph> getParagraphs() {
		return paragraphs;
	}

	/**
	 * 添加章节
	 * 
	 * @author liulongbiao
	 * @created 2014年12月8日 下午3:16:27
	 * @since v3.2
	 * @param p
	 */
	public void addParagraph(Paragraph p) {
		paragraphs.add(p);
	}

	public void addParagraphs(Collection<Paragraph> ps) {
		paragraphs.addAll(ps);
	}

	public void appendDoc(Doc doc) {
		if (doc != null) {
			addParagraphs(doc.getParagraphs());
		}
	}

	public void removeEmptyParagraphs() {
		paragraphs = ListUtils.filter(paragraphs, ParagraphHasContent.INSTANCE);
	}

	public boolean isEmpty() {
		return paragraphs.isEmpty();
	}

	public boolean isSingleParagraph() {
		return paragraphs.size() == 1;
	}

	@Override
	public void accept(DocVisitor visitor) {
		visitor.preVisit(this);
		if (CollectionUtils.isNotEmpty(paragraphs)) {
			for (Paragraph p : paragraphs) {
				p.accept(visitor);
			}
		}
		visitor.postVisit(this);
	}

	private static enum ParagraphHasContent implements Predicate<Paragraph> {
		INSTANCE;

		@Override
		public boolean test(Paragraph t) {
			return t != null && !t.isEmpty();
		}

	}
}
