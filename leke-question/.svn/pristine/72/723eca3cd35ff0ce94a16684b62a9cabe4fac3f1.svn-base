/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.strong.leke.common.utils.StringUtils;

/**
 * 分词权重
 * 
 * @author liulongbiao
 * @created 2015年1月14日 下午4:41:34
 * @since v3.2.2
 */
public class TermWeighter {

	private static final int MISSING_WEIGHT = 5;
	private static Map<String, Integer> dict;
	private static Logger logger = LoggerFactory.getLogger(TermWeighter.class);

	static {
		Map<String, Integer> map = new HashMap<String, Integer>();
		InputStreamReader input = null;
		BufferedReader reader = null;
		try {
			input = new InputStreamReader(TermWeighter.class
							.getResourceAsStream("/data/term_weight.txt"));
			reader = new BufferedReader(input);
			String line = reader.readLine();
			while (line != null) {
				String[] pair = StringUtils.split(line, '\t');
				int weight = Integer.valueOf(pair[1]);
				map.put(pair[0], weight);
				line = reader.readLine();
			}
		} catch (IOException e) {
			logger.error("load term weight error.", e);
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(reader);
		}
		dict = map;
	}

	/**
	 * 获取权重
	 * 
	 * @author liulongbiao
	 * @created 2015年1月15日 下午4:26:46
	 * @since v3.2.2
	 * @param term
	 * @return
	 */
	public static int weight(String term) {
		Integer w = dict.get(term);
		return w == null ? MISSING_WEIGHT : w;
	}
}
