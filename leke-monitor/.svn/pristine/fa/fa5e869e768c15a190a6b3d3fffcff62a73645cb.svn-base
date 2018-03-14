/**
 * 
 */
package cn.strong.leke.monitor.core.model.online;

import java.util.List;

import cn.strong.leke.monitor.mongo.online.model.OnlineUserStat;

/**
 * 市级在线用户统计
 * 
 * @author liulongbiao
 *
 */
public class CityOnlineUserStatDTO {
	private RegionOnlineUserStatDTO total; // 市级总计
	private List<SchoolOnlineUserStat> details; // 学校明细

	public RegionOnlineUserStatDTO getTotal() {
		return total;
	}

	public void setTotal(RegionOnlineUserStatDTO total) {
		this.total = total;
	}

	public List<SchoolOnlineUserStat> getDetails() {
		return details;
	}

	public void setDetails(List<SchoolOnlineUserStat> details) {
		this.details = details;
	}

	public static class SchoolOnlineUserStat extends OnlineUserStat {
		private Long schoolId; // 学校ID
		private String schoolName; // 学校名称
		private Long regionId; // 地区ID
		private String regionName; // 地区名称

		public SchoolOnlineUserStat() {
		}

		public SchoolOnlineUserStat(OnlineUserStat stat) {
			super(stat);
		}

		public Long getSchoolId() {
			return schoolId;
		}

		public void setSchoolId(Long schoolId) {
			this.schoolId = schoolId;
		}

		public String getSchoolName() {
			return schoolName;
		}

		public void setSchoolName(String schoolName) {
			this.schoolName = schoolName;
		}

		public Long getRegionId() {
			return regionId;
		}

		public void setRegionId(Long regionId) {
			this.regionId = regionId;
		}

		public String getRegionName() {
			return regionName;
		}

		public void setRegionName(String regionName) {
			this.regionName = regionName;
		}

	}
}
