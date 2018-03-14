package cn.strong.leke.scs.service;

import java.util.List;

import cn.strong.leke.scs.model.CommRule;

/**
 * 佣金规则服务。
 * @author  andy
 * @created 2015年6月24日 下午4:05:02
 * @since   v1.0.0
 */
public interface CommRuleService {

	/**
	 * 获取系统最近的两个规则
	 * @return
	 */
	public List<CommRule> findNearestTwoCommRules(Integer commType);

	/**
	 * 更新佣金规则。
	 * @param commRule
	 */
	public void saveChangeCommRule(CommRule commRule);

}
