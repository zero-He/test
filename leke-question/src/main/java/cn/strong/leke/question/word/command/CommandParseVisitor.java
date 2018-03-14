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
import java.util.regex.Pattern;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.question.word.ParseCsts.CommandNames;
import cn.strong.leke.question.word.model.BaseDocVisitor;
import cn.strong.leke.question.word.model.Br;
import cn.strong.leke.question.word.model.Command;
import cn.strong.leke.question.word.model.Img;
import cn.strong.leke.question.word.model.Paragraph;
import cn.strong.leke.question.word.model.QuestionPart;
import cn.strong.leke.question.word.model.Text;

/**
 * 解析文档命令
 * 
 * @author liulongbiao
 * @created 2014年12月9日 上午8:50:35
 * @since v3.2
 */
public class CommandParseVisitor extends BaseDocVisitor {

	private static final char DELEMITER_OPEN = '【';
	private static final char DELEMITER_CLOSE = '】';
	private static final Pattern PATTERN_SP_CMDS = Pattern.compile("[,，]");
	private static final Pattern PATTERN_SP_CMD_V = Pattern.compile("[:：]");

	private CommandParseCtx ctx;
	private CommandHandlerRegistry registry;
	private boolean isFirstCmd = true;
	private boolean lineBegin = true;
	private StringBuilder cmdCollector = new StringBuilder();
	private boolean cmdCollecting = false;

	public CommandParseVisitor() {
		this(CommandHandlerRegistryFactory.defaultInstance());
	}

	public CommandParseVisitor(CommandHandlerRegistry registry) {
		super();
		this.ctx = new CommandParseCtx();
		this.registry = registry != null ? registry
				: CommandHandlerRegistryFactory.defaultInstance();
	}

	public void setRegistry(CommandHandlerRegistry registry) {
		this.registry = registry;
	}

	/**
	 * 获取结果
	 * 
	 * @author liulongbiao
	 * @created 2014年12月12日 下午2:38:08
	 * @since v3.2
	 * @return
	 */
	public List<QuestionPart> getQuestionParts() {
		return ctx.getQuestions();
	}

	@Override
	public void preVisit(Paragraph p) {
		ctx.paragraphStarted();
		lineBegin = true;
	}

	@Override
	public void preVisit(Text text) {
		String content = StringUtils.trim(text.getContent());
		if (StringUtils.isEmpty(content)) {
			return;
		}
		if (cmdCollecting) {
			collectCmd(text);
		} else {
			if (lineBegin && content.charAt(0) == DELEMITER_OPEN) {
				cmdCollecting = true;
				text.setContent(content.substring(1));
				collectCmd(text);
			}
		}
	}

	private void collectCmd(Text text) {
		String content = StringUtils.trim(text.getContent());
		if (StringUtils.isEmpty(content)) {
			return;
		}
		int closeIdx = content.indexOf(DELEMITER_CLOSE);
		if (closeIdx != -1) {
			cmdCollector.append(content.substring(0, closeIdx));
			processCommands();

			cmdCollecting = false;
			lineBegin = true;
			content = StringUtils.trim(content.substring(closeIdx + 1));
			text.setContent(content);
			preVisit(text);
		} else {
			cmdCollector.append(content);
		}
	}

	private void processCommands() {
		String cmds = cmdCollector.toString();
		cmdCollector = new StringBuilder();

		String[] pairs = PATTERN_SP_CMDS.split(cmds);
		for (String pair : pairs) {
			String[] entry = PATTERN_SP_CMD_V.split(pair);
			Command cmd = new Command(entry[0].trim());
			if (entry.length > 1) {
				cmd.setValue(entry[1].trim());
			}
			this.handleCommand(cmd);
		}
	}

	private void handleCommand(Command cmd) {
		String name = cmd.getName();
		CommandHandler handler = registry.getCommandHandler(name);
		if (handler != null) {
			if (isFirstCmd && !CommandNames.TYPE.equals(name)) {
				throw new LekeRuntimeException(
						"que.word.command.first.must.be.type.command");
			}
			handler.process(cmd, ctx);
			isFirstCmd = false;
		}
	}

	@Override
	public void postVisit(Text text) {
		if (cmdCollecting) {
			return;
		}
		String content = StringUtils.trim(text.getContent());
		if (StringUtils.isNotEmpty(content)) {
			ctx.addInline(text);
			lineBegin = false;
		}
	}

	@Override
	public void postVisit(Br br) {
		if (cmdCollecting) {
			return;
		}
		ctx.addInline(br);
		lineBegin = true;
	}

	@Override
	public void postVisit(Img img) {
		if (cmdCollecting) {
			return;
		}
		ctx.addInline(img);
		lineBegin = false;
	}

}
