/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年6月25日 下午3:27:56
 * @since   v1.0.0
 */
public class JsonHelper {

	private static final ObjectMapper om;
	static {
		om = new ObjectMapper();
		om.enable(SerializationFeature.INDENT_OUTPUT);
	}

	public static String toJson(Object obj) {
		String result = null;
		try {
			result = om.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void printJson(Object obj) {
		System.out.println(toJson(obj));
	}
}
