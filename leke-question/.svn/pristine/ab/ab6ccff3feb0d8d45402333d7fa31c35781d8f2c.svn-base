package cn.strong.leke.question.dao.mybatis.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.model.question.Press;
import cn.strong.leke.question.model.base.StageSubjectPress;

/**
 *
 * 描述: 学段学科和出版社关联表
 *
 * @author raolei
 * @created 2016年11月9日 下午4:45:25
 * @since v1.0.0
 */
public interface IStageSubjectPressDao {

	/**
	 *
	 * 描述: 增加学段学科和出版社关联
	 *
	 * @author raolei
	 * @created 2016年11月9日 下午4:58:08
	 * @since v1.0.0
	 * @param assoc
	 * @return void
	 */
	void insert(StageSubjectPress assoc);

	/**
	 *
	 * 描述: 批量删除
	 *
	 * @author raolei
	 * @created 2016年11月10日 上午11:09:08
	 * @since v1.0.0
	 * @param assocs
	 * @return void
	 */
	void insertList(@Param("assocs") List<StageSubjectPress> assocs);
	
	/**
	 *
	 * 描述: 真删除表数据
	 *
	 * @author raolei
	 * @created 2016年11月9日 下午6:38:20
	 * @since v1.0.0
	 * @param stageSubjectPressId
	 * @return
	 * @return int
	 */
	int delete(@Param("stageSubjectPressId") Long stageSubjectPressId);

	/**
	 *
	 * 描述: 根据学段学科删除
	 *
	 * @author raolei
	 * @created 2016年11月10日 上午11:03:55
	 * @since v1.0.0
	 * @param schoolStageId
	 * @param subjectId
	 * @return
	 * @return int
	 */
	int deleteBySchoolStageIdSubjectId(@Param("schoolStageId") Long schoolStageId, @Param("subjectId") Long subjectId);

	/**
	 *
	 * 描述: 查询学段学科教材版本所有关联关系
	 *
	 * @author raolei
	 * @created 2016年11月9日 下午8:34:45
	 * @since v1.0.0
	 * @return
	 * @return List<StageSubjectPress>
	 */
	List<StageSubjectPress> findAll();

	/**
	 * 根据学段学科查询教材版本
	 * 
	 * @param schoolStageId
	 * @param subjectId
	 * @return
	 */
	List<Press> findPresses(@Param("schoolStageId") Long schoolStageId, @Param("subjectId") Long subjectId);

	/**
	 *
	 * 描述: 根据学段学科出版社获取信息
	 *
	 * @author raolei
	 * @param schoolStageId
	 * @param subjectId
	 * @param pressId
	 * @return
	 * @return StageSubjectPress
	 */
	StageSubjectPress getStageSubjectPress(@Param("schoolStageId") Long schoolStageId,
			@Param("subjectId") Long subjectId, @Param("pressId") Long pressId);

}
