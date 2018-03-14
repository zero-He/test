/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.que;

import java.util.HashMap;
import java.util.Map;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionType;
import cn.strong.leke.question.word.QuestionPartParser;
import cn.strong.leke.question.word.model.Command;
import cn.strong.leke.question.word.model.QuestionPart;
import cn.strong.leke.question.word.model.WordQuestionInfo;
import cn.strong.leke.remote.model.tukor.GradeRemote;

/**
 * 习题解析
 * 
 * @author liulongbiao
 * @created 2014年12月10日 上午8:35:27
 * @since v3.2
 */
public class BaseQuestionPartParser implements QuestionPartParser {

	private Map<String, TemplateConverter> registry;
	private Map<String, QuestionType> types;
	private Map<String, GradeRemote> grades;
	private Map<String, GradeRemote> gradeKeywords;

	public BaseQuestionPartParser() {
		super();
		registry = new HashMap<String, TemplateConverter>();
		types = new HashMap<String, QuestionType>();
		grades = new HashMap<String, GradeRemote>();
		gradeKeywords = new HashMap<String, GradeRemote>();
	}

	/**
	 * 将 QuestionPart 解析为 QuestionDTO
	 * 
	 * @author liulongbiao
	 * @created 2014年12月10日 上午10:56:23
	 * @since v3.2
	 * @param part
	 * @return
	 */
	public QuestionDTO parse(QuestionPart part, WordQuestionInfo info) {
		return parse(part, info, false);
	}

	public QuestionDTO parse(QuestionPart part, WordQuestionInfo info,
			boolean isSub) {
		if (part == null) {
			return null;
		}
		Command type = part.getType();
		String qtype = type.getValue();
		TemplateConverter converter = registry.get(qtype);
		if (converter == null) {
			throw new LekeRuntimeException("que.word.part.converter.not.found",
					qtype);
		}
		return converter.convert(part, info, isSub, this);
	}

	public void regist(String name, TemplateConverter converter) {
		if (StringUtils.isNotEmpty(name) && converter != null) {
			registry.put(name, converter);
		}
	}

	public void registGrade(String gradeName, GradeRemote grade) {
		if (StringUtils.isNotEmpty(gradeName) && grade != null) {
			grades.put(gradeName, grade);
		}
	}

	public void registGradeKw(String kw, GradeRemote grade) {
		if (StringUtils.isNotEmpty(kw) && grade != null) {
			gradeKeywords.put(kw, grade);
		}
	}

	public void registType(String typeName, QuestionType type) {
		if (StringUtils.isNotEmpty(typeName) && type != null) {
			types.put(typeName, type);
		}
	}

	public QuestionType getQuestionType(String typeName) {
		return types.get(typeName);
	}

	public Map<String, GradeRemote> getGrades() {
		return grades;
	}

	public Map<String, GradeRemote> getGradeKeywords() {
		return gradeKeywords;
	}

	public GradeRemote extractGrade(String source) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		GradeRemote grade = extractMatchedGrade(source, grades);
		if (grade == null) {
			grade = extractMatchedGrade(source, gradeKeywords);
		}
		return grade;
	}

	private GradeRemote extractMatchedGrade(String source,
			Map<String, GradeRemote> map) {
		GradeRemote grade = null;
		for (Map.Entry<String, GradeRemote> pair : map.entrySet()) {
			String name = pair.getKey();
			if (source.indexOf(name) != -1) {
				grade = pair.getValue();
				break;
			}
		}
		return grade;
	}
}
