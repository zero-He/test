package cn.strong.leke.homework.dao.mongo;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.strong.leke.homework.model.RankList;

import com.mongodb.MongoNamespace;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository
public class RankListDao implements InitializingBean {

	private static final String COLL_NAME = "rank.list";

	@Autowired
	private MongoDatabase db;
	private MongoCollection<RankList> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, RankList.class);
	}

	public void renameToBackup(String suffix) {
		this.coll.renameCollection(new MongoNamespace(db.getName(), "rank.list_" + suffix));
	}

	public RankList getRankList(Long dataId, Integer type) {
		return this.coll.find(and(eq("dataId", dataId), eq("type", type))).first();
	}
}
