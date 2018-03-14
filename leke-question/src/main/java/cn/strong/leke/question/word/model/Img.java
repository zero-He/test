/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.model;

import org.springframework.util.Assert;

import cn.strong.leke.common.utils.StringUtils;

/**
 * 图片
 * 
 * @author liulongbiao
 * @created 2014年12月8日 下午4:43:12
 * @since v3.2
 */
public class Img implements Inline {

	private String url;
	private ImgDim dim;

	public Img(String url) {
		Assert.notNull(url, "image url should not be null.");
		this.url = url;
	}

	public Img(String url, ImgDim dim) {
		this(url);
		this.dim = dim;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ImgDim getDim() {
		return dim;
	}

	public void setDim(ImgDim dim) {
		this.dim = dim;
	}

	@Override
	public String html() {
		if (StringUtils.isEmpty(url)) {
			return "";
		}
		StringBuilder sb = new StringBuilder("<img src=\"");
		sb.append(url);
		sb.append("\"");
		if (dim != null) {
			sb.append(" style=\"width:");
			sb.append(dim.width);
			sb.append("px;height:");
			sb.append(dim.height);
			sb.append("px;\"");
		}
		sb.append("/>");
		return sb.toString();
	}

	@Override
	public void accept(DocVisitor visitor) {
		visitor.preVisit(this);
		visitor.postVisit(this);
	}
}
