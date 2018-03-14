package cn.strong.leke.monitor.mongo.resource.model.query;

import java.util.Date;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.ne;
import org.bson.Document;
import org.bson.conversions.Bson;

import cn.strong.leke.common.utils.DateUtils;

public class ResourceUsedQuery {
	private Long schoolStageId;		
	private Long subjectId;			
	private Long schoolId;			
	private Integer resType;		
	private boolean sharePlatform;	
	private Long userId;	
	private Integer resSource;
	private Date startDateTime;
	private Date endDataTime;
	private int shareScope;
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
	public Long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Long schollId) {
		this.schoolId = schollId;
	}
	public Integer getResType() {
		return resType;
	}
	public void setResType(Integer resType) {
		this.resType = resType;
	}
	public boolean isSharePlatform() {
		return sharePlatform;
	}
	public void setSharePlatform(boolean sharePlatform) {
		this.sharePlatform = sharePlatform;
	}

	public Integer getResSource() {
		return resSource;
	}
	public void setResSource(Integer resSource) {
		this.resSource = resSource;
	}
	

	public Date getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}
	public Date getEndDataTime() {
		return endDataTime;
	}
	public void setEndDataTime(Date endDataTime) {
		this.endDataTime = endDataTime;
	}


	public int getShareScope() {
		return shareScope;
	}
	public void setShareScope(int shareScope) {
		this.shareScope = shareScope;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
		if (schoolId != null) {
			filter.append("createdSchoolId", schoolId);
		}
		Bson bson = and(filter,gte("createdOn", startDateTime),lte("createdOn", DateUtils.addDays(endDataTime, 1)),
				eq("sharePlatform", true));
		if (resSource ==1) {
			bson = and(filter,gte("createdOn", startDateTime),lte("createdOn", DateUtils.addDays(endDataTime, 1)),
					eq("schoolId", 0L),ne("resCreateBy", 2),eq("sharePlatform", true));
		}else if (resSource ==2) {
			bson = and(filter,gte("createdOn", startDateTime),lte("createdOn", DateUtils.addDays(endDataTime, 1)),
					eq("schoolId", 0L),eq("resCreateBy", 2),eq("sharePlatform", true));
		}else if (resSource ==3){
			bson = and(filter,gte("createdOn", startDateTime),lte("createdOn", DateUtils.addDays(endDataTime, 1)),
					ne("schoolId", 0L),eq("sharePlatform", true));
		}
		
		return bson;
	}

}
