package cn.strong.leke.homework.dao.mongo.activities;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cn.strong.leke.homework.model.activities.AccessRecord;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;

/**
 * 国庆活动数据需求：
 * 学生访问活动页面人数
 * 任务一学生参与人数、完成人数、作业订正总数；
 * 任务二学生参与人数、完成人数、良好人数、优秀人数、消灭错题份数、优秀份数、良好份数；
 * 任务三学生参与人数、完成人数、自主练习完成份数、平均正确率
 * @author Administrator
 *
 */
@Repository
public class AccessRecordDao {
	@Resource
	private MongoDatabase db;
	private MongoCollection<AccessRecord> coll;

	@PostConstruct
	public void afterPropertiesSet() {
		Assert.notNull(db);
		coll = db.getCollection("howework.activities.AccessRecord", AccessRecord.class);
	}
	
	public void insert(AccessRecord accessRecord){
		accessRecord.setId(ObjectId.get().toHexString());
		coll.insertOne(accessRecord);
	}
	
	public List<Map<String, Object>> find(){
		Bson project = Aggregates.project(and(eq("_id", 0), eq("date", 1)));
		Bson group = Aggregates.group(eq("date", "$date"), sum("countNum", 1));
		AggregateIterable<Document> aggregate = this.coll.aggregate(Arrays.asList(project, group),
				Document.class);
		List<Map<String, Object>> list = new ArrayList<>();
		Iterator<Document> iter = aggregate.iterator();
		while (iter.hasNext()) {
			Document item = iter.next();
			Map<String, Object> map = new HashMap<>();
			map.put("date", ((Document)item.get("_id")).get("date"));
			map.put("countNum", item.get("countNum"));
			list.add(map);
		}
		return list;
	}
	
}
