package cn.strong.leke.monitor.core.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.model.user.User;
import cn.strong.leke.model.user.area.Area;
import cn.strong.leke.model.user.area.Market;

/**
 * @ClassName: IClassLeaderService
 * @Description: CRM1.1教学监控
 * @author huangkai
 * @date 2017年3月21日 下午1:55:47
 * @version V1.0
 */
public interface IClassLeaderService
{
	/**
	 * @Description: 获取用户的管辖范围
	 * @param userId
	 * @param roleId
	 * @return    参数
	 * @throws
	 */
	List<Long> getMarket(Long userId, Long roleId);

	/**
	 * @Description: 获取用户管辖市场
	 * @param user（当前用户）
	 * @param IsAll（是否找出所有市场）
	 * @return Map<String, List<Market>>
	 * @throws
	 */
	Map<String, List<Area>> getMarketMap(User user, Boolean IsAll);

	/**
	 * @Description: crm1.1 获取上级管辖的下级人员
	 * @param secondDptIds（市场）
	 * @return List<Long>
	 * @throws
	 */
	List<Long> getSeller(@Param("secondDptIds") List<Long> secondDptIds);
	
	/**
	 * @Description: 营销部下所有营销处
	 * @param market
	 * @return    参数
	 * @throws
	 */
	List<Long> getMarketChildren(Long market);
}
