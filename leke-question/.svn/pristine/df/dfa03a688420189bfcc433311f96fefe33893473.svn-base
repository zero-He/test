/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.command;

import java.util.ArrayList;
import java.util.List;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.question.word.ParseCsts.QuestionTypeNames;
import cn.strong.leke.question.word.model.Command;
import cn.strong.leke.question.word.model.Inline;
import cn.strong.leke.question.word.model.QuestionPart;

/**
 * 
 *
 * @author  liulongbiao
 * @created 2014年12月9日 下午2:37:34
 * @since   v3.2
 */
public class CommandParseCtx {

	private QuestionPart current; // 当前习题
	private ControlMode mode = ControlMode.Normal; // 命令环境模式
	private int subIndex = -1; // 子索引
	private String control; // 当前默认控件名
	private String defaultSubType = QuestionTypeNames.OPENEND;

	private List<QuestionPart> questions;

	public CommandParseCtx() {
		super();
		this.questions = new ArrayList<QuestionPart>();
	}

	public List<QuestionPart> getQuestions() {
		return questions;
	}

	public void addQuestion(QuestionPart question) {
		questions.add(question);
	}

	public void paragraphStarted() {
		Command cmd = peekCurrentControlCommand();
		if (cmd != null) {
			cmd.paragraphStarted();
		}
	}

	public void addInline(Inline inline) {
		Command cmd = peekCurrentControlCommand();
		if (cmd != null) {
			cmd.addInline(inline);
		}
	}

	public QuestionPart peekCurrentQuestion() {
		return ControlMode.Sub.equals(mode) ? current.getChild(subIndex)
				: current;
	}

	public Command peekCurrentControlCommand() {
		if (StringUtils.isEmpty(control)) {
			return null;
		}
		QuestionPart part = peekCurrentQuestion();
		return part.getCommand(control);
	}

	public QuestionPart getCurrent() {
		return current;
	}

	public void setCurrent(QuestionPart current) {
		this.current = current;
	}

	public ControlMode getMode() {
		return mode;
	}

	public void setMode(ControlMode mode) {
		this.mode = mode;
	}

	public int getSubIndex() {
		return subIndex;
	}

	public void setSubIndex(int subIndex) {
		this.subIndex = subIndex;
	}

	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public String getDefaultSubType() {
		return defaultSubType;
	}

	public void setDefaultSubType(String defaultSubType) {
		this.defaultSubType = defaultSubType;
	}

	public enum ControlMode {
		Normal, Sub;
	}
}
