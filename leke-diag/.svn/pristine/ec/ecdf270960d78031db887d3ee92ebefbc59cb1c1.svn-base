package cn.strong.leke.diag.dao.homework.mongo;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Indexes.ascending;
import static com.mongodb.client.model.Sorts.descending;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cn.strong.leke.diag.model.report.ReportCycle;

@Repository
public class ReportCycleDao implements InitializingBean {

	private static final String COLL_NAME = "ReportCycle";

	@Resource(name = "homeworkDb")
	private MongoDatabase db;
	private MongoCollection<ReportCycle> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, ReportCycle.class);
	}

	/**
	 * 保存报告周期信息
	 * @param reportCycles
	 */
	public void insertReportCycle(List<ReportCycle> reportCycles) {
		this.coll.insertMany(reportCycles);
	}

	/**
	 * 获取周期信息
	 * @param id 周期ID
	 * @return
	 */
	public ReportCycle getReportCycleById(Integer id) {
		Bson filter = eq("_id", id);
		return this.coll.find(filter).first();
	}

	/**
	 * 获取指定时间所在的周周期
	 * @param date
	 * @return
	 */
	public ReportCycle getWeekReportCycleByDate(Date date) {
		Bson filter = and(eq("type", 1), lt("start", date), gt("end", date));
		return this.coll.find(filter).first();
	}

	/**
	 * 获取指定时间所在的月周期
	 * @param date
	 * @return
	 */
	public ReportCycle getMonthReportCycleByDate(Date date) {
		Bson filter = and(eq("type", 2), lt("start", date), gt("end", date));
		return this.coll.find(filter).first();
	}

	/**
	 * 获取最近的周期
	 * @param type 周期类型
	 * @param date 当前时间
	 * @param limit 获取数量
	 * @return
	 */
	public List<ReportCycle> findNearestReportCycles(Integer type, Date date, int limit) {
		Bson filter = and(eq("type", type), lt("start", date));
		Bson sorter = descending("_id");
		return this.coll.find(filter).sort(sorter).limit(limit).into(new ArrayList<>());
	}

	/**
	 * 查询一段时间内某个类型的周期列表
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param type 周期类型
	 * @return
	 */
	public List<ReportCycle> findReportCycleByTime(Date start, Date end, Integer type) {
		Bson filter = and(lt("start", end), gt("end", start), eq("type", type));
		Bson sorter = ascending("_id");
		return this.coll.find(filter).sort(sorter).into(new ArrayList<>());
	}

	/**
	 * 获取当前周期的前一个周期
	 * @param id 当前周期ID
	 * @param type 周期类型
	 * @return
	 */
	public ReportCycle getPrevReportCycle(Integer id, Integer type) {
		Bson filter = and(lt("_id", id), eq("type", type));
		Bson sorter = descending("_id");
		return this.coll.find(filter).sort(sorter).first();
	}

	/**
	 * 获取从当前周期向前的6个周期（包含当前周期），不足6个按实际返回，倒序排列
	 * @param reportCycle 当前周期
	 * @return
	 */
	public List<ReportCycle> findNear6ReportCycle(ReportCycle reportCycle) {
		Bson filter = and(lte("_id", reportCycle.getId()), eq("type", reportCycle.getType()));
		Bson sorter = descending("_id");
		return this.coll.find(filter).sort(sorter).limit(6).into(new ArrayList<>());
	}
}