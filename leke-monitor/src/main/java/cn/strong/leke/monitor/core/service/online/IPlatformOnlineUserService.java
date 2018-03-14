/**
 * 
 */
package cn.strong.leke.monitor.core.service.online;

import cn.strong.leke.monitor.core.model.online.CityOnlineUserStatDTO;
import cn.strong.leke.monitor.core.model.online.CountryOnlineUserStatDTO;
import cn.strong.leke.monitor.core.model.online.ProvinceOnlineUserStatDTO;

/**
 * 平台在线用户服务接口
 * 
 * @author liulongbiao
 *
 */
public interface IPlatformOnlineUserService {

	/**
	 * 获取平台在线用户统计
	 * 
	 * @param countryId
	 * @return
	 */
	CountryOnlineUserStatDTO getCountryStat(long countryId);

	/**
	 * 获取省级在线用户统计
	 * 
	 * @param provinceId
	 * @return
	 */
	ProvinceOnlineUserStatDTO getProvinceStat(long provinceId);

	/**
	 * 获取市级(区级)在线用户统计
	 * 
	 * @param regionId
	 * @return
	 */
	CityOnlineUserStatDTO getCityStat(long regionId);
}
