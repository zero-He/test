/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.monitor.mongo.model;

import cn.strong.leke.common.encrypt.EncryptUtils;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * SQL hash 记录
 * 
 * @author liulongbiao
 */
public class SqlHash {

	/**
	 * 创建 SqlHash 实例
	 * 
	 * @author liulongbiao
	 * @param sqlId
	 * @param className
	 * @param sql
	 * @return
	 */
	public static SqlHash of(String sqlId, String className, String sql) {
		String hash = hashOf(sqlId, className, sql);
		SqlHash result = new SqlHash();
		result.setHash(hash);
		result.setSql(sql);
		result.setClassName(className);
		result.setSqlId(sqlId);
		return result;
	}

	/**
	 * 获取 SQL hash 值
	 * 
	 * @author liulongbiao
	 * @param sqlId
	 * @param className
	 * @param sql
	 * @return
	 */
	public static String hashOf(String sqlId, String className, String sql) {
		StringBuilder sb = new StringBuilder(sqlId);
		sb.append('@');
		sb.append(className);
		sb.append('@');
		sb.append(sql);
		return EncryptUtils.md5(sb.toString());
	}

	@_id
	private String hash;

	private String sqlId;
	private String className;
	private String sql;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

}
