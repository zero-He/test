package cn.strong.leke.dwh.storage;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.T;

import cn.strong.leke.dwh.dao.mongo.SparkQueryDao;
import cn.strong.leke.dwh.dao.mongo.SparkResultDao;

public class MongoSparkRepoStorage implements SparkRepoStorage {

	@Resource
	private SparkQueryDao sparkQueryDao;
	@Resource
	private SparkResultDao sparkResultDao;

	/**
	 * 查询条件入库
	 * @param query
	 * @return
	 */
	public String storageQuery(Serializable query) {
		return this.sparkQueryDao.insertQuery(query);
	}

	/**
	 * 查询Spark结果对象
	 * @param id
	 * @param resultClass
	 * @return
	 */
	public <T extends Serializable> T getResult(String id, Class<T> resultClass) {
		return this.sparkResultDao.getResult(id, resultClass);
	}


}
