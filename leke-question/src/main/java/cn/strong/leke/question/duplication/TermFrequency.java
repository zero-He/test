/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication;

import static com.chenlb.mmseg4j.Word.TYPE_DIGIT;
import static com.chenlb.mmseg4j.Word.TYPE_DIGIT_OR_LETTER;
import static com.chenlb.mmseg4j.Word.TYPE_LETTER_NUMBER;
import static com.chenlb.mmseg4j.Word.TYPE_LETTER_OR_DIGIT;
import static com.chenlb.mmseg4j.Word.TYPE_OTHER_NUMBER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.strong.leke.common.utils.StringUtils;

import com.chenlb.mmseg4j.Word;

/**
 * 词频
 * 
 * @author liulongbiao
 * @created 2015年1月14日 下午5:11:03
 * @since v3.2.2
 */
public class TermFrequency {

	private Map<String, Integer> tfs;

	public TermFrequency() {
		super();
		tfs = new HashMap<String, Integer>();
	}

	public void add(Word word) {
		String type = word.getType();
		String content = word.getString();
		if (TYPE_DIGIT.equals(type) || TYPE_LETTER_NUMBER.equals(type)
				|| TYPE_OTHER_NUMBER.equals(type)) {
			return;
		} else if (TYPE_LETTER_OR_DIGIT.equals(type)
				|| TYPE_DIGIT_OR_LETTER.equals(type)) {
			List<String> letters = extractLetters(content);
			for (String letter : letters) {
				add(letter);
			}
		} else {
			add(content);
		}
	}

	private List<String> extractLetters(String content) {
		List<String> result = new ArrayList<String>();
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		for (int i = 0, len = content.length(); i < len; i++) {
			char ch = content.charAt(i);
			boolean isLetter = Character.isLetter(ch);

			if (isLetter != flag) {
				if (sb.length() > 0) {
					result.add(sb.toString());
				}
				sb = new StringBuilder();
			}

			if (isLetter) {
				sb.append(ch);
			}

			flag = isLetter;
		}
		if (sb.length() > 0) {
			result.add(sb.toString());
		}
		return result;
	}

	public void add(String word) {
		if (StringUtils.isNotEmpty(word)) {
			Integer count = tfs.get(word);
			if (count == null) {
				count = 0;
			}
			tfs.put(word, count + 1);
		}
	}

	public Map<String, Integer> tfs() {
		return tfs;
	}
}
