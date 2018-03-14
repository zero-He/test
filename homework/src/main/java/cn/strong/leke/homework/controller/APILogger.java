package cn.strong.leke.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APILogger {

	private static final Logger logger = LoggerFactory.getLogger(APILogger.class);

	/**
	 * API接口调用日志，用于记录访问数据。
	 * @param name
	 * @param data
	 */
	public static void invoking(String name, String data) {
		logger.info("Invoke api: {}, {}.", name, data);
	}

	/**
	 * 过时的日志。
	 * 在过时的API接口方法中加入该日志，后续日志中如果找不到它，可删除过时的接口方法。
	 * @param name
	 * @param data
	 */
	public static void departed(String name, String data) {
		logger.info("Deprecated API: {}, {}.", name, data);
	}

}
