/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.model.query;

import java.util.List;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.model.base.SchoolCst;
import cn.strong.leke.model.user.area.AreaMerge;
import cn.strong.leke.monitor.util.BsonCriteria;
import cn.strong.leke.monitor.util.BsonCriteriaUtils;

/**
 * @author liulongbiao
 *
 */
public class BaseAreaSchoolQuery {
	private AreaMerge area; // 所属区域
	private List<Long> schoolIds; // 学校ID集合
	private Integer schoolNature; // 学校性质
	private boolean areaRequired = false;

	public AreaMerge getArea() {
		return area;
	}

	public void setArea(AreaMerge area) {
		this.area = area;
	}

	public List<Long> getSchoolIds() {
		return schoolIds;
	}

	public void setSchoolIds(List<Long> schoolIds) {
		this.schoolIds = schoolIds;
	}

	public Integer getSchoolNature() {
		return schoolNature;
	}

	public void setSchoolNature(Integer schoolNature) {
		this.schoolNature = schoolNature;
	}

	public boolean isAreaRequired() {
		return areaRequired;
	}

	public void setAreaRequired(boolean areaRequired) {
		this.areaRequired = areaRequired;
	}

	public void appendAreaSchool(BsonCriteria filter) {
		boolean hasSchool = CollectionUtils.isNotEmpty(schoolIds);
		if (hasSchool) {
			filter.eqOrIn("schoolId", schoolIds);
		}
		if (schoolNature != null) {
			filter.eq("schoolNature", schoolNature);
		}

		if (areaRequired) {
			if (area == null) {
				throw new IllegalStateException("area is required.");
			}
			BsonCriteriaUtils.appendCriteria(filter, area);
			filter.eq("schoolNature", SchoolCst.NATURE_BASIC);
		} else if (!hasSchool && area != null) {
			BsonCriteriaUtils.appendCriteria(filter, area);
		}
	}
}
