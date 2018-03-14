package cn.strong.leke.homework.dao.mongo;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.homework.model.UserRank;

import com.mongodb.MongoNamespace;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository
public class UserRankDao implements InitializingBean {

	private static final String COLL_NAME = "user.rank";

	@Autowired
	private MongoDatabase db;
	private MongoCollection<UserRank> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, UserRank.class);
	}

	/**
	 * 重命名并备份数据
	 * @param suffix
	 */
	public void renameToBackup(String suffix) {
		this.coll.renameCollection(new MongoNamespace(db.getName(), "user.rank_" + suffix));
	}

	/**
	 * 获取用户排名数据
	 * @param userId 用户ID
	 * @return
	 */
	public UserRank getUserRankByUserId(Long userId) {
		return this.coll.find(eq("userId", userId)).first();
	}

	/**
	 * 更新用户排名信息
	 * @param userRank
	 */
	public void updateUserRank(UserRank userRank) {
		coll.updateOne(eq("_id", new ObjectId(userRank.getId())), new Document("$set", BsonUtils.toBsonValue(userRank)));
	}
}
