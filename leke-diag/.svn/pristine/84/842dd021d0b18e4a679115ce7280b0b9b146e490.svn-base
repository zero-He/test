package cn.strong.leke.diag.dao.homework.mongo;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Projections.include;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.diag.model.ClassStatsDto;
import cn.strong.leke.diag.model.GradeStatsDto;
import cn.strong.leke.diag.model.SubjStatsDto;
import cn.strong.leke.diag.model.UserStats;
import cn.strong.leke.diag.model.UserStatsDto;
import cn.strong.leke.diag.util.MapReduceUtils;

@Repository
public class UserStatsDao implements InitializingBean {

	private static final String COLL_NAME = "UserStats";

	@Resource(name = "homeworkDb")
	private MongoDatabase db;
	private MongoCollection<UserStats> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, UserStats.class);
	}

	/**
	 * 更新用户统计数据
	 * @param userStats
	 */
	public void saveOrUpdateUserStats(UserStats userStats) {
		Bson filter = eq("userId", userStats.getUserId());
		Bson update = BsonUtils.toUpdateSetDoc(userStats);
		this.coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}

	/**
	 * 获取用户统计数据
	 * @param userId 用户ID
	 * @return
	 */
	public UserStats getUserStatsByUserId(Long userId) {
		Bson filter = eq("userId", userId);
		return this.coll.find(filter).first();
	}

	/**
	 * 查询班级下学生的成绩信息
	 * @param classId 班级ID
	 * @return
	 */
	public List<UserStats> findClassUserStats(Long classId) {
		Bson filter = eq("classId", classId);
		Bson project = include("userId", "userName", "scores");
		return this.coll.find(filter).projection(project).into(new ArrayList<>());
	}

	/**
	 * 查询学校勤奋报告。
	 * @param schoolId
	 * @return
	 */
	public List<GradeStatsDto> findSchoolDiligent(Long schoolId) {
		String key = "1002";
		String map = MapReduceUtils.getMap(key);
		String reduce = MapReduceUtils.getReduce(key);
		String finalize = MapReduceUtils.getFinalize(key);
		Bson filter = and(eq("schoolId", schoolId), gt("totalNum", 0));
		MapReduceIterable<Document> mri = this.coll.mapReduce(map, reduce, Document.class);
		return mri.finalizeFunction(finalize).filter(filter).map(object -> {
			Document value = (Document) object.get("value");
			GradeStatsDto dto = new GradeStatsDto();
			dto.setGradeId((Long) object.get("_id"));
			dto.setStatNum(((Number) value.get("statNum")).intValue());
			dto.setSubmitRate((Double) value.get("submitRate"));
			dto.setDelayRate((Double) value.get("delayRate"));
			dto.setUndoneRate(100 - dto.getSubmitRate());
			return dto;
		}).into(new ArrayList<>());
	}

	/**
	 * 年级勤奋报告。
	 * @param schoolId 学校ID
	 * @param gradeId 年级ID
	 * @return
	 */
	public List<ClassStatsDto> findGradeDiligent(Long schoolId, Long gradeId) {
		String key = "1003";
		String map = MapReduceUtils.getMap(key);
		String reduce = MapReduceUtils.getReduce(key);
		String finalize = MapReduceUtils.getFinalize(key);
		Bson filter = and(eq("schoolId", schoolId), eq("gradeId", gradeId), gt("totalNum", 0));
		MapReduceIterable<Document> mri = this.coll.mapReduce(map, reduce, Document.class);
		return mri.finalizeFunction(finalize).filter(filter).map(object -> {
			Document value = (Document) object.get("value");
			ClassStatsDto dto = new ClassStatsDto();
			dto.setClassId((Long) object.get("_id"));
			dto.setStatNum(((Number) value.get("statNum")).intValue());
			dto.setSubmitRate((Double) value.get("submitRate"));
			dto.setDelayRate((Double) value.get("delayRate"));
			dto.setUndoneRate(100 - dto.getSubmitRate());
			return dto;
		}).into(new ArrayList<>());
	}

	/**
	 * 查询班级勤奋报告。
	 * @param classId 班级ID
	 * @return
	 */
	public List<UserStatsDto> findClassDillgent(Long classId) {
		Bson filter = eq("classId", classId);
		Bson project = include("userId", "userName", "normalNum", "delayNum", "totalNum");
		return this.coll.find(filter).projection(project).map(stats -> {
			UserStatsDto dto = new UserStatsDto();
			dto.setUserId(stats.getUserId());
			dto.setUserName(stats.getUserName());
			dto.setStatNum(stats.getTotalNum());
			if (stats.getTotalNum() > 0) {
				double totalNum = stats.getTotalNum();
				dto.setSubmitRate((stats.getNormalNum() + stats.getDelayNum()) * 100 / totalNum);
				dto.setDelayRate(stats.getDelayNum() * 100 / totalNum);
				dto.setUndoneRate(100 - dto.getSubmitRate());
			}
			return dto;
		}).into(new ArrayList<>());
	}

	/**
	 * 年级成绩分析
	 * @param schoolId 学校ID
	 * @param gradeId 年级ID
	 * @return
	 */
	public List<SubjStatsDto> findGradeSubjScore(Long schoolId, Long gradeId) {
		String key = "1004";
		String map = MapReduceUtils.getMap(key);
		String reduce = MapReduceUtils.getReduce(key);
		String finalize = MapReduceUtils.getFinalize(key);
		Bson filter = and(eq("schoolId", schoolId), eq("gradeId", gradeId), gt("scores.totalNum", 0));
		MapReduceIterable<Document> mri = this.coll.mapReduce(map, reduce, Document.class);
		return mri.finalizeFunction(finalize).filter(filter).map(object -> {
			Document value = (Document) object.get("value");
			SubjStatsDto dto = new SubjStatsDto();
			dto.setSubjectId((Long) object.get("_id"));
			dto.setStatNum(((Number) value.get("statNum")).intValue());
			dto.setMaxScore((Double) value.get("maxScore"));
			dto.setAvgScore((Double) value.get("avgScore"));
			return dto;
		}).into(new ArrayList<>());
	}

	/**
	 * 查询年级下各班级某一学科的得分统计。
	 * @param schoolId 学校ID
	 * @param gradeId 年级ID
	 * @param subjectId 学科ID
	 * @return
	 */
	public List<ClassStatsDto> findGradeClassScore(Long schoolId, Long gradeId, Long subjectId) {
		String key = "1005";
		String map = MapReduceUtils.getMap(key);
		String reduce = MapReduceUtils.getReduce(key);
		String finalize = MapReduceUtils.getFinalize(key);
		map = map.replace("##subjectId##", String.valueOf(subjectId));
		Bson filter = and(eq("schoolId", schoolId), eq("gradeId", gradeId),
				elemMatch("scores", and(eq("subjectId", subjectId), gt("totalNum", 0))));
		MapReduceIterable<Document> mri = this.coll.mapReduce(map, reduce, Document.class);
		return mri.finalizeFunction(finalize).filter(filter).map(object -> {
			Document value = (Document) object.get("value");
			ClassStatsDto dto = new ClassStatsDto();
			dto.setClassId((Long) object.get("_id"));
			dto.setStatNum(((Number) value.get("statNum")).intValue());
			dto.setAvgScore((Double) value.get("avgScore"));
			return dto;
		}).into(new ArrayList<>());
	}

	/**
	 * 班级成绩分析(学科视角)
	 * @param classId 班级ID
	 * @return
	 */
	public List<SubjStatsDto> findClassSubjScore(Long classId) {
		String key = "1004";
		String map = MapReduceUtils.getMap(key);
		String reduce = MapReduceUtils.getReduce(key);
		String finalize = MapReduceUtils.getFinalize(key);
		Bson filter = and(eq("classId", classId), gt("scores.totalNum", 0));
		MapReduceIterable<Document> mri = this.coll.mapReduce(map, reduce, Document.class);
		return mri.finalizeFunction(finalize).filter(filter).map(object -> {
			Document value = (Document) object.get("value");
			SubjStatsDto dto = new SubjStatsDto();
			dto.setSubjectId((Long) object.get("_id"));
			dto.setStatNum(((Number) value.get("statNum")).intValue());
			dto.setMaxScore((Double) value.get("maxScore"));
			dto.setAvgScore((Double) value.get("avgScore"));
			return dto;
		}).into(new ArrayList<>());
	}

	/**
	 * 班级成绩分析(学生视角)
	 * @param classId 班级ID
	 * @param subjectId 学科ID
	 * @return
	 */
	public List<UserStatsDto> findClassUserScore(Long classId, Long subjectId) {
		Bson filter = and(eq("classId", classId), elemMatch("scores", eq("subjectId", subjectId)));
		Bson project = include("userId", "userName", "scores");
		return this.coll.find(filter).projection(project).map(stats -> {
			UserStatsDto dto = new UserStatsDto();
			dto.setUserId(stats.getUserId());
			dto.setUserName(stats.getUserName());
			dto.setStatNum(0);
			stats.getScores().stream().filter(score -> {
				return score.getSubjectId().equals(subjectId);
			}).findFirst().ifPresent(score -> {
				dto.setStatNum(score.getTotalNum());
				dto.setMaxScore(score.getMaxScore());
				dto.setAvgScore(score.getTotalScore() / score.getTotalNum());
			});
			return dto;
		}).into(new ArrayList<>());
	}
}
