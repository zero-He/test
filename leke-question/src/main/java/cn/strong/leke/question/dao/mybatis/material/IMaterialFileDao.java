package cn.strong.leke.question.dao.mybatis.material;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.question.model.material.MaterialFile;

public interface IMaterialFileDao {

	int add(MaterialFile assoc);

	int del(@Param("materialId") Long materialId, @Param("userId") Long userId);

	MaterialFile get(@Param("materialFileId") Long materialFileId);

	MaterialFile getByMaterialId(@Param("materialId") Long materialId);
}
