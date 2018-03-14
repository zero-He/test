/**
 * 
 */
package cn.strong.leke.monitor.core.map;

import java.util.Collections;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 国家地图信息
 * 
 * @author liulongbiao
 *
 */
public class MapCountry implements MapRegion {
	/**
	 * 中国地区ID
	 */
	public static final long ID_CHINA = 100000;
	/**
	 * 中国地区名称
	 */
	public static final String NAME_CHINA = "中国";

	private Long id; // 国家ID
	private String name; // 国家名称
	private String map; // 地图文件
	private SortedMap<Long, MapProvince> provinces = new TreeMap<>(); // 省列表

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public SortedMap<Long, MapProvince> getProvinces() {
		return Collections.unmodifiableSortedMap(provinces);
	}

	public void setProvinces(SortedMap<Long, MapProvince> provinces) {
		this.provinces.clear();
		if (provinces != null) {
			this.provinces.putAll(provinces);
		}
	}

	/**
	 * 添加省地图信息
	 * 
	 * @param province
	 */
	public void add(MapProvince province) {
		Objects.requireNonNull(province);
		Objects.requireNonNull(province.getId(), "province id is required");
		this.provinces.put(province.getId(), province);
	}

	/**
	 * 获取省地图信息
	 * 
	 * @param provinceId
	 * @return
	 */
	public MapProvince province(Long provinceId) {
		return provinceId == null ? null : provinces.get(provinceId);
	}

	/**
	 * 省地图信息
	 * 
	 * @author liulongbiao
	 *
	 */
	public static class MapProvince implements MapRegion {
		private Long id; // 省ID
		private String name; // 省名称
		private String map; // 省地图文件
		private SortedMap<Long, MapCity> cities = new TreeMap<>(); // 地级市列表

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMap() {
			return map;
		}

		public void setMap(String map) {
			this.map = map;
		}

		public SortedMap<Long, MapCity> getCities() {
			return Collections.unmodifiableSortedMap(cities);
		}

		public void setCities(SortedMap<Long, MapCity> cities) {
			this.cities.clear();
			if (cities != null) {
				this.cities.putAll(cities);
			}
		}

		/**
		 * 添加地级市地图信息
		 * 
		 * @param city
		 */
		public void add(MapCity city) {
			Objects.requireNonNull(city);
			Objects.requireNonNull(city.getId(), "city id is required");
			this.cities.put(city.getId(), city);
		}

		/**
		 * 获取地级市地图信息
		 * 
		 * @param cityId
		 * @return
		 */
		public MapCity city(Long cityId) {
			return cityId == null ? null : cities.get(cityId);
		}

	}

	/**
	 * 地级市地图信息
	 * 
	 * @author liulongbiao
	 *
	 */
	public static class MapCity implements MapRegion {
		private Long id;
		private String name;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

}
