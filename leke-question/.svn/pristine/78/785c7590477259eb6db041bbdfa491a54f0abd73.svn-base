package cn.strong.leke.question.dao.mybatis.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.model.question.MaterialNodeJoin;

public interface IMaterialNodeJoinDao {

	int add(MaterialNodeJoin assoc);

	int del(@Param("materialNodeJoinId") Long materialNodeJoinId, @Param("userId") Long userId);

	int isExist(@Param("origMaterialNodeId") Long origMaterialNodeId,
			@Param("destMaterialNodeId") Long destMaterialNodeId);

	/**
	 *
	 * 描述: 查询改章节ID所有关联的章节ID
	 *
	 * @author raolei
	 * @created 2017年2月8日 下午4:47:55
	 * @since v1.0.0
	 * @param origMaterialNodeId
	 * @return
	 * @return List<MaterialNodeJoin>
	 */
	List<MaterialNodeJoin> findMaterialNodeJoins(@Param("origMaterialNodeId") Long origMaterialNodeId);
}
