/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.que;

import java.util.List;
import java.util.Map;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.context.question.QuestionTypeContext;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionType;
import cn.strong.leke.question.word.ParseCsts.QuestionTypeNames;
import cn.strong.leke.question.word.model.QuestionPart;
import cn.strong.leke.question.word.model.WordQuestionInfo;
import cn.strong.leke.question.word.que.template.DelayedChoiceConverter;
import cn.strong.leke.question.word.que.template.TemplateConverterRegistry;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;

/**
 * LEKE 习题解析器实现
 * 
 * @author liulongbiao
 * @created 2014年12月11日 下午7:39:25
 * @since v3.2
 */
public class LekeQuestionPartParser extends BaseQuestionPartParser {

	private boolean inited = false;

	@Override
	public QuestionDTO parse(QuestionPart part, WordQuestionInfo info) {
		if (!inited) {
			init();
		}
		return super.parse(part, info);
	}

	private void init() {
		registQuestionTypesFromDB();
		regist(QuestionTypeNames.CHOICE, new DelayedChoiceConverter());
		registGradesFromDB();
		registGradeKeywords();
		inited = true;
	}

	private void registQuestionTypesFromDB() {
		List<QuestionType> questionTypes = QuestionTypeContext
				.findAllQuestionTypes();
		if (CollectionUtils.isNotEmpty(questionTypes)) {
			for (QuestionType qt : questionTypes) {
				String typeName = qt.getQuestionTypeName();
				registType(typeName, qt);
				String template = qt.getTemplate();
				TemplateConverter cvt = TemplateConverterRegistry.get(template);
				if (cvt != null) {
					regist(typeName, cvt);
				}
			}
		}
	}

	private void registGradesFromDB() {
		List<SchoolStageRemote> stages = SchoolStageContext.findSchoolStages();
		if (CollectionUtils.isEmpty(stages)) {
			return;
		}
		for (SchoolStageRemote stage : stages) {
			List<GradeRemote> grades = stage.getGrades();
			if (CollectionUtils.isNotEmpty(grades)) {
				for (GradeRemote grade : grades) {
					// fix schoolStage
					grade.setSchoolStageId(stage.getSchoolStageId());
					grade.setSchoolStageName(stage.getSchoolStageName());

					registGrade(grade.getGradeName(), grade);
				}
			}
		}
	}

	private void registGradeKeywords() {
		Map<String, GradeRemote> grades = getGrades();
		GradeRemote g9 = grades.get("九年级");
		GradeRemote gh3 = grades.get("高三");

		registGradeKw("初一", grades.get("七年级"));
		registGradeKw("初二", grades.get("八年级"));
		registGradeKw("初三", g9);

		registGradeKw("中考", g9);
		registGradeKw("初中", g9);

		registGradeKw("高考", gh3);
		registGradeKw("高中", gh3);
		registGradeKw("高等学校", gh3);
	}
}
