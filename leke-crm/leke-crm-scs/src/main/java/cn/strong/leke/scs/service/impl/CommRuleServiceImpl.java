package cn.strong.leke.scs.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.scs.dao.master.CommRuleMasterDao;
import cn.strong.leke.scs.model.CommRule;
import cn.strong.leke.scs.service.CommRuleService;

@Service
public class CommRuleServiceImpl implements CommRuleService {

	@Resource
	private CommRuleMasterDao commRuleMasterDao;

	@Override
	public List<CommRule> findNearestTwoCommRules(Integer commType) {
		return this.commRuleMasterDao.findNearestTwoCommRules(commType);
	}

	@Override
	public void saveChangeCommRule(CommRule commRule) {
		List<CommRule> commRuleList = this.commRuleMasterDao.findNearestTwoCommRules(commRule.getCommType());

		Long currRuleId;
		if (commRuleList.get(0).getValidTime().before(new Date())) {
			// 规则都已生效，增加新规则
			currRuleId = commRuleList.get(0).getRuleId();
			this.commRuleMasterDao.insertCommRule(commRule);
		} else {
			// 有新规则未生效，变更新规则
			currRuleId = commRuleList.get(1).getRuleId();
			commRule.setRuleId(commRuleList.get(0).getRuleId());
			this.commRuleMasterDao.updateCommRule(commRule);
		}
		// 修改当前规则的结束时间
		this.commRuleMasterDao.updateExpireTimeByRuleId(currRuleId, commRule.getValidTime(), commRule.getCreatedBy());
	}

}
