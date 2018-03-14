/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.command;

import static cn.strong.leke.question.word.ParseCsts.CommandNames.ANALYSIS;
import static cn.strong.leke.question.word.ParseCsts.CommandNames.ANSWER;
import static cn.strong.leke.question.word.ParseCsts.CommandNames.DIFFICULTY;
import static cn.strong.leke.question.word.ParseCsts.CommandNames.GRADE;
import static cn.strong.leke.question.word.ParseCsts.CommandNames.SOURCE;
import static cn.strong.leke.question.word.ParseCsts.CommandNames.STEM;
import static cn.strong.leke.question.word.ParseCsts.CommandNames.SUBJECTIVE;
import static cn.strong.leke.question.word.ParseCsts.CommandNames.TYPE;
import static cn.strong.leke.question.word.ParseCsts.CommandNames.YEAR;
import static cn.strong.leke.question.word.ParseCsts.QuestionTypeNames.CLOZE;
import static cn.strong.leke.question.word.ParseCsts.QuestionTypeNames.OPENEND;
import static cn.strong.leke.question.word.ParseCsts.QuestionTypeNames.READING;
import static cn.strong.leke.question.word.ParseCsts.QuestionTypeNames.SINGLE_CHOICE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.question.word.command.CommandParseCtx.ControlMode;
import cn.strong.leke.question.word.model.Command;
import cn.strong.leke.question.word.model.QuestionPart;

/**
 * 命令处理器注册表工厂
 * 
 * @author liulongbiao
 * @created 2014年12月9日 下午3:26:49
 * @since v3.2
 */
public class CommandHandlerRegistryFactory {

	private static final Pattern CMD_SUB = Pattern
			.compile("^小题\\s*(\\d+)\\s*$");

	/**
	 * 获取默认的命令处理器注册表实例
	 * 
	 * @author liulongbiao
	 * @created 2014年12月9日 下午7:01:15
	 * @since v3.2
	 * @return
	 */
	public static CommandHandlerRegistry defaultInstance() {
		CommandHandlerRegistry result = new CommandHandlerRegistry();

		result.regist(TYPE, TypeCommandHandler.INSTANCE);
		
		String[] controls = new String[] { STEM, ANSWER, ANALYSIS };
		for (String ctrl : controls) {
			result.regist(ctrl, ControlCommandHandler.INSTANCE);
		}

		String[] configs =  new String[] { DIFFICULTY, SOURCE, YEAR, GRADE,
				SUBJECTIVE };
		for (String cfg : configs) {
			result.regist(cfg, ConfigCommandHandler.INSTANCE);
		}

		result.regist(CMD_SUB, SubCommandHandler.INSTANCE);

		return result;
	}

	public enum TypeCommandHandler implements CommandHandler {
		INSTANCE;

		@Override
		public void process(Command cmd, CommandParseCtx ctx) {
			String type = cmd.getValue();
			ControlMode mode = ctx.getMode();
			String control = ctx.getControl();
			if (ControlMode.Sub.equals(mode) && STEM.equals(control)) {
				// 设置类型
				QuestionPart sub = ctx.peekCurrentQuestion();
				sub.setType(cmd);
				ctx.setDefaultSubType(type);
			} else {
				// 新题
				QuestionPart current = new QuestionPart();
				current.setType(cmd);
				ctx.addQuestion(current);
				ctx.setCurrent(current);
				
				ctx.setControl(null);
				ctx.setMode(ControlMode.Normal);
				String subType = OPENEND;
				if (CLOZE.equals(type) || READING.equals(type)) {
					subType = SINGLE_CHOICE;
				}
				ctx.setDefaultSubType(subType);
			}
		}
	}

	public enum ControlCommandHandler implements CommandHandler {
		INSTANCE;

		@Override
		public void process(Command cmd, CommandParseCtx ctx) {
			QuestionPart current = ctx.peekCurrentQuestion();
			current.addCommand(cmd);
			ctx.setControl(cmd.getName());
			ctx.setMode(ControlMode.Normal);
		}

	}

	public enum ConfigCommandHandler implements CommandHandler {
		INSTANCE;

		@Override
		public void process(Command cmd, CommandParseCtx ctx) {
			QuestionPart current = ctx.peekCurrentQuestion();
			current.addCommand(cmd);
		}

	}
	
	public enum SubCommandHandler implements CommandHandler {
		INSTANCE;

		@Override
		public void process(Command cmd, CommandParseCtx ctx) {
			String name = cmd.getName();
			Matcher mat = CMD_SUB.matcher(name);
			if (!mat.find()) {
				throw new LekeRuntimeException(
						"que.word.command.sub.pattern.unmatch");
			}
			String ord = mat.group(1);
			int index = Integer.parseInt(ord) - 1;

			QuestionPart current = ctx.getCurrent();
			if (current == null) {
				throw new LekeRuntimeException(
						"que.word.command.sub.missing.parent.context", name);
			}
			QuestionPart cursor = current.getChild(index);
			if (cursor == null) {
				cursor = new QuestionPart();
				cursor.setType(Command.newTypeCommand(ctx.getDefaultSubType()));
				current.setChild(index, cursor);
			}

			cursor.addCommand(new Command(ctx.getControl()));

			ctx.setMode(ControlMode.Sub);
			ctx.setSubIndex(index);
		}

	}
}
