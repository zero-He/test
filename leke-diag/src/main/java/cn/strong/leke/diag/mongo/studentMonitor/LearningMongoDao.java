package cn.strong.leke.diag.mongo.studentMonitor;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.diag.model.studentMonitor.LearningBean;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.include;

/**
 * @author Liu.ShiTing
 * @version 1.5
 * @date 2017/11/30 9:39
 */
@Repository
public class LearningMongoDao implements InitializingBean {

	private static final String COLL_NAME = "learn.mission.results";

	@Resource(name = "learnDb")
	private MongoDatabase db;
	private MongoCollection<LearningBean> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, LearningBean.class);
	}

	/**
	 * 根据vo查微课通相关数据
	 * @param vo
	 * @return
	 */
	public List<LearningBean> queryLearningData(RequestVo vo) {
		Bson filter = and(in("userId", vo.getStudentIds()), eq("schoolId", vo.getSchoolId()), gte("start", DateUtils.parseDate(vo.getStartDate())),
				lt("end", DateUtils.parseDate(vo.getEndDate())));

		Bson project = and(include("userId", "start", "end", "learnKlgId", "learnMcId"));
		return this.coll.find(filter).projection(project).into(new ArrayList<>());
	}

	/**
	 * 根据具体的一个学生查微课通数据
	 * @param vo
	 * @return
	 */
	public List<LearningBean> queryLearningDataByStudentId(RequestVo vo) {
		Bson filter = and(eq("userId", vo.getStudentId()), eq("schoolId", vo.getSchoolId()), gte("start", DateUtils.parseDate(vo.getStartDate())),
				lt("end", DateUtils.parseDate(vo.getEndDate())));

		Bson project = and(include("userId", "start", "end", "learnKlgId", "learnMcId"));
		return this.coll.find(filter).projection(project).into(new ArrayList<>());
	}

}
