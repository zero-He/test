package cn.strong.leke.monitor.mongo.resource.model.query;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.ne;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.Document;
import org.bson.conversions.Bson;

import cn.strong.leke.common.utils.DateUtils;

public class SchoolUsedTrendQuery {
	
	private Long schoolId;
	private String schoolName;
	private String startDate;
	private String endDate;
	private Integer resType;
	private String resTypeName;
	private Integer resResource;
	private String resResourceName;
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public Integer getResType() {
		return resType;
	}
	public void setResType(Integer resType) {
		this.resType = resType;
	}
	public String getResTypeName() {
		return resTypeName;
	}
	public void setResTypeName(String resTypeName) {
		this.resTypeName = resTypeName;
	}
	public Integer getResResource() {
		return resResource;
	}
	public void setResResource(Integer resResource) {
		this.resResource = resResource;
	}
	public String getResResourceName() {
		return resResourceName;
	}
	public void setResResourceName(String resResourceName) {
		this.resResourceName = resResourceName;
	}
	
	public Bson toBSON() throws ParseException{
		Document filter = new Document();
		
		if (resType != null) {
			filter.append("resType", resType);
		}
		if (schoolId != null) {
			filter.append("createdSchoolId", schoolId);
		}
		
		Date startTime = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
		Date endTime = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
	
		Bson bson = and(filter,gte("createdOn", startTime),lte("createdOn", DateUtils.addDays(endTime, 1)),
				eq("sharePlatform", true));
		if (resResource ==1) {
			bson = and(filter,gte("createdOn", startTime),lte("createdOn", DateUtils.addDays(endTime, 1)),
					eq("schoolId", 0L),ne("resCreateBy", 2),eq("sharePlatform", true));
		}else if (resResource ==2) {
			bson = and(filter,gte("createdOn", startTime),lte("createdOn", DateUtils.addDays(endTime, 1)),
					eq("schoolId", 0L),eq("resCreateBy", 2),eq("sharePlatform", true));
		}else if (resResource ==3){
			bson = and(filter,gte("createdOn", startTime),lte("createdOn", DateUtils.addDays(endTime, 1)),
					ne("schoolId", 0L),eq("sharePlatform", true));
		}
		return bson;
	}
}
