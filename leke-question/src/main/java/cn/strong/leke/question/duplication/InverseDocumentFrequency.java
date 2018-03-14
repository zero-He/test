/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication;

import java.util.HashMap;
import java.util.Map;

/**
 * 反向文档频度
 * 
 * @author liulongbiao
 * @created 2015年1月15日 上午9:05:47
 * @since v3.2.2
 */
public class InverseDocumentFrequency {

	private Map<String, Integer> dfs;
	private int docs;

	public InverseDocumentFrequency() {
		super();
		dfs = new HashMap<String, Integer>();
		docs = 0;
	}

	public void add(TermFrequency tf) {
		if (tf == null) {
			return;
		}
		docs++;
		Map<String, Integer> tfs = tf.tfs();
		for (String word : tfs.keySet()) {
			Integer count = dfs.get(word);
			if (count == null) {
				count = 0;
			}
			dfs.put(word, count + 1);
		}
	}

	public int getDocs() {
		return docs;
	}

	public double idf(String word) {
		Integer df = dfs.get(word);
		if (df == null) {
			df = 0;
		}
		double seed = (double) docs / (df + 1);
		return Math.log10(seed);
	}

	public Map<String, Double> idfs() {
		Map<String, Double> result = new HashMap<String, Double>();
		for (String word : dfs.keySet()) {
			result.put(word, idf(word));
		}
		return result;
	}
}
