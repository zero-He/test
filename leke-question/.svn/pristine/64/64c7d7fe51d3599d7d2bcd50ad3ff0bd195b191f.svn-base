/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.model;

import org.springframework.web.util.HtmlUtils;

import cn.strong.leke.common.utils.StringUtils;

/**
 * 文本
 * 
 * @author liulongbiao
 * @created 2014年12月8日 下午4:42:34
 * @since v3.2
 */
public class Text implements Inline {

	private String content; // 内容
	private boolean bold;
	private boolean italic;
	private boolean underline;
	private boolean sup;
	private boolean sub;

	public Text() {
		super();
	}

	public Text(String content) {
		this.setContent(content);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isBold() {
		return bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public boolean isItalic() {
		return italic;
	}

	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	public boolean isUnderline() {
		return underline;
	}

	public void setUnderline(boolean underline) {
		this.underline = underline;
	}

	public boolean isSup() {
		return sup;
	}

	public void setSup(boolean sup) {
		this.sup = sup;
	}

	public boolean isSub() {
		return sub;
	}

	public void setSub(boolean sub) {
		this.sub = sub;
	}

	@Override
	public String html() {
		if (StringUtils.isEmpty(content)) {
			return "";
		}
		String str = HtmlUtils.htmlEscape(content);
		str = str.replaceAll("\\s{2}", " &nbsp;");
		StringBuilder sb = new StringBuilder(str);
		if (bold) {
			wrap(sb, "b");
		}
		if (italic) {
			wrap(sb, "i");
		}
		if (underline) {
			wrap(sb, "u");
		}
		if (sub) {
			wrap(sb, "sub");
		}
		if (sup) {
			wrap(sb, "sup");
		}
		return sb.toString();
	}

	private static void wrap(StringBuilder sb, String tag) {
		sb.insert(0, "<" + tag + ">");
		sb.append("</" + tag + ">");
	}

	/**
	 * 拷贝一份
	 * 
	 * @author liulongbiao
	 * @created 2014年12月10日 上午9:02:13
	 * @since v3.2
	 * @return
	 */
	public Text copy() {
		Text result = new Text();
		result.setContent(content);
		result.setBold(bold);
		result.setItalic(italic);
		result.setUnderline(underline);
		result.setSub(sub);
		result.setSup(sup);
		return result;
	}

	/**
	 * 是否具有相同的样式
	 * 
	 * @author liulongbiao
	 * @created 2014年12月22日 上午10:46:16
	 * @since v3.2.1
	 * @param that
	 * @return
	 */
	public boolean hasSameStyle(Text that) {
		if (that == null) {
			return false;
		}
		return this.bold == that.bold && this.italic == that.italic
				&& this.underline == that.underline && this.sub == that.sub
				&& this.sup == that.sup;
	}

	/**
	 * 添加文本
	 * 
	 * @author liulongbiao
	 * @created 2014年12月22日 上午10:55:56
	 * @since v3.2.1
	 * @param text
	 */
	public void append(String text) {
		if(text == null || text.isEmpty()) {
			return;
		}
		String old = this.content;
		this.content = old == null ? text : old + text;
	}

	@Override
	public void accept(DocVisitor visitor) {
		visitor.preVisit(this);
		visitor.postVisit(this);
	}

}
