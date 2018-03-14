/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.springframework.web.util.HtmlUtils;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.model.question.Answer;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionStem;

import com.chenlb.mmseg4j.ComplexSeg;
import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MMSeg;
import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.Word;

/**
 * 习题内容提取
 * 
 * @author liulongbiao
 * @created 2015年1月14日 上午9:50:36
 * @since v3.2.2
 */
public class QuestionContentExtracter {

	public static final Pattern PAT_TAG = Pattern.compile("<[\\s\\S]+?>");
	public static final Pattern PAT_NBSP = Pattern.compile("&nbsp;");
	public static final Pattern PAT_WS = Pattern.compile("\\s+");

	private static Dictionary dic = Dictionary.getInstance();

	/**
	 * 获取习题相似性文档
	 * 
	 * @author liulongbiao
	 * @created 2015年1月17日 下午3:33:05
	 * @since v3.2.2
	 * @param dto
	 * @return
	 */
	public static QueSimDoc getQueSimDoc(QuestionDTO dto) {
		if (dto == null || dto.getQuestionId() == null) {
			return null;
		}
		TermFrequency tf = QuestionContentExtracter.getTermFrequency(dto);
		List<WTerm> terms = weightedTerms(tf);
		long simHash = SimHash.simhash64(terms);
		List<Integer> buckets = SimHash.buckets(simHash);
		List<WTerm> keywords = keywords(terms);

		QueSimDoc result = new QueSimDoc();
		result.setQuestionId(dto.getQuestionId());
		result.setSimHash(simHash);
		result.setBuckets(buckets);
		result.setTermCount(terms.size());
		result.setKeywords(keywords);
		result.setSchoolStageId(dto.getSchoolStageId());
		result.setSubjectId(dto.getSubjectId());
		return result;
	}

	private static List<WTerm> weightedTerms(TermFrequency tf) {
		Map<String, Integer> tfs = tf.tfs();
		if (CollectionUtils.isEmpty(tfs)) {
			return Collections.emptyList();
		}
		List<WTerm> result = new ArrayList<WTerm>(tfs.size());
		for (Map.Entry<String, Integer> entry : tfs.entrySet()) {
			String term = entry.getKey();
			int w = entry.getValue() * TermWeighter.weight(term);
			result.add(new WTerm(term, w));
		}
		return result;
	}

	private static List<WTerm> keywords(List<WTerm> terms) {
		if (CollectionUtils.isEmpty(terms)) {
			return Collections.emptyList();
		}
		PriorityQueue<WTerm> queue = new PriorityQueue<WTerm>(terms.size());
		queue.addAll(terms);
		return topK(queue, QueDupCst.KEYWORD_SIZE);
	}

	private static <T> List<T> topK(PriorityQueue<T> queue, int size) {
		List<T> result = new ArrayList<T>(size);
		int i = 0;
		while (i < size && !queue.isEmpty()) {
			result.add(queue.poll());
			i++;
		}
		return result;
	}

	/**
	 * 提取习题词频
	 * 
	 * @author liulongbiao
	 * @created 2015年1月14日 下午5:09:11
	 * @since v3.2.2
	 * @param question
	 * @return
	 */
	public static TermFrequency getTermFrequency(QuestionDTO question) {
		String content = extractContent(question);
		return getTermFrequency(content);
	}

	public static TermFrequency getTermFrequency(String content) {
		TermFrequency tf = new TermFrequency();
		StringReader reader = new StringReader(content);
		Seg seg = new ComplexSeg(dic);
		MMSeg mmSeg = new MMSeg(reader, seg);
		Word word = null;
		try {
			while ((word = mmSeg.next()) != null) {
				tf.add(word);
			}
		} catch (IOException e) {
			throw new RuntimeException("read string content exception.", e);
		} finally {
			IOUtils.closeQuietly(reader);
		}
		return tf;
	}

	/**
	 * 提取习题内容
	 * 
	 * @author liulongbiao
	 * @created 2015年1月14日 下午3:39:17
	 * @since v3.2.2
	 * @param question
	 * @return
	 */
	public static String extractContent(QuestionDTO question) {
		if (question == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		QuestionStem stem = question.getStem();
		if (stem != null) {
			sb.append(extractHtml(stem.getStemContent()));
		}
		List<Answer> answers = question.getAnswers();
		if(CollectionUtils.isNotEmpty(answers)) {
			for (Answer answer : answers) {
				sb.append(extractHtml(answer.getAnswerContent()));
			}
		}
		List<QuestionDTO> subs = question.getSubs();
		if(CollectionUtils.isNotEmpty(subs)) {
			for(QuestionDTO sub: subs) {
				sb.append(extractContent(sub));
			}
		}
		return sb.toString();
	}

	public static String extractHtml(String html) {
		if (StringUtils.isEmpty(html)) {
			return "";
		}
		String result = replace(html, PAT_TAG, " ");
		result = replace(result, PAT_NBSP, " ");
		result = HtmlUtils.htmlUnescape(result);
		result = replace(result, PAT_WS, " ");
		return result.trim() + '\n';
	}

	private static String replace(String input, Pattern pattern,
			String replacement) {
		Matcher mat = pattern.matcher(input);
		return mat.replaceAll(replacement);
	}
}
