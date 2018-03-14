package cn.strong.leke.question.dao.mybatis.material;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.question.model.material.TeacherMaterialRecord;

public interface ITeacherMaterialRecordDao {

	int add(TeacherMaterialRecord assoc);

	int update(TeacherMaterialRecord assoc);

	int updateCurPage(TeacherMaterialRecord assoc);

	TeacherMaterialRecord get(@Param("teacherId") Long teacherId, @Param("materialFileId") Long materialFileId);

	TeacherMaterialRecord getNewest(@Param("teacherId") Long teacherId);
}
