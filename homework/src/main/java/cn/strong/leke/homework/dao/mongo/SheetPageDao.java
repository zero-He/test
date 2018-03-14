package cn.strong.leke.homework.dao.mongo;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Sorts.ascending;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.homework.model.SheetErr;
import cn.strong.leke.homework.model.SheetPage;

@Repository
public class SheetPageDao implements InitializingBean {

	private static final String COLL_NAME = "sheet.page";

	@Autowired
	private MongoDatabase db;
	private MongoCollection<SheetPage> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, SheetPage.class);
	}

	/**
	 * 保存答题卡页
	 * @param sheetPages
	 */
	public void insertSheetPages(List<SheetPage> sheetPages) {
		sheetPages.forEach(v -> v.setId(new ObjectId().toHexString()));
		this.coll.insertMany(sheetPages);
	}

	/**
	 * 更新答题卡页错误
	 * @param errorPages
	 */
	public void updateSheetPage(SheetPage sheetPage) {
		Bson filter = eq("_id", new ObjectId(sheetPage.getId()));
		Bson update = BsonUtils.toUpdateSetDoc(sheetPage);
		this.coll.updateOne(filter, update);
	}

	/**
	 * 更新答题卡页错误
	 * @param errorPages
	 */
	public void updateSheetPages(List<SheetPage> sheetPages) {
		for (SheetPage sheetPage : sheetPages) {
			this.updateSheetPage(sheetPage);
		}
	}

	/**
	 * 根据答题卡页ID，查询答题卡页详情
	 * @param pageId
	 * @return
	 */
	public SheetPage getSheetPageById(String pageId) {
		Bson filter = eq("_id", new ObjectId(pageId));
		return this.coll.find(filter).first();
	}

	/**
	 * 查询页数据
	 * @param taskId
	 * @return
	 */
	public List<SheetPage> findSheetPageByTaskId(String taskId) {
		Bson filter = eq("taskId", taskId);
		//	Bson project = include("_id", "lekeNo", "sheetId", "pageId", "img");
		return this.coll.find(filter).into(new ArrayList<>());
	}

	/**
	 * 根据ID查询页信息
	 * @param ids
	 * @return
	 */
	public List<SheetPage> findSheetPageByIds(List<String> ids) {
		Bson filter = in("_id", ids.stream().map(ObjectId::new).collect(toList()));
		Bson sorter = ascending("index");
		return this.coll.find(filter).sort(sorter).into(new ArrayList<>());
	}

	/**
	 * 查询任务的错误页码
	 * @param taskId
	 * @return
	 */
	public List<SheetPage> findErrorPagesByTaskId(String taskId) {
		Bson filter = and(eq("taskId", taskId), gt("errorNo", 0));
		Bson sorter = ascending("errorNo", "_id");
		return this.coll.find(filter).sort(sorter).into(new ArrayList<>());
	}

	/**
	 * 获取下一个乐号异常
	 * @param taskId
	 * @param id
	 * @return
	 */
	public SheetPage getNextLekeNoSheetPageById(String taskId, String id) {
		Bson filter = and(eq("taskId", taskId), gt("_id", new ObjectId(id)),
				eq("errorNo", SheetErr.UNKNOWN_LEKENO.code));
		Bson sorter = ascending("_id");
		return this.coll.find(filter).sort(sorter).first();
	}
}