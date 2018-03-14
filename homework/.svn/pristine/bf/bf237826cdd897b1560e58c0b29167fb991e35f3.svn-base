package cn.strong.leke.homework.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.homework.dao.mybatis.ExerciseSettingDao;
import cn.strong.leke.homework.dao.mybatis.ExerciseSettingDtlDao;
import cn.strong.leke.homework.model.ExerciseSetting;
import cn.strong.leke.homework.model.ExerciseSettingDTO;
import cn.strong.leke.homework.model.ExerciseSettingDtl;
import cn.strong.leke.homework.service.ExerciseSettingService;
import cn.strong.leke.model.question.QuestionTypeRule;

@Service("exerciseSettingService")
public class ExerciseSettingServiceImpl implements ExerciseSettingService {

	private static final Logger logger = LoggerFactory.getLogger(ExerciseSettingServiceImpl.class);

	@Resource
	private ExerciseSettingDao exerciseSettingDao;

	@Resource
	private ExerciseSettingDtlDao exerciseSettingDtlDao;

	@Override
	public Map<Long, List<ExerciseSettingDTO>> findExerciseSettingList(Long schoolId, Integer exerciseType) {
		Map<Long, List<ExerciseSettingDTO>> map = new HashMap<Long, List<ExerciseSettingDTO>>();
		List<ExerciseSettingDTO> ExerciseSettingList = exerciseSettingDao.queryExerciseSettingList(schoolId,
				exerciseType);
		List<ExerciseSettingDTO> list = new ArrayList<ExerciseSettingDTO>();
		Long key = null;
		//以年级分组，以年级id为key，其对应题型设置list为value放入map
		for (ExerciseSettingDTO es : ExerciseSettingList) {
			if (key == null || key.equals(es.getGradeId())) {
				list.add(es);
				key = es.getGradeId();
			} else {
				map.put(key, list);
				list = new ArrayList<ExerciseSettingDTO>();
				list.add(es);
				key = es.getGradeId();
			}
		}
		if (!CollectionUtils.isEmpty(ExerciseSettingList)) {
			map.put(key, list);//把最后一组list放入map
		}
		return map;
	}

	@Override
	public Boolean setExerciseSettingWithTx(String soStr, String kdStr) {
		try {
			handleExerciseSetting(soStr, 1);
			handleExerciseSetting(kdStr, 2);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}

	}

	public void handleExerciseSetting(String gradesString, Integer exerciseType) {
		Long userId = UserContext.user.getUserId();
		Date now = new Date();
		List<ExerciseSettingDtl> esdAddList = new ArrayList<ExerciseSettingDtl>();

		String[] gradeArr = gradesString.split(";");//以年级分组
		for (String gradeString : gradeArr) {//遍历年级
			String[] arr = gradeString.split(":");
			Long gradeId = Long.parseLong(arr[0]);
			String[] dataArr = arr[1].split(",");

			Long settingId = (long) 0;
			for (String data : dataArr) {//遍历同一年级的所有数据
				//data:questionType-subjectId-id-num
				String[] idArr = data.split("-");
				Long subjectId = Long.parseLong(idArr[1]);
				Long id = Long.parseLong(idArr[2]);
				Integer num = Integer.parseInt(idArr[3]);

				if (subjectId.equals((long) 0)) {//操作ExerciseSetting
					ExerciseSetting es = new ExerciseSetting();
					es.setDefaultNum(num);

					if (id.equals((long) 0)) {//新增
						es.setExerciseType(exerciseType);
						es.setSchoolId(UserContext.user.getCurrentSchoolId());
						es.setGradeId(gradeId);
						es.setQuestionTypeId(Long.parseLong(idArr[0]));
						es.setCreatedBy(userId);
						es.setCreatedOn(now);
						exerciseSettingDao.saveExerciseSetting(es);
						settingId = es.getSettingId();
					} else {//更新
						es.setSettingId(id);
						es.setModifiedBy(userId);
						es.setModifiedOn(now);
						exerciseSettingDao.updateExerciseSetting(es);
					}

				} else {//操作ExerciseSettingDtl
					ExerciseSettingDtl esd = new ExerciseSettingDtl();
					esd.setQuestionNum(num);

					if (id.equals((long) 0)) {//新增
						esd.setSettingId(settingId);
						esd.setSubjectId(subjectId);
						esd.setCreatedBy(userId);
						esd.setCreatedOn(now);
						esdAddList.add(esd);
					} else {//更新
						esd.setSettingDtlId(id);
						esd.setModifiedBy(userId);
						esd.setModifiedOn(now);
						exerciseSettingDtlDao.updateExerciseSettingDtl(esd);
					}
				}
			}
		}
		if (!CollectionUtils.isEmpty(esdAddList)) {
			exerciseSettingDtlDao.saveExerciseSettingDtl(esdAddList);
		}
	}

	public List<QuestionTypeRule> findQuestionTypeRule(Long exerciseType, Long schoolId, Long schoolStageId, Long subjectId) {
		return this.exerciseSettingDao.findQuestionTypeRule(exerciseType, -1L, schoolStageId, subjectId);
		/*
		 * 直接查询默认学校配置。
		List<QuestionTypeRule> list = this.exerciseSettingDao.findQuestionTypeRule(exerciseType, schoolId, gradeId,
				subjectId);
		if (list.isEmpty()) {
			if (logger.isDebugEnabled()) {
				logger.debug("school({}) exercise rule not setting.", schoolId);
			}
			list = this.exerciseSettingDao.findQuestionTypeRule(exerciseType, -1L, gradeId, subjectId);
		}
		return list;
		*/
	}
}
