package cn.strong.leke.homework.service;

import java.util.List;
import java.util.Map;

import cn.strong.leke.homework.model.ExerciseSettingDTO;
import cn.strong.leke.model.question.QuestionTypeRule;

/**
 * 练习设置表服务接口。
 */
public interface ExerciseSettingService {
	
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
	 * @return  Map<Long, List<ExerciseSettingDTO>>
	 */
	public Map<Long, List<ExerciseSettingDTO>> findExerciseSettingList(Long schoolId, Integer exerciseType);
	
	public Boolean setExerciseSettingWithTx(String soStr, String kdStr);
	
	/**
	 * 根据条件查询练习试题数规则，如果该学校未设置则取施强网校(-1)的设置。
	 * @param exerciseType 练习类型
	 * @param schoolId 学校标识
	 * @param gradeId 年级标识
	 * @param subjectId 学科标识
	 * @return
	 */
	public List<QuestionTypeRule> findQuestionTypeRule(Long exerciseType, Long schoolId, Long gradeId, Long subjectId);
}
