package cn.strong.leke.homework.dao.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

/**
 * 已练习的学科题目信息
 * @author Zhang Fujun
 * @date 2016年11月28日
 */
@Repository
public class ExerciseRecordQuestionDao implements InitializingBean {

	private static final String COLL_NAME = "exercise.record.question";

	@Autowired
	private MongoDatabase db;
	
	private MongoCollection<Document> coll;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME);
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> findRecordQuestion(Long userId,Long subjectId) {
		Document document= this.coll.find(eq("_id", userId)).first();
		if(document != null){
			return (List<Long>) document.get(subjectId.toString());
		}
		return null;
	}
	
	public void saveExerciseQuestion(Long userId,Long subjectId,List<Long> questionsIds) {
		Bson filter = eq("_id", userId);
		Document set = new Document();
		set.append("modifiedBy", userId).append(subjectId.toString(), questionsIds);
		Document setOnInsert = new Document().append("userId", userId).append("isDeleted", false)
				.append("createdBy", userId).append("createdOn", new Date());
		Document update = new Document().append("$set", set).append("$setOnInsert", setOnInsert);
		this.coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}
	
}
