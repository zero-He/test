/**
 * 
 */
package cn.strong.leke.monitor.mongo.service.impl;

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

import cn.strong.leke.monitor.mongo.model.AccessDaily;
import cn.strong.leke.monitor.mongo.model.query.AccessDailyQuery;
import cn.strong.leke.monitor.mongo.model.query.AccessRangeQuery;
import cn.strong.leke.monitor.mongo.model.query.AccessStat;
import cn.strong.leke.monitor.mongo.model.updates.AccessDailyDelta;
import cn.strong.leke.monitor.mongo.service.IAccessDailyService;

/**
 * @author liulongbiao
 *
 */
@Repository
public class AccessDailyService implements IAccessDailyService {
	
	public static final Document STAT_GROUP;
	public static final Document STAT_PROJECT;
	
	static {
		Document grp = new Document("_id",
				new Document("serverName", "$serverName").append("servletPath", "$servletPath"))
				.append("count", new Document("$sum", "$count"))
				.append("maxExecuteTime", new Document("$max", "$maxExecuteTime"))
				.append("totalExecuteTime", new Document("$sum", "$totalExecuteTime"));
		STAT_GROUP = new Document("$group", grp);
		Document prj = new Document("_id", 0)
				.append("serverName", "$_id.serverName")
				.append("servletPath", "$_id.servletPath").append("count", 1)
				.append("maxExecuteTime", 1).append("totalExecuteTime", 1);
		STAT_PROJECT = new Document("$project", prj);
	}

	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<AccessDaily> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("access.daily", AccessDaily.class);
	}

	@Override
	public void update(AccessDailyDelta delta) {
		coll.updateOne(delta.filter(), delta.update(), new UpdateOptions().upsert(true));
	}

	@Override
	public List<AccessDaily> findAccessDailys(AccessDailyQuery query) {
		return coll.find(query.toBSON()).into(new ArrayList<>());
	}

	/*
	 * 
db.access.daily.aggregate([{
  $match: {
    day: {
      $gte: 20150908,
      $lte: 20150909
    },
    serverName: 'monitor.leke.cn'
  }
},{
  $group: {
    _id: {
      'serverName': '$serverName',
      'servletPath': '$servletPath'
    },
    count: {
      $sum: '$count'
    },
    maxExecuteTime: {
      $max: '$maxExecuteTime'
    },
    totalExecuteTime: {
      $sum: '$totalExecuteTime'
    }
  }
}, {
  $project: {
    _id: 0,
    serverName: '$_id.serverName',
    servletPath: '$_id.servletPath',
    count: 1,
    maxExecuteTime: 1,
    totalExecuteTime: 1
  }
}])
	 */
	@Override
	public List<AccessStat> findAccessRangeStats(AccessRangeQuery query) {
		if (query == null) {
			throw new IllegalArgumentException("query should not be null.");
		}
		Document filter = query.toBSON();
		Bson match = new Document("$match", filter);
		return coll.aggregate(Arrays.asList(match, STAT_GROUP, STAT_PROJECT),
				AccessStat.class).into(new ArrayList<>());
	}

}
