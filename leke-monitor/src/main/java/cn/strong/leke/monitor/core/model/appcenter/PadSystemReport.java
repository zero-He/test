/**
 * 
 */
package cn.strong.leke.monitor.core.model.appcenter;

import java.util.List;

/**
 * 平板系统应用报告
 * 
 * @author liulongbiao
 *
 */
public class PadSystemReport {

	private String imei; // 移动设备识别码
	private String userId; // 用户ID (可选参数)
	private String version; // 系统版本号
	private Boolean rooted; // 是否被 root
	private String mac;//mac地址
	private Double longitude;//纬度
	private Double latitude;//经度
	private List<AppInfo> apps; // 安装的应用列表

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


	public Boolean getRooted() {
		return rooted;
	}


	public void setRooted(Boolean rooted) {
		this.rooted = rooted;
	}


	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public List<AppInfo> getApps() {
		return apps;
	}


	public void setApps(List<AppInfo> apps) {
		this.apps = apps;
	}


	/**
	 * 应用信息
	 * 
	 * @author liulongbiao
	 *
	 */
	public static class AppInfo {
		private String pkgName; // 包名
		private String appName; // 应用名

		public String getPkgName() {
			return pkgName;
		}

		public void setPkgName(String pkgName) {
			this.pkgName = pkgName;
		}

		public String getAppName() {
			return appName;
		}

		public void setAppName(String appName) {
			this.appName = appName;
		}
	}
}
