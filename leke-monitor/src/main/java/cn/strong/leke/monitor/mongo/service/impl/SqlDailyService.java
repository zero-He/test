/**
 * 
 */
package cn.strong.leke.monitor.mongo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.monitor.mongo.model.SqlDaily;
import cn.strong.leke.monitor.mongo.model.query.SqlDailyQuery;
import cn.strong.leke.monitor.mongo.model.updates.SqlDailyDelta;
import cn.strong.leke.monitor.mongo.service.ISqlDailyService;

/**
 * @author liulongbiao
 *
 */
@Repository
public class SqlDailyService implements ISqlDailyService {

	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<SqlDaily> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("sql.daily", SqlDaily.class);
	}

	@Override
	public void update(SqlDailyDelta delta) {
		coll.updateOne(delta.filter(), delta.update(), new UpdateOptions().upsert(true));
	}

	@Override
	public List<SqlDaily> findSqlDailys(SqlDailyQuery query) {
		return coll.find(query.toBSON()).into(new ArrayList<>());
	}

}
