package cn.strong.leke.diag.mongo.teachingMonitor;

import cn.strong.leke.diag.model.teachingMonitor.resource.ResourceBean;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.include;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-23 11:02:56
 */
@Repository
public class ResourceMongoDao implements InitializingBean {

	private static final String COLL_NAME = "teachingMonitor.resource";

	@Resource(name = "diagDb")
	private MongoDatabase db;
	private MongoCollection<ResourceBean> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, ResourceBean.class);
	}

	/**
	 * 把汇总好的数据写入到mongo中
	 * @param collectList
	 */
	public void insertResourceCollectData(List<ResourceBean> collectList) {
		collectList.forEach(c -> c.setId(new ObjectId().toHexString()));
		this.coll.insertMany(collectList);
	}

	/**
	 * 根据teacherIds、schoolId、subjectId、startTime、endTime查对应的list数据
	 * @param teacherIds
	 * @param subjectId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<ResourceBean> queryResourceBeanList(List<Long> teacherIds, Long schoolId, Long subjectId, Date startTime, Date endTime) {
		Bson filter = and(in("createdBy", teacherIds), eq("schoolId", schoolId), gte("startTime", startTime), lte("endTime", endTime), ne("createdBy", null));
		if (subjectId != null) {
			filter = and(filter, eq("subjectId", subjectId));
		}
		Bson project = and(include("resType", "type", "schoolId", "schoolStageId", "schoolStageName", "subjectId", "subjectName", "createdBy", "createdName", "createCount", "startTime", "endTime", "createTime"));
		return this.coll.find(filter).projection(project).into(new ArrayList<>());
	}

}
