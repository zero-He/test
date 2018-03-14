/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.que.template;

import static cn.strong.leke.question.word.ParseCsts.CommandNames.ANALYSIS;
import static cn.strong.leke.question.word.ParseCsts.CommandNames.ANSWER;
import static cn.strong.leke.question.word.ParseCsts.CommandNames.DIFFICULTY;
import static cn.strong.leke.question.word.ParseCsts.CommandNames.GRADE;
import static cn.strong.leke.question.word.ParseCsts.CommandNames.SOURCE;
import static cn.strong.leke.question.word.ParseCsts.CommandNames.STEM;
import static cn.strong.leke.question.word.ParseCsts.CommandNames.YEAR;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.model.question.Analysis;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionStem;
import cn.strong.leke.model.question.QuestionType;
import cn.strong.leke.question.word.Doc2HtmlConverter;
import cn.strong.leke.question.word.model.Command;
import cn.strong.leke.question.word.model.Doc;
import cn.strong.leke.question.word.model.JsArray;
import cn.strong.leke.question.word.model.QuestionPart;
import cn.strong.leke.question.word.model.WordQuestionInfo;
import cn.strong.leke.question.word.que.BaseQuestionPartParser;
import cn.strong.leke.question.word.que.TemplateConverter;
import cn.strong.leke.remote.model.tukor.GradeRemote;

/**
 * 基础模板转换器
 * 
 * @author liulongbiao
 * @created 2014年12月10日 上午11:22:29
 * @since v3.2
 */
public class BaseTemplateConverter implements TemplateConverter {

	public static final Pattern PATTERN_YEAR = Pattern.compile("\\d+");
	public static final Pattern PATTERN_SOURCE_YEAR = Pattern
			.compile("(\\d+)\\s*(年|学年|届)");
	public static final Map<String, Double> difficulties;

	static {
		difficulties = new HashMap<String, Double>();
		String[] levels = new String[] { "容易", "较易", "一般", "较难", "困难" };
		for (int i = 0; i < levels.length; i++) {
			difficulties.put(levels[i], 0.2 * i + 0.1);
		}
	}

	@Override
	public final QuestionDTO convert(QuestionPart part, WordQuestionInfo info,
			boolean isSub, BaseQuestionPartParser parser) {
		QuestionDTO result = new QuestionDTO();

		initInfo(result, info);

		parseType(result, part, parser);

		preProcess(result, part, parser);

		parseContent(result, part, parser);

		if (!isSub) {
			parseCommonConfigs(result, part, parser);
		}

		Boolean hasSub = result.getHasSub();
		if (hasSub != null && hasSub.equals(true)) {
			parseSubs(part, result, info, parser);
		}

		postProcess(result, part, parser);

		return result;
	}

	private void initInfo(QuestionDTO result, WordQuestionInfo info) {
		if (info != null) {
			result.setSchoolStageId(info.getSchoolStageId());
			result.setSubjectId(info.getSubjectId());
			result.setNote(info.getNote());
		}
	}

	public void parseType(QuestionDTO result, QuestionPart part,
			BaseQuestionPartParser parser) {
		Command type = part.getType();
		String typeName = type.getValue();
		QuestionType qt = parser.getQuestionType(typeName);
		if (qt != null) {
			result.setQuestionTypeId(qt.getQuestionTypeId());
			result.setHasSub(qt.getHasSub());
			result.setSubjective(qt.getSubjective());
			result.setTemplate(qt.getTemplate());
			result.setHandwritten(qt.isHandwritten());
		}
	}

	protected void preProcess(QuestionDTO result, QuestionPart part,
			BaseQuestionPartParser parser) {
		// to be override
	}

	protected void parseContent(QuestionDTO result, QuestionPart part,
			BaseQuestionPartParser parser) {
		parseStem(part.getCommand(STEM), result, part);
		parseAnswer(part.getCommand(ANSWER), result, part);
		parseAnalysis(part.getCommand(ANALYSIS), result, part);
	}

	protected void parseStem(Command cmd, QuestionDTO result, QuestionPart part) {
		if (cmd == null) {
			return;
		}
		Doc content = cmd.getContent();
		if (content != null && !content.isEmpty()) {
			QuestionStem stem = new QuestionStem();
			String stemContent = Doc2HtmlConverter.convert(content);
			stem.setStemContent(stemContent);
			result.setStem(stem);
		}
	}

