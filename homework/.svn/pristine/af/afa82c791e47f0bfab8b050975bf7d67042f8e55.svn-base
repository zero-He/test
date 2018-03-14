package cn.strong.leke.homework.dao.mongo;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.ne;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.homework.dao.mybatis.HomeworkDtlDao;
import cn.strong.leke.homework.model.AnswerInfo;
import cn.strong.leke.homework.model.HomeworkDtlInfo;
import cn.strong.leke.homework.model.QuestionProgress;
import cn.strong.leke.homework.model.WorkDetail;
import cn.strong.leke.homework.model.mongo.WorkStateSingleQuestion;
import cn.strong.leke.model.question.QuestionResult;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

@Repository
public class WorkDetailDao implements InitializingBean {

	private static final String COLL_NAME = "WorkDetail";

	@Autowired
	private MongoDatabase db;
	private MongoCollection<WorkDetail> coll;
	@Autowired
	private MongoDaoConsts mongoDaoConsts;
	@Resource
	private HomeworkDtlDao homeworkDtlDao;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, WorkDetail.class);
	}

	/**
	 * 保存作业信息
	 * @param workDetail
	 */
	public void saveWorkDetail(WorkDetail workDetail) {
		Bson filter = eq("homeworkDtlId", workDetail.getHomeworkDtlId());
		Document set = BsonUtils.toBSON(workDetail);
		set.remove("_id");
		Document update = new Document().append("$set", set);
		this.coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}

	/**
	 * 学生暂存作业更新。
	 * @param homeworkDtlInfo
	 * @param questions
	 */
	public void updateQuestionsByStudentSnapshot(HomeworkDtlInfo homeworkDtlInfo, List<QuestionResult> questions) {
		Bson filter = eq("homeworkDtlId", homeworkDtlInfo.getHomeworkDtlId());
		Document set = new Document().append("questions", BsonUtils.toBsonValue(questions))
				.append("modifiedBy", homeworkDtlInfo.getStudentId()).append("modifiedOn", new Date());
		Document setOnInsert = new Document().append("homeworkDtlId", homeworkDtlInfo.getHomeworkDtlId())
				.append("studentId", homeworkDtlInfo.getStudentId())
				.append("studentName", homeworkDtlInfo.getStudentName())
				.append("homeworkId", homeworkDtlInfo.getHomeworkId())
				.append("createdBy", homeworkDtlInfo.getStudentId()).append("correctCount", 0)
				.append("commentary", null).append("isDeleted", false).append("createdOn", new Date());
		Document update = new Document().append("$set", set).append("$setOnInsert", setOnInsert);
		this.coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}

	/**
	 * 学生提交作业更新。
	 * @param workDetail
	 */
	public void updateQuestionsByStudentSubmit(WorkDetail workDetail) {
		Bson filter = eq("homeworkDtlId", workDetail.getHomeworkDtlId());
		Document set = new Document()
				.append("questionNum", workDetail.getQuestionNum()).append("sheetBookId", workDetail.getSheetBookId())
				.append("questions", BsonUtils.toBsonValue(workDetail.getQuestions()))
				.append("queScores", BsonUtils.toBsonValue(workDetail.getQueScores()))
				.append("knoScores", BsonUtils.toBsonValue(workDetail.getKnoScores()))
				.append("score", workDetail.getScore() != null ? workDetail.getScore().doubleValue() : null)
				.append("scoreRate", workDetail.getScoreRate() != null ? workDetail.getScoreRate().doubleValue() : null)
				.append("submitTime", new Date()).append("correctCount", workDetail.getCorrectCount())
				.append("modifiedBy", workDetail.getStudentId()).append("modifiedOn", new Date());
		Document setOnInsert = new Document().append("homeworkDtlId", workDetail.getHomeworkDtlId())
				.append("studentId", workDetail.getStudentId()).append("studentName", workDetail.getStudentName())
				.append("homeworkId", workDetail.getHomeworkId()).append("commentary", null).append("isDeleted", false)
				.append("createdBy", workDetail.getStudentId()).append("createdOn", new Date());
		Document update = new Document().append("$set", set).append("$setOnInsert", setOnInsert);
		this.coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}

	/**
	 * 批改作业生效时更新。
	 * @param workDetail
	 */
	public void updateQuestionsAsCorrect(WorkDetail workDetail) {
		Bson filter = eq("homeworkDtlId", workDetail.getHomeworkDtlId());
		Document set = new Document()
				.append("questions", BsonUtils.toBsonValue(workDetail.getQuestions()))
				.append("queScores", BsonUtils.toBsonValue(workDetail.getQueScores()))
				.append("knoScores", BsonUtils.toBsonValue(workDetail.getKnoScores()))
				.append("score", workDetail.getScore() != null ? workDetail.getScore().doubleValue() : null)
				.append("scoreRate", workDetail.getScoreRate() != null ? workDetail.getScoreRate().doubleValue() : null)
				.append("commentary", workDetail.getCommentary()).append("correctCount", workDetail.getCorrectCount())
				.append("questionNum", workDetail.getQuestionNum()).append("modifiedBy", workDetail.getModifiedBy())
				.append("modifiedOn", workDetail.getModifiedOn());
		if(workDetail.getAnswerInfos() != null){
			set.append("answerInfos", BsonUtils.toBsonValue(workDetail.getAnswerInfos()));
		}
		Document update = new Document().append("$set", set);
		this.coll.updateOne(filter, update);
	}

	/**
	 * 更新题目数据，返回是否更新成功。
	 * @param homeworkDtlId
	 * @param questions
	 * @param modifiedBy
	 * @return
	 */
	public boolean updateQuestionsAsBugFix(Long homeworkDtlId, List<QuestionResult> questions, Long modifiedBy) {
		Bson filter = eq("homeworkDtlId", homeworkDtlId);
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start().push("$set");
		builder.add("modifiedBy", modifiedBy).add("modifiedOn", new Date())
				.add("questions", BsonUtils.toBsonValue(questions)).add("answerInfos", null);
		Bson update = (BasicDBObject) builder.get();
		UpdateResult result = this.coll.updateOne(filter, update);
		return result.getModifiedCount() > 0;
	}

	/**
	 * 批量批改更新，只更新作业中的一题。
	 * @param workDetail
	 * @param isIncrProgress
	 */
	public void updateQuestionWithBatchCorrect(WorkDetail workDetail, Boolean isIncrProgress) {
		QuestionResult questionResult = workDetail.getQuestions().get(0);
		Bson filter = and(eq("homeworkDtlId", workDetail.getHomeworkDtlId()),
				eq("questions.questionId", questionResult.getQuestionId()));
		Document set = new Document().append("questions.$", BsonUtils.toBsonValue(questionResult))
				.append("modifiedBy", workDetail.getModifiedBy()).append("modifiedOn", new Date());
		Document update = new Document("$set", set);
		if (isIncrProgress) {
			update.append("$inc", new Document("correctCount", 1));
		}
		this.coll.updateOne(filter, update);
	}

	/**
	 * 更新题目数据，返回是否更新成功。
	 * @param homeworkDtlId
	 * @param questions
	 * @param modifiedBy
	 * @return
	 */
	public boolean updateQuestions(Long homeworkDtlId, List<QuestionResult> questions, Long modifiedBy) {
		Bson filter = eq("homeworkDtlId", homeworkDtlId);
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start().push("$set");
		builder.add("modifiedBy", modifiedBy).add("modifiedOn", new Date())
				.add("questions", BsonUtils.toBsonValue(questions));
		Bson update = (BasicDBObject) builder.get();
		UpdateResult result = this.coll.updateOne(filter, update);
		return result.getModifiedCount() > 0;
	}

	/**
	 * 更新作业暂存数据
	 * @param homeworkDtlId 作业ID
	 * @param answerInfos 订正数据
	 */
	public void updateAnswerInfos(Long homeworkDtlId, List<AnswerInfo> answerInfos) {
		Bson filter = eq("homeworkDtlId", homeworkDtlId);
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start().push("$set");
		builder.add("answerInfos", BsonUtils.toBsonValue(answerInfos)).add("modifiedOn", new Date());
		Bson update = (BasicDBObject) builder.get();
		this.coll.updateOne(filter, update);
	}

	/**
	 * 根据作业明细ID获取作业数据。
	 * @param homeworkDtlId
	 * @return
	 */
	public WorkDetail getWorkDetailByHomeworkDtlId(Long homeworkDtlId) {
		return this.coll.find(eq("homeworkDtlId", homeworkDtlId)).first();
	}

	/**
	 * 批量批改查询，只查询作业中的一题
	 * @param homeworkDtlId
	 * @param questionId
	 * @return
	 */
	public WorkDetail getWorkDetailByBatchCorrect(Long homeworkDtlId, Long questionId) {
		Bson filter = eq("homeworkDtlId", homeworkDtlId);
		Bson projection = and(eq("homeworkDtlId", 1), eq("homeworkId", 1), eq("studentId", 1),eq("studentName", 1), eq("correctCount", 1),
				eq("questionNum", 1), elemMatch("questions", eq("questionId", questionId)));
		return this.coll.find(filter).projection(projection).first();
	}

	/**
	 */
	public List<WorkDetail> findWorkDetailByHomeworkDtlIds(List<Long> homeworkDtlIds) {
		return this.coll.find(in("homeworkDtlId", homeworkDtlIds)).into(new ArrayList<WorkDetail>());
	}

	/**
	 * 根据老师作业ID查询作业中试题的批改数量
	 * @param homeworkId 老师作业ID
	 * @return
	 */
	public List<QuestionProgress> findBatchProgressByHomeworkId(Long homeworkId) {
		String mapFunction = this.mongoDaoConsts.getFindBatchProgressByHomeworkId_MAP();
		String reduceFunction = this.mongoDaoConsts.getFindBatchProgressByHomeworkId_REDUCE();
		String finalizeFunction = this.mongoDaoConsts.getFindBatchProgressByHomeworkId_FINALIZE();
		MapReduceIterable<Document> mp = this.coll.mapReduce(mapFunction, reduceFunction, Document.class);
		mp.finalizeFunction(finalizeFunction).filter(and(eq("homeworkId", homeworkId), ne("submitTime", null)));
		List<Document> list = mp.into(new ArrayList<Document>());
		return list.stream().map(doc -> BsonUtils.fromBSON((Document) doc.get("value"), QuestionProgress.class))
				.collect(Collectors.toList());
	}

	/**
	 * 获取批量批改计数
	 * @param homeworkId 作业ID
	 * @param questionId 题目ID
	 * @param cutDate 切断的时间
	 * @return
	 */
	public Integer getBatchCorrectCount(Long homeworkId, Long questionId, Date cutDate) {
		Bson filter = and(eq("homeworkId", homeworkId), lte("submitTime", cutDate),
				elemMatch("questions", and(eq("questionId", questionId), ne("totalIsRight", null))));
		return (int) this.coll.count(filter);
	}

	/**
	 * 获取批量批改下一个
	 * @param homeworkId 作业ID
	 * @param questionId 题目ID
	 * @param cutDate 切断的时间
	 * @return
	 */
	public WorkDetail getBatchCorrectNext(Long homeworkId, Long questionId, Date cutDate) {
		Bson filter = and(eq("homeworkId", homeworkId), lte("submitTime", cutDate),
				elemMatch("questions", and(eq("questionId", questionId), eq("totalIsRight", null))));
		Bson projection = and(eq("homeworkDtlId", 1), eq("studentId", 1), eq("studentName", 1),
				elemMatch("questions", eq("questionId", questionId)));
		return this.coll.find(filter).projection(projection).sort(new BasicDBObject("submitTime",1)).first();
	}
	/**
	 * 获取批量批改提交学生作业id
	 * @param homeworkId 作业ID
	 * @param questionId 题目ID
	 * @param cutDate 切断的时间
	 * @return
	 */
	public List<Long> getBatchSubmitHwDtls(Long homeworkId, Date cutDate) {
		Bson filter = and(eq("homeworkId", homeworkId), lte("submitTime", cutDate));
		Bson projection = eq("homeworkDtlId", 1);
		List<Long> homeworkDtlIds = new ArrayList<Long>();
		FindIterable<WorkDetail> result = this.coll.find(filter).projection(projection).sort(new BasicDBObject("submitTime",1));
		if(result!=null){
			result.map(w->w.getHomeworkDtlId()).into(homeworkDtlIds);
		}
		return homeworkDtlIds;
	}
	
	/**
	 * 查找做错的题目
	 * @param homeworkDtlIds
	 * @return
	 */
	public List<Long> findErrorQuestions(List<Long> homeworkDtlIds){
		Bson filter =and(in("homeworkDtlId", homeworkDtlIds),
				elemMatch("questions",eq("totalIsRight", false)));
		Bson projection = eq("questions", 1);
		List<Long> errorQuestionIds = new ArrayList<Long>();
		FindIterable<WorkDetail> result = this.coll.find(filter).projection(projection);
		if(result!=null){
		  MongoCursor<WorkDetail>  list = result.iterator();
		  list.forEachRemaining(v->{
			  errorQuestionIds.addAll(v.getQuestions().stream()
					 .filter(q-> Boolean.FALSE.equals(q.getTotalIsRight()))
					 .map(q->q.getQuestionId()).collect(Collectors.toList()));
		  });
		}
		return errorQuestionIds;
	}
	
	/**
	 * 是否已经全部批改
	 * @param homeworkId
	 * @param questionId
	 * @param cutDate
	 * @return
	 */
	public boolean IsCorrectAll(Long homeworkId, Long questionId, Date cutDate) {
		Bson filter = and(eq("homeworkId", homeworkId), lte("submitTime", cutDate),
				elemMatch("questions", and(eq("questionId", questionId),eq("totalIsRight", null))));
		Bson projection = eq("homeworkDtlId", 1);
		FindIterable<WorkDetail> result = this.coll.find(filter).projection(projection);
		return result == null || result.first() == null;
	}
	
	/**
	 * 查询统计作业中的题目分析信息。
	 * @param homeworkId 作业ID
	 * @return
	 */
	public List<WorkStateSingleQuestion> findWorkStateSingleQuestion(List<Long> homeworkDtlIds) {
		String key = "1001";
		String map = MapReduceUtils.getMap(key);
		String reduce = MapReduceUtils.getReduce(key);
		String finalize = MapReduceUtils.getFinalize(key);
		Bson filter =Filters.in("homeworkDtlId", homeworkDtlIds);
		MapReduceIterable<Document> mri = this.coll.mapReduce(map, reduce, Document.class);
		return mri.finalizeFunction(finalize).filter(filter).map(object -> {
			Document value = (Document) object.get("value");
			WorkStateSingleQuestion item = new WorkStateSingleQuestion();
			item.setQuestionId((Long) object.get("_id"));
			item.setScoreRate((Double) value.get("scoreRate"));
			return item;
		}).into(new ArrayList<>());
	}
	
	public List<Long> findHwWrongStudent(Long questionId,Long homeworkId){
		Bson filter = eq("homeworkId", homeworkId);
		filter = and(filter,eq("questions.questionId", questionId));
		filter = and(filter,lte("scoreRate", 1));
		List<WorkDetail> list = new ArrayList<WorkDetail>();
		this.coll.find(filter).projection(Projections.include("studentId")).into(list);
		return list.stream().map(v->v.getStudentId()).collect(Collectors.toList());
	}
	
	/**
	 * 检查最近三天提交的作业中，题目是否是待订正状态 
	 * @param questionId
	 * @param stuId
	 * @return
	 */
	public Boolean isExistQuestion(Long questionId, List<Long> homeworkDtlIds) {
		Bson baseFilter = and(in("homeworkDtlId", homeworkDtlIds),
				eq("isDeleted", false));
		Bson bigQueFilter = and(
				baseFilter,
				elemMatch("questions", and(eq("questionId", questionId), eq("totalIsRight", false), ne("isPassedFix", true))));
		Boolean isExist = this.coll.count(bigQueFilter) > 0 ? true : false;
		if(!isExist) {
			Bson subsFilter =  and(
					baseFilter,
					elemMatch("questions", elemMatch("subs", and(eq("questionId", questionId), eq("totalIsRight", false), ne("isPassedFix", true)))));
			isExist = this.coll.count(subsFilter) > 0 ? true : false;
		}
		
		return isExist;
	}

	/**
	 * 根据作业ID获取作业数据详情
	 * @param homeworkId
	 * @return
	 */
	public List<WorkDetail> findWorkDetailByHomeworkId(Long homeworkId) {
		Bson filter = eq("homeworkId", homeworkId);
		return this.coll.find(filter).into(new ArrayList<>());
	}
	
	/**
	 * 判断该题目是否是错题
	 * @param homeworkId
	 * @param studentId
	 * @param questionId
	 * @return
	 */
	public Boolean isWrongQuestion(Long homeworkId,Long studentId,Long questionId) {
		Bson baseFilter = and(eq("homeworkId", homeworkId), eq("studentId", studentId));
		Bson bigQueFilter = and(baseFilter,elemMatch("questions", and(eq("questionId", questionId),eq("totalIsRight", false))));
		Boolean isExist = this.coll.count(bigQueFilter) > 0 ? true : false;
		if(!isExist) {
			Bson subsFilter = and(baseFilter,elemMatch("questions", elemMatch("subs", and(eq("questionId", questionId),eq("totalIsRight", false)))));
			isExist = this.coll.count(subsFilter) > 0 ? true : false;
		}
		return isExist;
	}
}
