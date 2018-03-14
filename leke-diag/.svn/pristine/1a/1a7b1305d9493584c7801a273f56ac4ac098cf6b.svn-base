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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CountOptions;

import cn.strong.leke.data.mongo.MongoPageUtils;
import cn.strong.leke.diag.model.report.ReportCycle;
import cn.strong.leke.diag.model.teachingMonitor.BeikeUnitCommit;
import cn.strong.leke.diag.model.teachingMonitor.BeikeUnitText;
import cn.strong.leke.diag.model.teachingMonitor.LessonBeikePkg;
import cn.strong.leke.diag.model.teachingMonitor.OnlineUserDevice;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.lessonlog.model.LessonBehavior;
import cn.strong.leke.lessonlog.model.StudentBehavior;

@Repository
public class MonitorMongoDao implements InitializingBean {

	private static final String ONLINE_USER_DEVICE = "onlineuser.device";

	@Resource(name = "monitorDb")
	private MongoDatabase db;
	
	private MongoCollection<OnlineUserDevice> onlineUserDevice;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.onlineUserDevice = db.getCollection(ONLINE_USER_DEVICE, OnlineUserDevice.class);
	}
	
	public Integer getOnlineUserCount(Long schoolId, List<Long> userIdList, Date date, Long roleId){
		Bson filter = and(eq("schoolId", schoolId), eq("roleId", roleId), in("userId", userIdList), gte("ts", date));
		List<Long> onlineUserList = this.onlineUserDevice.distinct("userId", filter, Long.class).into(new ArrayList<Long>());
		return onlineUserList.size();
	}
	
}