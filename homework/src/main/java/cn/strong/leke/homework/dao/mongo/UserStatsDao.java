package cn.strong.leke.homework.dao.mongo;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.homework.model.UserStats;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

@Repository
public class UserStatsDao implements InitializingBean {

	private static final String COLL_NAME = "UserStats";

	@Autowired
	private MongoDatabase db;
	private MongoCollection<UserStats> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, UserStats.class);
	}

	/**
	 * 获取用户统计数据
	 * @param userId 用户ID
	 * @return
	 */
	public UserStats getUserStatsByUserId(Long userId) {
		return this.coll.find(eq("userId", userId)).first();
	}

	/**
	 * 更新用户统计数据
	 * @param userStats
	 */
	public void saveOrUpdateUserStats(UserStats userStats) {
		Document document = new Document("$set", BsonUtils.toBsonValue(userStats));
		this.coll.updateOne(eq("userId", userStats.getUserId()), document, new UpdateOptions().upsert(true));
	}
}
