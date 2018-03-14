/**
 * 
 */
package cn.strong.leke.question.dao.mybatis;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.Material;

/**
 * 教材DAO
 * 
 * @author liulb
 * 
 */
public interface MaterialDao {

	/**
	 * 新增教材
	 * 
	 * @param material
	 * @return
	 */
	int insertMaterial(Material material);

	/**
	 * 修改教材
	 * 
	 * @param material
	 * @return
	 */
	int updateMaterial(Material material);

	/**
	 * 删除教材
	 * 
	 * @param material
	 * @return
	 */
	int deleteMaterial(Material material);

	int isExist(Material material);

	/**
	 * 
	 * 
	 * @param materialName
	 * @return
	 */
	int countByMaterialName(String materialName);

	/**
	 * 查询教材
	 * 
	 * @param material
	 * @return
	 */
	List<Material> queryMaterials(Material material, Page page);

	/**
	 * 根据ID查询教材
	 * 
	 * @param materialId
	 * @return
	 */
	Material getMaterialById(Long materialId);

	/**
	 * 仅更新教材名称
	 * 
	 * @author liulongbiao
	 * @created 2015年1月10日 下午5:12:13
	 * @since v3.2.2
	 * @param updates
	 */
	void updateMaterialName(Material updates);
}
