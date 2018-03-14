package cn.strong.leke.diag.dao.homework.mongo;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.include;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cn.strong.leke.diag.model.UserCount;

@Repository
public class ExerciseDao implements InitializingBean {

	private static final String COLL_NAME = "exercise";

	@Resource(name = "homeworkDb")
	private MongoDatabase db;
	private MongoCollection<Document> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, Document.class);
	}

	/**
	 * 获取学生一段时间内的练习统计
	 * @param studentId
	 * @param start
	 * @param end
	 * @return
	 */
	public Integer getStudentPracticeNumByTime(Long studentId, Date start, Date end) {
		Bson match = match(and(eq("studentId", studentId), eq("status", 1), eq("isDeleted", false),
				gt("submitTime", start.getTime()), lt("submitTime", end.getTime())));
		Bson project = project(and(include("totalNum"), exclude("_id")));
		Bson group = group(null, sum("totalNum", "$totalNum"));

		List<Bson> pipeline = Arrays.asList(match, project, group);
		AggregateIterable<Document> aggregate = this.coll.aggregate(pipeline);
		Document document = aggregate.first();
		if (document != null) {
			return document.getInteger("totalNum");
		}
		return 0;
	}

	/**
	 * 获取班级中学生一段时间内的练习统计
	 * @param userIds
	 * @param subjectId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<UserCount> findStudentPracticeNumByTime(List<Long> userIds, Long subjectId, Date start, Date end) {
		Bson match = match(and(in("studentId", userIds), eq("subjectId", subjectId), eq("status", 1),
				eq("isDeleted", false), gt("submitTime", start.getTime()), lt("submitTime", end.getTime())));
		Bson project = project(and(include("studentId", "totalNum"), exclude("_id")));
		Bson group = group("$studentId", sum("totalNum", "$totalNum"));

		List<Bson> pipeline = Arrays.asList(match, project, group);
		AggregateIterable<Document> aggregate = this.coll.aggregate(pipeline);
		return aggregate.map(doc -> {
			UserCount count = new UserCount();
			count.setUserId(doc.getLong("_id"));
			count.setNum(doc.getInteger("totalNum"));
			return count;
		}).into(new ArrayList<>());
	}
}
