/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 文本相似度Hash
 * 
 * @author liulongbiao
 * @created 2014年12月31日 上午9:26:45
 * @since v3.2.2
 */
public class SimHash {

	/**
	 * 64位 simhash
	 * 
	 * @author liulongbiao
	 * @created 2014年12月31日 上午10:32:29
	 * @since v3.2.2
	 * @param doc
	 * @return
	 */
	public static long simhash64(List<WTerm> terms) {
		int bitLen = 64;
		int[] bits = new int[bitLen];
		for (WTerm term : terms) {
			long hash = MurmurHash.hash64(term.getT());
			for (int i = bitLen; i >= 1; --i) {
				boolean exist = ((hash >> (bitLen - i)) & 1) == 1;
				int inc = (exist ? 1 : -1) * term.getW();
				bits[i - 1] = bits[i - 1] + inc;
			}
		}
		long hash = 0x0000000000000000;
		long one = 0x0000000000000001;
		for (int i = bitLen; i >= 1; --i) {
			if (bits[i - 1] > 1) {
				hash |= one;
			}
			one = one << 1;
		}
		return hash;
	}

	/**
	 * 获取 Hash 分组
	 * 
	 * @author liulongbiao
	 * @created 2015年1月17日 下午2:13:39
	 * @since v3.2.2
	 * @param hash
	 * @return
	 */
	public static List<Integer> buckets(long hash) {
		List<Integer> result = new ArrayList<Integer>(4);
		int[] keys = bucketArray(hash);
		for(int key: keys) {
			if(!result.contains(key)) {
				result.add(key);
			}
		}
		return result;
	}

	public static int[] bucketArray(long hash) {
		int[] buckets = new int[4];
		int shift = 16;
		int radix = 1 << shift;
		long mask = radix - 1;
		int i = 4;
		do {
			buckets[--i] = (int) (hash & mask);
			hash >>>= shift;
		} while (hash != 0);
		return buckets;
	}

	/**
	 * 获取两个 Hash 值之间的海明距离
	 * 
	 * @author liulongbiao
	 * @created 2015年1月17日 下午4:19:41
	 * @since v3.2.2
	 * @param hash1
	 * @param hash2
	 * @return
	 */
	public static int hammingDistance(long hash1, long hash2) {
		long i = hash1 ^ hash2;
		i = i - ((i >>> 1) & 0x5555555555555555L);
		i = (i & 0x3333333333333333L) + ((i >>> 2) & 0x3333333333333333L);
		i = (i + (i >>> 4)) & 0x0f0f0f0f0f0f0f0fL;
		i = i + (i >>> 8);
		i = i + (i >>> 16);
		i = i + (i >>> 32);
		return (int) i & 0x7f;
	}
}
