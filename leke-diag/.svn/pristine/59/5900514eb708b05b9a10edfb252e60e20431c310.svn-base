package cn.strong.leke.diag.mongo.studentMonitor;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.diag.model.studentMonitor.ActiveLearningBean;
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
 * @author LIU.SHITING
 * @version 1.5
 * @date 2017-11-25 16:28:15
 */
@Repository
public class ActiveMongoDao implements InitializingBean {

	private static final String COLL_NAME = "exercise";

	@Resource(name = "homeworkDb")
	private MongoDatabase db;
	private MongoCollection<ActiveLearningBean> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, ActiveLearningBean.class);
	}

	/**
	 * 根据vo查自主学习相关数据
	 * @param vo
	 * @return
	 */
	public List<ActiveLearningBean> queryActiveLearningData(RequestVo vo) {
		Bson filter = and(in("studentId", vo.getStudentIds()), eq("schoolId", vo.getSchoolId()), eq("status", 1), eq("submitState", 1), eq("isDeleted", false),
				gte("submitTime", DateUtils.parseDate(vo.getStartDate()).getTime()), lt("submitTime", DateUtils.parseDate(vo.getEndDate()).getTime()));
		if (vo.getSubjectId() != null) {
			filter = and(filter, eq("subjectId", vo.getSubjectId()));
		}
		Bson project = and(include("studentId", "totalNum", "rightNum", "relIds", "submitTime"));
		return this.coll.find(filter).projection(project).into(new ArrayList<>());
	}

	/**
	 * 根据具体的一个学生查
	 * @param vo
	 * @return
	 */
	public List<ActiveLearningBean> queryActiveLearningDataByStudentId(RequestVo vo) {
		Bson filter = and(eq("studentId", vo.getStudentId()), eq("schoolId", vo.getSchoolId()), eq("status", 1), eq("submitState", 1), eq("isDeleted", false),
				gte("submitTime", DateUtils.parseDate(vo.getStartDate()).getTime()), lt("submitTime", DateUtils.parseDate(vo.getEndDate()).getTime()));
		if (vo.getSubjectId() != null) {
			filter = and(filter, eq("subjectId", vo.getSubjectId()));
		}
		Bson project = and(include("studentId", "totalNum", "rightNum", "relIds", "submitTime"));
		return this.coll.find(filter).projection(project).into(new ArrayList<>());
	}

}
