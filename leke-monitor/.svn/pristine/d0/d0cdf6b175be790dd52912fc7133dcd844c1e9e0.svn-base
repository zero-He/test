/**
 * 
 */
package cn.strong.leke.monitor.core.map;

import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

import cn.strong.leke.monitor.core.map.MapCountry.MapCity;
import cn.strong.leke.monitor.core.map.MapCountry.MapProvince;

/**
 * 地图区域信息存储
 * 
 * @author liulongbiao
 *
 */
public class InMemoryMapRegionStore implements IMapRegionStore {

	private SortedMap<Long, MapRegion> regions = new TreeMap<>();

	@Override
	public void add(MapCountry country) {
		Objects.requireNonNull(country);
		Objects.requireNonNull(country.getId(), "country id is required");
		regions.put(country.getId(), country);
		regions.putAll(country.getProvinces());
		country.getProvinces().values().forEach(province -> {
			regions.putAll(province.getCities());
		});
	}

	@Override
	public MapRegion region(long regionId) {
		return regions.get(regionId);
	}

	@Override
	public MapCountry country(long countryId) {
		return (MapCountry) regions.get(countryId);
	}

	@Override
	public MapProvince province(long provinceId) {
		return (MapProvince) regions.get(provinceId);
	}

	@Override
	public MapCity city(long cityId) {
		return (MapCity) regions.get(cityId);
	}
}
