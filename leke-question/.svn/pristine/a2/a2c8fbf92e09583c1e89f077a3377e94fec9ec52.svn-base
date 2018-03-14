/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word;

import org.junit.Ignore;
import org.junit.Test;

import cn.strong.leke.question.word.model.Doc;
import cn.strong.leke.question.word.model.DocCtx;
import cn.strong.leke.question.word.model.Text;

/**
 * 
 *
 * @author  liulongbiao
 * @created 2014年12月10日 下午8:17:08
 * @since   v3.2
 */
public class DocMergeTest {

	@Test
	@Ignore
	public void testMerge() {
		DocCtx c1 = new DocCtx();
		c1.addInline(new Text("第一段"));

		DocCtx c2 = new DocCtx();
		c2.addInline(new Text("第二段"));

		Doc total = Doc.merge(c1.getDoc(), c2.getDoc());
		System.out.println(total.getParagraphs().size());
		System.out.println(Doc2HtmlConverter.convert(total));
	}
}
