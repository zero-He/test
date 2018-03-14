/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.model;

import cn.strong.leke.question.word.ParseCsts.CommandNames;


/**
 * 命令
 * 
 * @author liulongbiao
 * @created 2014年12月9日 上午8:58:20
 * @since v3.2
 */
public class Command {

	public static Command newTypeCommand(String type) {
		return new Command(CommandNames.TYPE, type);
	}

	private String name;
	private String value;
	private DocCtx ctx;

	public Command(String name) {
		super();
		this.name = name;
		this.ctx = new DocCtx();
	}

	public Command(String name, String value) {
		this(name);
		this.value = value;
	}

	/**
	 * 是否是题型命令
	 * 
	 * @author liulongbiao
	 * @created 2014年12月9日 下午2:51:18
	 * @since v3.2
	 * @return
	 */
	public boolean isTypeCommand() {
		return CommandNames.TYPE.equals(name);
	}

	/**
	 * 获取内容
	 * 
	 * @author liulongbiao
	 * @created 2014年12月9日 下午2:57:03
	 * @since v3.2
	 * @return
	 */
	public Doc getContent() {
		return ctx.getDoc();
	}

	public void paragraphStarted() {
		ctx.paragraphStarted();
	}

	public void addInline(Inline inline) {
		ctx.addInline(inline);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public DocCtx getCtx() {
		return ctx;
	}

	public void setCtx(DocCtx ctx) {
		this.ctx = ctx;
	}

}
