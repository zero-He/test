package cn.strong.leke.diag.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.strong.leke.core.io.ResLoaders;

public class MapReduceUtils {

	private static final String PREFIX = "classpath:/conf/mongo/";

	private static final Map<String, String> cache = new ConcurrentHashMap<>();

	public static String getMap(String name) {
		return getUsedCache(name + ".map");
	}

	public static String getReduce(String name) {
		return getUsedCache(name + ".reduce");
	}

	public static String getFinalize(String name) {
		return getUsedCache(name + ".finalize");
	}

	private static String getUsedCache(String name) {
		if (cache.containsKey(name)) {
			return cache.get(name);
		}
		return ResLoaders.loadString(PREFIX + name + ".js");
	}
}