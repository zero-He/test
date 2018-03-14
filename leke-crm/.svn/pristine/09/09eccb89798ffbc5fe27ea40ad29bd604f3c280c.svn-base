package cn.strong.leke.scs.dao.master;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.scs.model.CommRule;

public interface CommRuleMasterDao {

	/**
	 * 保存佣金规则。
	 * @param commRule 规则信息
	 * @return
	 */
	public int insertCommRule(CommRule commRule);

	/**
	 * 更新佣金规则。
	 * @param commRule 规则信息
	 * @return
	 */
	public int updateCommRule(CommRule commRule);

	/**
	 * 更新规则的生效时间
	 * @param ruleId 规则ID
	 * @param expireTime 失效时间
	 * @param modifiedBy 变更人
	 * @return
	 */
	public int updateExpireTimeByRuleId(@Param("ruleId") Long ruleId, @Param("expireTime") Date expireTime,
			@Param("modifiedBy") Long modifiedBy);

	/**
	 * 根据佣金类型获取该类型当前生效的规则。
	 * @return
	 */
	public CommRule getValidCommRuleByCommType(Integer commType);

	/**
	 * 获取系统最近的两个规则
	 * @return
	 */
	public List<CommRule> findNearestTwoCommRules(Integer commType);

}
