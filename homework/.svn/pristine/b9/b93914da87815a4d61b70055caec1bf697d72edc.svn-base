package cn.strong.leke.homework.dao.mongo;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.util.List;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.strong.leke.homework.model.HwWrongStudent;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository
public class HwWrongStudentDao implements InitializingBean {

	private static final String COLL_NAME = "homework.wrong.student";

	@Autowired
	private MongoDatabase db;
	private MongoCollection<HwWrongStudent> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, HwWrongStudent.class);
	}

	public void saveHwWrongStudent(HwWrongStudent hwWrongStudent) {
		this.coll.insertOne(hwWrongStudent);
	}

	public List<Long> findWrongStudent(Long homeworkId, Long questionId) {
		Bson filter = and(eq("homeworkId", homeworkId), eq("questionId", questionId));
		HwWrongStudent hwWrongStudent = this.coll.find(filter).first();
		return hwWrongStudent.getStudents();
	}
}
