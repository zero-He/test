package cn.strong.leke.homework.dao.mongo;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.strong.leke.homework.model.mongo.HwQueCommentary;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository
public class HwQueCommentaryDao implements InitializingBean {
	@Autowired
	private MongoDatabase db;
	private MongoCollection<HwQueCommentary> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection("homework.question.commentary", HwQueCommentary.class);
	}

	/**
	 * 新增 作业题目批注
	 * @param commentary
	 */
	public void saveCommentary(HwQueCommentary commentary) {
		this.coll.insertOne(commentary);
	}

	/**
	 * 移除 作业题目批注
	 * @param homeworkId
	 * @param questionId
	 */
	public void removeCommentary(Long homeworkId, Long questionId, Long resId, Long modifiedBy) {
		Bson filter = and(eq("homeworkId", homeworkId), eq("questionId", questionId), eq("resId", resId));
		Bson set = new Document().append("type", HwQueCommentary.WAY_PERSONAL)
				.append("excludeUserIds", Collections.emptySet())
				.append("modifiedBy", modifiedBy)
				.append("modifiedOn", new Date());
		Bson update = new Document().append("$set", set);
		this.coll.updateOne(filter, update);
	}
	/**
	 * 添加某个学生 能看微课 作业题目批注
	 * @param homeworkId
	 * @param questionId
	 */
	public void pushIncludeUserIds(Long homeworkId, Long questionId, Long resId, Long modifiedBy, Long userId) {
		Bson filter = and(eq("homeworkId", homeworkId), eq("questionId", questionId), eq("resId", resId),ne("includeUserIds", userId));
		Bson set = new Document().append("modifiedBy", modifiedBy)
				.append("modifiedOn", new Date());
		Bson update = new Document().append("$set", set).append("$push", new Document().append("includeUserIds", userId));
		this.coll.updateOne(filter, update);
	}

	/**
	 * 获取 一个作业题目批注
	 * @param homeworkId
	 * @param questionId
	 * @return
	 */
	public HwQueCommentary getSingle(Long homeworkId, Long questionId, Long resId) {
		Bson filter = and(eq("homeworkId", homeworkId), eq("questionId", questionId), eq("resId", resId),
				eq("isDeleted", false));
		return this.coll.find(filter).first();
	}
	
	/**
	 * 获取 某个作业题目批注的所有资源信息 
	 * @param homeworkId
	 * @param questionId 可为null
	 * @return
	 */
	public List<HwQueCommentary> findCommentaries(Long homeworkId, Long questionId) {
		Bson filter = and(eq("homeworkId", homeworkId));
		if (questionId != null) {
			filter = and(filter, eq("questionId", questionId));
		}
		filter = and(filter, eq("isDeleted", false));
		List<HwQueCommentary> list = new ArrayList<>();
		this.coll.find(filter).into(list);
		return list;
	}

	/**
	 * 更新 一个作业题目批注
	 * @param commentary
	 */
	public void updateCommentary(HwQueCommentary commentary) {
		Bson filter = eq("_id", new ObjectId(commentary.getId()));
		Bson set = new Document().append("type", commentary.getType())
				.append("includeUserIds", commentary.getIncludeUserIds())
				.append("excludeUserIds", commentary.getExcludeUserIds());
		Bson update = new Document().append("$set", set);
		this.coll.updateOne(filter, update);
	}

}
