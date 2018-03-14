/**
 * 
 */
package cn.strong.leke.monitor.core.map;

import cn.strong.leke.monitor.core.map.MapCountry.MapCity;
import cn.strong.leke.monitor.core.map.MapCountry.MapProvince;

/**
 * 地图区域信息存储
 * 
 * @author liulongbiao
 *
 */
public interface IMapRegionStore {
	/**
	 * 添加国家地图信息
	 * 
	 * @param country
	 */
	void add(MapCountry country);

	/**
	 * 获取区域地图信息
	 * 
	 * @param regionId
	 * @return
	 */
	MapRegion region(long regionId);

	/**
	 * 获取国家地图信息
	 * 
	 * @param countryId
	 * @return
	 */
	MapCountry country(long countryId);

	/**
	 * 获取省地图信息
	 * 
	 * @param provinceId
	 * @return
	 */
	MapProvince province(long provinceId);

	/**
	 * 获取城市地图信息
	 * 
	 * @param cityId
	 * @return
	 */
	MapCity city(long cityId);
}
