package cn.strong.leke.diag.dao.homework.mongo;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.elemMatch;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.set;

import java.util.List;

import javax.annotation.Resource;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.diag.model.WorkStats;
import cn.strong.leke.diag.model.WorkStats.Named;

@Repository
public class WorkStatsDao implements InitializingBean {

	private static final String COLL_NAME = "WorkStats";

	@Resource(name = "homeworkDb")
	private MongoDatabase db;
	private MongoCollection<WorkStats> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, WorkStats.class);
	}

	public void saveOrUpdateWorkStats(WorkStats workStats) {
		Bson filter = eq("homeworkId", workStats.getHomeworkId());
		Bson update = BsonUtils.toUpdateSetDoc(workStats);
		this.coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}

	public void updateSumsByHomeworkId(Long homeworkId, List<WorkStats.Sum> sums) {
		Bson filter = eq("homeworkId", homeworkId);
		Bson update = set("sums", BsonUtils.toBsonValue(sums));
		this.coll.updateOne(filter, update);
	}

	/**
	 * 查询作业的统计信息。
	 * @param homeworkId 作业ID
	 * @return
	 */
	public WorkStats getWorkStatsByHomeworkId(Long homeworkId) {
		Bson filter = eq("homeworkId", homeworkId);
		Bson project = include("sums", "charts");
		return this.coll.find(filter).projection(project).first();
	}

	/**
	 * 查询某个作业下题目的名单信息。
	 * @param homeworkId 作业ID
	 * @param questionId 题目ID
	 * @return
	 */
	public Named getNamedByHomeworkIdAndQuestionId(Long homeworkId, Long questionId) {
		Bson filter = eq("homeworkId", homeworkId);
		Bson project = elemMatch("nameds", eq("questionId", questionId));
		WorkStats workStats = this.coll.find(filter).projection(project).first();
		if (workStats != null && CollectionUtils.isNotEmpty(workStats.getNameds())) {
			return workStats.getNameds().get(0);
		}
		return null;
	}
}
