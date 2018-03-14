/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication;

import cn.strong.leke.common.utils.StringUtils;

/**
 * 带权重的分词
 * 
 * @author liulongbiao
 * @created 2015年1月17日 上午10:21:29
 * @since v3.2.2
 */
public class WTerm implements Comparable<WTerm> {

	private String t;
	private int w;

	public WTerm() {
		super();
	}

	public WTerm(String t, int w) {
		super();
		this.t = t;
		this.w = w;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		if (StringUtils.isEmpty(t)) {
			throw new RuntimeException("term text should not be empty");
		}
		this.t = t;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	@Override
	public int compareTo(WTerm that) {
		int result = that.w - this.w;
		if (result == 0) {
			result = this.t.compareTo(that.t);
		}
		return result;
	}

}
