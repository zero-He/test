/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.command;

import java.util.List;

import javax.annotation.PostConstruct;

import cn.strong.leke.question.word.DocCommandParser;
import cn.strong.leke.question.word.model.Doc;
import cn.strong.leke.question.word.model.QuestionPart;

/**
 * 命令解析器
 * 
 * @author liulongbiao
 * @created 2014年12月12日 下午2:32:11
 * @since v3.2
 */
public class CommandParser implements DocCommandParser {

	private CommandHandlerRegistry registry;

	public void setRegistry(CommandHandlerRegistry registry) {
		this.registry = registry;
	}

	@PostConstruct
	public void afterPropertiesSet() {
		if (registry == null) {
			registry = CommandHandlerRegistryFactory.defaultInstance();
		}
	}

	@Override
	public List<QuestionPart> parse(Doc doc) {
		CommandParseVisitor visitor = new CommandParseVisitor(registry);
		doc.accept(visitor);
		return visitor.getQuestionParts();
	}

}
