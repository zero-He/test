/**
 * 
 */
package cn.strong.leke.monitor.mongo.appcenter.dao.impl;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.ne;
import static com.mongodb.client.model.Sorts.descending;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.data.mongo.MongoPageUtils;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.mongo.appcenter.dao.IPadSystemDao;
import cn.strong.leke.monitor.mongo.appcenter.model.PadSystem;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

/**
 * @author liulongbiao
 *
 */
@Repository
public class PadSystemDao implements IPadSystemDao {

	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<PadSystem> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("appcenter.padsystem", PadSystem.class);
	}

	@Override
	public void save(PadSystem sys) {
		if (sys.getTs() == null) {
			sys.setTs(new Date());
		}
		coll.updateOne(eq("_id", sys.getImei()), BsonUtils.toUpdateSetDoc(sys), new UpdateOptions().upsert(true));
	}

	@Override
	public PadSystem getByImei(String imei) {
		return coll.find(eq("_id", imei)).first();
	}

	@Override
	public List<PadSystem> findPadSystem(Page page, List<String> imeis, Boolean root,
			String version, String mac) {
		Bson filter = ne("_id", "");
		if(CollectionUtils.isNotEmpty(imeis)) {
			filter = and(filter,in("_id", imeis));
		}
		if(root!=null){
			filter = and(filter,eq("rooted", root));
		}
		if(StringUtils.isNotEmpty(version)){
			filter = and(filter,eq("version", version.trim()));
		}
		if(StringUtils.isNotEmpty(mac)){
			filter = and(filter,eq("mac", mac.trim()));
		}
		Bson sorter = descending("ts");
		return MongoPageUtils.find(coll, filter, sorter, page);
	}

}
