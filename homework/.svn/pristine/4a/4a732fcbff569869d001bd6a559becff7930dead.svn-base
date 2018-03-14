package cn.strong.leke.homework.util;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.homework.model.ExerciseType;

public class ExerciseUtils {

	/**
	 * 格式化显示练习标题
	 * eg: ***等5个章节，或***等5个知识点
	 * @param exerciseName
	 * @param exerciseType
	 * @return
	 */
	public static String getExerciseTitle(String exerciseName,Long exerciseType ) {
		if (exerciseName.indexOf(',') > -1) {
			String[] ary = StringUtils.split(exerciseName, ',');
			exerciseName = ary[0] + "等" + (ary.length)
					+ (exerciseType == ExerciseType.KNOWLEDGE.value ? "个知识点" : "个章节");
		}
		return exerciseName;
	}
}
