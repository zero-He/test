package cn.strong.leke.question.core.material.query.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.question.core.material.query.ITeacherMaterialRecordQueryService;
import cn.strong.leke.question.dao.mybatis.material.ITeacherMaterialRecordDao;
import cn.strong.leke.question.model.material.TeacherMaterialRecord;

@Service
public class TeacherMaterialRecordQueryService implements ITeacherMaterialRecordQueryService {

	@Resource
	private ITeacherMaterialRecordDao teacherMaterialRecordDao;

	@Override
	public TeacherMaterialRecord getNewest(Long userId) {
		return teacherMaterialRecordDao.getNewest(userId);
	}

	@Override
	public TeacherMaterialRecord get(Long teacherId, Long materialFileId) {
		return teacherMaterialRecordDao.get(teacherId, materialFileId);
	}

}
