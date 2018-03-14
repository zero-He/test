package cn.strong.leke.monitor.mongo.resource;

import java.text.ParseException;
import java.util.List;

import org.bson.Document;

import cn.strong.leke.monitor.mongo.resource.model.query.ResourceTopQuery;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceTopSta;
import cn.strong.leke.model.monitor.ResourceUsedDetail;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceUsedQuery;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceUsedSta;
import cn.strong.leke.monitor.mongo.resource.model.query.SchoolResourceUsedSta;
import cn.strong.leke.monitor.mongo.resource.model.query.SchoolUsedTrendQuery;
import cn.strong.leke.monitor.mongo.resource.model.query.SchoolUsedTrendSta;

public interface IResourceUsedDao {
	
	public int getResourceUsedNum(ResourceUsedQuery query);//使用资源数
	
	public int getResourceUsedCount(ResourceUsedQuery query);//使用资源量
	
	public List<SchoolResourceUsedSta> getSchoolResourceUsedSta(ResourceUsedQuery query); //各个学校资源使用明细
	
	public List<Document> getResourceBySchoolId(ResourceUsedQuery query,Long schoolId);
	
	public List<ResourceUsedSta> getResourceGroupByDate(ResourceUsedQuery query);
	
	void save(List<ResourceUsedDetail> details);

	public List<ResourceTopSta> getResIdAndUsednum(ResourceTopQuery query, Page page);

	public List<SchoolUsedTrendSta> getSchoolUsedNum(SchoolUsedTrendQuery query) throws ParseException;

}
