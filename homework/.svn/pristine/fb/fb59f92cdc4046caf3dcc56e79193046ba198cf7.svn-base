package cn.strong.leke.homework.dao.mongo;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Updates.inc;
import static com.mongodb.client.model.Updates.push;
import static com.mongodb.client.model.Updates.set;

import java.util.Date;
import java.util.List;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.IdentityUtils;
import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.data.mongo.MongoPageUtils;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.model.SheetTask;

@Repository
public class SheetTaskDao implements InitializingBean {

	private static final String COLL_NAME = "sheet.task";

	@Autowired
	private MongoDatabase db;
	private MongoCollection<SheetTask> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, SheetTask.class);
	}

	/**
	 * 保存上传任务
	 * @param sheetTask
	 */
	public void insertSheetTask(SheetTask sheetTask) {
		sheetTask.setId(IdentityUtils.uuid2());
		this.coll.insertOne(sheetTask);
	}

	/**
	 * 更新上传任务
	 * @param sheetTask
	 */
	public void updateSheetTask(SheetTask sheetTask) {
		Bson filter = eq("_id", sheetTask.getId());
		Bson update = BsonUtils.toUpdateSetDoc(sheetTask);
		this.coll.updateOne(filter, update);
	}

	/**
	 * 更新任务执行阶段
	 * @param taskId
	 * @param step
	 */
	public void updateTaskStep(String taskId, Integer step) {
		Bson filter = eq("_id", taskId);
		Bson update = and(set("step", step), push("historys", and(eq("step", step), eq("ts", new Date()))));
		this.coll.updateOne(filter, update);
	}

	/**
	 * 递增有效卷数量
	 * @param taskId
	 */
	public void incrValidBookNum(String taskId) {
		Bson filter = eq("_id", taskId);
		Bson update = inc("validBookNum", 1);
		this.coll.updateOne(filter, update);
	}

	/**
	 * 获取任务信息
	 * @param taskId
	 * @return
	 */
	public SheetTask getSheetTaskById(String taskId) {
		Bson filter = eq("_id", taskId);
		return this.coll.find(filter).first();
	}

	/**
	 * 上传任务列表查询
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @return
	 */
	public List<SheetTask> findSheetTaskByQuery(Long userId, Date startDate, Date endDate, Page page) {
		Bson filter = eq("createdBy", userId);
		if (startDate != null) {
			filter = and(filter, gte("createdOn", startDate));
		}
		if (endDate != null) {
			filter = and(filter, lt("createdOn", DateUtils.addDays(endDate, 1)));
		}
		Bson sorter = descending("createdOn");
		return MongoPageUtils.find(coll, filter, sorter, page);
	}
}
