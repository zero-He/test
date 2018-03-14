package cn.strong.leke.monitor.mongo.resource.model.query;


import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;

import java.util.Date;

import org.bson.Document;
import org.bson.conversions.Bson;

import cn.strong.leke.common.utils.DateUtils;

public class ResourceTopQuery {
	
	private Date fromDate;
	private Date endDate;
	private Integer resType;
	private Long schoolStageId;
	private Long subjectId;
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getResType() {
		return resType;
	}
	public void setResType(Integer resType) {
		this.resType = resType;
	}
	public Long getSchoolStageId() {
		return schoolStageId;
	}
	public void setSchoolStageId(Long schoolStageId) {
		this.schoolStageId = schoolStageId;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	
	
	public Bson toBSON(){
		Document filter = new Document();
		
		if (schoolStageId != null) {
			filter.append("schoolStageId", schoolStageId);
		}
		if (subjectId != null) {
			filter.append("subjectId", subjectId);
		}
		if (resType != null) {
			filter.append("resType", resType);
		}
		Bson bson = and(filter.append("sharePlatform", true),gte("createdOn", fromDate),lte("createdOn", DateUtils.addDays(endDate,1)));
		
		return bson;
	}
	
}
