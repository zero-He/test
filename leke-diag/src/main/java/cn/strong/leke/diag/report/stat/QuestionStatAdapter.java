package cn.strong.leke.diag.report.stat;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.strong.leke.diag.model.StudentAnswer;
import cn.strong.leke.diag.model.WorkStats;
import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.framework.spring.SpringContextHolder;
import cn.strong.leke.model.question.QuestionDTO;

@Component
public class QuestionStatAdapter {

	private static final Logger logger = LoggerFactory.getLogger(QuestionStatAdapter.class);

	private static final String STAT_BEAN_NAME_SUFFIX = "stat_";

	/**
	 * 试题分析处理。
	 * @param questionDTO 题目信息
	 * @param queAnswerDtoList 答题信息列表
	 * @return
	 */
	public void analyze(QuestionDTO questionDTO, List<StudentAnswer> stuAnswerList, WorkStats workStats) {
		// 根据题型模板，获取对应的分析类
		String template = questionDTO.getTemplate();
		QuestionStatAnalyzer questionStatAnalyzer = getQuestionStatAnalyzer(template);
		// 执行分析
		questionStatAnalyzer.analyze(questionDTO, stuAnswerList, workStats);
	}

	/**
	 * 根据题目类型获取分析类。
	 * @param template 题型模板
	 * @return
	 */
	private QuestionStatAnalyzer getQuestionStatAnalyzer(String template) {
		String beanName = STAT_BEAN_NAME_SUFFIX + template;
		QuestionStatAnalyzer questionStatAnalyzer = (QuestionStatAnalyzer) SpringContextHolder.getBean(beanName);
		if (questionStatAnalyzer == null) {
			logger.error("Question stat " + beanName + " is not exist.");
			throw new LekeRuntimeException("Question stat " + beanName + " is not exist.");
		}
		return questionStatAnalyzer;
	}
}
