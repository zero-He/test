/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.service.impl;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.ne;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Projections.include;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.model.base.SchoolCst;
import cn.strong.leke.monitor.mongo.online.model.OnlineUserSchoolStat;
import cn.strong.leke.monitor.mongo.online.model.OnlineUserStat;
import cn.strong.leke.monitor.mongo.online.service.IOnlineUserSchoolStatDao;

/**
 * @author liulongbiao
 *
 */
@Repository
public class OnlineUserSchoolStatDao implements IOnlineUserSchoolStatDao {
	private static Bson AGG_GROUP = group(null, sum("registered", "$registered"), sum("platform", "$platform"),
			sum("lesson", "$lesson"), sum("web", "$web"), sum("device", "$device"));
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<OnlineUserSchoolStat> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("onlineuser.school.stat", OnlineUserSchoolStat.class);
	}

	@Override
	public void save(OnlineUserSchoolStat stat) {
		Bson filter = eq("_id", stat.getSchoolId());
		Bson update = BsonUtils.toUpdateSetDoc(stat);
		coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}

	@Override
	public OnlineUserSchoolStat getById(long schoolId) {
		return coll.find(eq("_id", schoolId)).first();
	}

	@Override
	public OnlineUserStat sumAll() {
		Bson filter = gte("schoolCode", SchoolCst.MIN_SCHOOL_CODE);
		OnlineUserStat stat = coll.aggregate(Arrays.asList(match(filter), AGG_GROUP), OnlineUserStat.class).first();
		return stat != null ? stat : new OnlineUserStat();
	}

	@Override
	public OnlineUserStat sumByRegion(Long regionId) {
		Bson filter = filterByRegion(regionId);
		OnlineUserStat stat = coll.aggregate(Arrays.asList(match(filter), AGG_GROUP), OnlineUserStat.class).first();
		return stat != null ? stat : new OnlineUserStat();
	}

	private static Bson filterByRegion(Long regionId) {
		return and(eq("regionIds", regionId), gt("schoolCode", SchoolCst.MIN_SCHOOL_CODE), ne("_id", SchoolCst.LEKE),
				in("schoolNature", SchoolCst.NATURE_BASIC, SchoolCst.NATURE_CRAM));
	}

	@Override
	public List<Long> findSchoolsHasOnline() {
		return coll.find(or(gt("platform", 0), gt("lesson", 0)), Document.class).projection(include("_id")).map(doc -> {
			return doc.getLong("_id");
		}).into(new ArrayList<>());
	}

	@Override
	public List<OnlineUserSchoolStat> findByRegion(Long regionId) {
		Bson filter = filterByRegion(regionId);
		return coll.find(filter).into(new ArrayList<>());
	}

	@Override
	public void removeById(Long schoolId) {
		coll.deleteOne(eq("_id", schoolId));
	}

}
