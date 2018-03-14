/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.command;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.strong.leke.common.utils.StringUtils;

/**
 * 命令处理器注册表
 * 
 * @author liulongbiao
 * @created 2014年12月9日 下午1:25:57
 * @since v3.2
 */
public class CommandHandlerRegistry {

	private Map<String, CommandHandler> namedHandlers;
	private Map<Pattern, CommandHandler> patternHandlers;

	public CommandHandlerRegistry() {
		super();
		namedHandlers = new HashMap<String, CommandHandler>();
		patternHandlers = new HashMap<Pattern, CommandHandler>();
	}

	/**
	 * 获取命令处理器
	 * 
	 * @author liulongbiao
	 * @created 2014年12月9日 下午1:40:53
	 * @since v3.2
	 * @param name
	 * @return
	 */
	public CommandHandler getCommandHandler(String name) {
		if(StringUtils.isEmpty(name)) {
			return null;
		}
		CommandHandler result = namedHandlers.get(name);
		if (result == null) {
			for (Map.Entry<Pattern, CommandHandler> entry : patternHandlers
					.entrySet()) {
				Pattern pattern = entry.getKey();
				Matcher mat = pattern.matcher(name);
				if (mat.matches()) {
					result = entry.getValue();
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 注册处理器
	 * 
	 * @author liulongbiao
	 * @created 2014年12月9日 下午1:45:54
	 * @since v3.2
	 * @param name
	 * @param handler
	 */
	public void regist(String name, CommandHandler handler) {
		if (StringUtils.isNotEmpty(name) && handler != null) {
			namedHandlers.put(name, handler);
		}
	}

	/**
	 * 注册处理器
	 * 
	 * @author liulongbiao
	 * @created 2014年12月9日 下午1:45:54
	 * @since v3.2
	 * @param name
	 * @param handler
	 */
	public void regist(Pattern pattern, CommandHandler handler) {
		if (pattern != null && handler != null) {
			patternHandlers.put(pattern, handler);
		}
	}
}
