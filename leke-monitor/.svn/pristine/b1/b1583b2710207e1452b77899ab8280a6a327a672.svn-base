package cn.strong.leke.monitor.mongo.resource.impl;

import static com.mongodb.client.model.Accumulators.addToSet;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Accumulators.max;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.BaseModel;
import cn.strong.leke.model.monitor.ResourceUsedDetail;
import cn.strong.leke.monitor.mongo.resource.IResourceUsedDao;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceTopQuery;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceTopSta;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceUsedQuery;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceUsedSta;
import cn.strong.leke.monitor.mongo.resource.model.query.SchoolResourceUsedSta;
import cn.strong.leke.monitor.mongo.resource.model.query.SchoolUsedTrendQuery;
import cn.strong.leke.monitor.mongo.resource.model.query.SchoolUsedTrendSta;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository
public class ResourceUsedDao implements IResourceUsedDao{
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<ResourceUsedDetail> coll;  

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("resource.used.detail", ResourceUsedDetail.class);
	}
	
	@Override
	public void save(List<ResourceUsedDetail> details) {
		String dataStr = BaseModel.formatDate(new Date(), "yyyyMMdd");
		for (ResourceUsedDetail detail : details) {
			Assert.notNull(detail.getResId(), "resId is required");
			Assert.notNull(detail.getResType(), "resType is required");
			Assert.notNull(detail.getUsedType(), "usedType is required");
			Assert.notNull(detail.getCreatedBy(), "createdBy is required");
			detail.setCreatedOnStr(dataStr);
			detail.setCreatedOn(new Date());
		}
		coll.insertMany(details);
	}

	@Override
	public int getResourceUsedNum(ResourceUsedQuery query) {
		Bson filter = query.toBSON();
		return  coll.distinct("resId", filter, Long.class).into(new HashSet<>()).size();
	}

	@Override
	public int getResourceUsedCount(ResourceUsedQuery query) {
		Bson filter = query.toBSON();
		return (int) coll.count(filter);
	}

	@Override
	public List<SchoolResourceUsedSta> getSchoolResourceUsedSta(ResourceUsedQuery query) {
		Bson filter = query.toBSON();

		List<Bson> pipeline = Arrays.asList(
				match(filter),
				group("$createdSchoolId", sum("usedNum", 1),max("schoolId", "$createdSchoolId")),
				sort(descending("usedNum")),
				project(fields(excludeId(),include("schoolId","usedNum")))
				);
		return coll.aggregate(pipeline,SchoolResourceUsedSta.class).into(new ArrayList<>());
	}

	@Override
	public List<Document> getResourceBySchoolId(ResourceUsedQuery query, Long schoolId) {
		Bson filter = and(query.toBSON(),eq("createdSchoolId", schoolId));
		List<Bson> pipeline = Arrays.asList(
				match(filter),
				group("$usedType", sum("usedNum", 1),max("usedType", "$usedType")),
				project(fields(excludeId(),include("usedType","usedNum")))
				);
		return coll.aggregate(pipeline,Document.class).into(new ArrayList<>());
	}

	@Override
	public List<ResourceUsedSta> getResourceGroupByDate(ResourceUsedQuery query) {
		Bson filter = and(query.toBSON());
		
		List<Bson> pipeline = Arrays.asList(
				match(filter),
				group("$createdOnStr", sum("usedRescourceCount", 1),addToSet("resIds", "$resId"),
						max("ts", "$createdOnStr")),
				sort(ascending("ts")),
				project(fields(excludeId(),include("ts","usedRescourceCount","resIds")))
				);
		return coll.aggregate(pipeline,ResourceUsedSta.class).into(new ArrayList<>());
	}

	
	@Override
	public List<ResourceTopSta> getResIdAndUsednum(ResourceTopQuery query, Page page) {
		Bson filter = query.toBSON();
		List<Bson> pipeline = Arrays.asList( 
				match(filter),
				group("$resId", sum("usedNum", 1),max("resId", "$resId"),max("resType","$resType")),
				sort(descending("usedNum")),
				project(fields(excludeId(),include("resId","usedNum","resType"))),
				limit(50)
				);
		return coll.aggregate(pipeline,ResourceTopSta.class).into(new ArrayList<>());
	}

	@Override
	public List<SchoolUsedTrendSta> getSchoolUsedNum(SchoolUsedTrendQuery query) throws ParseException {
		Bson filter = query.toBSON();
		List<Bson> pipeline = Arrays.asList(
				match(filter),
				group("$createdOnStr", sum("usedNum", 1),max("ts","$createdOnStr")),
				sort(ascending("ts")),
				project(fields(excludeId(),include("ts","usedNum")))  
				);
		return coll.aggregate(pipeline,SchoolUsedTrendSta.class).into(new ArrayList<>());
	}
}
