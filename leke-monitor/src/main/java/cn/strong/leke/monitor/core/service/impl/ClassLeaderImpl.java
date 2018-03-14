package cn.strong.leke.monitor.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import cn.strong.leke.context.base.MarketContext;
import cn.strong.leke.model.user.User;
import cn.strong.leke.model.user.area.Area;
import cn.strong.leke.monitor.core.dao.mybatis.IClassStatisticsDao;
import cn.strong.leke.monitor.core.service.IClassLeaderService;
import cn.strong.leke.remote.model.user.MarketManagerRemote;
import cn.strong.leke.remote.service.user.IMarketRemoteService;

/**
 * @ClassName: ClassLeaderImpl
 * @Description: CRM1.1教学监控
 * @author huangkai
 * @date 2017年3月21日 下午1:58:09
 * @version V1.0
 */
@Service
public class ClassLeaderImpl implements IClassLeaderService
{
	@Resource
	private IMarketRemoteService marketRemoteService;

	@Resource
	private IClassStatisticsDao classStatisticsDao;

	/**
	 * @Description: 获取用户的管辖范围
	 * @param userId
	 * @param roleId
	 * @return    参数
	 * @throws
	 */
	public List<Long> getMarket(Long userId, Long roleId)
	{
		List<Long> result = new ArrayList<Long>();

		List<MarketManagerRemote> listMarketManager = marketRemoteService.findMarketManagerByUserRoleId(userId, roleId);

		// 所有市场
		List<Area> listArea = MarketContext.findAll();
		
		if (listMarketManager.size() > 0)
		{
			for (MarketManagerRemote marketManagerRemote : listMarketManager)
			{
				// 一级营销部集合
				String[] firstDptIds = marketManagerRemote.getFirstDptIds().split(",");

				for (String string : firstDptIds)
				{
					result.add(Long.valueOf(string));
				}

				// 助理总经理，营销中心总经理获取所有的营销处
				if (roleId.equals(600L) || roleId.equals(604L))
				{
					// 当前用户管辖的营销处ID集合
					String strFirstDptIds = "," + marketManagerRemote.getFirstDptIds() + ",";

					for (Area area : listArea)
					{
						if (strFirstDptIds.contains("," + area.getpId() + ","))
						{
							result.add(Long.valueOf(area.getId()));
						}
					}
				} else
				{
					// 二级营销处集合
					String[] secondDptIds = marketManagerRemote.getSecondDptIds().split(",");

					for (String string : secondDptIds)
					{
						result.add(Long.valueOf(string));
					}
				}
			}
		} else
		{
			result.add(Long.valueOf(0));
		}

		return result;
	}

	/**
	 * @Description: 获取用户管辖市场
	 * @param user（当前用户）
	 * @param IsAll（是否找出所有市场）
	 * @return Map<String, List<Market>>
	 * @throws
	 */
	public Map<String, List<Area>> getMarketMap(User user, Boolean IsAll)
	{
		Map<String, List<Area>> result = new HashMap<String, List<Area>>();

		Long roleId = user.getCurrentRole().getId();

		// 用户管辖的市场
		List<MarketManagerRemote> listMarketManager = marketRemoteService.findMarketManagerByUserRoleId(user.getId(),
				roleId);

		// 所有市场
		List<Area> listArea = MarketContext.findAll();

		List<Area> secondDpt = new ArrayList<Area>();

		if (IsAll)
		{
			List<Area> firstDpt = new ArrayList<Area>();

			for (Area area : listArea)
			{
				// 营销部
				if (area.getpId().equals(1L))
				{
					firstDpt.add(area);
				}

				// 营销处
				if (!area.getpId().equals(1L) && !area.getpId().equals(0L))
				{
					secondDpt.add(area);
				}
			}

			result.put("firstDpt", firstDpt);
			result.put("secondDpt", secondDpt);
		} else
		{

			for (MarketManagerRemote market : listMarketManager)
			{
				// 助理总经理
				if (roleId.equals(600L))
				{
					// 当前用户管辖的营销处ID集合
					String firstDptIds = "," + market.getFirstDptIds() + ",";

					for (Area area : listArea)
					{
						if (firstDptIds.contains("," + area.getpId() + ","))
						{
							// 获取当前用户管辖的营销处数据结构
							secondDpt.add(area);
						}
					}
				} else
				{
					// 当前用户管辖的营销处ID集合
					String secondDptIds = "," + market.getSecondDptIds() + ",";

					for (Area area : listArea)
					{
						if (secondDptIds.contains("," + area.getId() + ","))
						{
							// 获取当前用户管辖的营销处数据结构
							secondDpt.add(area);
						}
					}
				}
			}

			result.put("secondDpt", secondDpt);
		}

		return result;
	}

	/**
	 * @Description: crm1.1 获取上级管辖的下级人员
	 * @param secondDptIds（市场）
	 * @return List<Long>
	 * @throws
	 */
	public List<Long> getSeller(@Param("secondDptIds") List<Long> secondDptIds)
	{
		return classStatisticsDao.getSeller(secondDptIds);
	}

	/**
	 * @Description: 营销部下所有营销处
	 * @param market
	 * @return    参数
	 * @throws
	 */
	public List<Long> getMarketChildren(Long market)
	{
		List<Long> result = new ArrayList<Long>();

		// 所有市场
		List<Area> listArea = MarketContext.findAll();

		for (Area area : listArea)
		{
			if (area.getpId().equals(market))
			{
				result.add(area.getId());
			}
		}

		return result;
	}
}
