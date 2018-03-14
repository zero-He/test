/**
 * 
 */
package cn.strong.leke.monitor.core.service.online.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.monitor.core.map.IMapRegionStore;
import cn.strong.leke.monitor.core.map.MapCountry;
import cn.strong.leke.monitor.core.map.MapCountry.MapProvince;
import cn.strong.leke.monitor.core.map.MapRegion;
import cn.strong.leke.monitor.core.model.online.CityOnlineUserStatDTO;
import cn.strong.leke.monitor.core.model.online.CityOnlineUserStatDTO.SchoolOnlineUserStat;
import cn.strong.leke.monitor.core.model.online.CountryOnlineUserStatDTO;
import cn.strong.leke.monitor.core.model.online.ProvinceOnlineUserStatDTO;
import cn.strong.leke.monitor.core.model.online.RegionOnlineUserStatDTO;
import cn.strong.leke.monitor.core.service.online.IPlatformOnlineUserService;
import cn.strong.leke.monitor.mongo.online.model.OnlineUserSchoolStat;
import cn.strong.leke.monitor.mongo.online.model.OnlineUserStat;
import cn.strong.leke.monitor.mongo.online.service.IOnlineUserSchoolStatDao;

/**
 * @author liulongbiao
 *
 */
@Service
public class PlatformOnlineUserService implements IPlatformOnlineUserService {
	@Resource
	private IMapRegionStore mapRegionStore;
	@Resource
	private IOnlineUserSchoolStatDao onlineUserSchoolStatDao;
	@Resource(name = "monitorExecutor")
	private ExecutorService monitorExecutor;

	@Override
	public CountryOnlineUserStatDTO getCountryStat(long countryId) {
		CountryOnlineUserStatDTO result = new CountryOnlineUserStatDTO();
		MapCountry country = mapRegionStore.country(countryId);
		Validation.notNull(country, "找不到地图配置信息");
		result.setTotal(getCountrySum(country));
		result.setDetails(findProvinceStats(country));
		return result;
	}

	private RegionOnlineUserStatDTO getCountrySum(MapCountry country) {
		OnlineUserStat stat = onlineUserSchoolStatDao.sumAll();
		RegionOnlineUserStatDTO total = new RegionOnlineUserStatDTO(stat);
		total.setId(country.getId());
		total.setName(country.getName());
		return total;
	}

	private List<RegionOnlineUserStatDTO> findProvinceStats(MapCountry country) {
		if (country == null) {
			return Collections.emptyList();
		}
		return findRegionStats(country.getProvinces());
	}

	private List<RegionOnlineUserStatDTO> findRegionStats(
			SortedMap<Long, ? extends MapRegion> regions) {
		if (regions == null || regions.isEmpty()) {
			return Collections.emptyList();
		}
		try {
			return findRegionStatsAsync(regions).get(3, TimeUnit.MINUTES);
		} catch (Exception e) {
			throw new LekeRuntimeException("获取区域在线人数统计失败", e);
		}
	}

	private CompletableFuture<List<RegionOnlineUserStatDTO>> findRegionStatsAsync(
			SortedMap<Long, ? extends MapRegion> regions) {
		@SuppressWarnings("unchecked")
		CompletableFuture<RegionOnlineUserStatDTO>[] subtasks = new CompletableFuture[regions.size()];
		int i = 0;
		for (Entry<Long, ? extends MapRegion> entry : regions.entrySet()) {
			subtasks[i] = getRegionStatAsync(entry.getValue());
			i++;
		}
		return CompletableFuture.allOf(subtasks).thenApply(v -> {
			List<RegionOnlineUserStatDTO> result = new ArrayList<>();
			for (CompletableFuture<RegionOnlineUserStatDTO> task : subtasks) {
				result.add(task.join());
			}
			return result;
		});
	}

	private CompletableFuture<RegionOnlineUserStatDTO> getRegionStatAsync(MapRegion region) {
		return CompletableFuture.supplyAsync(() -> {
			return getRegionStat(region);
		}, monitorExecutor);
	}

	private RegionOnlineUserStatDTO getRegionStat(MapRegion region) {
		Long id = region.getId();
		OnlineUserStat stat = onlineUserSchoolStatDao.sumByRegion(id);
		RegionOnlineUserStatDTO result = new RegionOnlineUserStatDTO(stat);
		result.setId(id);
		result.setName(region.getName());
		return result;
	}

	@Override
	public ProvinceOnlineUserStatDTO getProvinceStat(long provinceId) {
		MapProvince province = mapRegionStore.province(provinceId);
		if (province == null) {
			return null;
		}
		ProvinceOnlineUserStatDTO result = new ProvinceOnlineUserStatDTO();
		result.setTotal(getRegionStat(province));
		result.setDetails(findRegionStats(province.getCities()));
		return result;
	}

	@Override
	public CityOnlineUserStatDTO getCityStat(long regionId) {
		MapRegion region = mapRegionStore.region(regionId);
		if (region == null) {
			return null;
		}
		CityOnlineUserStatDTO result = new CityOnlineUserStatDTO();
		result.setTotal(getRegionStat(region));
		List<OnlineUserSchoolStat> schoolStats = onlineUserSchoolStatDao.findByRegion(regionId);
		List<SchoolOnlineUserStat> details = ListUtils.map(schoolStats, stat -> {
			SchoolOnlineUserStat s = new SchoolOnlineUserStat(stat);
			s.setSchoolId(stat.getSchoolId());
			s.setSchoolName(stat.getSchoolName());
			s.setRegionId(region.getId());
			s.setRegionName(region.getName());
			return s;
		});
		result.setDetails(details);
		return result;
	}

}
