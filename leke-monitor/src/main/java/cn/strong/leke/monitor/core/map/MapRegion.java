package cn.strong.leke.monitor.core.map;

/**
 * 地图区域接口
 * 
 * @author liulongbiao
 *
 */
public interface MapRegion {
	/**
	 * 获取区域ID
	 * 
	 * @return
	 */
	Long getId();

	/**
	 * 获取区域名称
	 * 
	 * @return
	 */
	String getName();
}