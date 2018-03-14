/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;

/**
 *
 *
 * @author  liulongbiao
 * @created 2015年1月21日 上午10:51:50
 * @since   v3.2.2
 */
public class QueSimDocUtils {

	/**
	 * 判断两个文档是否具有相似的 SimHash 值
	 * <p>
	 * simhash 的海明距离小于等于 QueDupCst.MAX_SIMHASH_HM_DIST
	 * </p>
	 * 
	 * @author liulongbiao
	 * @created 2015年1月21日 上午10:53:11
	 * @since v3.2.2
	 * @param doc
	 * @param ref
	 * @return
	 */
	public static boolean hasSimilarSimHash(QueSimDoc doc, QueSimDoc ref) {
		if (ref == null || doc == null) {
			return false;
		}
		long hash1 = doc.getSimHash();
		long hash2 = ref.getSimHash();
		int dist = SimHash.hammingDistance(hash1, hash2);
		return dist <= QueDupCst.MAX_SIMHASH_HM_DIST;
	}

	/**
	 * 判断两个文档是否具有相似的关键词
	 * <p>
	 * 关键词余弦距离小于
	 * </p>
	 * 
	 * @author liulongbiao
	 * @created 2015年1月21日 上午10:56:59
	 * @since v3.2.2
	 * @param doc
	 * @param ref
	 * @return
	 */
	public static boolean hasSimilarKeywords(QueSimDoc doc, QueSimDoc ref) {
		Map<String, Integer> mdoc = wtermMap(doc);
		Map<String, Integer> mref = wtermMap(ref);
		double dist = cosineSimilarity(mdoc, mref);
		return dist > QueDupCst.MIN_COS_SIM;
	}

	/**
	 * 获取两个
	 * 
	 * @author liulongbiao
	 * @created 2015年1月21日 上午11:29:46
	 * @since v3.2.2
	 * @param mdoc
	 * @param mref
	 * @return
	 */
	public static double cosineSimilarity(Map<String, Integer> mdoc,
			Map<String, Integer> mref) {
		if (CollectionUtils.isEmpty(mdoc) || CollectionUtils.isEmpty(mref)) {
			return 0;
		}
		Set<String> kws = new HashSet<String>(mdoc.keySet());
		kws.addAll(mref.keySet());
		double suma2 = 0;
		double sumb2 = 0;
		double sumab = 0;
		for (String kw : kws) {
			int wa = weight(mdoc, kw);
			int wb = weight(mref, kw);
			suma2 += wa * wa;
			sumb2 += wb * wb;
			sumab += wa * wb;
		}
		return sumab / (Math.sqrt(suma2) * Math.sqrt(sumb2));
	}

	private static int weight(Map<String, Integer> mdoc, String key) {
		Integer w = mdoc.get(key);
		return w == null ? 0 : w;
	}

	private static Map<String, Integer> wtermMap(QueSimDoc doc) {
		if (doc == null || CollectionUtils.isEmpty(doc.getKeywords())) {
			return Collections.emptyMap();
		}
		return wtermMap(doc.getKeywords());
	}

	private static Map<String, Integer> wtermMap(List<WTerm> keywords) {
		if (CollectionUtils.isEmpty(keywords)) {
			return Collections.emptyMap();
		}
		Map<String, Integer> result = new HashMap<String, Integer>();
		for (WTerm term : keywords) {
			result.put(term.getT(), term.getW());
		}
		return result;
	}
}
