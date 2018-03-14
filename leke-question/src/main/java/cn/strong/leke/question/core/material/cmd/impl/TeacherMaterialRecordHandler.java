package cn.strong.leke.question.core.material.cmd.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.question.core.material.cmd.ITeacherMaterialRecordHandler;
import cn.strong.leke.question.dao.mybatis.material.ITeacherMaterialRecordDao;
import cn.strong.leke.question.model.material.TeacherMaterialRecord;

@Service
public class TeacherMaterialRecordHandler implements ITeacherMaterialRecordHandler {
	@Resource
	private ITeacherMaterialRecordDao teacherMaterialRecordDao;

	@Override
	public void updateCurPage(TeacherMaterialRecord assoc) {
		if (assoc == null || assoc.getMaterialFileId() == null || assoc.getTeacherId() == null) {
			throw new ValidateException("浏览电子教材记录信息错误！");
		}
		if (assoc.getCurPage() == null) {
			assoc.setCurPage(0L);
		}
		Long materialFileId = assoc.getMaterialFileId();
		Long teacherId = assoc.getTeacherId();
		assoc.setCreatedBy(teacherId);
		assoc.setModifiedBy(teacherId);
		TeacherMaterialRecord old = teacherMaterialRecordDao.get(assoc.getTeacherId(), materialFileId);
		if (old == null) {
			teacherMaterialRecordDao.add(assoc);
		} else {
			teacherMaterialRecordDao.updateCurPage(assoc);
		}
	}

	@Override
	public void save(TeacherMaterialRecord assoc) {
		if (assoc == null || assoc.getMaterialFileId() == null || assoc.getTeacherId() == null) {
			throw new ValidateException("浏览电子教材记录信息错误！");
		}
		if (assoc.getCurPage() == null) {
			assoc.setCurPage(0L);
		}
		Long materialFileId = assoc.getMaterialFileId();
		Long teacherId = assoc.getTeacherId();
		assoc.setCreatedBy(teacherId);
		assoc.setModifiedBy(teacherId);
		TeacherMaterialRecord old = teacherMaterialRecordDao.get(assoc.getTeacherId(), materialFileId);
		if (old == null) {
			teacherMaterialRecordDao.add(assoc);
		} else {
			teacherMaterialRecordDao.update(assoc);
		}
	}

}