	protected void parseAnswer(Command cmd, QuestionDTO result,
			QuestionPart part) {
		// to be override
	}

	protected void parseAnalysis(Command cmd, QuestionDTO result,
			QuestionPart part) {
		if (cmd == null) {
			return;
		}
		Doc content = cmd.getContent();
		if (content != null && !content.isEmpty()) {
			Analysis analysis = new Analysis();
			String analysisContent = Doc2HtmlConverter.convert(content);
			analysis.setAnalysisContent(analysisContent);
			result.setAnalysis(analysis);
		}
	}

	private void parseCommonConfigs(QuestionDTO result, QuestionPart part,
			BaseQuestionPartParser parser) {
		parseDifficulty(part, result);
		parseSource(part, result);
		parseYear(part, result);
		parseGrade(part, result, parser);
	}

	private void parseGrade(QuestionPart part, QuestionDTO result,
			BaseQuestionPartParser parser) {
		GradeRemote grade = null;
		Command cmd = part.getCommand(GRADE);
		if (cmd != null) {
			grade = parser.extractGrade(cmd.getValue());
		}
		if (grade == null) {
			grade = parser.extractGrade(result.getSource());
		}
		if (grade == null) {
			grade = parser.extractGrade(result.getNote());
		}
		if (grade != null) {
			result.setGradeId(grade.getGradeId());
			result.setSchoolStageId(grade.getSchoolStageId());
		}
	}

	private void parseYear(QuestionPart part, QuestionDTO result) {
		Integer year = readYear(part);
		if (year == null) {
			year = readYearFromSource(result.getSource());
		}
		result.setYear(year);
	}

	private Integer readYearFromSource(String source) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		Integer year = null;
		Matcher mat = PATTERN_SOURCE_YEAR.matcher(source);
		if (mat.find()) {
			year = Integer.parseInt(mat.group(1));
		}
		return year;
	}

	private Integer readYear(QuestionPart part) {
		Command cmd = part.getCommand(YEAR);
		if (cmd == null) {
			return null;
		}
		String str = cmd.getValue();
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		Integer year = null;
		Matcher mat = PATTERN_YEAR.matcher(str);
		if (mat.find()) {
			year = Integer.parseInt(mat.group());
		}
		return year;
	}

	private void parseSource(QuestionPart part, QuestionDTO result) {
		Command cmd = part.getCommand(SOURCE);
		if (cmd != null) {
			result.setSource(cmd.getValue());
		}
	}

	private void parseDifficulty(QuestionPart part, QuestionDTO result) {
		Double difficulty = null;
		Command diff = part.getCommand(DIFFICULTY);
		if (diff != null) {
			String diffLevel = diff.getValue();
			difficulty = difficulties.get(diffLevel);
		}
		if (difficulty == null) {
			difficulty = 0.5;
		}
		result.setDifficulty(difficulty);
	}

	private void parseSubs(QuestionPart part, QuestionDTO result,
			WordQuestionInfo info, BaseQuestionPartParser parser) {
		JsArray<QuestionPart> children = part.getChildren();
		if (children.isEmpty()) {
			throw new LekeRuntimeException("que.word.part.subs.missing");
		}
		int i = 0;
		for (QuestionPart child : children) {
			if (child == null) {
				continue;
			}

			i++;
			try {
				QuestionDTO sub = parser.parse(child, info, true);
				if (sub != null) {
					result.addSub(sub);
				}
			} catch (Exception e) {
				System.out.println(i);
				throw new LekeRuntimeException("que.word.part.sub.error", e, i,
						e.getMessage());
			}
		}
	}

	protected void postProcess(QuestionDTO result, QuestionPart part,
			BaseQuestionPartParser parser) {
		// to be override
	}

	/**
	 * 确保读取到命令的内容
	 * 
	 * @author liulongbiao
	 * @created 2014年12月11日 下午4:31:51
	 * @since v3.2
	 * @param cmd
	 * @param missingMsg
	 *            缺失时的提示信息
	 * @return
	 */
	public Doc ensureReadContent(Command cmd, String missingMsg) {
		if (cmd == null) {
			throw new LekeRuntimeException(missingMsg);
		}
		Doc content = cmd.getContent();
		if (content.isEmpty()) {
			throw new LekeRuntimeException(missingMsg);
		}
		return content;
	}

}
