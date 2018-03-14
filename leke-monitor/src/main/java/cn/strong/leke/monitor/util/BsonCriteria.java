/**
 * 
 */
package cn.strong.leke.monitor.util;

import java.util.Collection;
import java.util.Map;

import org.bson.Document;

/**
 * BSON 查询条件
 * 
 * @author liulongbiao
 *
 */
public class BsonCriteria extends Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6278032065349029409L;

	public BsonCriteria() {
		super();
	}

	public BsonCriteria(String key, Object value) {
		super(key, value);
	}

	public BsonCriteria(Map<String, Object> map) {
		super(map);
	}

	/**
	 * 添加条件
	 * 
	 * @param field
	 *            字段
	 * @param op
	 *            操作符
	 * @param value
	 *            值
	 * @return
	 */
	public <T> BsonCriteria appendCriteria(String field, String op, T value) {
		Object old = get(field);
		BsonCriteria cond = null;
		if (old instanceof BsonCriteria) {
			cond = (BsonCriteria) old;
		} else {
			cond = new BsonCriteria();
			if (old != null) {
				cond.append("$eq", old);
			}
		}
		cond.put(op, value);
		this.put(field, cond);
		return this;
	}

	public <T> BsonCriteria eq(String field, T value) {
		Object old = get(field);
		if (old == null) {
			append(field, value);
			return this;
		}
		return appendCriteria(field, "$eq", value);
	}

	public <T> BsonCriteria gt(String field, T value) {
		return appendCriteria(field, "$gt", value);
	}

	public <T> BsonCriteria gte(String field, T value) {
		return appendCriteria(field, "$gte", value);
	}

	public <T> BsonCriteria lt(String field, T value) {
		return appendCriteria(field, "$lt", value);
	}

	public <T> BsonCriteria lte(String field, T value) {
		return appendCriteria(field, "$lte", value);
	}

	public <T> BsonCriteria ne(String field, T value) {
		return appendCriteria(field, "$ne", value);
	}

	public <T> BsonCriteria in(String field, Collection<T> value) {
		return appendCriteria(field, "$in", value);
	}

	public <T> BsonCriteria nin(String field, Collection<T> value) {
		return appendCriteria(field, "$nin", value);
	}

	/**
	 * 相等或在指定列表内
	 * 
	 * @param field
	 * @param candidates
	 */
	public <T> BsonCriteria eqOrIn(String field, Collection<T> candidates) {
		if (candidates == null || candidates.isEmpty()) {
			return this;
		}
		int size = candidates.size();
		if (size == 1) {
			return eq(field, candidates.iterator().next());
		} else {
			return in(field, candidates);
		}
	}
}
