package cn.strong.leke.diag.mongo.teachingMonitor;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.nin;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Projections.elemMatch;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;

import cn.strong.leke.data.mongo.MongoPageUtils;
import cn.strong.leke.diag.model.report.ReportCycle;
import cn.strong.leke.diag.model.teachingMonitor.BeikeUnitCommit;
import cn.strong.leke.diag.model.teachingMonitor.BeikeUnitText;
import cn.strong.leke.diag.model.teachingMonitor.LessonBeikePkg;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.lessonlog.model.LessonBehavior;
import cn.strong.leke.lessonlog.model.StudentBehavior;

@Repository
public class LessonBeikeMongoDao implements InitializingBean {

	private static final String LESSON_BEIKE_PKG = "lesson.beikePkg";
	private static final String BEIKE_UNIT_COMMIT = "beikeUnit.commit";
	private static final String BEIKE_UNIT_TEXT = "beikeUnit.text";

	@Resource(name = "beikeDb")
	private MongoDatabase db;
	
	private MongoCollection<LessonBeikePkg> lessonBeikePkg;
	
	private MongoCollection<BeikeUnitCommit> beikeUnitCommit;
	
	private MongoCollection<BeikeUnitText> beikeUnitText;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.lessonBeikePkg = db.getCollection(LESSON_BEIKE_PKG, LessonBeikePkg.class);
		this.beikeUnitCommit = db.getCollection(BEIKE_UNIT_COMMIT, BeikeUnitCommit.class);
		this.beikeUnitText = db.getCollection(BEIKE_UNIT_TEXT, BeikeUnitText.class);
	}
	
	/**
	 * 根据单科ID查询课堂最早备课时间
	 * @param courseSingleId
	 * @return
	 */
	public Date getLessonBeikeMinDateByLessonId(Long courseSingleId){
		Bson filter = eq("lessonId", courseSingleId);
		Bson project = include("createdOn");
		Bson sortAsc = ascending("createdOn");
		LessonBeikePkg beikePkg = this.lessonBeikePkg.find(filter).sort(sortAsc).projection(project).first();
		return null == beikePkg ? null : beikePkg.getCreatedOn();
	}
	
	/**
	 * 根据多个单科ID查询课堂最早备课时间
	 * @param courseSingleId
	 * @return
	 */
	public List<LessonBeikePkg> getLessonBeikeMinDateByLessonIds(List<Long> courseSingleIds){
		List<Bson> pipeline = new ArrayList<Bson>();
		List<LessonBeikePkg> lessonBeikePkgList = new ArrayList<>();
		if(courseSingleIds.isEmpty()){
			return lessonBeikePkgList;
		}
		pipeline.add(Aggregates.match(in("lessonId", courseSingleIds)));
		pipeline.add(Aggregates.group("$lessonId", Accumulators.min("createdOn", "$createdOn")));
		pipeline.add(Aggregates.project(and(eq("lessonId", "$_id"),eq("createdOn", 1))));
		this.lessonBeikePkg.aggregate(pipeline).into(lessonBeikePkgList);
		return lessonBeikePkgList;
	}
	
	/**
	 * 根据单科ID查询备课包
	 * @param courseSingleId
	 * @return
	 */
	public List<LessonBeikePkg> findLessonBeikePkgListByLessonId(Long courseSingleId){
		Bson filter = eq("lessonId", courseSingleId);
		return this.lessonBeikePkg.find(filter).into(new ArrayList<LessonBeikePkg>());
	}
	
	/**
	 * 根据备课单元提交ID查询备课提交单元
	 * @param commitId
	 * @return
	 */
	public BeikeUnitCommit findBeikeUnitCommitByCommitId(String commitId){
		Bson filter = eq("_id", new ObjectId(commitId));
		return this.beikeUnitCommit.find(filter).first();
	}
	
	/**
	 * 根据备课单元文本ID判断文本内容是否为空
	 * @param id
	 * @return
	 */
	public boolean hasContentForBeikeUnitText(String id){
		Bson filter = and(eq("_id", new ObjectId(id)), nin("content", null, ""));
		return this.beikeUnitText.find(filter).first() == null ? false : true;
	}
	
}