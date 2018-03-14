/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import cn.strong.leke.question.word.ParseCsts.CommandNames;

/**
 * 习题部分
 * 
 * @author liulongbiao
 * @created 2014年12月9日 上午9:16:46
 * @since v3.2
 */
public class QuestionPart {

	private Map<String, Command> commands;
	private JsArray<QuestionPart> children;

	public QuestionPart() {
		super();
		commands = new HashMap<String, Command>();
		children = new JsArray<QuestionPart>();
	}

	/**
	 * 添加命令
	 * 
	 * @author liulongbiao
	 * @created 2014年12月9日 下午3:54:14
	 * @since v3.2
	 * @param cmd
	 */
	public void addCommand(Command cmd) {
		if (cmd == null || StringUtils.isEmpty(cmd.getName())) {
			throw new RuntimeException("command name is missing.");
		}
		commands.put(cmd.getName(), cmd);
	}

	/**
	 * 获取命令
	 * 
	 * @author liulongbiao
	 * @created 2014年12月9日 下午3:54:27
	 * @since v3.2
	 * @param name
	 * @return
	 */
	public Command getCommand(String name) {
		return commands.get(name);
	}

	public Command getType() {
		return getCommand(CommandNames.TYPE);
	}

	public void setType(Command type) {
		if (type == null || !type.isTypeCommand()) {
			throw new RuntimeException("income command is not a type command.");
		}
		addCommand(type);
	}

	public void setChild(int index, QuestionPart child) {
		children.set(index, child);
	}

	public QuestionPart getChild(int index) {
		return children.get(index);
	}

	public Map<String, Command> getCommands() {
		return commands;
	}

	public JsArray<QuestionPart> getChildren() {
		return children;
	}

}
