package cn.strong.leke.homework.dao.mongo;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.strong.leke.homework.model.HomeworkPaper;
import cn.strong.leke.model.paper.ScoredQuestion;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

@Repository
public class HomeworkPaperDao implements InitializingBean {

	private static final String COLL_NAME = "homework.paper";

	@Autowired
	private MongoDatabase db;
	private MongoCollection<HomeworkPaper> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, HomeworkPaper.class);
	}

	public HomeworkPaper getByPaperId(String paperId) {
		return this.coll.find(eq("_id", new ObjectId(paperId))).first();
	}

	public String insertOne(HomeworkPaper homeworkPaper) {
		ObjectId objectId = new ObjectId();
		homeworkPaper.setPaperId(objectId.toString());
		this.coll.insertOne(homeworkPaper);
		return objectId.toString();
	}

	/**
	 * 通过试卷ID获取试卷题数
	 * @param paperIds
	 * @return
	 */
	public Map<String, Integer> findPaperQuestionNums(List<String> paperIds) {
		Bson filter = in("_id", paperIds.stream().map(ObjectId::new).collect(toList()));
		List<HomeworkPaper> papers = this.coll.find(filter).into(new ArrayList<>());
		return papers.stream().collect(toMap(HomeworkPaper::getPaperId, v ->v.getPaperDetail().getQuestionNum()));
	}

	public List<Long> findQuestionIdByPaperIds(List<String> paperIds) {
		Bson filter = in("_id", paperIds.stream().map(ObjectId::new).collect(toList()));
		FindIterable<HomeworkPaper> result = this.coll.find(filter);
		List<Long> questionIds = new ArrayList<Long>();
		if (result != null) {
			MongoCursor<HomeworkPaper> datas = result.iterator();
			datas.forEachRemaining(v -> {
				v.getPaperDetail().getGroups().forEach(q -> {
					questionIds.addAll(q.getQuestions().stream().map(ScoredQuestion::getQuestionId).collect(toList()));
				});
			});
		}
		return questionIds;
	}

}
