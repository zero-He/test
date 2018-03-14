package cn.strong.leke.homework.remote;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.homework.service.ExerciseSettingService;
import cn.strong.leke.model.question.QuestionTypeRule;
import cn.strong.leke.remote.service.homework.IExerciseSettingRemoteService;

@Service
public class ExerciseSettingRemoteService implements IExerciseSettingRemoteService {
	@Resource
	private ExerciseSettingService exerciseSettingService;

	@Override
	public List<QuestionTypeRule> findQuestionTypeRule(Long exerciseType, Long schoolId, Long gradeId, Long subjectId) {
		return exerciseSettingService.findQuestionTypeRule(exerciseType, schoolId, gradeId, subjectId);
	}

}
