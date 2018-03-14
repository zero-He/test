/**
 * 
 */
package cn.strong.leke.monitor.core.map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.strong.leke.common.utils.json.JSON;
import cn.strong.leke.core.io.ResLoaders;
import cn.strong.leke.monitor.core.map.MapCountry.MapCity;
import cn.strong.leke.monitor.core.map.MapCountry.MapProvince;

/**
 * 
 * 
 * @author liulongbiao
 *
 */
public class MapCountryInitializer {

	private static final String SQL_REGION_BY_PID = "select regionId, regionName from tutor_region where pId = ?";
	@Resource(name = "tutorJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 初始化中国地图信息
	 */
	public void initChina() {
		MapCountry china = getChina();
		System.out.println(JSON.prettify(china));
		try {
			FileUtils.writeStringToFile(new File("data/china.json"), JSON.prettify(china));
		} catch (IOException e) {
			throw new RuntimeException("写文件失败", e);
		}
	}

	private MapCountry getChina() {
		long countryId = MapCountry.ID_CHINA;
		MapCountry china = new MapCountry();
		china.setId(countryId);
		china.setName("中国");
		china.setMap("china");
		
		List<MapProvince> provinces = jdbcTemplate.query(SQL_REGION_BY_PID, new Object[]{countryId}, (rs, row) -> {
			MapProvince prov = new MapProvince();
			String name = rs.getString("regionName");
			prov.setId(rs.getLong("regionId"));
			prov.setName(name);
			prov.setMap(name.substring(0, 2));
			return prov;
		});
		provinces.forEach(province -> {
			findCities(province).forEach(city -> {
				province.add(city);
			});
			china.add(province);
		});
		return china;
	}

	private List<MapCity> findCities(MapProvince prov) {
		long provId = prov.getId();
		switch ((int) provId) {
		case 110000: // 北京
		case 120000: // 天津
		case 310000: // 上海
		case 500000: // 重庆
			return findDistricts(provId);
		case 810000: // 香港
		case 820000: // 澳门
			return Collections.emptyList();
		default:
			return findCitiesByPid(provId);
		}
	}

	private List<MapCity> findCitiesByPid(long parentId) {
		return jdbcTemplate.query(SQL_REGION_BY_PID, new Object[] { parentId }, (rs, row) -> {
			MapCity city = new MapCity();
			city.setId(rs.getLong("regionId"));
			city.setName(rs.getString("regionName"));
			return city;
		});
	}

	private List<MapCity> findDistricts(long provId) {
		List<MapCity> result = new ArrayList<>();
		findCitiesByPid(provId).forEach(city -> {
			result.addAll(findCitiesByPid(city.getId()));
		});
		return result;
	}

	public static void main(String[] args) {
		MapCountry country = ResLoaders.loadJSON("classpath:data/china.json", MapCountry.class);
		System.out.println(JSON.stringify(country));
	}
}
