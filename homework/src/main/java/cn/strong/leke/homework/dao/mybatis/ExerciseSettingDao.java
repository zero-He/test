package cn.strong.leke.homework.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.homework.model.ExerciseSetting;
import cn.strong.leke.homework.model.ExerciseSettingDTO;
import cn.strong.leke.model.question.QuestionTypeRule;

public interface ExerciseSettingDao {

	/**
	 *	
	 * 描述:根据学校id和练习类型查询练习设置列表（包含设置详情）
	 *
	 * @author  C.C
	 * @created 2014-6-10 下午9:28:00
	 * @since   v1.0.0 
	 * @param schoolId
	 * @param exerciseType
	 * @return
	 * @return  List<ExerciseSettingDTO>
	 */
	public List<ExerciseSettingDTO> queryExerciseSettingList(@Param("schoolId") Long schoolId,
			@Param("exerciseType") Integer exerciseType);

	public Integer saveExerciseSetting(ExerciseSetting exerciseSetting);

	public Integer updateExerciseSetting(ExerciseSetting exerciseSetting);

	/**
	 * 根据条件查询练习试题数规则。
	 * @param exerciseType 练习类型
	 * @param schoolId 学校标识
	 * @param schoolStageId 学段标识
	 * @param subjectId 学科标识
	 * @return
	 */
	public List<QuestionTypeRule> findQuestionTypeRule(@Param("exerciseType") Long exerciseType,
			@Param("schoolId") Long schoolId, @Param("schoolStageId") Long schoolStageId, @Param("subjectId") Long subjectId);
}
