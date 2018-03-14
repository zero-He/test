/**
 * 
 */
package cn.strong.leke.monitor.util;

import java.util.List;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.model.user.area.AreaMerge;

/**
 * BSON 查询条件帮助类
 * 
 * @author liulongbiao
 *
 */
public class BsonCriteriaUtils {

	/**
	 * 添加区域查询条件
	 * 
	 * @param filter
	 * @param area
	 */
	public static void appendCriteria(BsonCriteria filter, AreaMerge area) {
		if (area == null) {
			return;
		}
		int type = area.getType();
		if ((type == AreaMerge.TYPE_MARKET || type == AreaMerge.TYPE_ALL) && area.getMarketId() != null) {
			filter.append("marketIds", area.getMarketId());
		}
		if (type == AreaMerge.TYPE_REGION || type == AreaMerge.TYPE_ALL) {
			List<Long> regionIds = area.getRegionIds();
			if (CollectionUtils.isNotEmpty(regionIds)) {
				filter.eqOrIn("regionIds", regionIds);
			} else if (area.getCityId() != null) {
				filter.append("regionIds", area.getCityId());
			} else if (area.getProvinceId() != null) {
				filter.append("regionIds", area.getProvinceId());
			}
		}
	}
}
